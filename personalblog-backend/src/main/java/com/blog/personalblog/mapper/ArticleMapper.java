package com.blog.personalblog.mapper;

import com.blog.personalblog.pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addArticle(Article article);

    List<Article> articleList(Integer categoryId, String state, Integer userId);

    @Select("select id,title, content, cover_img, state, category_id, create_user, create_time, update_time from article where id=#{id}")
    Article selectDetail(Integer id);


    void updateArticle(Article article);

    @Delete("delete from article where id=#{id}")
    void deleteArticle(Integer id);
}
