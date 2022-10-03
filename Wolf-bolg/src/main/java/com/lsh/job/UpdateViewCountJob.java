package com.lsh.job;

import com.lsh.domain.entity.Article;
import com.lsh.service.ArticleService;
import com.lsh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时从redis更新mysql
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron ="0/59 0/10 * * * ? " )
    public void updateViewCount(){
        System.out.println("定时任务执行了。。");
        //获取redis中的浏览量  key就是文章id  value是浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
        //从redis中获取的是mqp集合，但是更新数据库的参数应该是list集合
        List<Article> collect = cacheMap.entrySet().stream()
                //增加一个Article的构造方法
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //更新到数据库
        articleService.updateBatchById(collect);
    }


}
