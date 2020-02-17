package com.tensquare.usercrawler.pipeline;

import com.tensquare.usercrawler.dao.UserDao;
import com.tensquare.usercrawler.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.DownloadUtil;
import util.IdWorker;

import java.io.IOException;

@Component
public class UserPipeline implements Pipeline {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        User user = new User();
        user.setId(idWorker.nextId() + "");
        user.setNickname(resultItems.get("nickname"));
        String image = resultItems.get("image");
        String fileName = image.substring(image.lastIndexOf("/") + 1);
        user.setAvatar(fileName);
        userDao.save(user);

        try{
            DownloadUtil.download(image, fileName, "D:\\JavaProject\\tensquare_parent52\\userimg");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
