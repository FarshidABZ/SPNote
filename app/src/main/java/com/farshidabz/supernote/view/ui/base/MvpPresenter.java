package com.farshidabz.supernote.view.ui.base;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public interface MvpPresenter<V extends MvpView>{
    void onAttach(V mvpView);
    void onDetach();
}
