package com.blog.service.impl;

import com.blog.dto.output.BlogDetails;
import com.blog.entity.Blog;
import com.blog.service.IBlogService;

import java.util.List;

public class BlogService implements IBlogService {
    @Override
    public void createBlog(Blog blog, Integer userId) {

    }

    @Override
    public void updateBlog(Blog blog, Integer userId) {

    }

    @Override
    public void deleteBlog(Integer blogId, Integer userId) {

    }

    @Override
    public List<BlogDetails> getUserBlogList(Integer userId) {
        return null;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Blog record) {
        return 0;
    }

    @Override
    public int insertSelective(Blog record) {
        return 0;
    }

    @Override
    public Blog selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Blog record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Blog record) {
        return 0;
    }
}
