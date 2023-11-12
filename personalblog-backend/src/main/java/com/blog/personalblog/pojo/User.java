package com.blog.personalblog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * id 用户ID
 * userName 用户名
 * password 用户密码
 * nickname 网名昵称
 * email 用户邮箱
 * userPic 用户头像地址
 * createTime 创建时间
 * updateTime 更新时间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    private Integer id;
    private String username;
    @JsonIgnore//让springmvc将对象转换成json的时候忽略password
    private String password;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
