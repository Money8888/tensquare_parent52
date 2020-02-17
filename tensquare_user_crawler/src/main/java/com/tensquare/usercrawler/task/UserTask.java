package com.tensquare.usercrawler.task;

import com.tensquare.usercrawler.pipeline.UserPipeline;
import com.tensquare.usercrawler.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Component
public class UserTask {

    @Autowired
    private UserProcessor userProcessor;

    @Autowired
    private UserPipeline userPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    @Scheduled(cron="0 0/2 11 * * ?")
    public void userTask(){
        System.out.println("爬取用户");
        Spider spider = Spider.create(userProcessor);
        spider.addUrl("https://www.csdn.net/");
        spider.addPipeline(userPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }
}
