package com.farshidabz.spnote.view.ui.note;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.interactor.dbinteractor.DatabaseInteractor;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.model.PopupModel;
import com.farshidabz.spnote.util.widgets.DrawingView;
import com.farshidabz.spnote.util.widgets.popupwindow.CustomPopupWindow;
import com.farshidabz.spnote.view.ui.base.BasePresenter;
import com.farshidabz.spnote.view.ui.note.drawstyle.DrawingStyleBottomSheet;
import com.farshidabz.spnote.view.ui.note.paperstyle.PaperStyleBottomSheet;
import com.farshidabz.spnote.view.ui.note.savenote.SaveNoteDialog;
import com.farshidabz.spnote.view.ui.note.textstyle.TextStyleBottomSheet;
import com.farshidabz.spnote.view.ui.warningdialog.WarningDialog;

import java.util.ArrayList;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public class NotePresenter<V extends NoteMvpView> extends BasePresenter<V>
        implements NoteMvpPresenter<V> {
    private Context context;
    private DrawingView drawingView;
    private NoteInputEditTextHandler noteInputEditTextHandler;
    private FragmentManager fragmentManager;
    private EditText writingInputEditText;
    private NoteModel noteModel;
    private int folderId;

    DatabaseInteractor interactor;
    private CustomPopupWindow customPopupWindow;

    public NotePresenter(Context context,
                         FragmentManager supportFragmentManager,
                         DrawingView drawingView,
                         EditText writingInputEditText) {
        this.context = context;
        this.fragmentManager = supportFragmentManager;
        this.drawingView = drawingView;
        this.writingInputEditText = writingInputEditText;

        noteInputEditTextHandler = new NoteInputEditTextHandler(writingInputEditText);
        interactor = new DatabaseInteractor(context);
    }

    @Override
    public void getNote(int noteId, int folderId) {
        this.folderId = folderId;
        noteModel = interactor.getNote(noteId);
        if (noteModel == null)
            return;

        writingInputEditText.setText(noteModel.getContent());
        drawingView.setBitmap(noteModel.getImage());

        noteInputEditTextHandler.setPaperStyle(0,
                context.getResources()
                        .getIdentifier(noteModel.getBackground(),
                                "drawable",
                                context.getPackageName()));

        getMvpView().onNoteReceived(noteModel);
    }

    @Override
    public void onTextStyleClicked() {
        int startPos = writingInputEditText.getSelectionStart();
        int endPos = writingInputEditText.getSelectionEnd();

        if (startPos == endPos) {
            getMvpView().onError(context.getString(R.string.select_text));
            return;
        }

        noteInputEditTextHandler.setSelectionPos(startPos, endPos);

        final TextStyleBottomSheet textStyleBottomSheet = TextStyleBottomSheet.newInstance(
                noteInputEditTextHandler.getTextStyle(),
                noteInputEditTextHandler.getTextColorId());
        textStyleBottomSheet.setOnTextStyleChangeListener((textStyle, colorId, formatColorId) -> {
            noteInputEditTextHandler.setTextStyle(textStyle, colorId);
            getMvpView().setTextStyleColorBackground(formatColorId);
        });
        textStyleBottomSheet.show(fragmentManager, textStyleBottomSheet.getTag());
    }

    @Override
    public void onInputTypeSwitcherClicked(View imgInputTypeSwitcher) {
        if (customPopupWindow == null) {
            initCustomPopupWindow(imgInputTypeSwitcher);
        }
        if (customPopupWindow.isPopupWindowShowing()) {
            customPopupWindow.dismiss();
        } else {
            customPopupWindow.show();
        }
    }

    private void initCustomPopupWindow(View imgInputTypeSwitcher) {
        ArrayList<PopupModel> popupModels = new ArrayList<>();
        popupModels.add(new PopupModel(context.getString(R.string.type_mode)));
        popupModels.add(new PopupModel(context.getString(R.string.drawing)));

        customPopupWindow = new CustomPopupWindow(context, popupModels, imgInputTypeSwitcher);

        customPopupWindow.setOnPopupItemClickListener((position, popupModel) -> {
            if (position == 0) {
                noteInputEditTextHandler.setInputTypeMode(true, drawingView);
                getMvpView().setInputTypeModeText(context.getString(R.string.type_mode));
                getMvpView().setEraserVisibility(View.GONE);
                getMvpView().setDrawingPenVisibility(View.GONE);
                getMvpView().setTextStyleVisibility(View.VISIBLE);
            } else {
                noteInputEditTextHandler.setInputTypeMode(false, drawingView);
                getMvpView().setInputTypeModeText(context.getString(R.string.drawing));
                getMvpView().setEraserVisibility(View.VISIBLE);
                getMvpView().setDrawingPenVisibility(View.VISIBLE);
                getMvpView().setTextStyleVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackClicked(boolean saveChanges, int noteId) {
        // if !isAnythingChanged there are no changes and there isn't force to show warning/save dialogs
        if (!isAnythingChanged()) {
            getMvpView().finishActivity();
            return;
        }

        if (!saveChanges) {
            showDiscardDialog();
        } else {
            if (noteId == -1) {
                showSaveNewNoteDialog();
            } else {
                showUpdateNoteDialog();
            }
        }
    }

    private boolean isAnythingChanged() {
        return isContentChanged() || isDrawingChanged() || isPaperStyleChanged();
    }

    private boolean isPaperStyleChanged() {
        if (noteModel == null) {
            return false;
        } else if (context
                .getResources()
                .getResourceEntryName(noteInputEditTextHandler
                        .getBackgroundResId())
                .equals(noteModel.getBackground())) {
            return false;
        }

        return true;
    }

    private boolean isContentChanged() {
        if (noteModel == null && TextUtils.isEmpty(writingInputEditText.getText().toString())) {
            return false;
        }
        if (noteModel != null && !TextUtils.isEmpty(writingInputEditText.getText().toString())) {
            if (writingInputEditText.getText().toString().equals(noteModel.getContent().toString())) {
                return false;
            }
        }
        if (noteModel != null && TextUtils.isEmpty(writingInputEditText.getText().toString())) {
            if (writingInputEditText.getText().toString().equals(noteModel.getContent().toString())) {
                return false;
            }
        }
        return true;
    }

    private boolean isDrawingChanged() {
        return drawingView.isSomethingDrawn();
    }

    @Override
    public void onEraserClicked() {
        drawingView.setErase(true);
    }

    @Override
    public void onDrawClicked() {
        drawingView.setErase(false);
    }

    @Override
    public void onPaperStyleClicked() {
        final PaperStyleBottomSheet paperStyleBottomSheet =
                PaperStyleBottomSheet.newInstance(noteInputEditTextHandler.getBackgroundResId(),
                        noteInputEditTextHandler.getPaperStyleId());

        paperStyleBottomSheet.setOnPaperStyleSelectedListener((paperStyleId, paperId) ->
                noteInputEditTextHandler.setPaperStyle(paperStyleId, paperId));

        paperStyleBottomSheet.show(fragmentManager, paperStyleBottomSheet.getTag());
    }

    @Override
    public void onDrawingStyleClicked() {
        DrawingStyleBottomSheet drawingStyleBottomSheet =
                DrawingStyleBottomSheet.newInstance(drawingView.getBrushSize(),
                        drawingView.getColorId());

        drawingStyleBottomSheet.setOnDrawingStyleChangeListener((brushSize, colorId) -> {
            drawingView.setBrushSize(brushSize);
            drawingView.setPaintColor(colorId);
        });

        drawingStyleBottomSheet.show(fragmentManager, drawingStyleBottomSheet.getTag());
    }

    @Override
    public void hideKeyboard() {
        if (writingInputEditText != null && writingInputEditText.isFocused()) {
            try {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(writingInputEditText.getApplicationWindowToken(), 0);
            } catch (RuntimeException e) {
                //ignore
            }
        }
    }

    private void createNewNote(String noteName) {
        Spannable spannable = writingInputEditText.getText();
        NoteModel noteModel = new NoteModel();
        noteModel.setTitle(noteName);
        noteModel.setAddress("application directory");
        noteModel.setContent(spannable);
        noteModel.setBackground(context.getResources().
                getResourceEntryName(noteInputEditTextHandler.getBackgroundResId()));
        noteModel.setImage(drawingView.getCanvasBitmap());
        noteModel.setFolder_id(folderId);

        if (interactor.createNote(noteModel)) {
            getMvpView().finishActivity();
        }
    }

    private void updateNote() {
        Spannable spannable = writingInputEditText.getText();
        noteModel.setContent(spannable);
        noteModel.setImage(drawingView.getCanvasBitmap());

        noteModel.setBackground(context.getResources().
                getResourceEntryName(noteInputEditTextHandler.getBackgroundResId()));

        noteModel.setFolder_id(folderId);

        if (interactor.updateNote(noteModel)) {
            getMvpView().finishActivity();
        }
    }

    private void showUpdateNoteDialog() {
        WarningDialog warningDialog = new WarningDialog(context,
                context.getString(R.string.confirm),
                context.getString(R.string.do_you_want_to_save_changes));
        warningDialog.setOnWarningDialogDismissListener(saveChanges -> {
            if (saveChanges) {
                updateNote();
            } else {
                showDiscardDialog();
            }
        });
        warningDialog.show();
    }

    private void showSaveNewNoteDialog() {
        SaveNoteDialog saveNoteDialog = new SaveNoteDialog(context);
        saveNoteDialog.setOnDialogDismissListener((save, noteName) -> {
            if (save) {
                createNewNote(noteName);
            } else {
                showDiscardDialog();
            }
        });
        saveNoteDialog.show();
    }

    private void showDiscardDialog() {
        WarningDialog warningDialog = new WarningDialog(context,
                context.getString(R.string.discard),
                context.getString(R.string.discard_message));

        warningDialog.setOnWarningDialogDismissListener(state -> {
            if (state) {
                getMvpView().finishActivity();
            }
        });
        warningDialog.show();
    }
}
