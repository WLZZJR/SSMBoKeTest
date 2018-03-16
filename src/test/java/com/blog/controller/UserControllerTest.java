package com.blog.controller;

import com.BaseTest;
import com.blog.dto.input.UserEditDetails;
import com.blog.entity.User;
import com.blog.service.impl.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void getByIdTest(){

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/getById").param("id",
                            "6")).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void addUserTest(){
        User user=new User();
        user.setName("李安");
        user.setDes("中国好声音第四季周杰伦组成员");
        user.setLevel(1);
        user.setPassword("123");
        user.setAddress("上海");

        String objString=new Gson().toJson(user);

        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/addUser")
                            .content(objString)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 测试获取会员详情
    @Test
    public void getUserDetails() {
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/getUserDetails").param(
                            "id", "5")).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 测试获取会员列表
    @Test
    public void getUserList() {
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/getUserList")
                            .param("level", "0").param("username", "Kim")
                            .param("pageIndex", "2").param("pageSize", "2"))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 测试修改用户详情
    @Test
    public void updateUserDetails() {
        User user = userService.selectByPrimaryKey(5);
        user.setAddress("山东省青岛市");
        UserEditDetails userEditDetails = new UserEditDetails();
        userEditDetails.setRoleIds("2");
        userEditDetails.setUser(user);
        String objString = new Gson().toJson(userEditDetails);
        System.out.println(objString);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/updateUserDetails")
                            .content(objString).characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .header("userId", "1")).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
