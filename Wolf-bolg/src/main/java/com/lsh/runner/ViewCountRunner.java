package com.lsh.runner;

import com.lsh.domain.entity.Article;
import com.lsh.mapper.ArticleMapper;
import com.lsh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 将文章的浏览器和id在项目启动时加入redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息   id  viewCount
        List<Article> articles = articleMapper.selectList(null);
//        key就是文章id  value是浏览量
        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article -> {return article.getId().toString();}, article -> {
            return article.getViewCount().intValue();
        }));
        // 存储到redis中
        redisCache.setCacheMap("article:viewCount",collect);
    }
}
