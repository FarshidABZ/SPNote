package com.farshidabz.spnote.view.ui.note;

import androidx.annotation.DrawableRes;

import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.view.ui.base.MvpView;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public interface NoteMvpView extends MvpView {
    void onNoteReceived(NoteModel noteModel);

    void setEraserVisibility(int visibility);

    void setTextStyleVisibility(int visibility);

    void setDrawingPenVisibility(int visibility);

    void finishActivity();

    void setInputTypeModeText(String text);

    void setTextStyleColorBackground(@DrawableRes int drawable);

    void setDrawingPenColorBackground(@DrawableRes int drawable);

}
