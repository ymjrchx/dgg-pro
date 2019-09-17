package net.dgg.zqq.controller.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.entity.order.ApplicantTemplate;
import net.dgg.zqq.services.order.ApplicantTemplateService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: ApplicantTemplateController
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/11/14 9:52
 */

@Controller
@RequestMapping("/applicantTemplate")
@Api(description = "申请模板")
public class ApplicantTemplateController extends BaseController {

    @Autowired
    private ApplicantTemplateService applicantTemplateService;

    /**
     * 保存修改申请模板
     *
     * @param applicantTemplate
     * @return
     */
    @RequestMapping(value = "/saveApplicantTemplate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存修改申请模板", notes = "保存申请模板", httpMethod = "POST")
    public Object saveApplicantTemplate(
            @ApiParam(name = "applicantTemplate", value = "申请模板信息：---" +
                    "<br>createrId:用户ID " +
                    "<br>applicantType:申请人类型 " +
                    "<br>templateName:模板名称 " +
                    "<br>applicantName:申请主体 " +
                    "<br>businessLicenceAddress:申请地址 " +
                    "<br>businessLicenceFile:营业执照 " +
                    "<br>businessLicenceNo:营业执照号 " +
                    "<br>contactName:联系人 " +
                    "<br>contactPhone:联系人电话 " +
                    "<br>contactEmail:联系人邮箱 " +
                    "<br>postalcode:邮政编码 " +
                    "<br>applicantCardNo:申请人身份证号 " +
                    "<br>applicantCardFile:身份证复印件 " +
                    "<br>applicantUserName:申请人 " +
                    "<br>applicantCardAddress:申请人地址 ", required = true) @RequestBody ApplicantTemplate applicantTemplate) {
        try {
            applicantTemplateService.save(applicantTemplate);
            return this.getSuccessResponse("保存成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /**
     * 分页查询
     *
     * @param createrId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/queryByPage", method = RequestMethod.GET)
    @ApiOperation(value = "申请模板分页查询", notes = "申请模板分页查询", httpMethod = "GET")
    @ResponseBody
    public RestResponse queryByPage(
            @ApiParam(name = "createrId", value = "用户ID", required = true) @RequestParam String createrId,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = false) @RequestParam(required = false) Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页码", required = true) @RequestParam Integer pageNum) {

        try {
            Map<String, Object> map = applicantTemplateService.queryByPage(createrId, pageSize, pageNum);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /**
     * 根据ID查询申请模板
     *
     * @param id
     * @param createrId
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询申请模板", notes = "根据ID查询申请模板", httpMethod = "GET")
    @ResponseBody
    public RestResponse pageQueryByTypeLevel3Code(
            @ApiParam(name = "id", value = "申请模板ID", required = true) @RequestParam Long id,
            @ApiParam(name = "createrId", value = "用户ID", required = true) @RequestParam String createrId) {

        try {
            ApplicantTemplate applicantTemplate = applicantTemplateService.findById(id, createrId);
            return this.getSuccessResponse(applicantTemplate);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    /**
     * 删除申请模板
     *
     * @param createrId
     * @param id
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ApiOperation(value = "删除申请模板", notes = "删除申请模板", httpMethod = "GET")
    @ResponseBody
    public RestResponse del(
            @ApiParam(name = "createrId", value = "用户ID", required = true) @RequestParam String createrId,
            @ApiParam(name = "id", value = "申请模板ID", required = true) @RequestParam Long id) {
        try {
            applicantTemplateService.del(createrId, id);
            return this.getSuccessResponse("删除成功");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


    /**
     * 设置默认接口
     *
     * @param createrId
     * @param id
     * @return
     */
    @RequestMapping(value = "/makeDefault", method = RequestMethod.GET)
    @ApiOperation(value = "设置默认", notes = "设置默认", httpMethod = "GET")
    @ResponseBody
    public RestResponse makeDefault(
            @ApiParam(name = "createrId", value = "用户ID", required = true) @RequestParam String createrId,
            @ApiParam(name = "id", value = "申请模板ID", required = true) @RequestParam Long id) {
        try {
            applicantTemplateService.makeDefault(createrId, id);
            return this.getSuccessResponse("设置默认成功");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }

    /**
     * 查询接口无分页有限制
     *
     * @param createrId
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ApiOperation(value = "根据声请人类型查询接口", notes = "根据声请人类型查询接口", httpMethod = "GET")
    @ResponseBody
    public RestResponse find(
            @ApiParam(name = "createrId", value = "用户ID", required = true) @RequestParam String createrId) {
        try {
            return this.getSuccessResponse(applicantTemplateService.find(createrId));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


}