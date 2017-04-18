package com.farshidabz.spnote.view.ui.movetofolder;

import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.view.ui.base.MvpPresenter;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public interface MoveToFolderMvpPresenter<V extends MoveToFolderMvpView> extends MvpPresenter<V> {
    void getFolders();
    void onFolderCLicked(FolderModel folderModel, int noteId);
    void onNewFolderClicked();
}
