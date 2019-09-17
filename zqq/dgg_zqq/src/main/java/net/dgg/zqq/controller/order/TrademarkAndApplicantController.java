package net.dgg.zqq.controller.order;

import io.swagger.annotations.Api;
import net.dgg.zqq.services.order.TrademarkAndApplicantService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/trademarkerAndApplicantController")
@Api(description = "商标和申请信息")
public class TrademarkAndApplicantController extends BaseController {

    @Autowired
    private TrademarkAndApplicantService trademarkAndApplicantService;

}
