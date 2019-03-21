package com.eli.emailserver.dao;

import com.eli.emailserver.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * PermissionMapper继承基类
 */
@Repository
public interface PermissionMapper extends MyBatisBaseDao<Permission, Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(Permission record);

    @Override
    int insertSelective(Permission record);

    @Override
    Permission selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(Permission record);

    @Override
    int updateByPrimaryKey(Permission record);

    Set<Permission> getPermissionListByRoleId(int roleId);
}