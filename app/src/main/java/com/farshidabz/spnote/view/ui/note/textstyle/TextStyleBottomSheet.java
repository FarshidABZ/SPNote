package com.farshidabz.spnote.view.ui.note.textstyle;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.farshidabz.spnote.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class TextStyleBottomSheet extends BottomSheetDialogFragment {
    @BindView(R.id.rlBlue)
    RelativeLayout rlBlue;
    @BindView(R.id.rlBlack)
    RelativeLayout rlBlack;
    @BindView(R.id.rlRed)
    RelativeLayout rlRed;
    @BindView(R.id.rlGreen)
    RelativeLayout rlGreen;
    @BindView(R.id.imgRegular)
    AppCompatImageView imgRegular;
    @BindView(R.id.imgItalic)
    AppCompatImageView imgItalic;
    @BindView(R.id.imgBold)
    AppCompatImageView imgBold;

    RelativeLayout selectedColor;
    AppCompatImageView selectedTextStyle;

    private int colorId;
    private int formatColorId;

    private int textStyle;

    private OnTextStyleChangeListener onTextStyleChangeListener;

    public static TextStyleBottomSheet newInstance(@TextStyle int textStyle, int colorId) {
        TextStyleBottomSheet textStyleBottomSheet = new TextStyleBottomSheet();
        Bundle args = new Bundle();
        args.putInt("colorId", colorId);
        args.putInt("textStyle", textStyle);
        textStyleBottomSheet.setArguments(args);
        return textStyleBottomSheet;
    }

    public void setOnTextStyleChangeListener(OnTextStyleChangeListener onTextStyleChangeListener) {
        this.onTextStyleChangeListener = onTextStyleChangeListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottomsheet_text_style, container, false);
        ButterKnife.bind(this, rootView);

        colorId = getArguments().getInt("colorId");
        textStyle = getArguments().getInt("textStyle");

        initStyles();

        return rootView;
    }

    private void initStyles() {
        setColors();
        setTextStyle();
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

    private void setTextStyle() {
        switch (textStyle) {
            case TextStyle.BOLD:
                selectedTextStyle = imgBold;
                break;
            case TextStyle.ITALIC:
                selectedTextStyle = imgItalic;
                break;
            case TextStyle.REGULAR:
                selectedTextStyle = imgRegular;
                break;
            default:
                selectedTextStyle = imgRegular;
                break;
        }

        setSelectedTextStyleBackground(selectedTextStyle);
    }

    private void setSelectedTextStyleBackground(AppCompatImageView selectedTextStyle) {
        this.selectedTextStyle.setBackgroundResource(0);
        this.selectedTextStyle = selectedTextStyle;
        selectedTextStyle.setBackgroundResource(R.drawable.circle_midnight_selected);
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

    @OnClick(R.id.imgBold)
    public void onBoldClicked() {
        textStyle = TextStyle.BOLD;
        setSelectedTextStyleBackground(imgBold);
    }

    @OnClick(R.id.imgRegular)
    public void onRegularClicked() {
        textStyle = TextStyle.REGULAR;
        setSelectedTextStyleBackground(imgRegular);
    }

    @OnClick(R.id.imgItalic)
    public void onItalicClicked() {
        textStyle = TextStyle.ITALIC;
        setSelectedTextStyleBackground(imgItalic);
    }

    @OnClick(R.id.tvTextStyleApply)
    public void onDoneClicked() {
        if (onTextStyleChangeListener != null)
            onTextStyleChangeListener.textStyleChanged(textStyle, colorId, formatColorId);
        dismiss();
    }
}
