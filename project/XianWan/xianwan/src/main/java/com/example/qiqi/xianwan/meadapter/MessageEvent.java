package com.example.qiqi.xianwan;

import android.widget.Button;

public class MessageEvent
{
    public String message;
    public Button button;


    public MessageEvent(String message, Button button) {
        this.message = message;
        this.button = button;
    }

    public MessageEvent(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
