package com.blog.personalblog.pojo;

import com.blog.personalblog.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

/**
 * id 文章ID
 * title 文章标题
 * content 文章内容
 * coverImg 封面图片
 * state 状态
 * categoryId 文章ID
 * createUser 创建用户
 * createTime 创建时间
 * updateTime 更新时间
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @NotNull(groups = update.class)
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,20}$")
    private String title;
    @NotEmpty
    private String content;
    @URL
    private String coverImg;
    //state的值必须为“已发布”|“草稿”其中一项，新的方式进行参数校验，自定义校验(已有的注解不能完成对特殊情况的校验)
    @State(groups = {add.class, update.class})
    private String state;
    @NotNull(groups = add.class)
    private Integer categoryId;
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public interface add extends Default {

    }

    public interface update extends Default {

    }
}
