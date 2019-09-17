package net.dgg.zqq.controller.trademarkclassify;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dgg.zqq.services.trademarkclassify.ClassFirstService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huanggai
 * @creatTime 11:28 2018/9/30
 */
@Controller
@RequestMapping("/classfirst")
@Api(description = "一级分类")
public class ClassFirstController extends BaseController{
    @Autowired
    private ClassFirstService classFirstService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ApiOperation(value = "商标一级分类查询", notes = "商标一级分类查询",httpMethod = "GET")
    @ResponseBody
    public Object query() {
        try {
            return this.getSuccessResponse(this.classFirstService.queryMap(null));
        }catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }
}
