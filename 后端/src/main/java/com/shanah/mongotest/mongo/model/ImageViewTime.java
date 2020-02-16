package com.shanah.mongotest.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "image_view_time")
public class ImageViewTime {
    private String poster_name;
    private int view_time;

    public String getPoster_name() {
        return poster_name;
    }

    public void setPoster_name(String poster_name) {
        this.poster_name = poster_name;
    }

    public void setView_time(int view_time) {
        this.view_time = view_time;
    }

    public int getView_time() {
        return view_time;
    }
}
