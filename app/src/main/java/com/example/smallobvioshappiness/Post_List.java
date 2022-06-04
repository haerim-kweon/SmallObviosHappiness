package com.example.smallobvioshappiness;

public class Post_List {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String title;
    String contents;

    public Post_List(int id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }


    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getRecent_chat() {
        return contents;
    }

    public void setRecent_chat(String contents) {
        this.contents = contents;
    }

    public void add(Post_List post_list) {
    }
}


