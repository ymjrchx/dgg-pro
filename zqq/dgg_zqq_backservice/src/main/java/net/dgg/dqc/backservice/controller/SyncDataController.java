package net.dgg.dqc.backservice.controller;

import net.dgg.dqc.backservice.service.SyncDataDequeService;
import net.dgg.dqc.backservice.service.SyncDataService;
import net.dgg.dqc.backservice.service.ThreadSaveService;
import net.dgg.iboss.base.util.DateUtil;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private Date queryStart;
    private Date queryEnd;

    @Autowired
    private ThreadSaveService threadSaveService;
    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private SyncDataDequeService syncDataDequeService;


    private SimpleDateFormat smpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");


    // 1533737472558     2018年8月8日22时11分12秒558毫秒

    @RequestMapping(value = "/countSync", method = RequestMethod.GET)
    @ResponseBody
    public Object syncCount(@RequestParam String queryStart, @RequestParam String queryEnd) {
        try {
            this.queryStart = smpleDateFormat.parse(queryStart);
            this.queryEnd = smpleDateFormat.parse(queryEnd);
            return this.getSuccessResponse(this.syncDataService.count(this.queryStart, this.queryEnd));
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /**
     * @return
     */
    @RequestMapping(value = "/startSync", method = RequestMethod.GET)
    @ResponseBody
    public Object startSync() {
        try {
            this.syncDataDequeService.resetAll();
            this.threadSaveService.resetAll();
            Long count = syncDataService.startSync(this.queryStart, this.queryEnd);
            Map map = new HashMap<>();
            map.put("queryStart", this.smpleDateFormat.format(this.queryStart));
            map.put("queryEnd", this.smpleDateFormat.format(this.queryEnd));
            map.put("count", count);
            return this.getSuccessResponse(map);
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
            map.put("查询起始时间", DateUtil.dateToString(this.queryStart));
            map.put("查询截止时间", DateUtil.dateToString(this.queryEnd));
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
