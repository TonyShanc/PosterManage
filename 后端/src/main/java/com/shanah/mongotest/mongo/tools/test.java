package com.shanah.mongotest.mongo.tools;

import com.shanah.mongotest.mongo.dao.ImagesDao;
import com.shanah.mongotest.mongo.dao.UserDao;
import com.shanah.mongotest.mongo.model.Images;
import com.shanah.mongotest.mongo.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.List;

public class test {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        List<User> list = userDao.findAllUser();
        for(User user:list)System.out.println(user.getUsername());
    }
}
