package com.example.smallobvioshappiness;

public class JoinPerson {
    int postId;
    String name;
    int userId;
//권선동

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public JoinPerson(int postId, String name, int userId) {
        this.postId = postId;
        this.name = name;
        this.userId = userId;
    }

    public static void remove(int position) {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static void add(JoinPerson joinPerson) {
    }
}


