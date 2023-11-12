package com.blog.personalblog.service.impl;

import com.blog.personalblog.mapper.CategoryMapper;
import com.blog.personalblog.pojo.Category;
import com.blog.personalblog.service.CategoryService;
import com.blog.personalblog.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {

        //补充属性值
        //如何获取创建者的id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    @Override
    public List<Category> categoryList() {
        //如何获取创建者的id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.selectList(userId);
    }

    @Override
    public Category selectDetail(String id) {
        Category category = categoryMapper.selectDetail(id);
        return category;
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void deleteById(String id) {
        //根据id查询用户id,只有创建该分类名的创建者才有资格删除该分类名
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Integer createUserId = categoryMapper.selectDetail(id).getCreateUser();
        if(userId.equals(createUserId)){
            categoryMapper.deleteById(id);
        }
    }
}
