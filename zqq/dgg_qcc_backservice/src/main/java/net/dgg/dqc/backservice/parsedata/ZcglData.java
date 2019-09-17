package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.entity.parse.zcgl.RegisterInfo;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 *  58资产管理
 *  Created by jiangsh on 2018/6/5 13:49
 */
public class ZcglData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZcglData.class);

    public static ZcglModel conver(Object o) {
        ZcglModel z = new ZcglModel();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                if (!jo.isNullObject()) {
                    //基本信息
                    putBase(z, jo);

                    if (jo.has("zss")) {
                        JSONArray zss = jo.getJSONArray("zss");
                        if (!zss.isEmpty()) {
                            putZss(z, zss);
                        }
                    }

                    if (jo.has("register_info")) {
                        JSONArray regInfo = jo.getJSONArray("register_info");
                        if (!regInfo.isEmpty()) {
                            putRegInfo(z, regInfo);
                        }
                    }

                }
            } catch (Exception e) {
                saveError(z, e, "ZcglData");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return z;
    }

    private static void putRegInfo(ZcglModel z, JSONArray ja) {
        List<RegisterInfo> registerInfos = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            RegisterInfo ri = new RegisterInfo();
            JSONObject obj = ja.getJSONObject(i);
            ri.setQymc(oot(obj.get("qymc")));
            ri.setName(oot(obj.get("name")));
            ri.setUrl(oot(obj.get("url")));
            ri.setXb(oot(obj.get("xb")));
            ri.setZjlx(oot(obj.get("zjlx")));
            ri.setSfzh(oot(obj.get("sfzh")));

            JSONArray ja1 = obj.getJSONArray("people_zss");
            if (!ja1.isEmpty()) {
                List<RegisterInfo.PeopleZss> peopleZsses = new ArrayList<>();
                for (int j = 0; j < ja1.size(); j++) {
                    RegisterInfo.PeopleZss pz = new RegisterInfo.PeopleZss();
                    JSONObject jo1 = ja1.getJSONObject(j);
                    if (!jo1.isNullObject()) {
                        pz.setZclb(oot(jo1.get("zclb")));
                        pz.setZsbh(oot(jo1.get("zsbh")));
                        pz.setZyyzh(oot(jo1.get("zyyzh")));
                        pz.setYxq(oot(jo1.get("yxq")));
                        pz.setZcdw(oot(jo1.get("zcdw")));
                        pz.setZczy(oot(jo1.get("zczy")));
                        peopleZsses.add(pz);
                    }
                }
                ri.setPeopleZsseList(peopleZsses);
            }
            registerInfos.add(ri);
        }
        z.setRegisterInfoList(registerInfos);
    }

    private static void putZss(ZcglModel z, JSONArray ja) {
        List<ZcglModel.Zss> zssList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            ZcglModel.Zss zs = new ZcglModel.Zss();
            JSONObject obj = ja.getJSONObject(i);
            zs.setZzlb(oot(obj.get("zzlb")));
            zs.setZzzsh(oot(obj.get("zzzsh")));
            zs.setZzmc(oot(obj.get("zzmc")));
            zs.setFzrq(oot(obj.get("fzrq")));
            zs.setZsyxq(oot(obj.get("zsyxq")));
            zs.setFzjg(oot(obj.get("fzjg")));
            zssList.add(zs);
        }
        z.setZssList(zssList);
    }

    private static void putBase(ZcglModel z, JSONObject jo) {
        z.setQymc(oot(jo.get("qymc")));
        z.setCompanyId(oot(jo.get("url"), PTConst.ZCGL_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
        z.setUrl(oot(jo.get("url")));
        z.setXydm(oot(jo.get("xydm")));
        z.setFr(oot(jo.get("fr")));
        z.setZclx(oot(jo.get("zclx")));
        z.setZcsd(oot(jo.get("zcsd")));
        z.setJydz(oot(jo.get("jydz")));
    }

}
