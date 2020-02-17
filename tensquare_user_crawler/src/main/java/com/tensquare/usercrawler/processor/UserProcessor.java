package com.tensquare.usercrawler.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class UserProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        page.addTargetRequests(  page.getHtml().links().regex("https://blog.csdn.net/[a-z 0-9 -]+/article/details/[0-9]{8}").all());
        // 昵称
        String nickname = page.getHtml().xpath("//*[@id=\"uid\"]/text()").get();
        // 头像图片地址
        String image = page.getHtml().xpath("//*[@id=\"asideProfile\"]/div[1]/div[1]/a").css("img", "src").toString();
        if(nickname != null && image != null){
            page.putField("nickname", nickname);
            page.putField("image", image);
        }else {
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3000).setSleepTime(100);
    }
}
