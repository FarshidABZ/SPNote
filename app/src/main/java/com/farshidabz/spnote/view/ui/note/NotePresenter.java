package com.farshidabz.spnote.view.ui.note;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.interactor.dbhandler.DbHandler;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.model.PopupModel;
import com.farshidabz.spnote.util.widgets.DrawingView;
import com.farshidabz.spnote.util.widgets.popupwindow.CustomPopupWindow;
import com.farshidabz.spnote.view.ui.base.BasePresenter;
import com.farshidabz.spnote.view.ui.note.discard.DiscardDialog;
import com.farshidabz.spnote.view.ui.note.drawstyle.DrawingStyleBottomSheet;
import com.farshidabz.spnote.view.ui.note.paperstyle.PaperStyleBottomSheet;
import com.farshidabz.spnote.view.ui.note.savenote.SaveNoteDialog;
import com.farshidabz.spnote.view.ui.note.textstyle.TextStyleBottomSheet;
import com.farshidabz.spnote.view.ui.note.updatenote.UpdateNoteDialog;

import java.util.ArrayList;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public class NotePresenter<V extends NoteMvpView> extends BasePresenter<V>
        implements NoteMvpPresenter<V> {

    DbHandler dbHandler;

    private Context context;
    private DrawingView drawingView;
    private NoteInputEditTextHandler noteInputEditTextHandler;
    private FragmentManager fragmentManager;
    private EditText writingInputEditText;
    private NoteModel noteModel;

    public NotePresenter(Context context,
                         FragmentManager supportFragmentManager,
                         DrawingView drawingView,
                         EditText writingInputEditText) {
        this.context = context;
        this.fragmentManager = supportFragmentManager;
        this.drawingView = drawingView;
        this.writingInputEditText = writingInputEditText;

        noteInputEditTextHandler = new NoteInputEditTextHandler(writingInputEditText);
        dbHandler = new DbHandler(context);
    }

    /**
     * DONE
     */
    @Override
    public void getNote(int noteId) {
        noteModel = dbHandler.getNotesTable().get(noteId);
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

    /**
     * DONE
     */
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

    /**
     * DONE
     */
    @Override
    public void onInputTypeSwitcherClicked(ImageView imgInputTypeSwitcher) {
        ArrayList<PopupModel> popupModels = new ArrayList<>();
        popupModels.add(new PopupModel(context.getString(R.string.type_mode)));
        popupModels.add(new PopupModel(context.getString(R.string.drawing)));

        CustomPopupWindow customPopupWindow = new CustomPopupWindow(context, popupModels, imgInputTypeSwitcher);

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

        customPopupWindow.show();
    }

    /**
     * SHOW SAVE DIALOG
     */
    @Override
    public void onBackClicked(boolean saveChanges, int noteId) {
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

    private void showUpdateNoteDialog() {
        UpdateNoteDialog updateNoteDialog = new UpdateNoteDialog(context);
        updateNoteDialog.setOnUpdateNoteDismissListener(saveChanges -> {
            if (saveChanges) {
                updateNote();
            } else {
                showDiscardDialog();
            }
        });
        updateNoteDialog.show();
    }

    private void showSaveNewNoteDialog() {
        SaveNoteDialog saveNoteDialog = new SaveNoteDialog(context);
        saveNoteDialog.setOnSaveNoteDismissListener((save, noteName) -> {
            if (save) {
                createNewNote(noteName);
            } else {
                showDiscardDialog();
            }
        });
        saveNoteDialog.show();
    }

    private void showDiscardDialog() {
        DiscardDialog discardDialog = new DiscardDialog(context);
        discardDialog.setOnDiscardDismissListener(discard -> {
            if (discard) {
                getMvpView().finishActivity();
            }
        });
        discardDialog.show();
    }

    /**
     * DONE
     */
    @Override
    public void onEraserClicked() {
        drawingView.setErase(true);
    }

    /**
     * DONE
     */
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

    private void createNewNote(String noteName) {
        Spannable spannable = writingInputEditText.getText();
        NoteModel noteModel = new NoteModel();
        noteModel.setTitle(noteName);
        noteModel.setAddress("application directory");
        noteModel.setContent(spannable);
        noteModel.setBackground(context.getResources().
                getResourceEntryName(noteInputEditTextHandler.getBackgroundResId()));
        noteModel.setImage(drawingView.getCanvasBitmap());
        noteModel.setFolder_id(-1);

        if (dbHandler.getNotesTable().create(noteModel)) {
            getMvpView().finishActivity();
        }
    }

    private void updateNote() {
        Spannable spannable = writingInputEditText.getText();
        noteModel.setContent(spannable);
        noteModel.setImage(drawingView.getCanvasBitmap());

        noteModel.setBackground(context.getResources().
                getResourceEntryName(noteInputEditTextHandler.getBackgroundResId()));

        if (dbHandler.getNotesTable().update(noteModel)) {
            getMvpView().finishActivity();
        }
    }
}
