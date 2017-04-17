package com.farshidabz.spnote.view.ui.mainpage;

import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.model.UserData;
import com.farshidabz.spnote.view.ui.base.MvpView;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public interface MainPageMvpView extends MvpView {
    void showEmptyState();

    void finishActivity();

    void showUserData(UserData userData);

    void noteNameChanged(int position, NoteModel noteModel);

    void noteRemoved(NoteModel noteModel);
}
