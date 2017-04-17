package com.farshidabz.spnote.view.ui.note.discard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/16/2017.
 */

public class DiscardDialog extends Dialog {
    private OnDiscardDismissListener onDismissListener;

    public DiscardDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnDiscardDismissListener(OnDiscardDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_discard_changes);

        ButterKnife.bind(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.white_background));
        getWindow().setLayout(ScreenUtils.getScreenHeight(getContext()) / 2, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.tvDiscardChanges)
    public void onDiscardChangesClicked() {
        dismiss(true);
    }

    @OnClick(R.id.tvCancelDiscardChanges)
    public void onCancelDiscardChangesClicked() {
        dismiss(false);
    }

    private void dismiss(boolean discard) {
        onDismissListener.onDismiss(discard);
        dismiss();
    }
}
