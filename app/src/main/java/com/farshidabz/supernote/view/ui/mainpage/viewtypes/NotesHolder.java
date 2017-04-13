package com.farshidabz.supernote.view.ui.mainpage.viewtypes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.farshidabz.supernote.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

public class NotesHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvNoteTitle)
    TextView tvNoteTitle;

    public NotesHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
