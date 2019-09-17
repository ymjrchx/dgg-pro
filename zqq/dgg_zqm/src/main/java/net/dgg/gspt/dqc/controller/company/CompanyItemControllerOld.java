package net.dgg.gspt.dqc.controller.company;

import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.params.ItemParam;
import net.dgg.gspt.dqc.utils.HidePhoneUtils;
import net.dgg.gspt.dqc.utils.esOld.EsConst;
import net.dgg.gspt.dqc.utils.esOld.services.EsServiceOld;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: CompanyItemController
 * @Description: 公司相关信息查询，如税号、商标等等。
 * @Author: jiangsh
 * @Date: 2018/8/21 10:38
 */

@RestController
@RequestMapping("/companyItemOld")
public class CompanyItemControllerOld extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(CompanyItemControllerOld.class);

    @Autowired
    private EsServiceOld esService;

    /**
     * 获取前五条数据
     *
     * @param keyWord 关键字
     * @param count   条数
     * @param type    辨别税号、商标、专利搜索
     * @return
     */
    @RequestMapping(value = "/searchFive", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse searchFive(@RequestParam String keyWord
            , @RequestParam int count
            , @RequestParam String type
    ) {
        if (count <= 0) count = 5;
        //税号和商标下拉框中取值都展示name, 专利展示piInventName
        List<Map> dataList = esService.getPart(indexsOld(type), typesOld(type), keyWord, count, includeFive(type), mixMap(type, keyWord), null);
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataList);
        else return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 获取税号、商标、专利列表
     */
    @RequestMapping(value = "/mix", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse mix(@RequestBody ItemParam ip) {
        Map<String, Object> dataMap = pages(ip.getPageIndex(), ip.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] exclude = {"", ""};
        final String t = ip.getType();
        final String sortKey = t.equals(PTConstOld.MIX_TAX) ? PTConstOld.QYGS_REGISTCAPI : null;
        List<Map> list = esService.getListMap(indexsOld(t), typesOld(t), mixMap(t, ip.getKeyWord()), startCount, endCount, includes(t), exclude, sortKey);
        dataMap.put("listData", t.equals(PTConstOld.MIX_TAX) ? list : dealPatentAndBrand(list));
        dataMap.put("totalCount", esService.getTotalCount(indexsOld(t), typesOld(t), mixMap(t, ip.getKeyWord())));

        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConstOld.PROMPT);
    }

    private List<Map> dealPatentAndBrand(List<Map> list) {
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Map e : list) {
                if (e.get("url") != null) {
                    final String url = e.get("url").toString();
                    if (StringUtils.isNotEmpty(url)) {
                        List<Map> dataList = esService.getMsgByKeyWord(PTConstOld.COMPANY_ID, url, EsConst.TYPE_COMPANY);
                        if (dataList.size() == 0)
                            e.put("url", null);
                    }
                }
                result.add(e);
            }
        }
        return result;
    }

    /**
     * 商标、专利、税号详情
     *
     * @param key  传值情况： 税号 companyId; 商标 esId;  专利 applicationNumber; 裁判文书 id;
     * @param val  传值情况： 即为列表中上述key对应的val值;
     * @param type 辨别税号、商标、专利接口
     */
    @RequestMapping(value = "/mixDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse mixDetail(@RequestParam String key,
                                  @RequestParam String val,
                                  @RequestParam String type,
                                  HttpServletRequest request) {

        List<Map> dataList = esService.getMsgByKeyWordCommon(key, val, indexsOld(type), typesOld(type));
        if (dataList != null && dataList.size() > 0) {
            if (!justLogin(request)) { //未登录状态
                if (type.equals(PTConstOld.MIX_TAX))
                    HidePhoneUtils.hidePhone(getColumns(), dataList);
            }
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 组装商标、专利、税号查询条件
     */
    public static Map<String, String> mixMap(String type, String keyWord) {
        Map<String, String> m = new HashMap<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            if (type.equals(PTConstOld.MIX_BRAND)) { //商标
                m.put("regNo", keyWord);
                m.put("applicantCn", keyWord);
                m.put("name", keyWord);
            } else if (type.equals(PTConstOld.MIX_PATENT)) { //专利
                m.put(PTConstOld.INDEX_ZLSQH, keyWord);
                m.put(PTConstOld.INDEX_ZLMC, keyWord);
                m.put(PTConstOld.INDEX_ZLSQR, keyWord);
            } else if (type.equals(PTConstOld.MIX_TAX)) { //税号
                m.put(PTConstOld.INDEX_NAME, keyWord);
                m.put(PTConstOld.INDEX_SHXYDM, keyWord);
            } else if (type.equals(PTConstOld.MIX_CPWS_TS)) { //裁判文书服务接口
                cpwsMap(m, keyWord);
//                m.put("content", keyWord);
            } else { //裁判文书-案例
                m.put("caseName", keyWord);
                m.put("caseType", keyWord);
                m.put("appellor", keyWord);
                m.put("defendantlist", keyWord);
                m.put("ajsf", keyWord);
            }
        }
        return m;
    }

    private String[] includeFive(String type) {
        if (type.equals(PTConstOld.MIX_BRAND)) //商标
            return new String[]{PTConstOld.INDEX_NAME, "regNo"};
        else if (type.equals(PTConstOld.MIX_PATENT)) //专利
            return new String[]{PTConstOld.INDEX_ZLMC, "applicationNumber"};
        else if (type.equals(PTConstOld.MIX_TAX)) //税号
            return new String[]{PTConstOld.INDEX_NAME, PTConstOld.COMPANY_ID};
        else //裁判文书-案例
            return new String[]{"caseName", "id"};
    }

    /**
     * 返回商标、专利、税号列表展示信息
     */
    public static String[] includes(String type) {
        if (type.equals(PTConstOld.MIX_BRAND)) //商标
            return new String[]{"name", "imageUrl", "regNo", "appDate", "intCls", "applicantCn", "status", "url"};
        else if (type.equals(PTConstOld.MIX_PATENT)) //专利
            return new String[]{"piInventName", "applicationNumber", "publicationNumber", "kindCodeDesc", "piApplyAnnounceDate", "inventorString", "assigneestring", "url"};
        else if (type.equals(PTConstOld.MIX_TAX))//税号
            return new String[]{PTConstOld.INDEX_NAME, PTConstOld.INDEX_SHXYDM, PTConstOld.INDEX_ADDRESS, PTConstOld.QYGS_STATUS, PTConstOld.INDEX_LOGO, PTConstOld.COMPANY_ID, PTConstOld.QYGS_STATUS};
        else if (type.equals(PTConstOld.MIX_CPWS_TS))//裁判文书服务接口
            return new String[]{"caseName", "caseNo", "judgeDate", "court", "id", "ssjl", "ajsf"};
        else { //裁判文书
            return new String[]{"caseName", "caseNo", "judgeDate", "caseType", "court", "id"};
        }
    }

    public static Map<String, String> cpwsMap(Map<String, String> m, String v) {
        m.put("caseName", v); //裁判文书名字
        m.put("caseType", v); //裁判文书类型
        m.put("appellor", v); //当事人
        m.put("defendantlist", v); //被告
        m.put("ajsf", v); //案件身份
        return m;
    }

}
