package net.dgg.zqq.controller.payWay;

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

import java.util.List;

/**
 * @author 刘阳
 * @ClassName <PayWayController>
 * @despriction 支付方式
 * @create 2018/10/11 13:42
 **/
@Controller
@RequestMapping("/payWay")
@Api(description = "支付方式")
public class PayWayController extends BaseController {

    @Autowired
    private TreeBookService treeBookService;

    @RequestMapping(value = "/getAllPayWay", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所有支付方式", notes = "查询所有支付方式", httpMethod = "GET")
    public Object queryUserOrder() {
        try {
            String topCode = "pay_way";
            List<TreeBook> resultList = treeBookService.getTreeBookListByCode(topCode, 1, 1, null);
            return this.getSuccessResponse(resultList);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }
}
