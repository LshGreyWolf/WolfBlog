package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.AddArticleDto;
import com.lsh.domain.dto.ArticleListDto;
import com.lsh.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);


    ResponseResult add(AddArticleDto addArticleDto);
}
