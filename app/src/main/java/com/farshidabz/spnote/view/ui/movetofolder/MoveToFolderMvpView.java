package com.farshidabz.spnote.view.ui.movetofolder;

import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.view.ui.base.MvpView;

import java.util.List;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public interface MoveToFolderMvpView extends MvpView {
    void showEmptyState();

    void finishActivity();

    void showFolders(List<FolderModel> folders);
}
