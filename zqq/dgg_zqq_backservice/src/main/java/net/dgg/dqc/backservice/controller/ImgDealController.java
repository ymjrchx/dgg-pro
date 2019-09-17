package net.dgg.dqc.backservice.controller;

import net.dgg.dqc.backservice.dao_mongodb.TempEntityMongodbDao;
import net.dgg.dqc.backservice.fast_dfs.FastDfsService;
import net.dgg.dqc.backservice.service.ImgDealService;
import net.fblock.web.common.BaseController;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <TestQueryController>
 * @despriction
 * @create 2018/08/03 11:11
 **/
@Controller
@RequestMapping("/imgDeal/")
public class ImgDealController extends BaseController {
    @Autowired
    private ImgDealService imgDealService;
    @Autowired
    private TempEntityMongodbDao tempEntityMongodbDao;
    @Autowired
    private FastDfsService fastDfsService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @RequestMapping(value = "/imgDeal", method = RequestMethod.POST)
    @ResponseBody
    public Object imgDeal(String path) {
        try {
            imgDealService.setDirPath(path);
            imgDealService.startDeal();

            Map map = new HashMap();
            map.put("dirPath", imgDealService.getDirPath());
            map.put("fileNum", imgDealService.getPathList().size());
            // map.put("allFile", imgDealService.getPathList());
            return this.getSuccessResponse(map);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/imgDealInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object imgDealInfo() {
        try {
            Map map = new HashMap();
            map.put("总数", this.imgDealService.getPathList().size());
            int s = this.imgDealService.getSuccessCount().intValue(), f = this.imgDealService.getFailCount().intValue();
            map.put("已处理", s + f);
            map.put("成功", s);
            map.put("失败", f);
            return this.getSuccessResponse(map);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/imgFailRecordRetryDeal", method = RequestMethod.POST)
    @ResponseBody
    public Object imgFailRecordRetryDeal() {
        try {
            Map map = this.imgDealService.startDealFailRecord();
            return this.getSuccessResponse(map);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }
    }


}
