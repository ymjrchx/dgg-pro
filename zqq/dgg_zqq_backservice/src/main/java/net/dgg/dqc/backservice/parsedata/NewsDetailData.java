package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by jiangsh on 2018/6/5 13:49
 */
public class NewsDetailData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(NewsDetailData.class);

    /**
     * 转换新闻详情数据
     * 版本 v1.0
     *
     * @param o
     * @return
     */
    public static NewsModel conver(Object o) {
        NewsModel n = new NewsModel();
        try {
            if (o != null) {
                JSONObject jo = JSONObject.fromObject(o.toString());

                n.setTitle(oot(jo.get("title")));
                if (justDatelen(jo.get("pubdate")))
                n.setPubdate(jo.get("pubdate").toString());
                n.setArticleSub(oot(jo.get("articleSub")));
                n.setContent(oot(jo.get("content")));
                n.setNewsType(oot(jo.get("newsType")));
                n.setTitleImg(oot(jo.get("titleImg")));
                n.setPart(oot(jo.get("part")));

            }
        }catch (Exception e) {
            saveError(n, e, "NewsDetailData");
            logger.error("出现异常情况", e.getMessage());
        }

        return n;
    }

    /**
     * 转换新闻详情数据
     * 版本 v2.0
     *
     * @param o
     * @return
     */
    public static NewsModel conver2(Object o) {
        NewsModel n = new NewsModel();
        try {
            if (o != null) {
                JSONObject jo = JSONObject.fromObject(o.toString());

                n.setTitle(oot(jo.get("title")));
                if (justDatelen(jo.get("pubDate")))
                    n.setPubdate(jo.get("pubDate").toString());
                n.setArticleSub(oot(jo.get("articleSub")));

                if (jo.has("content")) {
                    JSONArray ja = jo.getJSONArray("content");
                    if (!ja.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
//                        sb.append(oot(jo.get("title")));
                        for (int i = 0; i < ja.size(); i++) {
                            sb.append(ja.get(i).toString());
                        }
                        n.setContent(sb.toString());
                    }
                }

                n.setNewsType(oot(jo.get("newsType")));
                n.setTitleImg(oot(jo.get("titleImg")));
                n.setPart(oot(jo.get("part")));

            }
        }catch (Exception e) {
            saveError(n, e, "NewsDetailData");
            logger.error("出现异常情况", e.getMessage());
        }

        return n;
    }
}
