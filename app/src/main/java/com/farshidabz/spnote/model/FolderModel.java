package com.farshidabz.spnote.model;

import java.util.List;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class FolderModel {
    private long id;
    private String title;
    private String address;
    private List<NoteModel> noteModelList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<NoteModel> getNoteModelList() {
        return noteModelList;
    }

    public void setNoteModelList(List<NoteModel> noteModelList) {
        this.noteModelList = noteModelList;
    }
}
