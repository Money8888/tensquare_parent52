package com.tensquare.ai.task;

import com.tensquare.ai.service.CnnService;
import com.tensquare.ai.service.Word2VecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 训练任务
 */
@Component
public class TrainTask {

    @Autowired
    private Word2VecService word2VecService;

    @Autowired
    private CnnService cnnService;

    /**
     * 训练模型
     */

    @Scheduled(cron="0 0/2 13 * * ?")
    public void trainModel(){
        System.out.println("开始合并语料库......");
        word2VecService.mergeWord();
        System.out.println("合并语料库结束......");

        System.out.println("开始构建词向量模型");
        word2VecService.build();
        System.out.println("构建词向量模型结束");

        System.out.println("开始构建设计网络卷积模型");
        cnnService.build();
        System.out.println("构建神经网络模型结束");
    }
}
