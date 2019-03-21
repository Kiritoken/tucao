package com.eli.emailserver.service;

import com.eli.emailserver.entity.Role;
import com.eli.emailserver.entity.User;

import java.util.Set;


public interface RoleService {

    /**
     * 返回user的所有角色
     * @param user 用户
     * @return 角色列表
     */
    Set<Role> getRoleListByUser(User user);
}
