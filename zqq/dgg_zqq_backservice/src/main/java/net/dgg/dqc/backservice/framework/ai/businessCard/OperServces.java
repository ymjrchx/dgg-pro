package net.dgg.dqc.backservice.framework.ai.businessCard;

import net.dgg.dqc.backservice.utils.BaseUtil;
import net.dgg.dqc.backservice.utils.StringUtils;
import net.sf.json.JSONObject;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @ClassName: OperServces
 * @Description: 持久化数据
 * @Author: jiangsh
 * @Date: 2018/9/7 14:56
 */

public class OperServces extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(OperServces.class);

    /**
     * 开始处理图片
     */
    public static void dealImgData(String imgPath, MongoCollection imgDetail) {
        File file = new File(imgPath);
        File [] files = file.listFiles();
        if (files.length > 0)
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            final String result = APPCodeDemo.parsingImg(f.getPath());
            if (!StringUtils.isNullOrEmpty(result)) {
                imgDetail.save(conver(result));
                logger.info("================="+i+"============="+result);
            } else {
                logger.error(" error eeeeeeeeeeeeee-------------------> 第 " + i + " 张的解析结果是--> " + result);
            }
        }
    }

    private static BusinessCardInfo conver(Object o) {
        BusinessCardInfo bci = new BusinessCardInfo();
        if (o != null) {
            JSONObject jo = JSONObject.fromObject(o.toString());
            bci.setCompanyName(oot(jo.get("company")));
            bci.setJob(oot(jo.get("title")));
            bci.setName(oot(jo.get("name")));
            bci.setPhone(oot(jo.get("tel_cell")));
            bci.setSource(o.toString());
        }
        return bci;
    }

    private static String deal(String str) {
        if (!str.equals("-"))
            return str.substring(2, str.length()-2);
        else return "empty";
    }
}
