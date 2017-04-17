package com.farshidabz.spnote.view.ui.movetofolder.viewtypes;

import com.farshidabz.spnote.R;

import ir.coderz.ghostadapter.BindItem;
import ir.coderz.ghostadapter.Binder;

/**
 * Created by FarshidAbz.
 * Since 4/17/2017.
 */
@BindItem(layout = R.layout.content_folder_empty_state, holder = FolderEmptyStateHolder.class)
public class FolderEmptyStateItem {
    @Binder
    public void bind(FolderEmptyStateHolder folderEmptyStateHolder) {
    }
}
