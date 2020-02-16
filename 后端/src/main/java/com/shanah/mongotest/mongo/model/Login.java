package com.shanah.mongotest.mongo.model;

public class Login {
    private String username;
    private String password;

    public String getUserame() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
