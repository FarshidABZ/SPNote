package com.farshidabz.spnote.view.ui.mainpage.viewtypes;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.farshidabz.spnote.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

public class FolderHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvFoldersNotesCount)
    TextView tvFoldersNotesCount;

    @BindView(R.id.tvFolderTitle)
    TextView tvFolderTitle;

    public FolderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
