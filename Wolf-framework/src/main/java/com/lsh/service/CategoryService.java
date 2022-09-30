package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-09-30 10:08:55
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

