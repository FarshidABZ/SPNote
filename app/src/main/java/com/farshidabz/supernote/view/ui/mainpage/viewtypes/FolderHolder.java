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
