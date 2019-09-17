package net.dgg.tmd.foundation.platform;

/**
 * Created by wu on 2018-03-02.
 */
public class TestChar {

    public static void main(String[] args) {
        String s = "云雀系统";
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            // 取出每一个字符
            char c = s.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        System.out.println(unicode.toString());
    }
}
