package net.dgg.gspt.dqc.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李程 on 2018/10/16.
 * IK中文分词器工具
 */
public class IKAnalysisHelper {

    @SneakyThrows
    public static List<String> segment(String text) throws IOException {
        List<String> list = new ArrayList<>();
        @Cleanup StringReader re = new StringReader(text);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            list.add(lex.getLexemeText());
        }
        return list;
    }

    @SneakyThrows
    public static List<String> spliteSingleChineseAndSingleWord(String text) {
        List<String> words = segment(text);
        List<String> splits = new ArrayList<>();
        for(String word:words){
            char[] chars = word.toCharArray();
            if((int)chars[0] > 128){
                for(char c:chars){
                    splits.add(new String(new char[]{c}));
                }
            }else{
                splits.add(word);
            }
        }
        return splits;
    }
}
