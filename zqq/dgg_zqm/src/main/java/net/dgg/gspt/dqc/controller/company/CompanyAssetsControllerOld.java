package net.dgg.gspt.dqc.controller.company;

import net.dgg.gspt.dqc.entity.business.treebook.TreeBook;
import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.params.EntlibParam;
import net.dgg.gspt.dqc.params.ResumeParam;
import net.dgg.gspt.dqc.services.TreeBookService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CompanyAssetsController
 * @Description: 建筑资质相关（简历、快速行业地区查询等等） 调用接口
 * @Author: jiangsh
 * @Date: 2018/7/21 10:38
 */

@RestController
@RequestMapping("/companyAssets")
public class CompanyAssetsControllerOld extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(CompanyAssetsControllerOld.class);

    @Autowired
    private EsServiceOld esService;

    @Resource
    private TreeBookService treeBookService;

    /**
     * 企业库信息获取
     */
    @RequestMapping(value = "/entlib", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse entlib(@RequestBody EntlibParam ep) {
        Map<String, Object> dataMap = pages(ep.getPageIndex(), ep.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());
        final int pageSize = Integer.valueOf(dataMap.get("pageSize").toString());
        Map<String, String> condition = new HashMap<>();
        Map<String, String> map = maps(ep);
        dataMap.put("listData", esService.getListMap(EsConst.INDEX, condition, startCount, endCount, map, EsConst.TYPE_COMPANY_ZCGL, PTConstOld.F_TRUE, pageSize));
        dataMap.put("totalCount", esService.getTotalCount(EsConst.INDEX, condition, map, EsConst.TYPE_COMPANY_ZCGL, PTConstOld.F_TRUE));
        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse("未找到相关数据");
    }


    /**
     * 企业库-企业详情信息获取
     * @param companyId  公司ID
     * @param companyName 公司名称
     */
    @RequestMapping(value = "/entlibDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<Map>> entlibDetail(@RequestParam String companyId, @RequestParam String companyName) {
        List<Map> detail = esService.getMsgByKeyWord(PTConstOld.INDEX_NAME, companyName, EsConst.TYPE_COMPANY);
        Map flagMap = new HashMap<>();
        if (detail != null && detail.size() > 0) {
            flagMap.put("status", "1");
        } else flagMap.put("status", "0");

        List<Map> dataList = esService.getMsgByKeyWord(PTConstOld.COMPANY_ID, companyId, EsConst.TYPE_COMPANY_ZCGL);
        if (dataList != null && dataList.size() > 0) {
            dataList.add(flagMap);
            return getSuccessResponse(dataList);
        } else return getFailResponse("未找到相关数据");
    }

    /**
     * 转企业详情
     * @param companyId  公司名称（此处是为了方便前端调用，顾未用companyName）
     */
    @RequestMapping(value = "/entlibNameDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<Map>> entlibNameDetail(@RequestParam String companyId, HttpServletRequest request) {
        List<Map> dataList = esService.getMsgByKeyWord(PTConstOld.INDEX_NAME, companyId, EsConst.TYPE_COMPANY);
        if (dataList != null && dataList.size() > 0) {
            if (!justLogin(request)) {
                HidePhoneUtils.hidePhone(getColumns(), dataList);
            }
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConstOld.PROMPT);
    }


    /**
     * 获取下级数据
     * @param code 数据字典code
     */
    @RequestMapping(value = "/nextNodeName", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<TreeBook>> nextNodeName(@RequestParam String code) {
        List<TreeBook> dataList = treeBookService.getNameAndCodeByCode(code);
        if (dataList != null && dataList.size() > 0) {
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 数据字典中获取资质类别
     * @param code 数据字典code
     */
    @RequestMapping(value = "/getZcType", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<String>> getZcType(@RequestParam String code) {
        List<String> dataList = treeBookService.getChildNameByCode(code);
        if (dataList != null && dataList.size() > 0) {
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConstOld.PROMPT);
    }


    /**
     * 58挂靠简历证书列表
     */
    @RequestMapping(value = "/certificateResumes", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse resumes(@RequestBody ResumeParam rp) {
        Map<String, Object> dataMap = pages(rp.getPageIndex(), rp.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());
        final int pageSize = Integer.valueOf(dataMap.get("pageSize").toString());

        Map<String, String> condition = new HashMap<>();
        Map<String, String> map = mapResume(rp);

        dataMap.put("listData", esService.getListMap(EsConst.INDEX_RESUME, condition, startCount, endCount, map, EsConst.TYPE_COMPANY_RESUME, PTConstOld.F_TRUE, pageSize));
        dataMap.put("totalCount", esService.getTotalCount(EsConst.INDEX_RESUME, condition, map, EsConst.TYPE_COMPANY_RESUME, PTConstOld.F_TRUE));

        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConstOld.PROMPT);
    }


    /**
     * 58建筑资质详情
     * @param url 简历网址（唯一值）
     */
    @RequestMapping(value = "/resumesDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<Map>> resumesDetail(@RequestParam String url) {
        List<Map> dataList = esService.getMsgByKeyWord("url", url, EsConst.TYPE_COMPANY_RESUME);
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataList);
        else return getFailResponse(PTConstOld.PROMPT);
    }


    /**
     * 获取行业基本信息
     * @param code 数据字典code
     */
    @RequestMapping(value = "/getIndustryMsg", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<Map<String, List<TreeBook>>>> getIndustryMsg(@RequestParam String code) {
        List<TreeBook> dataList = treeBookService.getNameAndCodeByCode(code);
        List<Map<String, List<TreeBook>>> list = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            for (TreeBook t : dataList) {
                Map<String, List<TreeBook>> map = new HashMap<>();
                map.put(t.getName(), treeBookService.getNameAndCodeByCode(t.getCode()));
                list.add(map);
            }
        }
        if (list != null && list.size() > 0)
            return getSuccessResponse(list);
        else return getFailResponse(PTConstOld.PROMPT);
    }


    /**
     * 快速查询公司列表信息
     */
    @RequestMapping(value = "/QuickSelectCompany", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse selectCompany(@RequestBody ResumeParam rp) {
        Map<String, Object> dataMap = pages(rp.getPageIndex(), rp.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());
        final int pageSize = Integer.valueOf(dataMap.get("pageSize").toString());

        Map<String, String> condition = new HashMap<>();
        Map<String, String> map = mapsProvinceOrIndustry(rp);
        dataMap.put("listData", esService.getListMap(EsConst.INDEX, condition, startCount, endCount, map, EsConst.TYPE_COMPANY, PTConstOld.F_TRUE, pageSize));
        dataMap.put("totalCount", esService.getTotalCount(EsConst.INDEX, condition, map, EsConst.TYPE_COMPANY, PTConstOld.F_TRUE));
        if (StringUtils.isNotEmpty(rp.getType())) {
            dataMap.put("treeBookMaxDate", treeBookService.getNameAndCodeByCode("industry"));
        } else if (StringUtils.isNotEmpty(rp.getProvince())) {
            dataMap.put("treeBookDate", treeBookService.getNameAndCodeByCode("province"));
        }

        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConstOld.PROMPT);
    }


    private Map<String, String> mapResume(ResumeParam rp) {
        Map<String, String> map = new HashMap<>();
        map.put(PTConstOld.CON_REASUE, LocalDateTime.now().toString()); //添加过滤当前时间之后的条件
        if (StringUtils.isNotEmpty(rp.getType()))
            map.put("certificate_type", rp.getType());

        if (StringUtils.isNotEmpty(rp.getProvince()))
            map.put("region", rp.getProvince().replace("省", "").replace("市", ""));

        return map;
    }

    private Map<String, String> mapsProvinceOrIndustry(ResumeParam rp) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(rp.getType())) //行业
            if (rp.getType().contains("-")) {
                String[] type = rp.getType().split("-");
                map.put(PTConstOld.QYGS_INDUSTRY, type[1]);
            } else map.put(PTConstOld.QYGS_INDUSTRY, rp.getType());

        if (StringUtils.isNotEmpty(rp.getProvince())) //省份
            map.put(PTConstOld.QYGS_PROVINCE, rp.getProvince().replace("省", "").replace("市", ""));

        return map;
    }

    private Map<String, String> maps(EntlibParam ep) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(ep.getZzlb()))
        map.put("zssList.zzlb", ep.getZzlb());

        if (StringUtils.isNotEmpty(ep.getQymc()))
        map.put("qymc", ep.getQymc());

        if (StringUtils.isNotEmpty(ep.getXydm()))
        map.put("xydm", ep.getXydm());

        if (StringUtils.isNotEmpty(ep.getZsbh()))
        map.put("zssList.zzzsh", ep.getZsbh());

        if (StringUtils.isNotEmpty(ep.getFrdb()))
        map.put("fr", ep.getFrdb());

        if (StringUtils.isNotEmpty(ep.getZcAddress()))
            map.put("zcsd", ep.getZcAddress());

        return map;
    }
}
