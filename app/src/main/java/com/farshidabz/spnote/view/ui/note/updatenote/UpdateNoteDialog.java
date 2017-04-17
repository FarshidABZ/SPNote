package com.farshidabz.spnote.view.ui.note.updatenote;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/16/2017.
 */

public class UpdateNoteDialog extends Dialog {
    OnUpdateNoteDismissListener onUpdateNoteDismissListener;

    public UpdateNoteDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnUpdateNoteDismissListener(OnUpdateNoteDismissListener onUpdateNoteDismissListener) {
        this.onUpdateNoteDismissListener = onUpdateNoteDismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_update_note);

        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.white_background));
        getWindow().setLayout(ScreenUtils.getScreenHeight(getContext()) / 2, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.tvSaveChanges)
    public void onSaveChangesClicked() {
        dismiss(true);
    }

    @OnClick(R.id.tvCancelSaveChanges)
    public void onCancelSaveChangesClicked() {
        dismiss(false);
    }

    private void dismiss(boolean saveChanges) {
        onUpdateNoteDismissListener.onUpdateNoteDismissListener(saveChanges);
        dismiss();
    }
}
