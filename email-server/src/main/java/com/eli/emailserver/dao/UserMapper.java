package com.eli.emailserver.dao;

import com.eli.emailserver.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMapper继承基类
 */
@Repository
public interface UserMapper extends MyBatisBaseDao<User, Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(User record);

    @Override
    int insertSelective(User record);

    @Override
    User selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(User record);

    @Override
    int updateByPrimaryKey(User record);

    User selectByUserName(String username);


    List<User> getUserList();

}