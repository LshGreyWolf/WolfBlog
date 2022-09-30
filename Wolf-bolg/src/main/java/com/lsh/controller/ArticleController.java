package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Article;
import com.lsh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
   @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){


       return articleService.hotArticleList();
    }
}
