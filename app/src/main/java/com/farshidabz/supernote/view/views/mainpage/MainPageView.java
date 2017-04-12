package com.farshidabz.supernote.view.views.mainpage;

import com.farshidabz.supernote.model.UserData;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public interface MainPageView {
    void showUserData(UserData userData);

    void showEmptyState();
}
