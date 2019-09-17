package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.entity.parse.xst.XstModel;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: XstData
 * @Description: 相似图搜索所需数据解析
 * @Author: jiangsh
 * @Date: 2018/9/19 16:04
 */
public class XstData extends BaseUtil{

    private static final Logger logger = LoggerFactory.getLogger(ZcglData.class);

    public static XstModel conver(Object o) {
        XstModel c = new XstModel();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                c.setAppNo(oot(jo.get("sbNum")));
                c.setType(oot(jo.getJSONObject("head").get("sbType")));
                c.setAppPerson(oot(jo.getJSONObject("head").get("userName")));
                c.setImgName(oot(jo.getJSONObject("head").getJSONObject("imgLocal").get("clName")));
                c.setImgUrl(oot(jo.getJSONObject("head").getJSONObject("imgLocal").get("clPath")));
                c.setStatus(oot(jo.get("state")));
            } catch (Exception e) {
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return c;
    }

}
