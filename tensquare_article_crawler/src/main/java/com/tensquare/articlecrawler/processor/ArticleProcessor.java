package com.tensquare.articlecrawler.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 文章爬取类
 */

@Component
public class ArticleProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a-z 0-9 -]+/article/details/[0-9]{8}").all());
        // 文章标题
        String title = page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1").get();
        // 文章内容
        String content = page.getHtml().xpath("//*[@id=\"article_content\"]/div[2]").get();

        if(title != null && content != null){
            page.putField("title", title);
            page.putField("content", content);
        }else {
            // 跳过该页
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3000).setSleepTime(100);
    }
}
