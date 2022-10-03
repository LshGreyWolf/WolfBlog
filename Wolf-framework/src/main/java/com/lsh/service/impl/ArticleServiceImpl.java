package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.Constants.SystemConstants;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Category;
import com.lsh.domain.vo.ArticleDetailVo;
import com.lsh.domain.vo.ArticleListVo;
import com.lsh.domain.vo.HotArticleVo;
import com.lsh.domain.vo.PageVo;
import com.lsh.mapper.ArticleMapper;
import com.lsh.mapper.CategoryMapper;
import com.lsh.service.ArticleService;
import com.lsh.domain.entity.Article;
import com.lsh.service.CategoryService;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.RedisCache;
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
    @Autowired
    private RedisCache redisCache;

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
        //从redis中获取浏览量
        for (ArticleListVo articleListVo: articleListVos){
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", articleListVo.getId().toString());
            articleListVo.setViewCount(viewCount.longValue());
        }

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 获取文章详情
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id ) {
        //根据id查询文章
        Article article = articleMapper.selectById(id);
        //从redis中获取浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转为VO    由于查出的文章只有分类id没有分类名字，但是articleDetailVo中有分类名字这个字段  所以需要查询分类名称
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryMapper.selectById(categoryId);
        String categoryName = category.getName();
        if (category!=null){
            articleDetailVo.setCategoryName(categoryName);
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);

        return null;
    }
}
