package net.dgg.zqq.controller.invoice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.entity.invoice.Invoice;
import net.dgg.zqq.services.invoice.InvoiceService;
import net.dgg.zqq.services.order.OrderService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/9 13:52
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {

    @Autowired
    TranslateUtil translateUtil;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    OrderService orderService;


    /**
     * 发票管理主页面
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String invoiceManager() {
        return "invoice/index";
    }

    //返回表单数据
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "发票管理系统", notes = "发票管理系统", httpMethod = "POST")
    @ResponseBody
    public Object query(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.invoiceService.pageQuery(params);
        translateUtil.translateList(new String[]{"result"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    /**
     * 改变审核状态为成功
     */

    @RequestMapping(value = "/changeResultT", method = RequestMethod.POST)
    @ApiOperation(value = "通过发票审核", notes = "通过发票审核", httpMethod = "POST")
    @ResponseBody
    public void updateResultT(Long id) {
        this.invoiceService.updateResultT(id);
    }

    /**
     * 改变审核状态为失败
     */

    @RequestMapping(value = "/changeResultF", method = RequestMethod.POST)
    @ApiOperation(value = "未通过发票审核", notes = "未通过发票审核", httpMethod = "POST")
    @ResponseBody
    public void updateResultF(Long id) {
        this.invoiceService.updateResultF(id);
    }

    /**
     * 申请发票
     */
    @RequestMapping(value = "/applyInvoice", method = RequestMethod.GET)
    @ApiOperation(value = "申请发票", notes = "申请发票", httpMethod = "GET")
    @ResponseBody
    public Object applyInvoice(@ApiParam(name = "invoice", value = "发票信息", required = true) @RequestBody Invoice invoice) {
        try {
            this.invoiceService.invoiceApply(invoice);
            return this.getSuccessResponse("success");
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 订单信息
     */
    @RequestMapping(value = "/orderInfo", method = RequestMethod.GET)
    @ApiOperation(value = "订单信息", notes = "订单信息", httpMethod = "GEY")
    @ResponseBody
    public Object orderInfo(@ApiParam(name = "userId", value = "用户Id", required = true) @RequestParam String userId) {

        try {
            Map map = this.orderService.selectInfo(userId);
            List list = (List) map.get("orderList");
            if (list.size() > 0) {
                return getSuccessResponse(map);
            } else return this.getFailResponse("未找到相关订单信息");
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }
}
