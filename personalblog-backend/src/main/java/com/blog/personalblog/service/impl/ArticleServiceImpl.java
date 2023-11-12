package com.blog.personalblog.service.impl;

import com.blog.personalblog.mapper.ArticleMapper;
import com.blog.personalblog.pojo.Article;
import com.blog.personalblog.service.ArticleService;
import com.blog.personalblog.utils.ThreadLocalUtil;
import com.blog.personalblog.vo.PageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;


    public void addArticle(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.addArticle(article);
    }

    public PageBean<Article> articleList(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象封装数据结果
        PageBean<Article> pageBean = new PageBean<>();

        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper查询
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> articleList = articleMapper.articleList(categoryId, state,userId);
        //page中提供了方法，可以获取分页查询后记录的总条数和当前页数据
        Page<Article> page = (Page<Article>) articleList;
        //为pageBean填充数据
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());

        return pageBean;
    }

    @Override
    public Article selectDetail(Integer id) {
        return articleMapper.selectDetail(id);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(Integer id) {
        articleMapper.deleteArticle(id);
    }
}
