package com.blog.personalblog.service;

import com.blog.personalblog.pojo.User;

/**
 * selectByUsername 查询
 * InsertUserData 插入用户数据，用于快速注册
 */

public interface UserService {
    User selectByUsername(String username);

    int insertUserData(String username, String password);

    int updateUserInfo(User user);

    void updateAvatar(String url);

    void updatePWD(String newPWD);
}
