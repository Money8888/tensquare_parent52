package com.tensquare.ai;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class aiTest {
    public static void main(String[] args) throws IOException {
        String text = "基于java语言开发的轻量级的中文分词工具包";
        // 读取一个String字符串
        StringReader stringReader = new StringReader(text);
        // true表示最大切分， false表示最细切分
        IKSegmenter ikSegmenter = new IKSegmenter(stringReader, true);
        // Lexeme 表示词的单位
        Lexeme lexeme = null;
        while ((lexeme = ikSegmenter.next()) != null){
            System.out.println(lexeme.getLexemeText() + " ");
        }
    }
}
