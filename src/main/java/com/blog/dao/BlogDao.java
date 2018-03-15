package com.blog.dao;

import com.blog.dto.output.BlogDetails;
import com.blog.entity.Blog;
import com.core.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogDao extends IBaseDao<Blog> {

    //删除指定会员的指定博客（如果不是该会员的博客，不能删除）
    public void deleteByIdAndUserId(@Param("userid")Integer userid, @Param("id")Integer id);
    //获取特定会员所有博客
    public List<BlogDetails> getUserBlogDetails(Integer userId);
}
