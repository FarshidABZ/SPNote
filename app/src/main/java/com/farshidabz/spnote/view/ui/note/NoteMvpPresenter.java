package com.farshidabz.spnote.view.ui.note;

import android.view.View;

import com.farshidabz.spnote.view.ui.base.MvpPresenter;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public interface NoteMvpPresenter<V extends NoteMvpView> extends MvpPresenter<V> {
    void getNote(int noteId, int folderId);

    void onTextStyleClicked();

    void onInputTypeSwitcherClicked(View imgInputTypeSwitcher);

    void onBackClicked(boolean saveChanges, int noteId);

    void onEraserClicked();

    void onDrawClicked();

    void onPaperStyleClicked();

    void onDrawingStyleClicked();

    void hideKeyboard();
}
