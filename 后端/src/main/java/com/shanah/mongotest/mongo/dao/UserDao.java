package com.shanah.mongotest.mongo.dao;
import com.shanah.mongotest.mongo.model.User;
import com.shanah.mongotest.mongo.model.UserFavourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    public boolean deleteUser(String username){
        if(findOneByUsername(username)==null)return false;
        Query query = new Query(Criteria.where("username").is(username));
        mongoTemplate.remove(query,User.class);
        mongoTemplate.remove(query, UserFavourite.class);
        return true;
    }
    public User findOne(String name, String password){
        Query query = new Query(Criteria.where("username").is(name).and("password").is(password));
        User user = mongoTemplate.findOne(query,User.class);
        return user;
    }
    public User findOneByUsername(String username){
        Query query = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query,User.class);
        return user;
    }
    public  List<User> findAllUser(){
        List<User> userList = mongoTemplate.findAll(User.class);
        return  userList;
    }



    public User registUser(String username, String password){

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setRoles("ROLE_USER");
        user.setReg_time(new Date());
        mongoTemplate.save(user);
        UserFavourite userFavourite = new UserFavourite();
        userFavourite.setUsername(username);
        userFavourite.setPostername(null);
        mongoTemplate.save(userFavourite);

        return user;
    }
    public List<User> findUserBykeyWord(String keyWord){
        Pattern pattern = Pattern.compile("^.*"+keyWord+".*$", Pattern.CASE_INSENSITIVE);
        Query query = Query.query(Criteria.where("username").regex(pattern));
        List<User> list = mongoTemplate.find(query,User.class);
        return list;
    }
}
