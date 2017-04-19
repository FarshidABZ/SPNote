package com.farshidabz.spnote.view.ui.mainpage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.flowcontroller.ActivityFactory;
import com.farshidabz.spnote.interactor.dbinteractor.DatabaseInteractor;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.model.UserData;
import com.farshidabz.spnote.view.ui.base.BasePresenter;
import com.farshidabz.spnote.view.ui.mainpage.moreaction.ActionType;
import com.farshidabz.spnote.view.ui.mainpage.moreaction.MoreActionBottomSheet;
import com.farshidabz.spnote.view.ui.movetofolder.MoveToFolderActivity;
import com.farshidabz.spnote.view.ui.note.NoteActivity;
import com.farshidabz.spnote.view.ui.note.savenote.SaveNoteDialog;
import com.farshidabz.spnote.view.ui.warningdialog.WarningDialog;

import java.util.List;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public class MainPagePresenter<V extends MainPageMvpView> extends BasePresenter<V>
        implements MainPageMvpPresenter<V> {
    Context context;
    private FragmentManager fragmentManager;
    private DatabaseInteractor interactor;
    private int currentFolderId = -1;

    public MainPagePresenter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;

        interactor = new DatabaseInteractor(context);
    }

    @Override
    public void getUserData() {
        UserData userData = interactor.getUserData();

        if (userData.isEmpty()) {
            getMvpView().showEmptyState();
        } else {
            getMvpView().showUserData(userData);
        }
    }

    @Override
    public void onNoteLongClicked(int position, NoteModel noteModel) {
        MoreActionBottomSheet moreActionBottomSheet = MoreActionBottomSheet.newInstance(noteModel.getTitle());
        moreActionBottomSheet.setOnActionClickListener(action -> {
            switch (action) {
                case ActionType.MOVE:
                    showMovingNoteView(noteModel);
                    break;
                case ActionType.REMOVE:
                    showWarningDialog(context.getString(R.string.remove),
                            context.getString(R.string.remove_note),
                            noteModel);
                    break;
                case ActionType.RENAME:
                    showRenameDialog(position, noteModel);
                    break;
            }
        });
        moreActionBottomSheet.show(fragmentManager, moreActionBottomSheet.getTag());
    }

    private void showMovingNoteView(NoteModel noteModel) {
        Bundle bundle = new Bundle();
        bundle.putInt("noteId", noteModel.getId());
        bundle.putString("noteTitle", noteModel.getTitle());

        ActivityFactory.startActivity(context, MoveToFolderActivity.class.getSimpleName(), bundle);
    }

    private void showWarningDialog(String title, String message, NoteModel noteModel) {
        WarningDialog warningDialog = new WarningDialog(context, title, message);
        warningDialog.setOnWarningDialogDismissListener(state -> {
            if (state) {
                if (interactor.removeNote(noteModel)) {
                    getMvpView().noteRemoved(noteModel);
                }
            }
        });
        warningDialog.show();
    }

    private void showRenameDialog(int position, NoteModel noteModel) {
        SaveNoteDialog saveNoteDialog = new SaveNoteDialog(context);
        saveNoteDialog.setOnDialogDismissListener((save, noteName) -> {
            if (save) {
                noteModel.setTitle(noteName);
                if (interactor.updateNote(noteModel)) {
                    getMvpView().noteNameChanged(position, noteModel);
                }
            }
        });
        saveNoteDialog.show();
    }

    @Override
    public void onFolderLongClicked(FolderModel folderModel) {
        // TODO: 4/19/2017 fixme
    }

    @Override
    public void onNoteClicked(NoteModel noteModel) {
        Bundle bundle = new Bundle();
        bundle.putInt("noteId", noteModel.getId());
        bundle.putInt("folderId", noteModel.getFolder_id());

        ActivityFactory.startActivity(context, NoteActivity.class.getSimpleName(), bundle);
    }

    @Override
    public void onFolderClicked(FolderModel folderModel) {
        currentFolderId = folderModel.getId();
        List<NoteModel> noteModels = interactor.getFolderContent(folderModel);
        if (noteModels == null || noteModels.size() == 0) {
            getMvpView().showEmptyState();
        } else {
            UserData userData = new UserData();
            userData.setNoteModel(noteModels);
            getMvpView().showUserData(userData);
        }
    }

    @Override
    public void onBackPressed() {
        //check current folder to return to parent folder or exit from app
        if (currentFolderId > 0) {
            getUserData();
            currentFolderId = -1;
        } else {
            getMvpView().finishActivity();
        }
    }

    @Override
    public void onNewNoteClicked() {
        // put folderId to bundle to save note in a right folder
        Bundle bundle = new Bundle();
        bundle.putInt("folderId", currentFolderId);
        ActivityFactory.startActivity(context, NoteActivity.class.getSimpleName(), bundle);
    }
}
