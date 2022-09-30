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

    /**
     * 热门文章列表查询
     * @return
     */
   @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //get请求，且不带任何参数
       return articleService.hotArticleList();
    }

    /**
     * 主页的文章分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
       return articleService.articleList(pageNum,pageSize,categoryId);
    }
}
