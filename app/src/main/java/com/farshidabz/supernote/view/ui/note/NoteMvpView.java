package com.farshidabz.supernote.view.ui.note;

import android.support.annotation.DrawableRes;

import com.farshidabz.supernote.view.ui.base.MvpView;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public interface NoteMvpView extends MvpView {
    void setEraserVisibility(int visibility);

    void setTextStyleVisibility(int visibility);

    void setDrawingPenVisibility(int visibility);

    void finishActivity();

    void setInputTypeModeText(String text);

    void setTextStyleColorBackground(@DrawableRes int drawable);

    void setDrawingPenColorBackground(@DrawableRes int drawable);

}
