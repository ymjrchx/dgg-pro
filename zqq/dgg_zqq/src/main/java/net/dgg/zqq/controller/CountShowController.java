package net.dgg.zqq.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.services.TreeBookService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Count")
@Api(description = "banner下数据显示")
public class CountShowController extends BaseController {

    @Autowired
    TreeBookService treeBookService;


    /**
     * * 首页 数据搜索次数，
     * 服务人数显示
     */

    @RequestMapping(value = "/showData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "banner下数据显示", notes = "banner下数据显示")
    public Object countShow() {
        return this.getSuccessResponse(this.treeBookService.getChildNameByCode("provide_num"));
    }

    /**
     * * 首页 标题配置
     */
    @RequestMapping(value = "/showTitle", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "首页", notes = "首页")
    public Object titleShow() {
        return this.getSuccessResponse(this.treeBookService.getTreeBookByCode("web_page_conf_sy"));
    }

    /**
     * *首页  链接配置显示
     */
    @RequestMapping(value = "/showLink", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "友情链接", notes = "友情链接")
    public Object linkShow() {
        String pcode = "homepage_link";
        TreeBook treeBook = this.treeBookService.getTreeBookByCode(pcode);
        return this.getSuccessResponse(this.treeBookService.getTreeBooksByPid(treeBook.getId()));
    }

}
