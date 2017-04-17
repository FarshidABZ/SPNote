package com.farshidabz.spnote.interactor.dbinteractor;

import android.content.Context;

import com.farshidabz.spnote.interactor.dbhandler.DbHandler;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.model.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class DatabaseInteractor {
    DbHandler dbHandler;

    public DatabaseInteractor(Context context) {
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

    public boolean updateNote(NoteModel noteModel) {
        return dbHandler.getNotesTable().update(noteModel);
    }

    public boolean removeNote(NoteModel noteModel) {
        return dbHandler.getNotesTable().delete(noteModel.getId());
    }

    public List<FolderModel> getFolders() {
        return dbHandler.getFolderTable().getAll();
    }

    public boolean createNewFolder(FolderModel folderModel) {
        return dbHandler.getFolderTable().create(folderModel);
    }

    public NoteModel getNote(int noteId) {
        return dbHandler.getNotesTable().get(noteId);
    }

    public boolean createNote(NoteModel noteModel) {
        return dbHandler.getNotesTable().create(noteModel);
    }
}
