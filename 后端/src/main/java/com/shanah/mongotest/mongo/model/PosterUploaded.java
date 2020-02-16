package com.shanah.mongotest.mongo.model;

import java.io.File;

public class PosterUploaded {
    private File poster_image;
    private String poster_name;
    private String location;
    private String director;
    private Integer year;
    private String poster_url;
    private String[] type;
    private String[] staring;


    public File getPoster_image() {
        return poster_image;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPoster_name() {
        return poster_name;
    }

    public void setPoster_name(String poster_name) {
        this.poster_name = poster_name;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public void setStaring(String[] staring){
        this.staring = staring;
    }
    public String[] getStaring(){
        return this.staring;
    }

    public void setPoster_image(File poster_image) {
        this.poster_image = poster_image;
    }
}
