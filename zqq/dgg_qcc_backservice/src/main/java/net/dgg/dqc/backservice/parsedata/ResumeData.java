package net.dgg.dqc.backservice.parsedata;

import net.dgg.dqc.backservice.constant.PTConst;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.utils.BaseUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  58挂靠（简历信息）
 *  Created by jiangsh on 2018/6/5 13:49
 */
public class ResumeData extends BaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResumeData.class);

    public static ResumeModel conver(Object o) {
        ResumeModel r = new ResumeModel();
        if (o != null) {
            try {
                JSONObject jo = JSONObject.fromObject(o.toString());
                if (!jo.isNullObject()) {
                    //基本信息
                    r.setUrl(oot(jo.get("url"), PTConst.RESUME_WEBSITE_URL, PTConst.TARGET_PREFIX, ".html", PTConst.TARGET_SUFFIX));
                    r.setName(oot(jo.get("name")));
                    r.setSex(oot(jo.get("sex")));
                    r.setAge(oot(jo.get("age")));
                    r.setEducation(oot(jo.get("education")));
                    r.setExperience(oot(jo.get("experience")));
                    r.setArea(oot(jo.get("area")));
                    r.setLabels(oot(jo.get("labels")));
                    r.setCertificate_type(oot(jo.get("certificate_type")));
                    r.setCertificate_status(oot(jo.get("certificate_status")));
                    r.setSalary(oot(jo.get("salary")));
                    r.setInsurance_area(oot(jo.get("insurance_area")));
                    r.setOther_note(oot(jo.get("other_note")));
                    r.setImage_url(oot(jo.get("image_url")));
                    r.setType(oot(jo.get("type")));

                    r.setRegion(oot(jo.get("location")));
                    r.setUpdateTime(oot(jo.get("time")));
                }
            } catch (Exception e) {
                saveError(r, e, "ResumeData");
                logger.error("出现异常情况", e.getMessage());
            }
        }
        return r;
    }

}
