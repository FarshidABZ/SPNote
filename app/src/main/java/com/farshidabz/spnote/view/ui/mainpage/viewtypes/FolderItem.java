package com.farshidabz.spnote.view.ui.mainpage.viewtypes;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.view.ui.OnItemClickListener;

import ir.coderz.ghostadapter.BindItem;
import ir.coderz.ghostadapter.Binder;

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

@BindItem(layout = R.layout.content_folders, holder = FolderHolder.class)
public class FolderItem {
    FolderModel folderModel;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onLongClickListener;

    public FolderItem(FolderModel folderModel) {
        this.folderModel = folderModel;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Binder
    public void binder(FolderHolder folderHolder) {
        folderHolder.tvFolderTitle.setText(folderModel.getTitle());
        folderHolder.tvFoldersNotesCount.setText(folderHolder.itemView.getContext()
                .getString(R.string.folder_pages_count, folderModel.getNoteModelList().size()));

        folderHolder.itemView.setOnClickListener(v ->
                onItemClickListener.onItemClicked(folderHolder.getLayoutPosition(), folderModel));
    }
}
