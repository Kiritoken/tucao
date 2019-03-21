package com.eli.emailserver.service;

import com.eli.emailserver.entity.Permission;

import java.util.Set;

public interface PermissionService {

    Set<Permission> getPermissionListByRoleId(int roleId);
}
