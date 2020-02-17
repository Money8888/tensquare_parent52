package com.tensquare.articlecrawler.task;

import com.tensquare.articlecrawler.pipeline.ArticleDbPipeline;
import com.tensquare.articlecrawler.pipeline.ArticleTextPipeline;
import com.tensquare.articlecrawler.processor.ArticleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 文章任务类
 * 用于爬取文章
 */

@Component
public class ArticleTask {

    @Autowired
    private ArticleProcessor articleProcessor;

    @Autowired
    private ArticleDbPipeline articleDbPipeline;

    @Autowired
    private ArticleTextPipeline articleTextPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    /**
     * 爬取AI文章
     */
    @Scheduled(cron = "0 0/2 23 * * ?")
    public void archTask(){
        System.out.println("爬取架构文章");
        Spider spider =Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/arch");
        articleDbPipeline.setChannelId("arch");
        articleTextPipeline.setChannelId("arch");
        spider.addPipeline(articleDbPipeline);
        spider.addPipeline(articleTextPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }

    /**
     * 爬取db文章
     */
    @Scheduled(cron = "0 0/2 2 * * ?")
    public void dbTask(){
        System.out.println("爬取DB文章");
        Spider spider =Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/db");
        articleDbPipeline.setChannelId("db");
        articleTextPipeline.setChannelId("db");
        spider.addPipeline(articleDbPipeline);
        spider.addPipeline(articleTextPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }

    /**
     * 爬取web文章
     */
    @Scheduled(cron = "0 0/2 1 * * ?")
    public void webTask(){
        System.out.println("爬取web文章");
        Spider spider =Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/web");
        articleDbPipeline.setChannelId("web");
        articleTextPipeline.setChannelId("web");
        spider.addPipeline(articleDbPipeline);
        spider.addPipeline(articleTextPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }
}
