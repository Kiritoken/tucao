package com.eli.emailserver.service.Impl;

import com.eli.emailserver.dao.UserMapper;
import com.eli.emailserver.entity.User;
import com.eli.emailserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUserByName(String name) {
        return userMapper.selectByUserName(name);
    }

    @Override
    public Boolean isExistByUserName(String userName) {
        User user = getUserByName(userName);
        if( null == user ){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Boolean createUser(User user) {
        return  (userMapper.insert(user) !=0) ;
    }

    @Override
    public Boolean updateUser(User user) {
        return (userMapper.updateByPrimaryKeySelective(user)!=0);
    }
}
