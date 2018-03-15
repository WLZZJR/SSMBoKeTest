package com.blog.dao;


import com.blog.dto.output.UserDetails;
import com.blog.entity.User;
import com.core.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao extends IBaseDao<User> {

	//通过会员id获取其详情
   public UserDetails getUserDetailsById(Integer id);

    //通过等级，用户名（模糊查询）获取会员列表
   public List<User> getUserListByLevel(@Param("level") Integer level, @Param("username") String username);
   //批量删除
   public void deleteByIdList(@Param("idList") List<Integer> idList);
   //查询一个用户名的
   public Integer getUserNameCount(String username);
   //根据用户名查询
   public User getByUserName(String username);
}