package net.dgg.zqq.controller.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.services.order.PayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/10/29 9:09
 */

@Controller
@RequestMapping("/payRecord")
@Api(value = "支付记录后台管理")
public class PayRecordController {

    @Autowired
    PayRecordService payRecordService;

    @Autowired
    private TranslateUtil translateUtil;

    /**
     * 返回主页面
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String commentCheck() {
        return "pay_record/index";
    }

    //返回表单数据
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "支付记录管理系统", notes = "支付记录管理系统", httpMethod = "POST")
    @ResponseBody
    public Object query(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.payRecordService.pageQuery(params);
        translateUtil.translateList(new String[]{"payWay", "serviceTypeLevel1Code", "serviceTypeLevel3Code", "result"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }


}
