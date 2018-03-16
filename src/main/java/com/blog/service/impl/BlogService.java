package com.blog.service.impl;

import com.blog.dao.BlogDao;
import com.blog.dao.UserRoleDao;
import com.blog.dto.output.BlogDetails;
import com.blog.entity.Blog;
import com.blog.service.IBlogService;
import com.core.dao.IBaseDao;
import com.core.exception.MyException;
import com.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService extends BaseService<Blog> implements IBlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private UserRoleDao userRoleDao;


    @Override
    public IBaseDao<Blog> getBaseDao() {
        return blogDao;
    }


    /**
     * 编写博客
     * @param blog
     * @param userId
     */
    @Override
    public void createBlog(Blog blog, Integer userId) {

        blog.setId(null);
        blog.setUserid(userId);
        blogDao.insertSelective(blog);
    }

    /**
     * 更新博客
     * @param blog
     * @param userId
     */
    @Override
    public void updateBlog(Blog blog, Integer userId) {

        if(userId.equals(blog.getUserid())){
            blogDao.updateByPrimaryKey(blog);
        }else {
            throw new MyException("只能修改自己的博客");
        }

    }

    /**
     * 删除博客
     * @param blogId 博客id
     * @param userId 操作人
     */
    @Override
    public void deleteBlog(Integer blogId, Integer userId) {
        List<Integer> roleList=userRoleDao.getRoleIdListByUserId(userId);
        if (roleList.contains(1)){
            //管理员爱怎么删除怎么删除
            blogDao.deleteByPrimaryKey(blogId);

        }else {
            //非管理只能删自己的
            blogDao.deleteByIdAndUserId(userId, blogId);
        }

    }

    @Override
    public List<BlogDetails> getUserBlogList(Integer userId) {
        return blogDao.getUserBlogDetails(userId);
    }


}
