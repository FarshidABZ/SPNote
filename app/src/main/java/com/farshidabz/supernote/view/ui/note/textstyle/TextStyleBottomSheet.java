package com.farshidabz.supernote.view.ui.note.textstyle;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.farshidabz.supernote.R;

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
    private String textStyle;

    private View rootView;
    private OnTextStyleChangeListener onTextStyleChangeListener;

    public static TextStyleBottomSheet newInstance(@TextStyle String textStyle, int colorId) {
        TextStyleBottomSheet textStyleBottomSheet = new TextStyleBottomSheet();
        Bundle args = new Bundle();
        args.putInt("colorId", colorId);
        args.putString("textStyle", textStyle);
        textStyleBottomSheet.setArguments(args);
        return textStyleBottomSheet;
    }

    public void setOnTextStyleChangeListener(OnTextStyleChangeListener onTextStyleChangeListener) {
        this.onTextStyleChangeListener = onTextStyleChangeListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottomsheet_text_style, container, false);
        ButterKnife.bind(this, rootView);

        colorId = getArguments().getInt("colorId");
        textStyle = getArguments().getString("textStyle");

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
                break;
            case R.color.black:
                selectedColor = rlBlack;
                break;
            case R.color.red:
                selectedColor = rlRed;
                break;
            case R.color.green:
                selectedColor = rlGreen;
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
        setSelectedColorBackground(rlGreen);
    }

    @OnClick(R.id.rlBlue)
    public void onBlueClicked() {
        colorId = R.color.blue;
        setSelectedColorBackground(rlBlue);
    }

    @OnClick(R.id.rlBlack)
    public void onBlackClicked() {
        colorId = R.color.black;
        setSelectedColorBackground(rlBlack);
    }

    @OnClick(R.id.rlRed)
    public void onRedClicked() {
        colorId = R.color.red;
        setSelectedColorBackground(rlRed);
    }

    @OnClick(R.id.imgBold)
    public void onBoldClicked() {
        textStyle = TextStyle.BOLD;
        setSelectedTextStyleBackground(imgBold);
    }

    @OnClick(R.id.imgRegular)
    public void onRegularClicked() {
        textStyle = TextStyle.ITALIC;
        setSelectedTextStyleBackground(imgRegular);
    }

    @OnClick(R.id.imgItalic)
    public void onItalicClicked() {
        textStyle = TextStyle.REGULAR;
        setSelectedTextStyleBackground(imgItalic);
    }

    @OnClick(R.id.tvTextStyleApply)
    public void onDoneClicked() {
        if(onTextStyleChangeListener != null)
            onTextStyleChangeListener.textStyleChanged(textStyle, colorId);
        dismiss();
    }
}
