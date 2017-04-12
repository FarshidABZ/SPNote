package com.farshidabz.supernote.model;

import java.util.List;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class UserData {
    private List<FolderModel> folderModel;
    private List<NoteModel> noteModel;

    public List<FolderModel> getFolderModel() {
        return folderModel;
    }

    public void setFolderModel(List<FolderModel> folderModel) {
        this.folderModel = folderModel;
    }

    public List<NoteModel> getNoteModel() {
        return noteModel;
    }

    public void setNoteModel(List<NoteModel> noteModel) {
        this.noteModel = noteModel;
    }

    public boolean isEmpty() {
        return folderModel == null || noteModel == null ||
                getNoteModel().size() == 0 || getFolderModel().size() == 0;
    }
}
