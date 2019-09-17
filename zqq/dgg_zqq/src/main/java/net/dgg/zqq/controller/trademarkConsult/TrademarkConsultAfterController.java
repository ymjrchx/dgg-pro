package net.dgg.zqq.controller.trademarkConsult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.services.trademarkConsult.TrademarkConsultService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/trademarkConsult")
@Api(description = "商标咨询-后台管理")
public class TrademarkConsultAfterController extends BaseController {

    @Autowired
    private TrademarkConsultService trademarkConsultService;

    @Autowired
    private TranslateUtil translateUtil;

    @ApiOperation(value = "页面跳转-列表页面", notes = "页面跳转-列表页面", httpMethod = "GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "trademarkConsult/trademarkConsultList";
    }


    //查询操作
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "查询商标咨询", notes = "查询商标咨询", httpMethod = "POST")
    @ResponseBody
    public DataTableResponse queryPage(@ApiParam(name = "params", value = "查询条件map", required = true) @RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.trademarkConsultService.pageQuery(params);
        translateUtil.translateList(new String[]{"intentionalPrice"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }


    //修改状态
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "商标咨询-修改状态", notes = "商标咨询-修改状态", httpMethod = "POST")
    @ResponseBody
    public Object delete(@ApiParam(name = "id", value = "商标咨询ID", required = true) @RequestParam Long id,
                         @ApiParam(name = "flag", value = "商标咨询状态", required = true) @RequestParam Integer flag) {
        try {
            this.trademarkConsultService.delete(id, flag);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
