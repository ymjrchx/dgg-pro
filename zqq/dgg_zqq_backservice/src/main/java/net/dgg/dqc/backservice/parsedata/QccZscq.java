package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.entity.parse.QccCompany;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  企查查-知识产权（获取所有）
 *  Created by jiangsh on 2018/6/5 13:49
 */
public class QccZscq extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(QccZscq.class);

    /**
     * 获取关于企查查的所有 知识产权  信息
     * @param o
     * @return
     */
    public static QccCompany getZscqAboutQcc(Object o) {
        QccCompany c = new QccCompany();
        if (o != null) {
            JSONObject jo = JSONObject.fromObject(o.toString());
            if (!jo.isNullObject()) {
                c.setCompanyId(oot(jo.get("url")));
                c.setName(oot(jo.get("title")));
                JSONObject zscq = jo.getJSONObject("zhishi_chanquan");
                if (!zscq.isNullObject()) {
                    //商标信息
                    if (zscq.has("shangbiao_xinxi")) {
                        JSONArray sbxx = zscq.getJSONArray("shangbiao_xinxi");
                        if (!sbxx.isEmpty())
                            QccDetailData.putSbxx(sbxx, c);
                    }

                    //证书信息
                    if (jo.has("zhengshu_xinxi")) {
                        JSONArray zsxx = zscq.getJSONArray("zhengshu_xinxi");
                        if (!zsxx.isEmpty())
                            QccDetailData.putZsxx(zsxx, c);
                    }

                    //网站信息
                    if (jo.has("wangzhan_xinxi")) {
                        JSONArray wzxx = zscq.getJSONArray("wangzhan_xinxi");
                        if (!wzxx.isEmpty())
                            QccDetailData.putWzxx(wzxx, c);
                    }
                }
            }
        }
        return c;
    }

}
