package com.farshidabz.supernote.view.ui.note;

import android.graphics.Typeface;
import android.widget.EditText;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.util.widgets.DrawingView;
import com.farshidabz.supernote.view.ui.note.textstyle.TextStyle;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class NoteInputEditTextHandler {
    private EditText inputEditText;
    private int paperStyleId;
    private int paperId;
    private String textStyle;

    private int textColorId;

    public NoteInputEditTextHandler(EditText inputEditText) {
        this.inputEditText = inputEditText;
    }

    public void setPaperStyle(int paperStyleId, int paperId) {
        this.paperStyleId = paperStyleId;
        this.paperId = paperId;

        inputEditText.setBackgroundResource(paperId);
    }

    public void setTextStyle(@TextStyle String textStyle, int textColorId) {
        this.textStyle = textStyle;
        this.textColorId = textColorId;

        inputEditText.setTypeface(inputEditText.getTypeface(), Typeface.BOLD);
    }

    public int getBackgroundResId() {
        if (paperStyleId == 0) {
            return R.drawable.line_edit_text;
        }

        return paperStyleId;
    }

    public int getPaperStyleId() {
        if (paperStyleId == 0) {
            return R.drawable.line_style;
        }

        return paperStyleId;
    }

    public String getTextStyle() {
        if (textStyle != null || !textStyle.equals(""))
            return textStyle;

        return TextStyle.REGULAR;
    }

    public int getTextColorId() {
        if (textColorId == 0)
            return R.color.black;

        return textColorId;
    }

    public void setInputTypeMode(boolean writingMode, DrawingView drawingView) {
        inputEditText.setOnTouchListener((v, event) -> {
            if (!writingMode) {
                drawingView.onTouchEvent(event);
                return true;
            } else {
                return false;
            }
        });
    }
}
