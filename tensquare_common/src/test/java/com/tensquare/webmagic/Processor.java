package com.tensquare.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisScheduler;

public class Processor implements PageProcessor {

    /**
     * 处理网页的逻辑过程
     * @param page 网页
     */
    @Override
    public void process(Page page) {
        // 将当前页面的所有链接都添加到目标页面
        page.addTargetRequests(page.getHtml().links().all());
        System.out.println(page.getHtml().xpath("//*[@id=\"nav\"]/div/div/ul/li[5]/a").toString());
    }

    /**
     *  Site用于定义站点本身的一些配置信息，例如编码、HTTP头、超时时间、重试策略等、代理等
     *  setSleepTime 设置间隔时间
     *  setRetrySleepTime 设置重试次数
     * @return
     */
    @Override
    public Site getSite() {
        return Site.me().setSleepTime(100).setRetrySleepTime(3);
    }

    /**
     * Spider是爬虫启动的入口。
     * 在启动爬虫之前，我们需要使用一个PageProcessor创建 一个Spider对象，然后使用run()进行启动
     * @param args
     */
    public static void main(String[] args) {
        Spider.create(new Processor())
                .addUrl("https://blog.csdn.net")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new FilePipeline("D:/java/code"))
                .addPipeline(new JsonFilePipeline("D:/java/code"))
                .setScheduler(new QueueScheduler())
                .setScheduler(new FileCacheQueueScheduler("D:/java/code/scheduler"))
                .setScheduler(new RedisScheduler("localhost"))
                .run();
    }
}
