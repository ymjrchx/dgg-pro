package net.dgg.dqc.backservice.controller;

import net.dgg.dqc.backservice.dao_mongodb.CompanyNameResultMongodbDao;
import net.dgg.dqc.backservice.service.CompanyNameResultService;
import net.dgg.dqc.backservice.service.SyncDataDequeService;
import net.dgg.dqc.backservice.service.SyncDataService;
import net.dgg.dqc.backservice.service.ThreadSaveService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <SyncDataController>
 * @despriction
 * @create 2018/07/25 18:42
 **/
@Controller
@RequestMapping("/syncData/")
public class SyncDataController extends BaseController {


    @Autowired
    private CompanyNameResultService companyNameResultService;
    @Autowired
    private CompanyNameResultMongodbDao companyNameResultMongodbDao;
    @Autowired
    private ThreadSaveService threadSaveService;
    @Autowired
    private SyncDataService syncDataService;
    @Autowired
    private SyncDataDequeService syncDataDequeService;


    /**
     * @return
     */
    @RequestMapping(value = "/startSync", method = RequestMethod.GET)
    @ResponseBody
    public Object startSync() {
        try {
            syncDataService.startSync();
            return this.getSuccessResponse("success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getFailResponse("fail!");
    }

    /**
     * @return
     */
    @RequestMapping(value = "/syncInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object syncInfo() {
        try {
            Map map = new HashMap();
            map.put("总数据量", this.syncDataService.getAll());
            map.put("已查询量", this.syncDataDequeService.getAll());
            map.put("未提交量", this.syncDataDequeService.size());
            map.put("已提交量", this.threadSaveService.getAll());

            return this.getSuccessResponse(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getFailResponse("fail!");
    }


    /**
     * @return
     */
    @RequestMapping(value = "/threadDeal", method = RequestMethod.GET)
    @ResponseBody
    public Object threadDeal() {
        try {
            threadSaveService.threadDeal();
            return this.getSuccessResponse("success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getFailResponse("fail!");
    }


}
