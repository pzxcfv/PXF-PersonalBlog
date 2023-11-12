package com.blog.personalblog.controller;

import com.blog.personalblog.pojo.User;
import com.blog.personalblog.service.UserService;
import com.blog.personalblog.utils.JwtUtil;
import com.blog.personalblog.utils.Md5Util;
import com.blog.personalblog.utils.ThreadLocalUtil;
import com.blog.personalblog.vo.Result;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //用户注册接口开发
    @PostMapping("/register")
    public Result registry(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //检查该用户是否存在
        User user = userService.selectByUsername(username);
        //如果存在则提示存在，不存在，那么就直接注册
        if (user == null) {
            //没有被占用，直接注册
            int i = userService.insertUserData(username, password);
            System.out.println("被影响的行数" + i);
            return Result.Success();
        } else {
            //提醒用户更换用户名
            return Result.Fail("用户名被占用，请重新更换用户名");
        }
    }

    //用户登录接口
    @PostMapping("/login")
    public Result login(String username, String password) {
//        先查询user是否存在
        User loginUser = userService.selectByUsername(username);
        if (loginUser != null) {
//            存在的话验证码密码
            String passwd = loginUser.getPassword();
            //先对参数密码进加密操作
            if (Md5Util.getMD5String(password).equals(passwd)) {
                //登录成功返回jwt令牌
                //生成jwt
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", loginUser.getId());
                claims.put("username", loginUser.getUsername());
                String jwtTOKEN = JwtUtil.genToken(claims);
                //将jwt令牌存储到Redis中
                ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
                stringStringValueOperations.set(jwtTOKEN, jwtTOKEN, 1, TimeUnit.HOURS);

                return Result.Success(jwtTOKEN);
            } else {
                return Result.Fail("密码错误");
            }
        } else {
            return Result.Fail("用户不存在，请注册");
        }

    }

    //用户基本信息
    @GetMapping("/userinfo")
    public Result<User> userInfo() {
        //从ThreadLocal中获取信息
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.selectByUsername(username);
        return Result.Success(user);
    }


    //用户基本信息更新
    @PutMapping("/update")
    public Result<User> update(@RequestBody @Validated User user) {
        int i = userService.updateUserInfo(user);
        System.out.println("被影响的行数" + i);
        return Result.Success();

    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String url) {
            userService.updateAvatar(url);
        return Result.Success();
    }

    //重置用户密码
    @PatchMapping("/updatePWD")
    public Result updatePWD(@RequestBody Map<String, Object> params, @RequestHeader(name = "Authorization") String token) {
        System.out.println("该接口被访问");
        //首先校验参数是否为空
        String oldPWD = (String) params.get("oldPWD");
        String newPWD = (String) params.get("newPWD");
        String rePWD = (String) params.get("rePWD");
        if (!StringUtils.hasLength(oldPWD) || !StringUtils.hasLength(newPWD) || !StringUtils.hasLength(rePWD)) {
            return Result.Fail("旧密码与新密码不能为空");
        }
        if (!newPWD.equals(rePWD)) {
            return Result.Fail("两次密码输入不一致");
        }
        //然后判断旧密码是否正确
        //使用ThreadLocal中的username进行确认
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.selectByUsername(username);
        if (!Md5Util.getMD5String(oldPWD).equals(user.getPassword())) {
            return Result.Fail("原密码不正确");
        }
        //判断成功可以继续修改密码
        userService.updatePWD(newPWD);
        //密码修改成功后需要重新登录，删除redis中的token
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        RedisOperations<String, String> operations = stringStringValueOperations.getOperations();
        operations.delete(token);

        return Result.Success();
    }


}
