package com.farshidabz.spnote.view.ui.mainpage.moreaction;

import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farshidabz.spnote.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class MoreActionBottomSheet extends BottomSheetDialogFragment {

    @BindView(R.id.llRenameNote)
    LinearLayout llRenameNote;
    @BindView(R.id.llRemoveNote)
    LinearLayout llRemoveNote;
    @BindView(R.id.llMoveNote)
    LinearLayout llMoveNote;
    @BindView(R.id.tvMoreActionTitle)
    TextView tvMoreActionTitle;

    private View rootView;
    private OnActionClickListener onActionClickListener;

    public static MoreActionBottomSheet newInstance(String title) {
        MoreActionBottomSheet moreActionBottomSheet = new MoreActionBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        moreActionBottomSheet.setArguments(bundle);
        return moreActionBottomSheet;
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottomsheet_more_action, container, false);
        ButterKnife.bind(this, rootView);

        tvMoreActionTitle.setText(getArguments().getString("title"));

        return rootView;
    }

    @OnClick(R.id.llRenameNote)
    public void onRenameClicked() {
        onActionClickListener.onActionClicked(ActionType.RENAME);
        dismiss();
    }

    @OnClick(R.id.llMoveNote)
    public void onMoveClicked() {
        onActionClickListener.onActionClicked(ActionType.MOVE);
        dismiss();
    }

    @OnClick(R.id.llRemoveNote)
    public void onRemoveClicked() {
        onActionClickListener.onActionClicked(ActionType.REMOVE);
        dismiss();
    }
}
