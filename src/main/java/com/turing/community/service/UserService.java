package com.turing.community.service;

import com.turing.community.bean.User;

/**
 * @author Meng
 * @date 2019/8/17
 */
public interface UserService {
    boolean addUser(User user);

    boolean registerUser(User user);

    User selectByUserId(Integer userId);

    boolean equalEmailCode(User user);

    boolean isRegister(Integer userId);

}
