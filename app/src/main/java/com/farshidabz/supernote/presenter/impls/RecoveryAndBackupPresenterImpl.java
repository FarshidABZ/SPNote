package com.farshidabz.supernote.presenter.impls;

import android.content.Context;

import com.farshidabz.supernote.interactor.recoveryandbackup.RecoveryAndBackupPresenterInteractor;
import com.farshidabz.supernote.model.UserData;
import com.farshidabz.supernote.presenter.presenter.RecoveryAndBackupPresenter;
import com.farshidabz.supernote.view.views.mainpage.MainPageView;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class RecoveryAndBackupPresenterImpl implements RecoveryAndBackupPresenter {

    Context context;
    MainPageView mainPageView;

    public RecoveryAndBackupPresenterImpl(Context context, MainPageView mainPageView) {
        this.context = context;
        this.mainPageView = mainPageView;
    }

    @Override
    public void getUserData() {
        RecoveryAndBackupPresenterInteractor interactor = new RecoveryAndBackupPresenterInteractor(context);
        UserData userData = interactor.getUserData();

        if(userData.isEmpty()){
            mainPageView.showEmptyState();
        }
        else {
            mainPageView.showUserData(userData);
        }
    }
}
