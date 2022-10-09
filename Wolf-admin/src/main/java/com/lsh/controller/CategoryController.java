package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.CategoryListDto;
import com.lsh.domain.entity.Category;
import com.lsh.domain.vo.CategoryVo;
import com.lsh.service.CategoryService;
import com.lsh.utils.BeanCopyUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.net.idn.Punycode;

import javax.management.RuntimeErrorException;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类
     *
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    /**
     * 分页查询分类数据
     *
     * @param pageNum
     * @param pageSize
     * @param categoryListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listPageCategory(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        return categoryService.listPageCategory(pageNum, pageSize, categoryListDto);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryListDto categoryListDto) {
        Category category = BeanCopyUtils.copyBean(categoryListDto, Category.class);
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    /**
     * 回显分类
     *
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable Long id) {

        Category category = categoryService.getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    /**
     * 更新分类
     * @param categoryListDto
     * @return
     */
    @PutMapping
    public ResponseResult saveCategory(@RequestBody CategoryListDto categoryListDto) {

        Category category = BeanCopyUtils.copyBean(categoryListDto, Category.class);
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    /**
     * 删除分类
     */

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }
}
