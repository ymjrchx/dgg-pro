package net.dgg.dqc.backservice.framework.ai.xst;

import com.baidu.aip.imagesearch.AipImageSearch;
import net.dgg.dqc.backservice.entity.parse.xst.XstModel;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @ClassName: XstOper
 * @Description: 百度-相似图搜索
 * @Author: jiangsh
 * @Date: 2018/9/19 15:51
 */
public class XstOper {

    private static final Logger logger = LoggerFactory.getLogger(XstOper.class);

    /**
     * 相似图-入库百度
     */
    public static void saveXstImg(AipImageSearch client, XstModel x, String tags, String imgPath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("brief", AsciiUtils.encode(x.toString(), AsciiUtils.key));
        options.put("tags", tags);
        if (StringUtils.isNotEmpty(x.getImgUrl())) {
            final String path = imgPath.concat(x.getImgUrl());
            // 参数为本地路径
            if (StringUtils.isNotEmpty(path)) {
//            JSONObject res =client.similarAdd(imgPath.concat(x.getImgName()), options);
                JSONObject res = client.similarAddUrl(path, options);
                System.out.println(res.toString(2));
            }
        }
    }


}
