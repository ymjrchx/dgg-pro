package net.dgg.zqq.controller.webConf;


import net.dgg.zqq.entity.webConf.WebConfParam;
import net.dgg.zqq.services.webConf.WebConfService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 刘阳
 * @ClassName <WebConfController>
 * @despriction 前端web配置 控制器
 * @create 2018/08/09 9:53
 **/
@Controller
@RequestMapping("/webConf/")
public class WebConfController extends BaseController {

    @Autowired
    private WebConfService webConfService;


    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        List<WebConfParam> webConfParamList = this.webConfService.queryAll();
        for (WebConfParam webConfParam : webConfParamList) {
            model.addAttribute(webConfParam.getCode(), webConfParam.getValue());
        }
        return "web_conf/web_conf";
    }

    /**
     * 更新配置
     *
     * @param code
     * @param value
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(String code, String value) {
        try {
            webConfService.updateConf(code, value);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }


}
