package com.farshidabz.supernote.view.ui.mainpage.viewtypes;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.model.NoteModel;

import ir.coderz.ghostadapter.BindItem;
import ir.coderz.ghostadapter.Binder;

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

@BindItem(layout = R.layout.content_notes, holder = NotesHolder.class)
public class NotesItem {
    NoteModel noteModel;

    public NotesItem(NoteModel noteModel) {
        this.noteModel = noteModel;
    }

    @Binder
    public void binder(NotesHolder notesHolder) {
        notesHolder.tvNoteTitle.setText(noteModel.getTitle());
    }
}
