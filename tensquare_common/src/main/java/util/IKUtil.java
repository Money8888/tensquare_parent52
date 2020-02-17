package util;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * IK分词工具类
 * 根据文本返回分词后的文本
 */
public class IKUtil {
    /**
     * @param content 分词的文本内容
     * @param splitChar 分割符号
     * @return
     */
    public static String spilt(String content, String splitChar) throws IOException {
        // 读取一个String字符串
        StringReader stringReader = new StringReader(content);
        // true表示最大切分， false表示最细切分
        IKSegmenter ikSegmenter = new IKSegmenter(stringReader, true);
        // Lexeme 表示词的单位
        Lexeme lexeme = null;
        // 创建一个字符串基类
        StringBuilder stringBuilder = new StringBuilder("");
        while ((lexeme = ikSegmenter.next()) != null){
            stringBuilder.append(lexeme.getLexemeText() + splitChar);
        }
        return stringBuilder.toString();
    }
}
