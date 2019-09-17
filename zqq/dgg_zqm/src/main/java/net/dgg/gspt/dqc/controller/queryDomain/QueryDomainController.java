package net.dgg.gspt.dqc.controller.queryDomain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.services.domainQuery.DomainQueryService;
import net.fblock.web.common.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘阳
 * @ClassName <QueryDomainController>
 * @despriction 域名查询
 * @create 2018/12/14 18:06
 **/
@Api(description = "域名查询")
@Controller
@RequestMapping("/queryDomain")
public class QueryDomainController extends BaseController {
    @Autowired
    private DomainQueryService domainQueryService;


    @ApiOperation(value = "域名查询", notes = "页面跳转-修改页面", httpMethod = "POST")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Object query(HttpServletRequest request, @ApiParam(name = "domainName", value = "域名", required = true) @RequestParam String domainName,
                        @ApiParam(name = "suffix", value = "后缀", required = true) @RequestParam String suffix) {
        try {
            String token = request.getHeader(PTConst.USER_TOKEN);
            if (StringUtils.isEmpty(token) || !RedisUtils.exists(token)) {
                return this.getFailResponse("请先登录！");
            }
            return this.getSuccessResponse(this.domainQueryService.queryDomain(domainName, suffix));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }


    }

}
