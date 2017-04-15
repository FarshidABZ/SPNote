package com.farshidabz.supernote.view.ui.note;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.interactor.dbhandler.DbHandler;
import com.farshidabz.supernote.model.NoteModel;
import com.farshidabz.supernote.model.PopupModel;
import com.farshidabz.supernote.util.widgets.DrawingView;
import com.farshidabz.supernote.util.widgets.popupwindow.CustomPopupWindow;
import com.farshidabz.supernote.view.ui.base.BasePresenter;
import com.farshidabz.supernote.view.ui.note.paperstyle.PaperStyleBottomSheet;
import com.farshidabz.supernote.view.ui.note.textstyle.TextStyleBottomSheet;

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

    @Override
    public void onTextStyleClicked() {
        int startPos = writingInputEditText.getSelectionStart();
        int endPos = writingInputEditText.getSelectionEnd();

        if(startPos == endPos){
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

    @Override
    public void onBackClicked(boolean saveChanges) {
        if (!saveChanges)
            getMvpView().finishActivity();
        else {
            NoteModel noteModel = new NoteModel();
            noteModel.setTitle("title");
            noteModel.setAddress("/root/direcory/");
            noteModel.setContent(writingInputEditText.getText().toString());
            noteModel.setBackground(noteInputEditTextHandler.getBackgroundResId());
            noteModel.setImage(drawingView.getCanvasBitmap());
            noteModel.setFolder_id(-1);

            if (dbHandler.getNotesTable().create(noteModel)) {
                getMvpView().finishActivity();
            }
        }
    }

    @Override
    public void onEraserClicked() {

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

    }
}
