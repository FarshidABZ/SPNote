package com.farshidabz.spnote.view.ui.mainpage.viewtypes;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.FolderModel;

import ir.coderz.ghostadapter.BindItem;
import ir.coderz.ghostadapter.Binder;

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

@BindItem(layout = R.layout.content_folders, holder = FolderHolder.class)
public class FolderItem {
    FolderModel folderModel;

    public FolderItem(FolderModel folderModel) {
        this.folderModel = folderModel;
    }

    @Binder
    public void binder(FolderHolder folderHolder) {
        folderHolder.tvFolderTitle.setText(folderModel.getTitle());
        folderHolder.tvFoldersNotesCount.setText(folderHolder.itemView.getContext()
                .getString(R.string.folder_pages_count, folderModel.getNoteModelList().size()));
    }
}
