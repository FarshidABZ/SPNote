package com.farshidabz.supernote.view.ui.mainpage.viewtypes;

import com.farshidabz.supernote.R;

import ir.coderz.coreadapter.CoreItem;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class EmptyStateItem implements CoreItem<EmptyStateHolder, Void> {
    @Override
    public void bind(EmptyStateHolder viewHolder) {

    }

    @Override
    public int getLayout() {
        return R.layout.content_empty_state;
    }

    @Override
    public Void getData() {
        return null;
    }
}
