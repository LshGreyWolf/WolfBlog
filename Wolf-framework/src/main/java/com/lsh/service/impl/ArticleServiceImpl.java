package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.Constants.SystemConstants;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.vo.HotArticleVo;
import com.lsh.mapper.ArticleMapper;
import com.lsh.service.ArticleService;
import com.lsh.domain.entity.Article;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
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
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);

//                articles.stream().map((item)->{
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(item,vo);
//
//            return vo;
//        }).collect(Collectors.toList());
//         bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }

        return ResponseResult.okResult(hotArticleVos);
    }
}
