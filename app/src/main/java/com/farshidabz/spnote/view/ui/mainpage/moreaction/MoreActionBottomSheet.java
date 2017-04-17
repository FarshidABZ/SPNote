package com.farshidabz.spnote.view.ui.mainpage.moreaction;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farshidabz.spnote.R;

import butterknife.ButterKnife;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class MoreActionBottomSheet extends BottomSheetDialogFragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottomsheet_more_action, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
