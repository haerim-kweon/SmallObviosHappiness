package com.example.smallobvioshappiness;

public class Post {
    Integer postId;
    String title;
    String category;
    Integer price;
    String transaction_state;
    Integer interest_state;
    Integer interest_num;
    String createdAt;
    String imgURL;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTransaction_state() {
        return transaction_state;
    }

    public void setTransaction_state(String transaction_state) {
        this.transaction_state = transaction_state;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Post(Integer postId, String title, String category, Integer price, String transaction_state, Integer interest_state, Integer interest_num, String createdAt, String imgURL) {
        this.postId = postId;
        this.title = title;
        this.category = category;
        this.price = price;
        this.transaction_state = transaction_state;
        this.interest_state = interest_state;
        this.interest_num = interest_num;
        this.createdAt = createdAt;
        this.imgURL = imgURL;
    }
}
