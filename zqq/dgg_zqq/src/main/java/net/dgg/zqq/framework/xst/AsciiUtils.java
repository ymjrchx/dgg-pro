package net.dgg.zqq.framework.xst;

/**
 * @ClassName: AsciiUtils
 * @Description: 加密工具
 * @Author: jiangsh
 * @Date: 2018/9/19 15:51
 */
public class AsciiUtils {

    public static final String key = "}}}dahello";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        System.out.println("man ");

//        String str=  "jacklist+-(*$2+";
        String key = "}}}dahello";

//        System.out.println("str is:" + str );
//        System.out.println("key is:" + key );
//
//        String encStr = encode( str, key);
//        System.out.println("after encode:" + encStr );
//        System.out.println("-----------------------------");

        String decStr = decode("ҨӃӄҝҿҴҵҼӋӄӉӀҵҍѷ罼҉胋ѽ縡徶区媸ѷѼѰӃӄұӄӅӃҍѷ黃嬮嵝忱剽ѷѼѰұӀӀҠҵӂӃҿҾҍѷ剽孍籃焉鬖嬲剬樉宀硓皹硖在柲歙骠酳卋喼塈ѷѼѰұӀӀҞҿҍѷҁ҄҂҅҉҅҄ҁѷӍ", key);
        System.out.println("after decode:" + decStr);
    }

    private static void jiami() {
        //明文 原始字符串
        String sourceStr = "Hello";
        System.out.println("加密前的明文数据:");
        System.out.println("" + sourceStr);
        //开始加密
        //一 将明文拆分成单个字符
        byte[] strBytes = sourceStr.getBytes();
        //二 将每个字符 都做 - 5 操作
        for (int i = 0; i < strBytes.length; i++) {
            //对每个字符都做 - 5 操作
            strBytes[i] -= 5;
        }
        //加密后的数据
        String targetStr = new String(strBytes);

        System.out.println("加密后的数据:");
        System.out.println("" + targetStr);
    }

    private static void jiemi() {
        //开始解密
        //一 将密文拆分成单个字符
        byte[] tarBytes = "C`ggj".getBytes();
        //二 将每个字符 都做 + 5 操作 (加密是 -5 我们这里反过来就是 +5)
        for (int i = 0; i < tarBytes.length; i++) {
            //对每个字符都做 + 5 (操作 加密是 -5 我们这里反过来就是 +5)
            tarBytes[i] += 5;
        }
        //加密后的数据
        String reTarStr = new String(tarBytes);

        System.out.println("解密后的数据:");
        System.out.println("" + reTarStr);

    }

    public static String decode(String str, String key) {
        if (null == str || null == key || str.length() < 1 || key.length() < 1)
            return null;
        int len = str.length();
        int klen = key.length();
        String result = "";
        for (int n = 0; n < len; n++) {
            char ch = str.charAt(n);
            for (int i = 0; i < klen; i++) {
                char k = key.charAt(i);
                ch = (char) (ch - k);
            }
            result += ch;
        }
        return result;
    }

    public static String encode(String str, String key) {
        if (null == str || null == key || str.length() < 1 || key.length() < 1)
            return null;
        int len = str.length();
        int klen = key.length();
        String result = "";
        for (int n = 0; n < len; n++) {
            char ch = str.charAt(n);
            for (int i = 0; i < klen; i++) {
                char k = key.charAt(i);
                ch = (char) (ch + k);
            }
            result += ch;
        }
        return result;
    }
}