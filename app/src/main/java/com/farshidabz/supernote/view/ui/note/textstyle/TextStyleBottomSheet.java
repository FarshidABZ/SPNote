package com.farshidabz.supernote.view.ui.note.textstyle;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farshidabz.supernote.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class TextStyleBottomSheet extends BottomSheetDialogFragment {

    @BindView(R.id.imgBlue)
    AppCompatImageView imgBlue;
    @BindView(R.id.imgBlack)
    AppCompatImageView imgBlack;
    @BindView(R.id.imgRed)
    AppCompatImageView imgRed;
    @BindView(R.id.imgGreen)
    AppCompatImageView imgGreen;
    @BindView(R.id.imgRegular)
    AppCompatImageView imgRegular;
    @BindView(R.id.imgItalic)
    AppCompatImageView imgItalic;
    @BindView(R.id.imgBold)
    AppCompatImageView imgBold;

    private int colorId;
    private String textStyle;

    private View rootView;

    public static TextStyleBottomSheet newInstance(@TextStyle String textStyle, int colorId) {
        TextStyleBottomSheet textStyleBottomSheet = new TextStyleBottomSheet();
        Bundle args = new Bundle();
        args.putInt("colorId", colorId);
        args.putString("textStyle", textStyle);
        textStyleBottomSheet.setArguments(args);
        return textStyleBottomSheet;
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
        initColors();
        initStyle();
    }

    private void initColors() {
        switch (colorId) {
            case R.color.blue:
                imgBlue.setImageResource(R.drawable.circle_blue_selected);
                break;
            case R.color.black:
                imgBlack.setImageResource(R.drawable.circle_black_selected);
                break;
            case R.color.red:
                imgRed.setImageResource(R.drawable.circle_red_selected);
                break;
            case R.color.green:
                imgGreen.setImageResource(R.drawable.circle_green_selected);
                break;
        }
    }

    private void initStyle() {
        switch (textStyle) {
            case TextStyle.BOLD:
                imgBold.setBackgroundResource(R.drawable.circle_midnight_selected);
                break;
            case TextStyle.ITALIC:
                imgItalic.setBackgroundResource(R.drawable.circle_midnight_selected);
                break;
            case TextStyle.REGULAR:
                imgRegular.setBackgroundResource(R.drawable.circle_midnight_selected);
                break;
            default:
                imgRegular.setBackgroundResource(R.drawable.circle_midnight_selected);
                break;
        }
    }
}
