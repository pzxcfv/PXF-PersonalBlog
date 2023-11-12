package com.blog.personalblog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * id 分类ID
 * categoryName 分类名字
 * categoryAlias
 * createUser 创建用户
 * createTime 创建时间
 * updateTime 更新时间
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /*
    分组校验 新增分类与修改分类参数校验需要分组，因为新增分类不需要传id
    没有确定分组的话，那么就是默认分组，默认分组可以被继承
     */
    public interface add extends Default {

    }
    public interface update extends Default{

    }
    @NotNull(groups = update.class)//只是不能为空
    private Integer id;
    @NotEmpty()//不能为空并且不能是空字符串
    private String categoryName;
    @NotEmpty()
    private String categoryAlias;
    private Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
