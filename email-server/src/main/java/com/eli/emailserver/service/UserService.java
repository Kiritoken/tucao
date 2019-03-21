package com.eli.emailserver.service;

import com.eli.emailserver.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    /**
     * 根据ID返回用户信息
     * @param userName  用户
     * @return  用户信息
     */
    @Transactional
    User getUserByName(String userName);


    /**
     * 判断用户名为username的用户是否存在
     * @param userName unique的用户名
     * @return true已经存在 false不存在
     */
    Boolean isExistByUserName(String userName);


    /**
     * 创建用户user
     * @param user 用户bean
     * @return 是否创建成功
     */
    Boolean createUser(User user);

    Boolean updateUser(User user);
}
