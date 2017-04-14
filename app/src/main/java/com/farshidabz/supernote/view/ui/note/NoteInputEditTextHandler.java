package com.farshidabz.supernote.view.ui.note;

import android.widget.EditText;

import com.farshidabz.supernote.R;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class NoteInputEditTextHandler {
    private EditText inputEditText;
    private int paperStyleId;
    private int paperId;

    public NoteInputEditTextHandler(EditText inputEditText) {
        this.inputEditText = inputEditText;
    }

    public void changePaperStyle(int paperStyleId, int paperId) {
        this.paperStyleId = paperStyleId;
        this.paperId = paperId;

        inputEditText.setBackgroundResource(paperId);
    }

    public int getBackgroundResId() {
        if (paperStyleId == 0) {
            return R.drawable.line_edit_text;
        }
        return paperStyleId;
    }

    public int getPaperStyleId(){
        if(paperStyleId == 0){
            return R.drawable.line_style;
        }
        return paperStyleId;
    }
}
