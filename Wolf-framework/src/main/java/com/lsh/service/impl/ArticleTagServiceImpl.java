package com.lsh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.domain.entity.ArticleTag;
import com.lsh.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;
import com.lsh.service.ArticleTagService;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-05 20:27:36
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

