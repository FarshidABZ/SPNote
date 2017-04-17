package com.farshidabz.spnote.view.ui.note;

import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import com.farshidabz.spnote.R;
import com.farshidabz.spnote.util.ScreenUtils;
import com.farshidabz.spnote.util.widgets.DrawingView;
import com.farshidabz.spnote.view.ui.note.textstyle.TextStyle;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class NoteInputEditTextHandler {
    private EditText inputEditText;
    private int paperStyleId;
    private int paperId;
    private int textStyle;

    private int textColorId;
    private int startPos;
    private int endPos;

    public NoteInputEditTextHandler(EditText inputEditText) {
        this.inputEditText = inputEditText;
    }

    public void setPaperStyle(int paperStyleId, int paperId) {
        this.paperStyleId = paperStyleId;
        this.paperId = paperId;

        if (paperStyleId == 0) {
            getStyleFromBackground();
        }

        setEditLayoutParams();
        inputEditText.setBackgroundResource(paperId);
    }

    private void setEditLayoutParams() {
        switch (paperId) {
            case R.drawable.line_edit_text:
                setPadding(36);
                break;
            case R.drawable.simple_edit_text_paper:
                setPadding(16);
                break;
            case R.drawable.grid_edit_text:
                setPadding(16);
                break;
        }
    }

    private void setPadding(int left) {
        inputEditText.setPadding(ScreenUtils.dpToPx(inputEditText.getContext(), left),
                ScreenUtils.dpToPx(inputEditText.getContext(), 16),
                ScreenUtils.dpToPx(inputEditText.getContext(), 16),
                ScreenUtils.dpToPx(inputEditText.getContext(), 16));
    }

    private void getStyleFromBackground() {
        switch (paperId) {
            case R.drawable.line_edit_text:
                paperStyleId = R.drawable.line_style;
                break;
            case R.drawable.simple_edit_text_paper:
                paperStyleId = R.drawable.simple_style;
                break;
            case R.drawable.grid_edit_text:
                paperStyleId = R.drawable.grid_style;
                break;
        }
    }

    public void setTextStyle(@TextStyle int textStyle, int textColorId) {
        this.textStyle = textStyle;
        this.textColorId = textColorId;

        Spannable spannable = inputEditText.getText();

        spannable.setSpan(new StyleSpan(textStyle), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(inputEditText.getContext().getResources().getColor(textColorId)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        inputEditText.setText(spannable);
        inputEditText.setSelection(inputEditText.getText().length());
    }

    public int getBackgroundResId() {
        if (paperStyleId == 0) {
            return R.drawable.line_edit_text;
        }

        return paperId;
    }

    public int getPaperStyleId() {
        if (paperStyleId == 0) {
            return R.drawable.line_style;
        }

        return paperStyleId;
    }

    public int getTextStyle() {
        if (textStyle == 0)
            return TextStyle.REGULAR;

        return textStyle;
    }

    public int getTextColorId() {
        if (textColorId == 0)
            return R.color.black;

        return textColorId;
    }

    public void setInputTypeMode(boolean writingMode, DrawingView drawingView) {
        if (writingMode)
            drawingView.canDraw(false);
        else
            drawingView.canDraw(true);

        inputEditText.setOnTouchListener((v, event) -> {
            if (!writingMode) {
                drawingView.onTouchEvent(event);
                return true;
            } else {
                return false;
            }
        });
    }

    public void setSelectionPos(int startPos, int endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }
}
