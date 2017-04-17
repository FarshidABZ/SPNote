package com.farshidabz.spnote.presenter.impls;

import android.content.Context;

import com.farshidabz.spnote.interactor.recoveryandbackup.RecoveryAndBackupPresenterInteractor;
import com.farshidabz.spnote.model.UserData;
import com.farshidabz.spnote.presenter.presenter.RecoveryAndBackupPresenter;
import com.farshidabz.spnote.view.views.mainpage.MainPageView;

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
