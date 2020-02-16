package com.shanah.mongotest.mongo.service;
import com.shanah.mongotest.mongo.model.User;
import com.shanah.mongotest.mongo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
        User user = userDao.findOneByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        return user;
    }
}
