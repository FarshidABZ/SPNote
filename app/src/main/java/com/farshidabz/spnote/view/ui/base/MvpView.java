package com.farshidabz.spnote.view.ui.base;

import androidx.annotation.StringRes;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public interface MvpView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);
}
