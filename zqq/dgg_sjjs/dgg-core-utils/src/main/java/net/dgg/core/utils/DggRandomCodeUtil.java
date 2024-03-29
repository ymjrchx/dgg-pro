package net.dgg.core.utils;

import java.util.Calendar;
import java.util.Random;

/**
 * 随机编码生成器
 *
 * @author nature
 * @create 2017-12-28 14:06
 */
public class DggRandomCodeUtil {

    public static final String LOWER_CASE_LETTER = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_CASE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";

    /**
     * 生成随机编码
     *
     * @param length   所需要的字符串的位数
     * @param assemble 可以使用的字符集合的字符串
     * @return 生成的指定构成和位数的随机编码
     */
    public static String generateRandomCode(int length, String assemble) {
        StringBuilder result = new StringBuilder();
        if (assemble != null && DggValidateUtil.strNotEmptyWithTrim(assemble, null, null)) {
            Random random = new Random(Calendar.getInstance().getTimeInMillis());
            for (int i = 0; i < length; i++) {
                //获取随机出，从assemble中读取字符
                int number = random.nextInt(assemble.length());
                result.append(assemble.charAt(number));
            }
        }
        return result.toString();
    }
}
