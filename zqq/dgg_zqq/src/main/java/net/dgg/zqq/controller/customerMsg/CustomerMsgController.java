package net.dgg.zqq.controller.customerMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.entity.customerMsg.CustomerMsg;
import net.dgg.zqq.services.customerMsg.CustomerMsgService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <CustomerMsgController>
 * @despriction
 * @create 2018/12/18 16:58
 **/
@Controller
@RequestMapping("/customerMsg")
@Api(description = "客留信息")
public class CustomerMsgController extends BaseController {
    @Autowired
    private CustomerMsgService customerMsgService;
    @Autowired
    private TranslateUtil translateUtil;


    @PostMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存客留信息", notes = "保存客留信息", httpMethod = "POST")
    public Object save(
            @ApiParam(name = "customerMsg", value = "客留信息") @RequestBody CustomerMsg customerMsg) {
        try {
            this.customerMsgService.save(customerMsg);
            return this.getSuccessResponse("success");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @ApiOperation(value = "页面跳转-列表页面", notes = "页面跳转-列表页面", httpMethod = "GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/customerMsg/customerMsgList";
    }


    //查询操作
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "列表查询", notes = "列表查询", httpMethod = "POST")
    @ResponseBody
    public DataTableResponse queryPage(@ApiParam(name = "params", value = "查询条件map", required = true) @RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.customerMsgService.pageQuery(params);

        for (Map map : list) {

        }

        translateUtil.translateList(new String[]{"typeName"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }
}
