package com.farshidabz.supernote.view.ui.note;

import android.widget.ImageView;

import com.farshidabz.supernote.view.ui.base.MvpPresenter;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public interface NoteMvpPresenter<V extends NoteMvpView> extends MvpPresenter<V> {
    void onTextStyleClicked();

    void onInputTypeSwitcherClicked(ImageView imgInputTypeSwitcher);

    void onBackClicked(boolean saveChanges);

    void onEraserClicked();

    void onPaperStyleClicked();

    void onDrawingStyleClicked();
}
