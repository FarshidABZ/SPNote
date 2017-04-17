package com.farshidabz.spnote.view.views.mainpage;

import com.farshidabz.spnote.model.UserData;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public interface MainPageView {
    void showUserData(UserData userData);

    void showEmptyState();
}
