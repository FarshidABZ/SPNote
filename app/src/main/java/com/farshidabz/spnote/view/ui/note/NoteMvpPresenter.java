package com.farshidabz.spnote.view.ui.note;

import android.widget.ImageView;

import com.farshidabz.spnote.view.ui.base.MvpPresenter;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public interface NoteMvpPresenter<V extends NoteMvpView> extends MvpPresenter<V> {
    void getNote(int noteId);

    void onTextStyleClicked();

    void onInputTypeSwitcherClicked(ImageView imgInputTypeSwitcher);

    void onBackClicked(boolean saveChanges, int noteId);

    void onEraserClicked();

    void onDrawClicked();

    void onPaperStyleClicked();

    void onDrawingStyleClicked();
}
