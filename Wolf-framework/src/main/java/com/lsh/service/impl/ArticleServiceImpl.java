package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.Constants.SystemConstants;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Category;
import com.lsh.domain.vo.ArticleListVo;
import com.lsh.domain.vo.HotArticleVo;
import com.lsh.domain.vo.PageVo;
import com.lsh.mapper.ArticleMapper;
import com.lsh.mapper.CategoryMapper;
import com.lsh.service.ArticleService;
import com.lsh.domain.entity.Article;
import com.lsh.service.CategoryService;
import com.lsh.utils.BeanCopyUtils;
import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Lazy
    @Autowired
    private CategoryService categoryService;

    /**
     * 热门文章列表查询
     *
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章  封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章，不是草稿   按照浏览量排序，显示前十条
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //筛选前十条
        Page<Article> page = new Page<>(1, 10);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> articles = articlePage.getRecords();
        //bean拷贝        调用自动难以的utils包的工具类来实现bean拷贝
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);


        return ResponseResult.okResult(hotArticleVos);
    }

    /**
     * 主页的文章分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //判断categoryId是否为null
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //状态是正式发布的
        //按照isTop降序
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);

        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> articles = page.getRecords();

        //查询分类名称  不清楚哪里的错误--循环依赖  百度后再任意一个加上@Lazy即可
        articles=articles.stream().map((article)->{
            Long categoryId1 = article.getCategoryId();
            Category category = categoryMapper.selectById(categoryId1);
            String categoryName = category.getName();
            article.setCategoryName(categoryName);
            return article;
        }).collect(Collectors.toList());

        //封装查询结果   要使用两次vo 一次封装row的 一次封装 row 和 total
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
