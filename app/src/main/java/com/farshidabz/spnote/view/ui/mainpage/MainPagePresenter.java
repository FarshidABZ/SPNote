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
import com.farshidabz.spnote.view.ui.note.savenote.SaveNoteDialog;
import com.farshidabz.spnote.view.ui.warningdialog.WarningDialog;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public class MainPagePresenter<V extends MainPageMvpView> extends BasePresenter<V>
        implements MainPageMvpPresenter<V> {
    Context context;
    private FragmentManager fragmentManager;
    private DatabaseInteractor interactor;

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

    }

    @Override
    public void onNoteClicked(NoteModel noteModel) {

    }

    @Override
    public void onFolderClicked(FolderModel folderModel) {

    }
}
