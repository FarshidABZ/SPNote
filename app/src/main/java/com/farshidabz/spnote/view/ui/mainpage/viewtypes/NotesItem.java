package com.farshidabz.spnote.view.ui.mainpage.viewtypes;

import android.os.Bundle;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.flowcontroller.ActivityFactory;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.view.ui.note.NoteActivity;

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

        notesHolder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("noteId", noteModel.getId());
            ActivityFactory.startActivity(notesHolder.itemView.getContext(),
                    NoteActivity.class.getSimpleName(), bundle);
        });
    }
}
