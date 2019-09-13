package com.farshidabz.spnote.view.ui.movetofolder.viewtypes;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.farshidabz.spnote.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

public class FolderListHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvFolderName)
    TextView tvFolderName;

    public FolderListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
