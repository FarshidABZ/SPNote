package com.farshidabz.spnote.model;

import android.graphics.Bitmap;
import android.text.Spannable;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */
public class NoteModel {
    private int id;
    private int folder_id;

    private String address;
    private String title;
    private Spannable content;
    private String type;
    private String background;

    private Bitmap image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Spannable getContent() {
        return content;
    }

    public void setContent(Spannable content) {
        this.content = content;
    }

    public int getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(int folder_id) {
        this.folder_id = folder_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
