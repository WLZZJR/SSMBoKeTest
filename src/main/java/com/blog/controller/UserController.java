package com.blog.controller;

import com.blog.dto.input.UserEditDetails;
import com.blog.dto.output.UserDetails;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.service.impl.UserService;
import com.core.ajaxResult.AjaxResult;
import com.core.controller.BaseController;
import com.core.exception.MyException;
import com.core.utils.MD5Util;
import com.core.utils.StringUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String getByController(String id){
        return "Hello";
    }


    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ModelAndView getTeemo(Integer id){

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("userName",userService.selectByPrimaryKey(id));
        modelAndView.addObject("userDesc",userService.selectByPrimaryKey(id));
        modelAndView.setViewName("user");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    public AjaxResult getById(Integer id) {

        return AjaxResult.getOK(userService.selectByPrimaryKey(id));
    }

    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public AjaxResult addUser(@RequestBody User user) {
        if (user.getPassword() == null) {
            throw new MyException("密码不能为空");
        }
        // 对密码进行MD5加密
        user.setPassword(MD5Util.getMD5(user.getPassword().getBytes()));
        userService.insertSelective(user);
        return AjaxResult.getOK();
    }

    /**
     * 会员登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(String username,String password){
        UserDetails userDetails = userService.login(username, password);
        if (userDetails==null){
            return AjaxResult.getOK("用户名或密码错误",userDetails);
        }

        return AjaxResult.getOK(userDetails);
    }

    /**
     * 会员注册
     * @param username
     * @param password
     * @param des
     * @param tel
     * @param address
     * @return
     */
    public AjaxResult createUser(String username, String password,
                                 String des, String tel, String address){
        userService.createUser(username, password, des, tel, address);
        return AjaxResult.getOK();
    }


    /**
     * 获取会员信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserDetails",method = RequestMethod.POST)
    public AjaxResult getUserDetails(@RequestParam(value = "id")Integer id){

        if(id==null){
            throw new MyException("id不能空");
        }
        UserDetails details=userService.getUserDetails(id);

        return AjaxResult.getOK(details);
    }

    /**
     * 获取会员列表
     * @param level
     * @param username
     * @param pageIndex
     * @param pageSize
     * @return
     */

    public AjaxResult getUserList(
            @RequestParam(value = "level",required = false)Integer level,
            @RequestParam(value = "username",required = false)String username,
            @RequestParam(value = "pageIndex",defaultValue = "1")Integer pageIndex,
            @RequestParam(value = "pageSize",defaultValue = "20")Integer pageSize
    ){
        List<User> list=userService.getUserList(level,username,new  RowBounds(pageIndex,pageSize));

        return AjaxResult.getOK(list);
    }

    /**
     * 修改用户信息
     * @param userEditDetails
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserDetails",method = RequestMethod.POST)
    public AjaxResult updateUserDetails(
            @RequestBody UserEditDetails userEditDetails,
            HttpServletRequest request
            ){
        Integer userId=getNotNullUserId(request);

        if(userEditDetails.getUser()==null||userEditDetails.getUser().getId()==null){
            throw new MyException("要修改的用户不能为空");
        }
        userService.updateUserDetails(userId, userEditDetails);
        return AjaxResult.getOK();

    }

    /**
     * 删除用户信息
     *
     * @param ids
     *            用户id，多个用,隔开
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteUserList", method = RequestMethod.POST)
    public AjaxResult delteUserList(String ids) {

        if (StringUtil.isEmpty(ids)) {
            throw new MyException("要删除的用户id不能为空");
        }
        userService.deleteUser(ids);
        return AjaxResult.getOK();
    }

    /**
     * 获取角色信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRoles",method = RequestMethod.POST)
    public AjaxResult getRoles(Integer id){

        List<Role> roles=userService.getRoles(id);
        return AjaxResult.getOK(roles);
    }

    /**
     * 增加角色
     * @param roleName 角色名称
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public AjaxResult addRole(String roleName,HttpServletRequest request) {
        Integer userId=getNotNullUserId(request);
        if(!userId.equals(1)){
            throw new MyException("非管理员不能增加角色");
        }
        userService.addRole(roleName);
        return AjaxResult.getOK();
    }


}
