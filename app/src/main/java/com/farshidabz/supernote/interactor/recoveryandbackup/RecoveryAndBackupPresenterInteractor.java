package com.farshidabz.supernote.interactor.recoveryandbackup;

import android.content.Context;

import com.farshidabz.supernote.interactor.dbhandler.DbHandler;
import com.farshidabz.supernote.model.FolderModel;
import com.farshidabz.supernote.model.NoteModel;
import com.farshidabz.supernote.model.UserData;

import java.util.ArrayList;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class RecoveryAndBackupPresenterInteractor {
    DbHandler dbHandler;

    public RecoveryAndBackupPresenterInteractor(Context context) {
        dbHandler = new DbHandler(context);
    }

    public UserData getUserData() {
        ArrayList<FolderModel> folderModels = getFoldersAndContainNotes();
        ArrayList<NoteModel> noteModels = dbHandler.getNotesTable().getNotes();

        UserData userData = new UserData();
        userData.setFolderModel(folderModels);
        userData.setNoteModel(noteModels);

        return userData;
    }

    private ArrayList<FolderModel> getFoldersAndContainNotes() {
        ArrayList<FolderModel> folderModels = dbHandler.getFolderTable().getAll();

        for (FolderModel folderModel : folderModels) {
            ArrayList<NoteModel> noteModels = dbHandler.getNotesTable().getNotesOfFolder((int) folderModel.getId());
            folderModel.setNoteModelList(noteModels);
        }

        return folderModels;
    }
}
