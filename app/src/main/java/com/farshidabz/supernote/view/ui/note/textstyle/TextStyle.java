package com.farshidabz.supernote.view.ui.note.textstyle;

import android.graphics.Typeface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface TextStyle {
    int BOLD = Typeface.BOLD;
    int ITALIC = Typeface.ITALIC;
    int REGULAR = Typeface.NORMAL;
}
