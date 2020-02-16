package com.shanah.mongotest.mongo.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Document(collection = "images")
public class Images {
    private String poster_name;
    private String location;
    private String director;
    private Integer year;
    private String poster_url;
    private String[] type;
    private String[] staring;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date upload_time;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date modify_time;
    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public void setStaring(String[] staring) {
        this.staring = staring;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPoster_name(String poster_name) {
        this.poster_name = poster_name;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public String[] getType() {
        return type;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public String getLocation() {
        return location;
    }

    public String getPoster_name() {
        return poster_name;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public String getDirector() {
        return director;
    }

    public Integer getYear() {
        return year;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public String[] getStaring() {
        return staring;
    }
}
