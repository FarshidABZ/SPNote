package com.farshidabz.spnote.view.ui.movetofolder;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.farshidabz.spnote.interactor.dbinteractor.DatabaseInteractor;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.view.ui.base.BasePresenter;
import com.farshidabz.spnote.view.ui.movetofolder.newfolder.CreateNewFolderDialog;

import java.util.List;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public class MoveToFolderPresenter<V extends MoveToFolderMvpView> extends BasePresenter<V>
        implements MoveToFolderMvpPresenter<V> {
    Context context;
    private FragmentManager fragmentManager;
    private DatabaseInteractor interactor;

    public MoveToFolderPresenter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;

        interactor = new DatabaseInteractor(context);
    }

    @Override
    public void getFolders() {
        List<FolderModel> folderModels = interactor.getFolders();
        if (folderModels == null || folderModels.size() == 0) {
            getMvpView().showEmptyState();
        } else {
            getMvpView().showFolders(folderModels);
        }
    }

    @Override
    public void onFolderCLicked(FolderModel folderModel, int noteId) {
        NoteModel noteModel = interactor.getNote(noteId);
        noteModel.setFolder_id(folderModel.getId());

        if (interactor.updateNote(noteModel)) {
            getMvpView().finishActivity();
        }
    }

    @Override
    public void onNewFolderClicked() {
        CreateNewFolderDialog createNewFolderDialog = new CreateNewFolderDialog(context);
        createNewFolderDialog.setOnDialogDismissListener((save, name) -> {
            if (save) {
                FolderModel folderModel = new FolderModel();
                folderModel.setTitle(name);
                folderModel.setAddress("/appDirectory");
                if (interactor.createNewFolder(folderModel)) {
                    getFolders();
                }
            }
        });
        createNewFolderDialog.show();
    }
}
