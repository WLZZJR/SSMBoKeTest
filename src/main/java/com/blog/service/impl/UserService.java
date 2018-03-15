package com.blog.service.impl;

import com.blog.dao.RoleDao;
import com.blog.dao.UserDao;
import com.blog.dao.UserRoleDao;
import com.blog.dto.input.UserEditDetails;
import com.blog.dto.output.UserDetails;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.service.IUserService;
import com.core.dao.IBaseDao;
import com.core.exception.MyException;
import com.core.service.BaseService;
import com.core.utils.MD5Util;
import com.core.utils.StringUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService extends BaseService<User> implements IUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public IBaseDao<User> getBaseDao() {
        return userDao;
    }

    /**
     * 获取会员详情
     */
    @Override
    public UserDetails getUserDetails(Integer userId) {
        UserDetails userDetails = userDao.getUserDetailsById(userId);
        if (userDetails == null) {
            throw new MyException("该会员信息不存在");
        }
        List<Role> roles = roleDao.getRoleListByUserId(userId);
        userDetails.setRoles(roles);
        return userDetails;
    }

    /**
     * 查询特定等级，用户名的会员信息
     */
    @Override
    public List<User> getUserList(Integer level, String username,RowBounds rowBounds) {
        List<User> userList = userDao.getUserListByLevel(level, username);
        //com.github.pagehelper.PageInfo<User> uInfo=new com.github.pagehelper.PageInfo<User>(userList);
        //这里的分页实在太坑，暂时先set进去吧，我知道这样不对，但是搞了半天还是没搞定(。﹏。*)

        return userList;
    }

    /**
     * 更新会员信息（管理员的话可以修改会员的角色信息）
     */
    @Override
    public void updateUserDetails(Integer userId,UserEditDetails userEditDetails) {
        List<Integer> roleIdList = userRoleDao.getRoleIdListByUserId(userId);
        User userTempUser=userEditDetails.getUser();
        User user = userDao.selectByPrimaryKey(userTempUser.getId());
        if (user == null) {
            throw new MyException("该用户不存在");
        }
        if (roleIdList.contains(1)) {
            // 管理员（修改角色信息）
            if (!StringUtil.isEmpty(userEditDetails.getRoleIds())) {
                // 1获取该用户原有角色
                List<Integer> roleIdListPre = userRoleDao
                        .getRoleIdListByUserId(userTempUser.getId());
                // 2获取前台传给的角色列表
                List<String> roldeIdStrings = StringUtil.splitToList(null,
                        userEditDetails.getRoleIds());
                List<Integer> roleIdIntegers = new ArrayList<Integer>();
                // 需要增加的角色
                List<Integer> roleIdAdds = new ArrayList<Integer>();
                // 需要删除的角色
                List<Integer> roleIdDeletes = new ArrayList<Integer>();
                for (String idString : roldeIdStrings) {
                    roleIdIntegers.add(StringUtil.toInt(idString));
                }
                for (Integer roleId : roleIdIntegers) {
                    if (!roleIdListPre.contains(roleId)) {
                        roleIdAdds.add(roleId);
                    }
                }
                for (Integer roleId : roleIdListPre) {
                    if (!roleIdIntegers.contains(roleId)) {
                        roleIdDeletes.add(roleId);
                    }
                }
                //批量增加
                if(!roleIdAdds.isEmpty()){
                    userRoleDao.addUserRoleList(userTempUser.getId(), roleIdAdds);
                }
                if(!roleIdDeletes.isEmpty()){
                    userRoleDao.deleteUserRoleList(userTempUser.getId(), roleIdDeletes);
                }
            }
            //管理员可以修改的用户基础信息
            if(userTempUser.getLevel()!=null){
                user.setLevel(userTempUser.getLevel());
            }
            if(userTempUser.getName()!=null&&!userTempUser.getName().equals(user.getName())){
                //确保唯一性
                if(userDao.getUserNameCount(userTempUser.getName())>0){
                    user.setName(userTempUser.getName());
                }
            }
        }
        //管理员与管理员都可以修改的部分：
        if(!StringUtil.isEmpty(userTempUser.getDes())){
            user.setDes(userTempUser.getDes());
        }
        if(!StringUtil.isEmpty(userTempUser.getAddress())){
            user.setAddress(userTempUser.getAddress());
        }
        if(!StringUtil.isEmpty(userTempUser.getTel())){
            user.setTel(userTempUser.getTel());
        }
        if(!StringUtil.isEmpty(userTempUser.getPassword())){
            if(!user.getPassword().equals(MD5Util.getMD5(userTempUser.getPassword().getBytes()))){
                user.setPassword(MD5Util.getMD5(userTempUser.getPassword().getBytes()));
            }

        }
        userDao.updateByPrimaryKeySelective(user);
    }
    /**
     * 批量删除会员
     */
    @Override
    public void deleteUser(String ids) {
        List<String> idListStrings=StringUtil.splitToList(null, ids);
        List<Integer> idList=new ArrayList<Integer>();
        for(String id:idListStrings){
            idList.add(StringUtil.toInt(id));
        }
        if(!idList.isEmpty()){
            //删除会员及对应角色
            userDao.deleteByIdList(idList);
            userRoleDao.deleteByUserIdList(idList);
        }
    }
    /**
     * 登录
     */
    @Override
    public UserDetails login(String username, String password) {
        User user=userDao.getByUserName(username);
        if(user.getPassword().equals(MD5Util.getMD5(password.getBytes()))){
            return getUserDetails(user.getId());
        }
        System.out.println("密码不匹配");
        return null;
    }
    /**
     * 创建新用户
     */
    @Override
    public void createUser(String username, String password, String des,
                           String tel, String address) {
        if(userDao.getUserNameCount(username)>0){
            throw new MyException("该用户名已经存在");
        }
        User user=new User();
        user.setName(username);
        user.setPassword(MD5Util.getMD5(password.getBytes()));
        user.setTel(tel);
        user.setDes(des);
        user.setLevel(1);
        user.setAddress(address);
        userDao.insertSelective(user);
    }

    @Override
    public List<Role> getRoles(Integer id) {

        return roleDao.getRoleList(id);
    }

    @Override
    public void addRole(String roleName) {
        if(roleDao.getNameCount(roleName)>0){
            throw new MyException("该角色名已经存在");
        }
        Role role=new Role();
        role.setName(roleName);
        roleDao.insertSelective(role);
    }

    @Override
    public List<Integer> getUserRoleList(Integer userId) {
        return userRoleDao.getRoleIdListByUserId(userId);
    }

}