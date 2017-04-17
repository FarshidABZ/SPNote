package com.farshidabz.spnote.view.ui.mainpage;


import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.view.ui.base.MvpPresenter;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public interface MainPageMvpPresenter<V extends MainPageMvpView> extends MvpPresenter<V> {
    void getUserData();
    void onNoteLongClicked(int position, NoteModel noteModel);
    void onFolderLongClicked(FolderModel folderModel);
    void onNoteClicked(NoteModel noteModel);
    void onFolderClicked(FolderModel folderModel);
}
