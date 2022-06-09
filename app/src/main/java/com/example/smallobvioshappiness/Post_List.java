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
    String imgUrl;
    int price;

    public Post_List(int id, String title, String contents, String imgUrl, int price) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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


