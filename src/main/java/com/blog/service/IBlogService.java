package com.blog.service;



import com.blog.dto.output.BlogDetails;
import com.blog.entity.Blog;
import com.core.service.IBaseService;

import java.util.List;

/**
 * 
 * @Description: blog的service接口
 */
public interface IBlogService extends IBaseService<Blog> {
    /**
     * 写博客
     * @param blog
     * @param userId
     */
	public void createBlog(Blog blog, Integer userId);
	/**
	 * 更新博客
	 * @param blog
	 * @param userId
	 */
	public void updateBlog(Blog blog, Integer userId);
	/**
	 * 删除博客
	 * @param blogId 博客id
	 * @param userId 操作人
	 */
	public void deleteBlog(Integer blogId, Integer userId);
	/**
	 * 查看某位用户的所有博客
	 * @param userId
	 * @return
	 */
	public List<BlogDetails> getUserBlogList(Integer userId);
}

