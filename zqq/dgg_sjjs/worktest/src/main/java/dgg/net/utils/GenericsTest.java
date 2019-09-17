package dgg.net.utils;

import java.util.Arrays;

/**
 * @author chenxin
 * @date 2019/4/16 19:49
 */
public class GenericsTest {

    public static void main(String[] args) {
        String[] stringArray = { "Barbara", "James", "Mary", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);
        Arrays.sort(stringArray, (String x,String y)-> x.compareToIgnoreCase(y));
        System.out.println(Arrays.asList(stringArray));
    }
}
