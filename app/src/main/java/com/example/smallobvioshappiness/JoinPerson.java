package com.example.smallobvioshappiness;

public class JoinPerson {
    String name;
    int userId;
//권선동

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public JoinPerson(String name, int userId) {
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


