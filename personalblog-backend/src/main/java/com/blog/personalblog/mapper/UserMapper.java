package com.blog.personalblog.mapper;

import com.blog.personalblog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //添加用户登录数据
    @Insert("insert into user(username,password,create_time,update_time) " +
            "values(#{username},#{password},now(),now())")
    int register(String username, String password);


    //查询用户名是否存在
    @Select("select * from user where username=#{username}")
    User select(String username);

    //查询密码
    @Select("select password form user where username=#{username}")
    User selectPassword(String username);

    //更新用户数据
    @Update("update user set nickname=#{nickname},email=#{email},user_pic=#{userPic},update_time=#{updateTime} where id=#{id}")
    int updateUserInfo(User user);

    @Update("update user set user_pic=#{url},update_time=now() where id=#{id}")
    void updateAvatar(String url, Integer id);

    @Update("update user set password=#{newPWD},update_time=now() where id=#{id}")
    void updatePWD(String newPWD, Integer id);
}
