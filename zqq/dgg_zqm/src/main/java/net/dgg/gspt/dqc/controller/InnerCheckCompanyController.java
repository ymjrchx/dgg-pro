package net.dgg.gspt.dqc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.services.OperationRecordService;
import net.dgg.gspt.dqc.services.checkCompany.CheckCompanyService;
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

import static net.dgg.gspt.dqc.constant.OperationRecordConstant.COMPANY_CHECK;

/**
 * @author 刘阳
 * @ClassName <InnerCheckCompanyController>
 * @despriction 网站自用核名 接口
 * @create 2018/08/09 18:07
 **/
@Controller
@RequestMapping("/company")
@Api(description = "工商接口")
public class InnerCheckCompanyController extends BaseController {

    @Autowired
    private CheckCompanyService checkCompanyService;
    @Autowired
    private OperationRecordService operationRecordService;

    /**
     * 核名接口
     */
    @RequestMapping(value = "/checkCompany", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "核名接口", notes = "核名接口", httpMethod = "GET")
    public Object checkCompany(HttpServletRequest request,
                               @ApiParam(name = "city", value = "行政区划", required = true) @RequestParam String city,
                               @ApiParam(name = "name", value = "名称", required = true) @RequestParam String name,
                               @ApiParam(name = "industry", value = "行业", required = true) @RequestParam String industry,
                               @ApiParam(name = "companyType", value = "公司类型", required = true) @RequestParam String companyType,
                               @ApiParam(name = "highlight", value = "是否用<i>标签标明高亮字词:0 否， 1 是, 默认为 :1 ") @RequestParam(required = false) Integer highlight,
                               @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            if (StringUtils.isEmpty(token) || !RedisUtils.exists(token)) {
                return this.getFailResponse("请先登录！");
            }
            String url = request.getRequestURI();
            String ip = IpAddressUtils.getIP(request);
            String content = city + "|" + name + "|" + industry + "|" + companyType;
            this.operationRecordService.saveRecord(userId, COMPANY_CHECK, content);
            return this.getSuccessResponse(checkCompanyService.innerCheckCompany(url, userId, city, name, industry, companyType, highlight, ip));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }
    }
}
