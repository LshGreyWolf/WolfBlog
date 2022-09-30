package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.Constants.SystemConstants;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Article;
import com.lsh.domain.entity.Category;
import com.lsh.domain.vo.CategoryVo;
import com.lsh.mapper.ArticleMapper;
import com.lsh.mapper.CategoryMapper;
import com.lsh.service.ArticleService;
import com.lsh.service.CategoryService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-09-30 10:08:57
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 分类文章的查询
     *
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {

        //查询文章表，状态为正常  select distinct category_id from sg_article where status = 0
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //List<Article> articleList = articleService.list(queryWrapper);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        //获取文章的分类id并去重  set不可重复  通过stream流对获取到的正常文章列表 取出其分类id 并用Set收集
        Set<Long> categoryIds = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        //得到分类id的集合 分类的状态也为正常
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //select sg_category.id from sg_category where sg_category.status = 0
        wrapper.eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> categories = categoryMapper.selectList(wrapper);
        //再从正常的分类id集合中查询正常的分类
        //select id from sg_category where id = sg_article.categoryId
        categories = categoryMapper.selectBatchIds(categoryIds);
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

