package com.shanah.mongotest.mongo.model;

public class SearchPosterByAttri {
    private String location;
    private String[] type;
    private int year;
    private String[] staring;

    public String getLocation() {
        return location;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getStaring() {
        return staring;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getType() {
        return type;
    }

    public void setStaring(String[] staring) {
        this.staring = staring;
    }
}
