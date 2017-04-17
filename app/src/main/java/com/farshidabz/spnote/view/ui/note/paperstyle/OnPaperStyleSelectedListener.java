package com.farshidabz.spnote.view.ui.note.paperstyle;

import android.support.annotation.DrawableRes;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public interface OnPaperStyleSelectedListener {
    void onPaperStyleSelected(@DrawableRes int paperStyleId, @DrawableRes int paperId);
}
