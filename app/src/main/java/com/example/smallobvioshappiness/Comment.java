package com.example.smallobvioshappiness;

public class Comment {
    String nick;
    String content;


    public Comment(String nick, String content) {
        this.nick = nick;
        this.content = content;
    }



    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void add(Comment comment) {
    }
}


