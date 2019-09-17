package net.dgg.dqc.backservice.framework.ai.xst;

import com.baidu.aip.imagesearch.AipImageSearch;
import com.baidu.aip.ocr.AipOcr;

/**
 * @ClassName: ClientAipOcr
 * @Description: 得到client
 * @Author: jiangsh
 * @Date: 2018/9/19 9:42
 */
public class ClientAipOcr {

    //设置APPID/AK/SK
    public static final String APP_ID = "14131969";
    public static final String API_KEY = "XRrm2vm9k15xNumnjoKIpvTj";
    public static final String SECRET_KEY = "OziO4hCIwrlsRWv8CB1TPlK3nwVS0AGc";

    /**
     * 返回客户端
     * @return
     */
    public static AipOcr getAipOcr() {
        return new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }

    public static AipImageSearch getAipImageSearch() {
        return  new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);
    }

}
