package com.shanah.mongotest.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_favourite")
public class UserFavourite {
    public String username;
    public String[] postername;

    public String getUsername() {
        return username;
    }

    public void setPostername(String[] postername) {
        this.postername = postername;
    }

    public String[] getPostername() {
        return postername;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
