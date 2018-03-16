package com.blog.service;

import com.BaseTest;
import com.blog.entity.User;
import com.blog.service.impl.UserService;
import com.google.gson.Gson;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest extends BaseTest {


    @Autowired
    private UserService userService;

    @Test
    public void testInsertUser(){

        User user=new User();
        user.setName("张磊");
        user.setPassword("123123");
        user.setLevel(1);
        user.setDes("中国好声音第四季张磊");
        user.setAddress("浙江杭州");
        userService.insert(user);
    }

    @Test
    public void testGetUserById(){

        User user=userService.selectByPrimaryKey(5);
        System.out.println(user.getName()+":"+user.getDes());

    }


    @Test
    public void testGetUserList(){
        String username="kim";
        List<User> pageInfo= userService.getUserList(null, username, new RowBounds(1,2));
        String objString=new Gson().toJson(pageInfo);
        System.out.println(objString);
    }
}
