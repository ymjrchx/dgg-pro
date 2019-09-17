package net.dgg.gspt.dqc.controller.getCompanyName;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.services.GetCompanyName.GetCompanyNameService;
import net.dgg.gspt.dqc.services.OperationRecordService;
import net.dgg.gspt.dqc.utils.IpAddressUtils;
import net.fblock.web.common.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static net.dgg.gspt.dqc.constant.OperationRecordConstant.COMPANY_NAMING;
import static net.dgg.gspt.dqc.constant.OperationRecordConstant.COMPANY_TEST;

/**
 * @author 刘阳
 * @ClassName <GetCompanyNameController>
 * @despriction
 * @create 2018/12/17 16:26
 **/
@Controller
@RequestMapping("/getCompanyName")
@Api(description = "公司取名")
public class GetCompanyNameController extends BaseController {
    @Autowired
    private GetCompanyNameService getCompanyNameService;
    @Autowired
    private OperationRecordService operationRecordService;


    //添加操作
    @RequestMapping(value = "/getCompanyName", method = RequestMethod.POST)
    @ApiOperation(value = "获取公司取名", notes = "公司取名", httpMethod = "POST")
    @ResponseBody
    public Object getCompanyName(HttpServletRequest request,
                                 @ApiParam(name = "industry", value = "行业code", required = true) @RequestParam("industry") String industry) {

        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            GetCompanyNameService.GetCompanyRes re = this.getCompanyNameService.getGetCompanyName(industry, IpAddressUtils.getIP(request));
            if (StringUtils.isEmpty(token) || !RedisUtils.exists(token)) {
                GetCompanyNameService.GetCompanyRes re1 = new GetCompanyNameService.GetCompanyRes();
                re1.setMsg(re.getMsg());
                re1.setStatus(re.getStatus());
                re1.setData(re != null && re.getData() != null && !re.getData().isEmpty() ? Arrays.asList(re.getData().get(0)) : null);
                return this.getSuccessResponse(re1);
            }
            this.operationRecordService.saveRecord(token, COMPANY_NAMING, industry);
            return this.getSuccessResponse(re);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/queryInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取公司名信息", notes = "获取公司名信息", httpMethod = "POST")
    @ResponseBody
    public Object queryInfo(HttpServletRequest request,
                            @ApiParam(name = "name", value = "公司名", required = true) @RequestParam("name") String name,
                            @ApiParam(name = "industry", value = "行业code", required = true) @RequestParam("industry") String industry) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            GetCompanyNameService.GetCompanyInfoRes res = this.getCompanyNameService.queryInfo(name, industry, IpAddressUtils.getIP(request));
            String content = name + "|" + industry;
            this.operationRecordService.saveRecord(token, COMPANY_TEST, content);
            return this.getSuccessResponse(res);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/queryZhouYi", method = RequestMethod.POST)
    @ApiOperation(value = "获取公司名周易解释", notes = "获取公司名信息", httpMethod = "POST")
    @ResponseBody
    public Object queryZhouYi(HttpServletRequest request,
                              @ApiParam(name = "name", value = "公司名", required = true) @RequestParam("name") String name) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            return this.getSuccessResponse(this.getCompanyNameService.queryZhouyi(name, IpAddressUtils.getIP(request)));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
