package net.dgg.zqq.controller.template;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.dgg.zqq.services.template.TemplateService;
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

@Controller
@RequestMapping("/template")
@Api(description = "模板管理")
public class TemplateBeforeController extends BaseController{

    @Autowired
    private TemplateService templateService;

    @Autowired
    private FileUploadService fileUploadService;

    /*
     *  下载相应的模板
     */
    @RequestMapping(value = "/findTemplateUrl", method = RequestMethod.GET)
    @ApiOperation(value = "获取模板", notes = "根据模板类型获取模板",httpMethod = "GET")
    @ResponseBody
    public RestResponse findTemplateUrl(
            @ApiParam(name = "userId", value = "用户ID", required = false) @RequestParam(required = false) String userId) {
        try {
            List<Map<String, Object>> list = templateService.findByMap(userId);
            return this.getSuccessResponse(list);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


    /*
     *  根据模板类型获取模板
     */
    @RequestMapping(value = "/findByTemplateType", method = RequestMethod.GET)
    @ApiOperation(value = "根据模板类型获取模板", notes = "根据模板类型（以逗号分隔）获取模板", httpMethod = "GET")
    @ResponseBody
    public RestResponse findByTemplateType(
            @ApiParam(name = "templateTypes", value = "模板类型编码(以逗号分隔)", required = true) @RequestParam String templateTypes) {
        try {
            List<Map<String, Object>> list = templateService.findByTemplateTypes(templateTypes);
            return this.getSuccessResponse(list);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }



}
