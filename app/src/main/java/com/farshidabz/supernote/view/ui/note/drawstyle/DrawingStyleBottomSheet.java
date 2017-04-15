package com.farshidabz.supernote.view.ui.note.drawstyle;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.farshidabz.supernote.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/16/2017.
 */

public class DrawingStyleBottomSheet extends BottomSheetDialogFragment {
    @BindView(R.id.sbPenSize)
    SeekBar sbPenSize;
    @BindView(R.id.rlBlue)
    RelativeLayout rlBlue;
    @BindView(R.id.rlBlack)
    RelativeLayout rlBlack;
    @BindView(R.id.rlRed)
    RelativeLayout rlRed;
    @BindView(R.id.rlGreen)
    RelativeLayout rlGreen;
    private int colorId;
    private int formatColorId;

    private RelativeLayout selectedColor;
    private OnDrawingStyleChangeListener onDrawingStyleChangeListener;

    public static DrawingStyleBottomSheet newInstance() {
        return new DrawingStyleBottomSheet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottomsheet_drawing_style, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void setOnDrawingStyleChangeListener(OnDrawingStyleChangeListener onDrawingStyleChangeListener) {
        this.onDrawingStyleChangeListener = onDrawingStyleChangeListener;
    }

    private void setColors() {
        switch (colorId) {
            case R.color.blue:
                selectedColor = rlBlue;
                formatColorId = R.drawable.ic_format_color_text_blue;
                break;
            case R.color.black:
                selectedColor = rlBlack;
                formatColorId = R.drawable.ic_format_color_text_black;
                break;
            case R.color.red:
                selectedColor = rlRed;
                formatColorId = R.drawable.ic_format_color_text_red;
                break;
            case R.color.green:
                selectedColor = rlGreen;
                formatColorId = R.drawable.ic_format_color_text_green;
                break;
        }
        setSelectedColorBackground(selectedColor);
    }

    private void setSelectedColorBackground(RelativeLayout selectedColor) {
        this.selectedColor.setBackgroundResource(0);
        this.selectedColor = selectedColor;
        selectedColor.setBackgroundResource(R.drawable.circle_midnight_selected);
    }

    @OnClick(R.id.rlGreen)
    public void onGreenClicked() {
        colorId = R.color.green;
        formatColorId = R.drawable.ic_format_color_text_green;
        setSelectedColorBackground(rlGreen);
    }

    @OnClick(R.id.rlBlue)
    public void onBlueClicked() {
        colorId = R.color.blue;
        formatColorId = R.drawable.ic_format_color_text_blue;
        setSelectedColorBackground(rlBlue);
    }

    @OnClick(R.id.rlBlack)
    public void onBlackClicked() {
        colorId = R.color.black;
        formatColorId = R.drawable.ic_format_color_text_black;
        setSelectedColorBackground(rlBlack);
    }

    @OnClick(R.id.rlRed)
    public void onRedClicked() {
        colorId = R.color.red;
        formatColorId = R.drawable.ic_format_color_text_red;
        setSelectedColorBackground(rlRed);
    }

    @OnClick(R.id.tvTextStyleApply)
    public void onDoneClicked() {
        int penSize = sbPenSize.getProgress() + 4;

        if (onDrawingStyleChangeListener != null) {
            onDrawingStyleChangeListener.onDrawingStyleChanged(penSize, colorId);
        }
        dismiss();
    }
}
