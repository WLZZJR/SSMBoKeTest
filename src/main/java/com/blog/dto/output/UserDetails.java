package com.blog.dto.output;

import com.blog.entity.Role;
import com.blog.entity.User;

import java.util.List;

public class UserDetails extends User {

    /**
     * 角色信息
     */
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
