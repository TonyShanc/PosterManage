package com.shanah.mongotest.mongo.service;

import com.shanah.mongotest.mongo.dao.ImagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckFileNameExist {
    @Autowired
    private ImagesDao imagesDao;
    public boolean checkFilmNameExist(String poster_name){
        if (imagesDao.findOneByFileName(poster_name)!=null)return true;
        else return false;
    }
}
