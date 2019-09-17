package net.dgg.dqc.backservice.controller;

import net.dgg.dqc.backservice.service.SyncDealDataService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by jiangsh on 2018/8/9 09:57
 */
@Controller
@RequestMapping("/syncDealData/")
public class SyncDealDataController extends BaseController {

    @Autowired
    private SyncDealDataService syncDealDataService;

    @RequestMapping(value = "/startInit", method = RequestMethod.GET)
    @ResponseBody
    public Object startInit() {
        syncDealDataService.queryAllqcc("qichacha_com", "detail_dgg");
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startEqc", method = RequestMethod.GET)
    @ResponseBody
    public Object startEqc() {
        syncDealDataService.queryAlleqc("eqicha_com", "detail_results");
        return this.getSuccessResponse("success");
    }


    @RequestMapping(value = "/startQcc", method = RequestMethod.GET)
    @ResponseBody
    public Object startQcc() {
        syncDealDataService.queryAllqcc("qichacha_com", "detail_results");
        return this.getSuccessResponse("success");
    }


    @RequestMapping(value = "/startZcgl", method = RequestMethod.GET)
    @ResponseBody
    public Object startZcgl() {
        syncDealDataService.queryAllzcgl("zcgl", "company_info");
        return this.getSuccessResponse("success");
    }


    @RequestMapping(value = "/startResumes", method = RequestMethod.GET)
    @ResponseBody
    public Object startResumes() {
        syncDealDataService.queryAllResumes("58guakao_com", "new_detail_results");
        return this.getSuccessResponse("success");
    }


}
