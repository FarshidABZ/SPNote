package com.farshidabz.spnote.view.ui.mainpage.viewtypes;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.view.ui.OnItemClickListener;

// import ir.coderz.ghostadapter.BindItem; // Commented: GhostAdapter annotations unavailable; JCenter is EOL
// import ir.coderz.ghostadapter.Binder; // Commented: GhostAdapter annotations unavailable

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

// @BindItem(layout = R.layout.content_notes, holder = NotesHolder.class) // Commented: GhostAdapter annotation removed to allow build
public class NotesItem {
    NoteModel noteModel;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    public NotesItem(NoteModel noteModel) {
        this.noteModel = noteModel;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    // @Binder // Commented: GhostAdapter annotation removed to allow build
    public void binder(NotesHolder notesHolder) {
        // UI binding disabled because GhostAdapter is removed
        // notesHolder.tvNoteTitle.setText(noteModel.getTitle());
        // notesHolder.itemView.setOnClickListener(v ->
        //        onItemClickListener.onItemClicked(notesHolder.getAdapterPosition(), noteModel));
        // notesHolder.itemView.setOnLongClickListener(v -> {
        //    onItemLongClickListener.onItemLongClicked(notesHolder.getLayoutPosition(), noteModel);
        //    return false;
        // });
    }
}
