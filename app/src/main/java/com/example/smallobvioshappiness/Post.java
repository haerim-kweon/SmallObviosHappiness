package com.example.smallobvioshappiness;

public class Post {
    String title;
    String category;
    Integer price;
    Integer interest_state;
    Integer interest_num;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getInterest_state() {
        return interest_state;
    }

    public void setInterest_state(Integer interest_state) {
        this.interest_state = interest_state;
    }

    public Integer getInterest_num() {
        return interest_num;
    }

    public void setInterest_num(Integer interest_num) {
        this.interest_num = interest_num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Post(String title, String category, Integer price, Integer interest_state, Integer interest_num, String time) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.interest_state = interest_state;
        this.interest_num = interest_num;
        this.time = time;
    }

    String time;

}
