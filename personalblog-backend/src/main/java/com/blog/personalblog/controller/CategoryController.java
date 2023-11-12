package com.blog.personalblog.controller;

import com.blog.personalblog.pojo.Category;
import com.blog.personalblog.service.CategoryService;
import com.blog.personalblog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated(value = Category.add.class) Category category) {

        categoryService.add(category);
        return Result.Success();
    }

    @GetMapping("/add")
    public Result<List<Category>> categoryList() {
        return Result.Success(categoryService.categoryList());
    }

    @GetMapping("detail")
    public Result<Category> detail(@RequestParam(name = "id") String listId) {

        return Result.Success(categoryService.selectDetail(listId));
    }

    @PutMapping("/add")
    public Result update(@RequestBody @Validated(value = {Category.update.class}) Category category) {
        categoryService.update(category);
        return Result.Success();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam(name = "id") String id) {
        categoryService.deleteById(id);
        return Result.Success();

    }

}
