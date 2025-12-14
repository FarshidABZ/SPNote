package com.farshidabz.spnote.view.ui.mainpage.viewtypes;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.view.ui.OnItemClickListener;

// import ir.coderz.ghostadapter.BindItem; // Commented: GhostAdapter library was hosted on JCenter and is unavailable; annotations removed
// import ir.coderz.ghostadapter.Binder; // Commented: GhostAdapter annotation removed

/**
 * Created by FarshidAbz.
 * Since 4/13/2017.
 */

// @BindItem(layout = R.layout.content_folders, holder = FolderHolder.class) // Commented: GhostAdapter annotation removed to allow build
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

    // @Binder // Commented: GhostAdapter annotation removed to allow build
    public void binder(FolderHolder folderHolder) {
        // UI binding disabled because GhostAdapter is removed
        // folderHolder.tvFolderTitle.setText(folderModel.getTitle());
        // folderHolder.tvFoldersNotesCount.setText(folderHolder.itemView.getContext()
        //        .getString(R.string.folder_pages_count, folderModel.getNoteModelList().size()));
        // folderHolder.itemView.setOnClickListener(v ->
        //        onItemClickListener.onItemClicked(folderHolder.getLayoutPosition(), folderModel));
    }
}
