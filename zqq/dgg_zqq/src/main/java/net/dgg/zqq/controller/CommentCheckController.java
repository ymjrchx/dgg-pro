package net.dgg.zqq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.services.CommentCheckService;
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
 * @Description: 评论审核后台管理
 * @Author: huangl
 * @Date: 2018/9/29 18:49
 */

@RequestMapping("/comment")
@Controller
@Api(description = "评论-后台管理")
public class CommentCheckController {

    @Autowired
    CommentCheckService commentCheckService;

    @Autowired
    TranslateUtil translateUtil;


    /**
     * 返回主页面
     *
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String commentCheck() {
        return "commentCheck/comment_check";
    }

    //返回表单数据
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "评论审核管理", notes = "评论审核管理", httpMethod = "POST")
    @ResponseBody
    public Object charging(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.commentCheckService.pageQuery(params);
        translateUtil.translateList(new String[]{"serviceTypeLevel1Code", "serviceTypeLevel3Code"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    /**
     * 改变审核状态
     */

    @RequestMapping(value = "/changeFlag", method = RequestMethod.POST)
    @ApiOperation(value = "改变审核状态", notes = "改变审核状态", httpMethod = "POST")
    @ResponseBody
    public void updateFlag(Long id) {
        this.commentCheckService.updateFlag(id);
    }
}
