package net.dgg.bigdata.sjjs.web.controller.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.condition.service.TreeBookService;
import net.dgg.bigdata.sjjs.web.config.EsConfig;
import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import net.dgg.bigdata.sjjs.web.entity.dto.search.enterprise.EnterpriseParam;
import net.dgg.bigdata.sjjs.web.entity.dto.search.enterprise.HighParam;
import net.dgg.bigdata.sjjs.web.service.search.EnterpriseSearchService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EnterpriseSearchController
 * @Description: 企业搜索
 * @Author: jiangsh
 * @Date: 2018/12/11 9:43
 */
@RestController
@RequestMapping("/enterprise")
public class EnterpriseSearchController extends DggBaseController implements ConstMethod {

    @Resource
    private EnterpriseSearchService ess;
    @Resource
    private TreeBookService treeBookService;

    /**
     * 获取前五条数据
     * @return
     */
    @RequestMapping(value = "/searchFive", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse searchFive(@RequestBody HighParam highParam
    ) {
        List<Map> dataList = ess.getPart(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_PARENT, highParam.getKeyWord(), highParam.getCount(), null);
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataList);
        else return getSuccessResponse("");
    }


    /**
     * 查询企业信息
     *
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse search(@RequestBody EnterpriseParam ep, HttpServletRequest request) {
        boolean kw = BConst.F_TRUE;
        if (StringUtils.isEmpty(ep.getKeyWord()))  kw = BConst.F_FALSE;
        final int startCount = startCount(ep.getPageIndex(), ep.getPageSize());
        final int endCount = endCount(ep.getPageIndex(), ep.getPageSize());
        final long totalCount = !kw ? endCount : ess.totalCount(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_PARENT, startCount, endCount, ep, kw);
        if (totalCount > BConst.UPSIZE)
            return getSuccessResponse(PTConst.UPSIZE_PROMPT);
        else {
            Map<String, Object> dataMap = new HashMap<>();
            final List<Map> dataList = ess.search(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_PARENT, startCount, endCount, ep, kw);
            dataMap.put("listData", dataList);
            dataMap.put("totalCount", totalCount);
            if (dataList != null && dataList.size() > 0)
                return getSuccessResponse(dataMap);
            else return getSuccessResponse(PTConst.PROMPT);
        }
    }


    /**
     * 企业搜索数据字典
     */
    @RequestMapping(value = "/comSearchTreeBook", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse comSearchTreeBook() {
        return getSuccessResponse(ess.comSearchTreeBookToCache(treeBookService));
    }


    /**
     * 获取数据字典对象
     */
    @RequestMapping(value = "/treeBook", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse getTreeBooksByPid(@RequestParam Long pid) {
        return getSuccessResponse(treeBookService.getTreeBooksByPid(pid));
    }

    /**
     * 获取一级节点
     */
    @RequestMapping(value = "/oneLevel", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse getOneLevelNode(@RequestParam String pid) {
        return getSuccessResponse(treeBookService.getCodeByPid(pid));
    }

    /**
     * 获取下级节点
     */
    @RequestMapping(value = "/next", method = RequestMethod.GET)
    @ResponseBody
    public DggRestResponse next(@RequestParam String code) {
        return getSuccessResponse(treeBookService.getNameAndCodeByCode(code));
    }

}
