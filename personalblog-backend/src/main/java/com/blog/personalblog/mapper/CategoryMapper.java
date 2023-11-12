package com.blog.personalblog.mapper;

import com.blog.personalblog.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //新增文章分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);
    //分类列表查询
    @Select("select * from category where create_user=#{userId}")
    List<Category> selectList(Integer userId);

    //通过id查询
    @Select("select * from category where id=#{id}")
    Category selectDetail(String id);

    //更新分类名
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id=#{id}")
    void update(Category category);

    //根据id返回userId
    @Select("select create_user from category where id=#{id}")
    Integer selectById(String id);

    //删除分类名
    @Delete("delete from category where id=#{id}")
    void deleteById(String id);
}
