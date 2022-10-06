package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.AddArticleDto;
import com.lsh.domain.dto.ArticleListDto;
import com.lsh.domain.entity.Article;
import com.lsh.service.ArticleService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/content/article")
public class articleController {
    /**
     * 文章列表
     */
    @Autowired
    private ArticleService articleService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.pageArticleList(pageNum,pageSize,articleListDto);
    }
    @PostMapping
        public ResponseResult add(@RequestBody AddArticleDto addArticleDto){

        return articleService.add(addArticleDto);

        }

}
