package com.example.smallobvioshappiness;

public class Post {
    String title;
    String category;
    String time;
    String price;

    public Post(String title, String category, String time, String price) {
        this.title = title;
        this.category = category;
        this.time = time;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
