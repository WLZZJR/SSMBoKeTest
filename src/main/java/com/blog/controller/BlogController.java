package com.blog.controller;

import com.blog.dto.output.BlogDetails;
import com.blog.entity.Blog;
import com.blog.service.impl.BlogService;
import com.blog.service.impl.UserService;
import com.core.ajaxResult.AjaxResult;
import com.core.controller.BaseController;
import com.core.exception.MyException;
import com.core.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description:
 */
@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    /**
     * 写博客
     * @param blog
     * @param request
     * @return
     */
    @ResponseBody
	@RequestMapping(value="/writeBlog",method=RequestMethod.POST)
	public AjaxResult createBlog(@RequestBody Blog blog, HttpServletRequest request){
		Integer userId=getNotNullUserId(request);
		if(StringUtil.isEmpty(blog.getTitle())||StringUtil.isEmpty(blog.getDes())||StringUtil.isEmpty(blog.getContext())){
			throw new MyException("信息不全");
		}
		blogService.createBlog(blog, userId);
		return AjaxResult.getOK();
	}
	/**
	 * 更新博客
	 * @param blog
	 * @param request
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value="/updateBlog",method=RequestMethod.POST)
	public AjaxResult updateBlog(@RequestBody Blog blog,HttpServletRequest request){
		Integer userId=getNotNullUserId(request);
		if(StringUtil.isEmpty(blog.getTitle())||StringUtil.isEmpty(blog.getDes())||StringUtil.isEmpty(blog.getContext())||blog.getUserid()==null){
			throw new MyException("信息不全");
		}
		blogService.updateBlog(blog, userId);
		return AjaxResult.getOK();
	}
	/**
	 * 删除博客
	 * @param blogId
	 * @param request
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value="/deleteBlog",method=RequestMethod.POST)
	public AjaxResult deleteBlog(Integer blogId,HttpServletRequest request){
		Integer userId=getNotNullUserId(request);
		
		blogService.deleteBlog(blogId, userId);
		return AjaxResult.getOK();
	}
	/**
	 * 获取博客列表
	 * @param blogId
	 * @param request
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value="/getBlogList",method=RequestMethod.POST)
	public AjaxResult getBlogList(@RequestParam(value="userId",required=false)Integer userId,HttpServletRequest request){
		Integer id=getNotNullUserId(request);
		List<BlogDetails> blogDetailsList=new ArrayList<BlogDetails>();
		if(userService.getUserRoleList(id).contains(1)){
			if(userId==null){
				blogDetailsList=blogService.getUserBlogList(id);
			}else {
				blogDetailsList=blogService.getUserBlogList(userId);
			}
		}else {
			blogDetailsList=blogService.getUserBlogList(id);
		};
		
		return AjaxResult.getOK(blogDetailsList);
	}
	/**
	 * 获取博客详情
	 * @param blogId
	 * @param request
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value="/getBlogDetails",method=RequestMethod.POST)
	public AjaxResult getBlogDetails(Integer blogId){
		Blog blog= blogService.selectByPrimaryKey(blogId);
		return AjaxResult.getOK(blog);
	}
}

