package net.dgg.zqq.utils;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: BrandUtil
 * @Description: 近似商标搜索相关处理
 * @Author: jiangsh
 * @Date: 2018/10/16 18:23
 */
public class BrandUtil {

    public static void main(String[] args) {
//        final String s = new String(BrandUtil.bubbleSort("百度"));
        System.out.println(hxSort("我是中国人"));
    }

    public static String hxSort(String str) {
        if (org.apache.commons.lang.StringUtils.isNotEmpty(str)) {
            String[] one = new String[str.length()];
            int j = 0;
            for (int i = str.length(); i > 0; i--) {
                one[j] = str.substring(i - 1, i);
                j++;
            }
            return JSON.toJSONString(one);
        } else return "";
    }


    public static boolean flagChar(String s) {
        try {
            return s.getBytes("GBK").length == s.length() ? false : true;
        } catch (UnsupportedEncodingException e) {
            return true;
        }
    }

    public static String specialChar() {
        final String s = " ! \" # $ % & ' ( ) * + , -. / 0 1 2 3 4 5 6 7 8 9 : ; < = > @ A B C D E F G H I J K L M N OP Q R S T U V W X Y Z [ \\ ] ^ _ ` a b c d e f g h i j k l m n o p q r st u v w x y z { | } ~ \u007F￠ ￡ ¤ ￥ | § ¨ a - ˉ ° ± 2 3 ′ μ · 1 o à á è é êì í D ò ó × ù ú ü Y T à á a è é ê ì í e ò ó ÷ ù ú ü y t ā ā ē ē ě ě ī īń ň ō ō ū ū ∥ ǎ ǎ ǐ ǐ ǒ ǒ ǔ ǔ ǖ ǖ ǘ ǘ ǚ ǚ ǜ ǜ ɑ ɡ ˇ ˉ ˊ ˋ ˙ Α Β Γ Δ Ε ΖΗ Θ Ι Κ Λ Μ Ν Ξ Ο Π Ρ Σ Τ Υ Φ Χ Ψ Ω α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ στ υ φ χ ψ ω Ё А Б В Г Д Е Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы ЬЭ Ю Я а б в г д е ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю яё‐ – — ― ‖ ‘ ’ “ ” ‥ … ‰ ′ ″ ‵ ※ ￣";
        return s;
    }
}
