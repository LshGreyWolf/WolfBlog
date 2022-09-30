package com.lsh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;
    private String title;
//    //摘要
//    private String summary;
    //缩略图
    private String thumbnail;
    //数据库是分类id，但是要传给前端的是分类名称
    private String categoryName;
    private Long categoryId;
    private Long viewCount;
    private Date createTime;
    //文章内容
    private String content;
}
