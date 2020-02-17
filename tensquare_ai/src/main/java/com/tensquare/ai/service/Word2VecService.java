package com.tensquare.ai.service;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.FileUtil;


import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class Word2VecService {
    // 模型分词路径
    @Value("${ai.wordLib}")
    private String wordLib;

    // 爬虫分词后的数据路径
    @Value("${ai.dataPath}")
    private String dataPath;

    @Value("${ai.vecModel}")
    private String vecModel;

    /**
     * 合并
     */
    public void mergeWord(){
        List<String> fileNames = FileUtil.getFiles(dataPath);
        try {
            FileUtil.merge(wordLib, fileNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据分词模型生成词向量模型
     */
    public void build(){
        // 加载爬虫分词数据集
        try{
            SentenceIterator sentenceIterator = new LineSentenceIterator(new File(wordLib));
            Word2Vec word2Vec = new Word2Vec.Builder()
                    .minWordFrequency(5)  // 词在语料中必须出现的最少次数，少于该次数的不进行学习
                    .iterations(1) // 网络在处理一批数据时允许更新系数的次数。迭代次数太少，网络可能 来不及学习所有能学到的信息;迭代次数太多则会导致网络定型时间变长
                    .layerSize(100) // 指定词向量中的特征数量，与特征空间的维度数量相等
                    .seed(42) // 随机数种子，用于初始化向量
                    .windowSize(5) // 当前词与预测词在一个句子中的最大距离
                    .iterate(sentenceIterator) // 告知网络当前定型的是哪一批数据集
                    .build(); // 开始建立模型
            word2Vec.fit();
            // 删除之前的模型
            new File(vecModel).delete();
            WordVectorSerializer.writeWordVectors(word2Vec, vecModel);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
