package com.example.smallobvioshappiness;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Chat {
    String name;
    String recent_chat;

    public Chat(String name, String recent_chat) {
        this.name = name;
        this.recent_chat = recent_chat;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecent_chat() {
        return recent_chat;
    }

    public void setRecent_chat(String recent_chat) {
        this.recent_chat = recent_chat;
    }

    public void add(Chat chat) {
    }
}


