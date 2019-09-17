package net.dgg.bigdata.sjjs.web.controller.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.config.EsConfig;
import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import net.dgg.bigdata.sjjs.web.entity.dto.search.workbench.Kv;
import net.dgg.bigdata.sjjs.web.entity.dto.search.workbench.WorkBenchParam;
import net.dgg.bigdata.sjjs.web.service.SysUserService;
import net.dgg.bigdata.sjjs.web.service.search.WorkbenchService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: WorkbenchController
 * @Description: 工作台搜索
 * @Author: jiangsh
 * @Date: 2018/12/17 14:59
 */
@RestController
@RequestMapping("/workbench")
public class WorkbenchController extends DggBaseController implements ConstMethod {

    @Resource
    private WorkbenchService workbenchService;
    @Resource
    private SysUserService sysUserService;

    /**
     * 获取线索状态数量（销售简报-线索状态）
     * @return
     */
    @RequestMapping(value = "/searchStatusCount", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse searchStatusCount(@RequestBody WorkBenchParam wb) {
        if (StringUtils.isEmpty(wb.getKey())) throw new IllegalArgumentException("请输入相关搜索选项");
        return getSuccessResponse(converKv(workbenchService.filterAggregationCount(
                EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_CHILD, wb, BConst.CLUSE_STATUS, BConst.F_TRUE)));
    }

    /**
     * 获取线索动态变化数量（销售简报-新增线索）
     * @return
     */
    @RequestMapping(value = "/searchClues", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse searchClues(@RequestBody WorkBenchParam wb) {
        if (StringUtils.isEmpty(wb.getKey())) throw new IllegalArgumentException("请输入相关搜索选项");
        return getSuccessResponse(new Kv("转线索（领取）", workbenchService.totalClues(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_CHILD, wb))); //从线索转换表获取领取数据
    }

    /**
     * 获取线索动态变化情况（线索动态）
     * @return
     */
    @RequestMapping(value = "/searchCluesRecord", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse searchCluesRecord(@RequestBody WorkBenchParam wb) {
        if (StringUtils.isEmpty(wb.getKey())) throw new IllegalArgumentException("请输入相关搜索选项");
        Map<String, Object> dataMap = new HashMap<>();
        final int startCount = startCount(wb.getPageIndex(), wb.getPageSize());
        final int endCount = endCount(wb.getPageIndex(), wb.getPageSize());
        dataMap.put("listData", workbenchService.searchCluesRecord(EsConfig.YK_COMMERCIAL_CLUES_RECORD, EsConfig.YK_COMMERCIAL_CLUES_RECORD, wb, startCount, endCount));
        dataMap.put("totalCount", workbenchService.searchCluesRecordCount(EsConfig.YK_COMMERCIAL_CLUES_RECORD, EsConfig.YK_COMMERCIAL_CLUES_RECORD, wb, startCount, endCount));
        return getSuccessResponse(dataMap);
    }


    /**
     * 销售排行榜
     * @return
     */
    @RequestMapping(value = "/searchSaleRank", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse searchSaleRank(@RequestBody WorkBenchParam wb) {
        if (StringUtils.isEmpty(wb.getKey())) throw new IllegalArgumentException("请输入相关搜索选项");
        if (sysUserService.getAllUserId().size() == 0) return getSuccessResponse(new ArrayList<>());
        return getSuccessResponse(converKv(workbenchService.searchSaleRank(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_CHILD, wb, BConst.USERID, BConst.F_TRUE)));
    }

}
