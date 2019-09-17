package net.dgg.zqq.controller.trademarkConsult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.services.TreeBookService;
import net.dgg.zqq.services.trademarkConsult.TrademarkConsultService;
import net.dgg.zqq.utils.IpAddressUtils;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/trademarkConsult")
@Api(description = "商标咨询")
public class TrademarkConsultController extends BaseController {

    @Autowired
    private TrademarkConsultService trademarkConsultService;

    @Autowired
    private TreeBookService treeBookService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "商标咨询保存操作", notes = "商标咨询保存操作", httpMethod = "POST")
    @ResponseBody
    public RestResponse save(
            @ApiParam(name = "trademarkeName", value = "商标名称", required = false) @RequestParam(required = false) String trademarkeName,
            @ApiParam(name = "trademarkerType", value = "商标类型", required = true) @RequestParam String trademarkerType,
            @ApiParam(name = "userName", value = "你的称呼", required = true) @RequestParam String userName,
            @ApiParam(name = "phone", value = "联系号码", required = true) @RequestParam String phone,
            @ApiParam(name = "email", value = "邮箱", required = false) @RequestParam(required = false) String email,
            @ApiParam(name = "intentionalPrice", value = "意向价格字典编码", required = true) @RequestParam String intentionalPrice,
            @ApiParam(name = "requirementDesc", value = "需求描述", required = false) @RequestParam(required = false) String requirementDesc,
            @ApiParam(name = "applicationNum", value = "申请号", required = true) @RequestParam String applicationNum,
            HttpServletRequest request) {

        try {
            Assert.hasText(applicationNum,"申请号不能为空");
            String ip = IpAddressUtils.getIP(request);
            Assert.hasText(ip,"IP获取失败");
            String key = ip.concat("-").concat(applicationNum);

            if (RedisUtils.exists(key)) {
                return this.getFailResponse("您已经提交过了 请24小时后重试");
            }

            RedisUtils.set(key,key);
            RedisUtils.expire(key,60*60*24);

            trademarkConsultService.save(trademarkeName, trademarkerType, userName, phone, email, intentionalPrice, requirementDesc, applicationNum);
            return this.getSuccessResponse(null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/selectPrice", method = RequestMethod.GET)
    @ApiOperation(value = "查询意向价格接口", notes = "查询意向价格接口", httpMethod = "GET")
    @ResponseBody
    public RestResponse selectPrice() {
        try {
            List<TreeBook> list = treeBookService.getTreeBookListByCode("intentional_price", 1, 2, null);
            List<Map<String, Object>> resultList = null;
            if (list != null && list.size() > 0) {
                resultList = new ArrayList<>();
                for (TreeBook treeBook : list) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", treeBook.getCode());
                    map.put("name", treeBook.getName());
                    resultList.add(map);
                }

            }
            return this.getSuccessResponse(resultList);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
