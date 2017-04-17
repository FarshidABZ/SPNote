package com.farshidabz.spnote.view.ui.note.savenote;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.util.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/16/2017.
 */

public class SaveNoteDialog extends Dialog {

    @BindView(R.id.etNoteName)
    EditText etNoteName;
    private OnSaveNoteDismissListener onSaveNoteDismissListener;

    public SaveNoteDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnSaveNoteDismissListener(OnSaveNoteDismissListener onSaveNoteDismissListener) {
        this.onSaveNoteDismissListener = onSaveNoteDismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_save_note);

        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.white_background));
        getWindow().setLayout(ScreenUtils.getScreenHeight(getContext()) / 2, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.tvCancelSaveNote)
    public void onCancelSaveNoteClicked() {
        dismiss(false);
    }

    @OnClick(R.id.tvSaveNote)
    public void onSaveNoteClicked() {
        dismiss(true);
    }

    private void dismiss(boolean saveNote) {
        String noteName = etNoteName.getText().toString();
        if (TextUtils.isEmpty(noteName))
            noteName = getContext().getString(R.string.my_note);

        onSaveNoteDismissListener.onDismiss(saveNote, noteName);
        dismiss();
    }
}
