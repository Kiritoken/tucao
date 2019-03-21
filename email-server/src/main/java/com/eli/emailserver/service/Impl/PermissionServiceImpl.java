package com.eli.emailserver.service.Impl;

import com.eli.emailserver.dao.PermissionMapper;
import com.eli.emailserver.entity.Permission;
import com.eli.emailserver.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper mapper;


    @Override
    public Set<Permission> getPermissionListByRoleId(int roleId) {
        return mapper.getPermissionListByRoleId(roleId);
    }
}
