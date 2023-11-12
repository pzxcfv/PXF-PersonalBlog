package com.blog.personalblog.service.impl;

import com.blog.personalblog.mapper.UserMapper;
import com.blog.personalblog.pojo.User;
import com.blog.personalblog.service.UserService;
import com.blog.personalblog.utils.Md5Util;
import com.blog.personalblog.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {
        return userMapper.select(username);
    }

    @Override
    public int insertUserData(String username, String password) {
        //先对密码进加密操作
        String md5String = Md5Util.getMD5String(password);
        int count = userMapper.register(username, md5String);
        return count;
    }

    @Override
    public int updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        int i = userMapper.updateUserInfo(user);
        return i;
    }

    @Override
    public void updateAvatar(String url) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(url, id);
    }

    @Override
    public void updatePWD(String newPWD) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String md5String = Md5Util.getMD5String(newPWD);
        userMapper.updatePWD(md5String, id);
    }
}
