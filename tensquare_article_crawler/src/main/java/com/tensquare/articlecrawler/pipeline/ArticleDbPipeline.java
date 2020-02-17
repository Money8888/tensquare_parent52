package com.tensquare.articlecrawler.pipeline;

import com.tensquare.articlecrawler.dao.ArticleDao;
import com.tensquare.articlecrawler.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.HTMLUtil;
import util.IdWorker;

/**
 * 爬虫内容存入数据库类
 */
@Component
public class ArticleDbPipeline implements Pipeline {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    private String channelId;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = HTMLUtil.delHTMLTag(resultItems.get("title"));
        String content = HTMLUtil.delHTMLTag(resultItems.get("content"));
        // 新建一个文件对象
        Article article = new Article();
        article.setId(idWorker.nextId()+ "");
        article.setChannelid(channelId);
        article.setTitle(title);
        article.setContent(content);
        articleDao.save(article);
    }
}
