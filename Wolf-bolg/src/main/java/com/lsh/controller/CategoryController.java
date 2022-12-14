package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类文章的查询
     * @return
     */
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();

    }
}
