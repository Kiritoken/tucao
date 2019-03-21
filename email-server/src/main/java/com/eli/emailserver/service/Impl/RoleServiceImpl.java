package com.eli.emailserver.service.Impl;

import com.eli.emailserver.dao.RoleMapper;
import com.eli.emailserver.entity.Role;
import com.eli.emailserver.entity.User;
import com.eli.emailserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper mapper;

    @Override
    public Set<Role> getRoleListByUser(User user) {
        return mapper.getRoleListByUser(user);
    }
}
