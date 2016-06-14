package com.rainycube.petbuddy.dataset;

import android.graphics.drawable.Drawable;

/**
 * Created by SBKim on 2016-06-13.
 */
public class RecyclerViewItem {
    private Drawable image ;
    private String title ;
    private String content;

    public void setImage(Drawable image) {
        this.image = image ;
    }

    public void setTitle(String title) {
        this.title = title ;
    }

    public void setContent(String contetn) {
        this.content = contetn;
    }

    public Drawable getImage() {
        return this.image ;
    }

    public String getTitle() {
        return this.title ;
    }

    public String getContent() {
        return content;
    }
}