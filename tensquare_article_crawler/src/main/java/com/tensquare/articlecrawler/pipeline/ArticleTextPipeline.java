package com.tensquare.articlecrawler.pipeline;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.HTMLUtil;
import util.IKUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

// 分词后保存成txt
@Component
public class ArticleTextPipeline implements Pipeline {

    @Value("${ai.dataPath}")
    private String dataPath;

    private String channelId;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = HTMLUtil.delHTMLTag(resultItems.get("title"));
        String content = HTMLUtil.delHTMLTag(resultItems.get("content"));
        try{
            //将标题+正文分词后保存到相应的文件夹
            PrintWriter printWriter = new PrintWriter(new File(dataPath + "/" + channelId + "/" + UUID.randomUUID() + ".txt"));
            printWriter.print(IKUtil.spilt(title + " " + content, " "));
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
