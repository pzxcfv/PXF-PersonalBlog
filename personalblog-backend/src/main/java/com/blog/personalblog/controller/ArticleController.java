package com.blog.personalblog.controller;

import com.blog.personalblog.pojo.Article;
import com.blog.personalblog.service.ArticleService;
import com.blog.personalblog.vo.PageBean;
import com.blog.personalblog.vo.Result;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/article")
@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<PageBean<Article>> list(@NotNull Integer pageNum, @NotNull Integer pageSize, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false)String state) {
        PageBean<Article> articleList = articleService.articleList(pageNum,pageSize,categoryId,state);
        return Result.Success(articleList);
    }
    @PostMapping("/add")
    public Result addArticle(@RequestBody @Validated(value= Article.add.class) Article article){
        articleService.addArticle(article);
        return Result.Success();
    }
    @GetMapping("/detail")
    public Result ArticleDetail(@RequestParam(name="id") Integer id){

        Article article = articleService.selectDetail(id);
        return Result.Success(article);
    }
    @PutMapping("/update")
    public Result UpdateArticle(@RequestBody @Validated(value = Article.update.class) Article article){
        articleService.updateArticle(article);
        return Result.Success();
    }
    @DeleteMapping("/delete")
    public Result DeleteArticle(Integer id){
        articleService.deleteArticle(id);
        return Result.Success();
    }
}
