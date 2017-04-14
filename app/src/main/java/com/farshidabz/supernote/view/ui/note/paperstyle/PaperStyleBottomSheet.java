package com.farshidabz.supernote.view.ui.note.paperstyle;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.BottomSheetDialogFragment;
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

public class PaperStyleBottomSheet extends BottomSheetDialogFragment {

    @BindView(R.id.rlGridStyle)
    RelativeLayout rlGridStyle;
    @BindView(R.id.rlLineStyle)
    RelativeLayout rlLineStyle;
    @BindView(R.id.rlSimpleStyle)
    RelativeLayout rlSimpleStyle;

    private View rootView;

    private RelativeLayout selectedItem;
    private int paperStyleResId;
    private int paperResId;
    private Drawable paperStyleDrawable;
    private Drawable paperDrawable;

    private OnPaperStyleSelectedListener onPaperStyleSelectedListener;

    public static PaperStyleBottomSheet newInstance(@DrawableRes int paperResId, @DrawableRes int paperStyleResId) {
        PaperStyleBottomSheet paperStyleBottomSheet = new PaperStyleBottomSheet();
        Bundle args = new Bundle();
        args.putInt("paperStyleResId", paperStyleResId);
        args.putInt("paperResId", paperResId);
        paperStyleBottomSheet.setArguments(args);
        return paperStyleBottomSheet;
    }

    public void setOnPaperStyleSelectedListener(OnPaperStyleSelectedListener onPaperStyleSelectedListener) {
        this.onPaperStyleSelectedListener = onPaperStyleSelectedListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottomsheet_paper_style, container, false);

        ButterKnife.bind(this, rootView);

        paperStyleResId = getArguments().getInt("paperStyleResId");
        paperResId = getArguments().getInt("paperResId");

        setPaperStyle(paperStyleResId);

        return rootView;
    }

    private void setPaperStyle(int paperStyleResId) {
        switch (paperStyleResId) {
            case R.drawable.simple_style:
                selectedItem = rlSimpleStyle;
                paperResId = R.drawable.simple_edit_text_paper;
                break;
            case R.drawable.line_style:
                selectedItem = rlLineStyle;
                paperResId = R.drawable.line_edit_text;
                break;
            case R.drawable.grid_style:
                selectedItem = rlGridStyle;
                paperResId = R.drawable.grid_edit_text;
                break;
            default:
                selectedItem = rlLineStyle;
                paperResId = R.drawable.line;
                break;
        }
        setSelectedItemBackground(selectedItem);
    }

    @OnClick(R.id.rlGridStyle)
    public void onRlGridStyleClicked() {
        paperStyleResId = R.drawable.grid_style;
        paperResId = R.drawable.grid_edit_text;
        setSelectedItemBackground(rlGridStyle);
    }

    @OnClick(R.id.rlLineStyle)
    public void onRlLineStyleClicked() {
        paperStyleResId = R.drawable.line_style;
        paperResId = R.drawable.line_edit_text;
        setSelectedItemBackground(rlLineStyle);
    }

    @OnClick(R.id.rlSimpleStyle)
    public void onRlSimpleStyleClicked() {
        paperStyleResId = R.drawable.simple_style;
        paperResId = R.drawable.simple_edit_text_paper;
        setSelectedItemBackground(rlSimpleStyle);
    }

    private void setSelectedItemBackground(RelativeLayout selectedItem) {
        this.selectedItem.setBackgroundResource(0);
        this.selectedItem = selectedItem;
        selectedItem.setBackgroundResource(R.drawable.paper_style_border);
        onPaperStyleSelectedListener.onPaperStyleSelected(paperStyleResId, paperResId);
    }
}
