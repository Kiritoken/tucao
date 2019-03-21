package com.eli.emailserver.dao;

import com.eli.emailserver.entity.Role;
import com.eli.emailserver.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * RoleMapper继承基类
 */
@Repository
public interface RoleMapper extends MyBatisBaseDao<Role, Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(Role record);

    @Override
    int insertSelective(Role record);

    @Override
    Role selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(Role record);

    @Override
    int updateByPrimaryKey(Role record);

    Set<Role> getRoleListByUser(User user);
}