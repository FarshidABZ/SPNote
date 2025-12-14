package com.farshidabz.spnote.view.ui.movetofolder.viewtypes;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.model.FolderModel;
import com.farshidabz.spnote.view.ui.OnItemClickListener;

// import ir.coderz.ghostadapter.BindItem; // Commented: GhostAdapter annotations library is unavailable (was on JCenter)
// import ir.coderz.ghostadapter.Binder; // Commented: GhostAdapter annotations library is unavailable

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */

// @BindItem(layout = R.layout.content_folder_list, holder = FolderListHolder.class) // Commented: GhostAdapter annotation removed to allow build
public class FolderListItem {

    private final FolderModel folderModel;
    private OnItemClickListener onItemClickListener;

    public FolderListItem(FolderModel folderModel) {
        this.folderModel = folderModel;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // @Binder // Commented: GhostAdapter annotation removed to allow build
    public void binder(FolderListHolder folderListHolder) {
        // UI binding disabled because GhostAdapter is removed
        // folderListHolder.tvFolderName.setText(folderModel.getTitle());
        // folderListHolder.itemView.setOnClickListener(v ->
        //        onItemClickListener.onItemClicked(folderListHolder.getLayoutPosition(), folderModel));
    }
}
