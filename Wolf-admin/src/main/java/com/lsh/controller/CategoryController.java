package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.CategoryListDto;
import com.lsh.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
@Autowired
private CategoryService categoryService;

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    /**
     * 分页查询分类数据
     * @param pageNum
     * @param pageSize
     * @param categoryListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listPageCategory(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto){
        return categoryService.listPageCategory(pageNum,pageSize,categoryListDto);


    }

}
