package com.lsh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {
    //文章id
    private Long id ;
//    文章标题
    private String title;
    //文章浏览量
    private Long viewCount;
}
