package com.farshidabz.supernote.view.ui.mainpage.viewtypes;

import com.farshidabz.supernote.R;

import ir.coderz.ghostadapter.BindItem;
import ir.coderz.ghostadapter.Binder;


/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

@BindItem(layout = R.layout.content_empty_state, holder = EmptyStateHolder.class)
public class EmptyStateItem {
    @Binder
    public void bind(EmptyStateHolder emptyStateHolder) {
    }
}
