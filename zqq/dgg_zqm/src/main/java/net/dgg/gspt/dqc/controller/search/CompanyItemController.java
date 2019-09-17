package net.dgg.gspt.dqc.controller.search;

import net.dgg.gspt.dqc.dto.brandsearch.ItemParam;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.utils.es.EsConst;
import net.dgg.gspt.dqc.utils.es.services.EsService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/companyItem")
public class CompanyItemController extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(CompanyItemController.class);

    @Resource
    private EsService esService;

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
        List<Map> dataList = esService.getPart(indexs(type), types(type), keyWord, count, includeFive(type), mixMap(type, keyWord), null);
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataList);
        else return getFailResponse(PTConst.PROMPT);
    }

    /**
     * 获取商标、专利列表
     */
    @RequestMapping(value = "/mix", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse mix(@RequestBody ItemParam ip) {
        Map<String, Object> dataMap = pages(ip.getPageIndex(), ip.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] exclude = {"", ""};
        final String t = ip.getType();
        List<Map> list = esService.getListMap(indexs(t), types(t), mixMap(t, ip.getKeyWord()), startCount, endCount, includes(t), exclude, null);
        dataMap.put("listData", t.equals(PTConst.MIX_TAX) ? list : dealPatentAndBrand(list, indexs(t), types(t)));
        dataMap.put("totalCount", esService.getTotalCount(indexs(t), types(t), mixMap(t, ip.getKeyWord())));

        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConst.PROMPT);
    }

    private List<Map> dealPatentAndBrand(List<Map> list, String index, String type) {
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Map e : list) {
                if (e.get("url") != null) {
                    final String url = e.get("url").toString();
                    if (StringUtils.isNotEmpty(url)) {
                        List<Map> dataList = esService.getMsgByKeyWord(PTConst.COMPANY_ID, url, index, type);
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
     * 商标、专利详情
     *
     * @param key  传值情况： 专利 applicationNumber; 商标 regNo;
     * @param val  传值情况： 即为列表中上述key对应的val值;
     * @param type 辨别税号、商标、专利接口
     */
    @RequestMapping(value = "/mixDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse mixDetail(@RequestParam String key,
                                  @RequestParam String val,
                                  @RequestParam String type,
                                  HttpServletRequest request) {

        List<Map> dataList = esService.getMsgByKeyWordCommon(key, val, indexs(type), types(type));
        if (dataList != null && dataList.size() > 0) {
//            if (!justLogin(request)) { //未登录状态
//                HidePhoneUtils.hidePhone(getColumns(), dataList);
//            }
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConst.PROMPT);
    }

    public static String indexs(String t) {
        String index;
        switch (t) {
            case PTConst.MIX_BRAND:
                index = EsConst.INDEX_BRAND;
                break;

            case PTConst.MIX_PATENT:
                index = EsConst.INDEX_PATENT;
                break;

            default:
                index = EsConst.INDEX_BRAND;
                break;
        }
        return index;
    }

    public static String types(String t) {
        String type;
        switch (t) {
            case PTConst.MIX_BRAND:
                type = EsConst.TYPE_COMPANY_BRAND;
                break;

            case PTConst.MIX_PATENT:
                type = EsConst.TYPE_COMPANY_PATENT;
                break;

            default:
                type = EsConst.TYPE_COMPANY_BRAND;
                break;
        }
        return type;
    }

    /**
     * 组装商标、专利、税号查询条件
     */
    public static Map<String, String> mixMap(String type, String keyWord) {
        Map<String, String> m = new HashMap<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            if (type.equals(PTConst.MIX_BRAND)) { //商标
                m.put("regNo", keyWord);
                m.put("applicantCn", keyWord);
                m.put("name", keyWord);
            } else { //专利
                //    m.put(PTConst.INDEX_ZLSQH, keyWord);
                m.put(PTConst.INDEX_ZLMC, keyWord);
                m.put(PTConst.INDEX_ZLSQR, keyWord);
            }
        }
        return m;
    }

    private String[] includeFive(String type) {
        if (type.equals(PTConst.MIX_BRAND)) //商标
            return new String[]{PTConst.INDEX_NAME, "regNo"};
        else  //专利
            return new String[]{PTConst.INDEX_ZLMC, "applicationNumber"};
    }

    /**
     * 返回商标、专利、税号列表展示信息
     */
    public static String[] includes(String type) {
        if (type.equals(PTConst.MIX_BRAND)) //商标
            return new String[]{"name", "imageUrl", "regNo", "appDate", "intCls", "applicantCn", "status", "url"};
        else  //专利
            return new String[]{"piInventName", "applicationNumber", "publicationNumber", "kindCodeDesc", "piApplyAnnounceDate", "inventorString", "assigneestring", "url"};
    }

}
