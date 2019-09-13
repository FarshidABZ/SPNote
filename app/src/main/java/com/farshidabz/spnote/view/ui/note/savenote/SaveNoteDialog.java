package com.farshidabz.spnote.view.ui.note.savenote;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.interactor.dbinteractor.DatabaseInteractor;
import com.farshidabz.spnote.model.NoteModel;
import com.farshidabz.spnote.util.ScreenUtils;
import com.farshidabz.spnote.view.ui.OnDialogDismissListener;

import java.util.List;

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
    private OnDialogDismissListener onDialogDismissListener;
    private DatabaseInteractor interactor;

    public SaveNoteDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
        this.onDialogDismissListener = onDialogDismissListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_save_note);

        ButterKnife.bind(this);

        interactor = new DatabaseInteractor(getContext());

        getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.white_background));
        getWindow().setLayout(ScreenUtils.getScreenHeight(getContext()) / 2, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.tvCancelSaveNote)
    public void onCancelSaveNoteClicked() {
        dismiss(false);
    }

    @OnClick(R.id.tvSaveNote)
    public void onSaveNoteClicked() {
        if (checkForDuplicateName(etNoteName.getText().toString())) {
            Toast.makeText(getContext(),
                    getContext().getString(R.string.exist_note_name),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        dismiss(true);
    }

    private boolean checkForDuplicateName(String noteName) {
        if (TextUtils.isEmpty(noteName)) {
            noteName = getContext().getString(R.string.my_note);
        }

        List<NoteModel> notes = interactor.getAllNotes();
        if (notes == null || notes.size() == 0) {
            return false;
        }

        for (NoteModel noteModel : notes) {
            if (noteName.equals(noteModel.getTitle())) {
                return true;
            }
        }

        return false;
    }

    private void dismiss(boolean saveNote) {
        String noteName = etNoteName.getText().toString();
        if (TextUtils.isEmpty(noteName))
            noteName = getContext().getString(R.string.my_note);

        onDialogDismissListener.onDismiss(saveNote, noteName);
        dismiss();
    }
}
