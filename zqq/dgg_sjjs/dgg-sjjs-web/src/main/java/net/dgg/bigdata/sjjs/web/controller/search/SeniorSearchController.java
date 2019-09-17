package net.dgg.bigdata.sjjs.web.controller.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.config.EsConfig;
import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import net.dgg.bigdata.sjjs.web.entity.dto.search.senior.SeniorParam;
import net.dgg.bigdata.sjjs.web.service.search.SeniorSearchService;
import net.dgg.core.utils.HidePhoneUtils;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import net.dgg.core.utils.exception.DggBaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.dgg.bigdata.sjjs.web.util.SysUserUtils.getUser;

/**
 * @ClassName: SeniorSearchController
 * @Description: 企业高级搜索
 * @Author: jiangsh
 * @Date: 2018/12/12 10:53
 */
@RestController
@RequestMapping("/senior")
public class SeniorSearchController extends DggBaseController implements ConstMethod {

    @Resource
    private SeniorSearchService seniorSearchService;

    /**
     * 查询企业信息列表
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse search(@RequestBody SeniorParam ep, HttpServletRequest request) {
        String account;
        try {
            account = getUser(request).getUserId();
        } catch (Exception e) {throw new DggBaseException("请先登录，绑定个人信息");}
        if (StringUtils.isEmpty(ep.getJudge())) throw new IllegalArgumentException("关键字为空");
        final int startCount = startCount(ep.getPageIndex(), ep.getPageSize());
        final int endCount = endCount(ep.getPageIndex(), ep.getPageSize());
        Map<String, Object> dataMap = new HashMap<>();
        List<Map> dataList = seniorSearchService.search(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_PARENT, startCount, endCount, ep, BConst.F_TRUE, account);
        final long total = seniorSearchService.totalCount(EsConfig.YK_COMMERCIAL,
                EsConfig.YK_COMMERCIAL_PARENT, startCount, endCount, ep, BConst.F_TRUE, account);
        if (dataList != null && dataList.size() > 0) {
            HidePhoneUtils.hidePhone(seniorSearchService.getColumns(), dataList);
            dataMap.put("listData", dataList);
            dataMap.put("totalCount", total);
            return getSuccessResponse(dataMap);
        } else return getSuccessResponse(PTConst.PROMPT);
    }


    /**
     * 查询企业信息 总数
     * @return
     */
    @RequestMapping(value = "/searchTotalCount", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse searchTotalCount(@RequestBody SeniorParam ep, HttpServletRequest request) {
        if (StringUtils.isEmpty(ep.getJudge())) throw new IllegalArgumentException("关键字为空");
        return getSuccessResponse(seniorSearchService.totalCount(EsConfig.YK_COMMERCIAL,
                EsConfig.YK_COMMERCIAL_PARENT, 1, 10, ep, BConst.F_TRUE, getUser(request).getUserId()));
    }


    /**
     * 列表 详情
     */
    @RequestMapping(value = "/searchSimpleDetail", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse searchSimpleDetail(@RequestParam String companyId) {
        List<Map> dataList = seniorSearchService.searchSimpleDetail(BConst.COMPANY_ID, companyId, EsConfig.YK_COMPANY, EsConfig.YK_COMPANY);
        if (dataList != null && dataList.size() > 0) {
            HidePhoneUtils.hidePhone(seniorSearchService.getColumns(), dataList);
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConst.PROMPT);
    }

    /**
     * 基本工商 详情
     */
    @RequestMapping(value = "/searchBaseGsDetail", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse searchBaseGsDetail(@RequestParam String companyId) {
        List<Map> dataList = seniorSearchService.searchSimpleDetail(BConst.COMPANY_ID, companyId, EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_PARENT);
        if (dataList != null && dataList.size() > 0) {
            HidePhoneUtils.hidePhone(seniorSearchService.getColumns(), dataList);
            return getSuccessResponse(dataList);
        } else return getFailResponse(PTConst.PROMPT);
    }

}
