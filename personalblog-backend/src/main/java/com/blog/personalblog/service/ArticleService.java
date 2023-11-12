package com.blog.personalblog.service;

import com.blog.personalblog.pojo.Article;
import com.blog.personalblog.vo.PageBean;

public interface ArticleService {
    void addArticle(Article article);

    PageBean<Article> articleList(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article selectDetail(Integer id);

    void updateArticle(Article article);

    void deleteArticle(Integer id);
}
