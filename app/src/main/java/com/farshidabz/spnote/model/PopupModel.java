package com.farshidabz.spnote.model;

/**
 * Created by FarshidAbz.
 * Since 2/19/2017.
 */

public class PopupModel {
    private String title;
    private int iconResId;

    public PopupModel(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public PopupModel(String title) {
        this.title = title;
    }

    public PopupModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
