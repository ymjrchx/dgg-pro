package net.dgg.zqq.controller.news;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.services.news.NewsLabelService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: NewsLabelController
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/11/9 13:37
 */
@Controller
@RequestMapping("/newsLabel")
@Api(description = "资讯标签")
public class NewsLabelController extends BaseController {

    @Autowired
    private NewsLabelService newsLabelService;

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ApiOperation(value = "咨询标签查询接口", notes = "咨询标签查询接口", httpMethod = "POST")
    @ResponseBody
    public RestResponse newsPageQuery(
            @ApiParam(name = "pageSize", value = "分页查询条数", required = true) @RequestParam Integer pageSize,
            @ApiParam(name = "pageNum", value = "分页查询第几页", required = true) @RequestParam Integer pageNum,
            @ApiParam(name = "newsLabel", value = "咨询标签名", required = true) @RequestParam String newsLabel) {
        try {
            List<Map<String, Object>> list = newsLabelService.find(pageSize, pageNum, newsLabel);
            return this.getSuccessResponse(list);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


}