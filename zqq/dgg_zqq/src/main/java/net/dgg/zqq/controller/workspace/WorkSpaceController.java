package net.dgg.zqq.controller.workspace;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 刘阳
 * @ClassName <WorkSpaceController>
 * @despriction 工作台
 * @create 2018/07/11 11:59
 **/
@Controller
@RequestMapping("/workSpace")
public class WorkSpaceController {
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index() {
        return "redirect:/swagger-ui.html";
    }

}
