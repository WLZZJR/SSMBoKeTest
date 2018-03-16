package com.blog.controller;

import com.BaseTest;
import com.blog.entity.Blog;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BlogControllerTest extends BaseTest {

    /**
     * 写博客测试
     */
    @Test
    public void writeBlog() {
        Blog blog =new Blog();
        blog.setTitle("9.24小韩要闻5");
        blog.setDes("小韩关于调戏妹子的几点问题5");
        blog.setContext("问题1：。。。。。。问题2.。。。。。");
        String objString=new Gson().toJson(blog);
        System.out.println(objString);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/blog/writeBlog")
                            .header("userId", "4")
                            .content(objString)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 改博客测试
     */
    @Test
    public void updateBlog() {
        Blog blog =new Blog();
        blog.setId(8);
        blog.setTitle("9.24小韩要闻4.1");
        blog.setDes("小韩关于调戏妹子的几点问题4.1");
        blog.setContext("问题1：。。。。。。问题2.。。。。。");
        blog.setUserid(4);
        String objString=new Gson().toJson(blog);
        System.out.println(objString);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/blog/updateBlog")
                            .header("userId", "4")
                            .content(objString)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
