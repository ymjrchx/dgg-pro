package net.dgg.gspt.dqc.controller.template;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.services.fileUpload.FileUploadService;
import net.dgg.gspt.dqc.services.template.TemplateService;
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
public class TemplateBeforeController extends BaseController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private FileUploadService fileUploadService;

    /*
     *  下载相应的模板
     */
    @RequestMapping(value = "/findTemplateUrl", method = RequestMethod.GET)
    @ApiOperation(value = "获取模板", notes = "根据模板类型获取模板", httpMethod = "GET")
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
            @ApiParam(name = "templateTypes", value = "模板类型编码(以逗号分隔)，" +
                    "有限责任公司:template_type_11," +
                    "合伙企业:template_type_12," +
                    "分公司:template_type_13," +
                    "个人独资企业:template_type_14" +
                    "企业注册：template_type_15" +
                    "经营公司：template_type_16", required = true) @RequestParam String templateTypes,
            @ApiParam(name = "limit", value = "数量,非必填", required = false) @RequestParam(required = false) Integer limit) {
        try {
            List<Map<String, Object>> list = templateService.findByTemplateTypes(templateTypes, limit);
            return this.getSuccessResponse(list);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }

    @RequestMapping(value = "/addDownload", method = RequestMethod.GET)
    @ApiOperation(value = "增加下载次数", notes = "增加下载次数", httpMethod = "GET")
    @ResponseBody
    public RestResponse findByTemplateType(
            @ApiParam(name = "id", value = "id", required = true) @RequestParam(required = true) Long id) {
        try {
            return this.getSuccessResponse(templateService.addDownload(id));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


}
