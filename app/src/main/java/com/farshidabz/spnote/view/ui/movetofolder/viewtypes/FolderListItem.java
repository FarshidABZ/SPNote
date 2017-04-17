package com.farshidabz.spnote.view.ui.movetofolder.viewtypes;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.view.ui.OnItemClickListener;

import ir.coderz.ghostadapter.BindItem;
import ir.coderz.ghostadapter.Binder;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

@BindItem(layout = R.layout.content_folder_list, holder = FolderListHolder.class)
public class FolderListItem {

    private final FolderModel folderModel;
    private OnItemClickListener onItemClickListener;

    public FolderListItem(FolderModel folderModel) {
        this.folderModel = folderModel;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Binder
    public void binder(FolderListHolder folderListHolder) {
        folderListHolder.tvFolderName.setText(folderModel.getTitle());

        folderListHolder.itemView.setOnClickListener(v ->
                onItemClickListener.onItemClicked(folderListHolder.getLayoutPosition(), folderModel));
    }
}
