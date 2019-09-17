package net.dgg.zqq.utils.glc;


import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 关联词搜索
 */
public class GlcUtils {

    public static String glcRes(String input) {
        String[] arguments = new String[]{"python", "D:\\DGG\\知千秋\\ZQQ\\04_Coding\\dgg_zqq\\src\\main\\java\\net\\dgg\\zqq\\utils\\glc\\tt.py", input};
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            String result = "";
            while ((line = in.readLine()) != null) {
                result = line;
            }
            in.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String s = glcRes("衣服");
        String s1 = glcRes("公司");
        String s3 = glcRes("图片");
        String s4 = glcRes("激励");
        String s5 = glcRes("交流");
        String s6 = glcRes("你好");
////        String s = "足球";
//        System.out.println( new String(s.getBytes(),"GBK"));
//        System.out.println( new String(s.getBytes("UTF-8"),"UTF-8"));
//        System.out.println( new String( s.getBytes("GBK") , "GBK"));
//        System.out.println( new String(s.getBytes("iso-8859-1") ,"GBK"));
//        System.out.println( new String(s.getBytes("iso-8859-1") ,"UTF-8"));
        System.out.println(s);
        System.out.println(s1);
//        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);
        System.out.println(s6);

//        String a = URLEncoder.encode(s, "UTF-8");//编码
//        String a = URLEncoder.encode("中文测试", "UTF-8");//编码
//        System.out.println(a);
//        System.out.println(URLDecoder.decode(a,"UTF-8"));//还原
//        System.out.println(URLDecoder.decode(a,"UTF-8"));//还原
    }
}
