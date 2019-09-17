package dgg.net.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CommonUtils {

    private final static String AK = "jKznUKWBRNGoYrNy1u0vMzxZwpZ73wrG";
    private final static String COOR = "bd09ll";
    private final static String APIURL = "http://api.map.baidu.com/location/ip";
    private final static int SEVEN = 7;
    private final static String PARAMSUFFIX = "&ak=" + AK + "&coor=" + COOR;

    private enum NetworkISPs {
        CHINANET("中国电信"),
        CMNET("中国移动"),
        UNINET("中国联通"),
        CSTNET("中国科技网"),
        CGWNET("中国长城互联网"),
        CERNET("中国教育网");
        String name;

        NetworkISPs(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        public static NetworkISPs getISP(String name) {
            try {
                return NetworkISPs.valueOf(name);
            } catch (Exception e) {
                return null;
            }
        }
    }


    private static void getIPGeographicLocation(String ip) {
        if (ip == null) {
            return;
        }
        String url = APIURL + "?ip=" + ip + PARAMSUFFIX;
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                return;
            }
            String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("响应内容为：" + resultString);

            JSONObject jsonObject = JSONObject.parseObject(resultString);
            if (!jsonObject.get("status").equals(0)) {
                return;
            }
            String address = (String) jsonObject.get("address");
            System.out.println(address);
            String[] addr = address.split("|");
            if (addr.length == SEVEN) {

            }
            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
      /*  CommonUtils.getIPGeographicLocation("27.18.42.135");

        NetworkISPs isPs = NetworkISPs.getISP("CHINANET1");
        System.out.println(isPs.getName());*/

        List<String> campaignFields = Arrays.asList("campaignId", "campaignName", "budget");
        System.out.println(campaignFields);

        System.out.println(campaignFields);




    }


}
