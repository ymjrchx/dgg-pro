package net.dgg.zqq.controller.news;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.services.TreeBookService;
import net.dgg.zqq.services.news.NewsService2;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <NewsController2>
 * @despriction 资讯2
 * @create 2018/11/22 16:26
 **/
@Controller
@RequestMapping("/news")
@Api(description = "资讯")
public class NewsController2 extends BaseController {
    @Autowired
    private NewsService2 newsService2;
    @Autowired
    private TranslateUtil translateUtil;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private TreeBookService treeBookService;


    @ResponseBody
    @GetMapping(value = "type2")
    @ApiOperation(value = "type分类接口", notes = "type分类接口", httpMethod = "GET")
    public RestResponse getNewsType() {
        List<TreeBook> news_types = treeBookService.getNameAndCodeByCode("news_types");
        List<Map> maps = new ArrayList<>(news_types.size());

        for (TreeBook treeBook : news_types) {
            Map map = new HashMap();
            map.put("code", treeBook.getCode());
            map.put("name", treeBook.getName());
            map.put("ext1", treeBook.getExt1());
            maps.add(map);
        }
        return getSuccessResponse(maps);
    }

    @RequestMapping(value = "/getContentById", method = RequestMethod.GET)
    @ApiOperation(value = "资讯详细接口", notes = "获取每条资讯内容接口", httpMethod = "GET")
    @ResponseBody
    public Object getContentById(
            @ApiParam(name = "id", value = "每条资讯ID", required = true) @RequestParam Long id,
            @ApiParam(name = "type", value = "分页查询类型", required = true) @RequestParam(required = true) String type) {
        try {

            return this.getSuccessResponse(newsService2.getNewsContentById(type, id));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/getContentByNumber", method = RequestMethod.GET)
    @ApiOperation(value = "资讯详细接口", notes = "获取每条资讯内容接口", httpMethod = "GET")
    @ResponseBody
    public Object getContentByNumber(
            @ApiParam(name = "number", value = "资讯编号", required = true) @RequestParam(required = true) Long number,
            @ApiParam(name = "limit", value = "查询多少条", required = false) @RequestParam(required = false) Integer limit
            ) {
        try {
            return this.getSuccessResponse(newsService2.getNewsContentByNumber(number,limit));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /**
     * 最新资讯按类型
     * @param limit
     * @param type
     * @return
     */
    @RequestMapping(value = "/getNewsByType", method = RequestMethod.GET)
    @ApiOperation(value = "最新资讯按类型", notes = "最新资讯按类型", httpMethod = "GET")
    @ResponseBody
    public Object getNewsByType(
            @ApiParam(name = "limit", value = "查询多少条", required = true) @RequestParam(required = true) Integer limit,
            @ApiParam(name = "type", value = "分页查询类型", required = true) @RequestParam(required = true) String type) {
        try {
            return this.getSuccessResponse(newsService2.getNewsByType(type, limit));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /**
     * 查看几天之内的资讯  按照浏览量排序
     * @param limit
     * @param type
     * @param datecount
     * @return
     */
    @RequestMapping(value = "/getNewsByViewTimes", method = RequestMethod.GET)
    @ApiOperation(value = "查看几天之内的资讯  按照浏览量排序", notes = "查看30天之内的资讯  按照浏览量排序", httpMethod = "GET")
    @ResponseBody
    public Object getNewsByViewTimes(
            @ApiParam(name = "limit", value = "查询多少条", required = true) @RequestParam(required = true) Integer limit,
            @ApiParam(name = "type", value = "分页查询类型", required = true) @RequestParam(required = true) String type,
            @ApiParam(name = "datecount", value = "查询近几天的资讯", required = true) @RequestParam(required = true) Integer datecount) {
        try {
            return this.getSuccessResponse(newsService2.getNewsByViewTimes(type, limit,datecount));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
