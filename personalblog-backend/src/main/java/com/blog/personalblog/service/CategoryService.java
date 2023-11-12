package com.blog.personalblog.service;

import com.blog.personalblog.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> categoryList();

    Category selectDetail(String id);

    void update(Category category);

    void deleteById(String id);
}
