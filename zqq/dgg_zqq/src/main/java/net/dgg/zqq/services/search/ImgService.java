package net.dgg.zqq.services.search;

import net.dgg.zqq.dto.imgidentify.BusinessLicense;
import net.dgg.zqq.dto.imgidentify.IdCard;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ImgService
 * @Description: TODO
 * @Author: jiangsh
 * @Date: 2018/11/14 16:08
 */
@Service
public class ImgService {

    /**
     * 解析身份证
     *
     * @param result
     * @return
     */
    public IdCard parseIdCardData(String result, String idCardSide) {
        IdCard idCard = new IdCard();
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jo = JSONObject.fromObject(result);
            JSONObject ja = jo.getJSONObject("words_result");
            if (idCardSide.equals("front")) {
                if (ja.has("住址"))
                    idCard.setAddress(deal(ja.getJSONObject("住址").get("words").toString()));
                if (ja.has("出生"))
                    idCard.setBirthday(deal(ja.getJSONObject("出生").get("words").toString()));
                if (ja.has("姓名"))
                    idCard.setName(deal(ja.getJSONObject("姓名").get("words").toString()));
                if (ja.has("公民身份号码"))
                    idCard.setNo(deal(ja.getJSONObject("公民身份号码").get("words").toString()));
                if (ja.has("性别"))
                    idCard.setGender(deal(ja.getJSONObject("性别").get("words").toString()));
                if (ja.has("民族"))
                    idCard.setNational(deal(ja.getJSONObject("民族").get("words").toString()));
            } else if (idCardSide.equals("back")) {
                if (ja.has("签发机关"))
                    idCard.setOrg(deal(ja.getJSONObject("签发机关").get("words").toString()));
                if (ja.has("失效日期"))
                    idCard.setEndDate(ja.getJSONObject("失效日期").get("words").toString());
                if (ja.has("签发日期"))
                    idCard.setStartDate(ja.getJSONObject("签发日期").get("words").toString());
            }
        }
        return idCard;
    }

    private String deal(String s) {
        return s.replace("/", "");
    }

    /**
     * 解析营业执照
     *
     * @param result
     * @return
     */
    public BusinessLicense parseLicenseData(String result) {
        BusinessLicense bl = new BusinessLicense();
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jo = JSONObject.fromObject(result);
            JSONObject ja = jo.getJSONObject("words_result");
            if (ja.has("注册资本"))
                bl.setRegCapital(ja.getJSONObject("注册资本").get("words").toString());
            if (ja.has("社会信用代码"))
                bl.setSocialCredit(ja.getJSONObject("社会信用代码").get("words").toString());
            if (ja.has("单位名称"))
                bl.setOrg(deal(ja.getJSONObject("单位名称").get("words").toString()));
            if (ja.has("法人"))
                bl.setLegalPerson(ja.getJSONObject("法人").get("words").toString());
            if (ja.has("证件编号"))
                bl.setNo(ja.getJSONObject("证件编号").get("words").toString());
            if (ja.has("成立日期"))
                bl.setStartDate(ja.getJSONObject("成立日期").get("words").toString());
            if (ja.has("地址"))
                bl.setAddress(deal(ja.getJSONObject("地址").get("words").toString()));
            if (ja.has("有效期"))
                bl.setEndDate(ja.getJSONObject("有效期").get("words").toString());

        }
        return bl;
    }


}
