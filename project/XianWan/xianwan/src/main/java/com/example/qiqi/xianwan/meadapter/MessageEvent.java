package com.example.qiqi.xianwan.meadapter;

import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;

public class MessageEvent
{
    public String message;
    public ImageView button;
    public Drawable drawable;


    public MessageEvent(String message, ImageView button) {
        this.message = message;
        this.button = button;
    }

    public MessageEvent(ImageView button, Drawable drawable) {
        this.button = button;
        this.drawable = drawable;
    }

    public MessageEvent(ImageView button) {
        this.button = button;
    }

    public ImageView getButton() {
        return button;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
