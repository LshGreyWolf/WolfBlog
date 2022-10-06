package com.lsh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsh.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-05 20:27:27
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}

