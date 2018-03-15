package com.blog.dao;

import com.BaseTest;
import com.blog.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends BaseTest {

    @Autowired
    private UserDao userDao;


    @Test
    public void save(){

        User user=new User();
        user.setName("Kim");
        user.setPassword("123");
        user.setLevel(0);
        user.setDes("中国好声音第四季张磊");
        user.setTel("15876188036");
        user.setAddress("浙江杭州");

        userDao.insert(user);
    }
}
