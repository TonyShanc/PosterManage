package com.shanah.mongotest.mongo.service;

import com.shanah.mongotest.mongo.dao.ImagesDao;
import com.shanah.mongotest.mongo.dao.UserFavouriteDao;
import com.shanah.mongotest.mongo.model.Images;
import com.shanah.mongotest.mongo.model.UserFavourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class FindUserAllfavPoster {
    @Autowired
    ImagesDao imagesDao;
    @Autowired
    UserFavouriteDao userFavouriteDao;
    public List<Images> findUserAllfavPoster(String username){
        UserFavourite userFavourite = userFavouriteDao.findUserallFavPosterByName(username);
        if (userFavourite!=null) {
            String[] namelist =  userFavourite.getPostername();
            System.out.println(Arrays.toString(namelist));
            List<Images> list = new LinkedList<>();

            if (namelist!=null)
            for(String name: namelist){
                list.add(imagesDao.findOneByFileName(name));
            }
            return list;
        }
        return null;
    }
}
