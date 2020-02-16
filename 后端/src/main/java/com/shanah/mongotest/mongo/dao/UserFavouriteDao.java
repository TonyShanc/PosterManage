package com.shanah.mongotest.mongo.dao;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Query;
import com.shanah.mongotest.mongo.model.UserFavourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class UserFavouriteDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    public UserFavourite findUserallFavPosterByName(String username){
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query,UserFavourite.class);
    }
    public boolean addFavPoster(String username,String postername){
        UserFavourite userFavourite = findUserallFavPosterByName(username);
        if(userFavourite==null)return false;
        String[] poster = userFavourite.getPostername();
        int size = poster.length;
        String[] tmp = new String[size+1];
        for(int i=0;i<size;i++)tmp[i] = poster[i];
        tmp[size] = postername;
        Query query = new Query(Criteria.where("username").is(username));
        Update update= new Update().set("postername", tmp);
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,UserFavourite.class);
        return true;
    }
    public boolean deleteFavPoster(String username,String postername){
        UserFavourite userFavourite = findUserallFavPosterByName(username);
        if(userFavourite==null)return false;
        String[] poster = userFavourite.getPostername();
        //for(String s:poster)
        //    System.out.println(s);
        boolean flag = Arrays.asList(poster).contains(postername);
        if(!flag)return false;
        int size = poster.length;
        String[] tmp = new String[size-1];
        for(int i=0,j=0;i<size-1&&j<size;i++,j++){
            tmp[i] = poster[j];
            if(poster[j].equals(postername)){
                i--;
            }
        }
        Query query = new Query(Criteria.where("username").is(username));
        Update update= new Update().set("postername", tmp);
        mongoTemplate.updateFirst(query,update,UserFavourite.class);
        return true;
    }
}
