package net.dgg.zqq.controller.firsttrial;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.firsttrial.dto.DistinctField;
import net.dgg.zqq.services.trademark.TrademarkService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by 李程 on 2018/10/11.
 */
@Api(value = "初审公告", description = "初审公告接口")
@RestController
@RequestMapping("/term")
public class FirstTrialController extends BaseController {

    @Autowired
    TrademarkService trademarkService;

    //查询最近12期数据
    @RequestMapping(value = {"/last12terms"}, method = RequestMethod.POST)
    @ApiOperation(value = "最新12期数据", notes = "最新12期数据", httpMethod = "POST")
    public RestResponse launchPayment() {
        return this.getSuccessResponse(trademarkService.getLast12Terms());
    }

    //查询本期列表页面
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "查询当期的工商注册数据", notes = "查询当期的工商注册数据，分页查询", httpMethod = "POST")
    public Object queryPage(
            @ApiParam(name = "start", value = "数据集记录游标起始行", defaultValue = "0") @RequestParam(name = "start", defaultValue = "0", required = false) Integer start,
            @ApiParam(name = "length", value = "数据集记录读取条数", defaultValue = "10") @RequestParam(name = "length", defaultValue = "20", required = false) Integer length,
            @ApiParam(name = "announcementIssue", value = "期号") @RequestParam(name = "announcementIssue", required = false) String announcementIssue,
            @ApiParam(name = "name", value = "商标名称检索字段") @RequestParam(name = "name", required = false) String name,
            @ApiParam(name = "matchMethod", value = "商标名称检索匹配方法，含义：full-精确，like-精确+近似，near-近似", defaultValue = "full") @RequestParam(name = "matchMethod", defaultValue = "full", required = false) String matchMethod,
            @ApiParam(name = "regNo", value = "商标注册号") @RequestParam(name = "regNo", required = false) String regNo,
            @ApiParam(name = "applicantCn", value = "申请人") @RequestParam(name = "applicantCn", required = false) String applicantCn,
            @ApiParam(name = "tmSection", value = "代理机构") @RequestParam(name = "tmSection", required = false) String tmSection,
            @ApiParam(name = "intCls", value = "申请类别，传数字加汉字如：第43类，支持多类，用逗号分割") @RequestParam(name = "intCls", required = false) String intCls,
            HttpServletRequest request) {
        start *= length;
        DataTableRequest r = DataTableUtils.getParam(request);
        Map<String, Object> params = new HashMap<>();
        params.put("announcementIssue", announcementIssue);
        params.put("name", name);
        params.put("matchMethod", matchMethod);
        params.put("regNo", regNo);
        params.put("applicantCn", applicantCn);
        params.put("tmSection", tmSection);
        params.put("intCls", intCls);
        params.put("start", start);
        params.put("limit", length);
        Map<String, Object> searchResponse = trademarkService.queryPage(params);
        Integer count = Integer.parseInt(searchResponse.get("count").toString());
        DataTableResponse response = new DataTableResponse(r.getDraw(), count, count, (List<Object>) trademarkService.queryPage(params).get("list"), "");
        return this.getSuccessResponse(response);
    }

    //导出查询列表页面
    @RequestMapping(value = "/export", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "导出当期的工商注册数据", notes = "导出当期的工商注册数据，不分页查询", httpMethod = "POST")
    public void export(
            @ApiParam(name = "announcementIssue", value = "期号") @RequestParam(name = "announcementIssue", required = false) String announcementIssue,
            @ApiParam(name = "name", value = "商标名称检索字段") @RequestParam(name = "name", required = false) String name,
            @ApiParam(name = "matchMethod", value = "商标名称检索匹配方法，含义：full-精确，like-精确+近似，near-近似", defaultValue = "full") @RequestParam(name = "matchMethod", defaultValue = "full", required = false) String matchMethod,
            @ApiParam(name = "regNo", value = "商标注册号") @RequestParam(name = "regNo", required = false) String regNo,
            @ApiParam(name = "applicantCn", value = "申请人") @RequestParam(name = "applicantCn", required = false) String applicantCn,
            @ApiParam(name = "tmSection", value = "代理机构") @RequestParam(name = "tmSection", required = false) String tmSection,
            @ApiParam(name = "intCls", value = "申请类别，支持多类，用逗号分割") @RequestParam(name = "intCls", required = false) String intCls,
            HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("announcementIssue", announcementIssue);
        params.put("name", name);
        params.put("matchMethod", matchMethod);
        params.put("regNo", regNo);
        params.put("applicantCn", applicantCn);
        params.put("tmSection", tmSection);
        params.put("intCls", intCls);
        trademarkService.exportResponse(params, response);
    }

    @RequestMapping(value = "/queryDistinctFields", method = RequestMethod.POST)
    @ApiOperation(value = "查询申请人、代理机构", notes = "查询代理机构申请人", httpMethod = "POST")
    public RestResponse queryDistinctField(
            @ApiParam(name = "count", value = "数据集记录读取条数", defaultValue = "10", required = true) @RequestParam(name = "count", defaultValue = "20") Integer count,
            @ApiParam(name = "field", value = "匹配字段取值：tmSection-代理机构，applicationCn-申请人", required = true) @RequestParam(name = "field") DistinctField field,
            @ApiParam(name = "text", value = "匹配文本") @RequestParam(name = "text", required = false) String text
    ) {
        String fieldText;
        switch (field) {
            case tmSection:
                fieldText = "tmSection";
                break;
            case applicationCn:
                fieldText = "regName";
                break;
            default:
                return this.getFailResponse("检索字段非法");
        }
        return this.getSuccessResponse(trademarkService.queryField(fieldText, text, count));
    }


    //详细页面查询
    @RequestMapping(value = "/getTradeMarkInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询工商注册详情", notes = "查询工商注册详情，包含注册信息所有内容", httpMethod = "POST")
    public RestResponse getTradeMarkInfo(@ApiParam(name = "id", value = "标识", required = true) @RequestParam(name = "id") String id) {
        return this.getSuccessResponse(trademarkService.getTradeMark(id));
    }

    //下载详情
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "下载详情", notes = "下载pdf详情", httpMethod = "POST")
    public void getTradeMarkInfo(@ApiParam(name = "id", value = "标识", required = true) @RequestParam(name = "id") String id, HttpServletResponse response) {
        trademarkService.exportPdf(id, response);
    }

    //得到所有类目
    @RequestMapping(value = "/getAllTypes", method = RequestMethod.POST)
    @ApiOperation(value = "得到所有类目", notes = "获取所有存在的类别", httpMethod = "POST")
    public RestResponse getAllTypes() {
        return this.getSuccessResponse(trademarkService.getAllTypes());
    }


}
