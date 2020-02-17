package com.tensquare.ai.service;

import com.tensquare.ai.util.CnnUtil;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.IKUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CnnService {

    @Value("${ai.dataPath}")
    private String dataPath; // 合并前的分词语料库

    @Value("{ai.vecModel}")
    private String vecModel; // 词向量模型

    @Value("${ai.cnnModel}")
    private String cnnModel; // 卷积神经网络模型

    public void build(){
        // 创建计算图对象
        ComputationGraph net = CnnUtil.createComputationGraph(100);
        // 加载训练数据集
        String [] childPaths = {"arch", "db", "web"};
        DataSetIterator dataSet = CnnUtil.getDataSetIterator(dataPath, childPaths, vecModel);
        // 训练模型
        net.fit(dataSet);
        // 保存模型
        new File(cnnModel).delete();
        try {
            ModelSerializer.writeModel(net, cnnModel, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回map集合 分类与百分比
     * @param content
     * @return
     */
    public Map textClassify(String content){
        System.out.println("content:" + content);
        // 分词
        try{
            content = IKUtil.spilt(content, " ");
        }catch (IOException e){
            e.printStackTrace();
        }

        String[] childPaths = {"arch", "db", "web"};
        // 获取预测结果
        Map map = null;
        try {
            map = CnnUtil.predictions(vecModel, cnnModel, dataPath, childPaths, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
