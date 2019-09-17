package net.dgg.gspt.dqc.controller.OperationManager;

import net.dgg.gspt.dqc.controller.treebook.TranslateUtil;
import net.dgg.gspt.dqc.services.OperationRecordService;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OperationManagerController
 * @Description: TODO
 * @Author: huangL
 * @Date: 2019/1/3 15:48
 */

@Controller
@RequestMapping("/operation")
public class OperationManagerController {

    @Autowired
    OperationRecordService operationRecordService;
    @Autowired
    private TranslateUtil translateUtil;

    /**
     * 操作管理主页面
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String operationManager() {
        return "operationManager/index";
    }

    @RequestMapping(value = "/showRecord", method = RequestMethod.POST)
    @ResponseBody
    public Object pageQuery(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.operationRecordService.pageQuery(params);
        translateUtil.translateList(new String[]{"operationCode"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    /**
     * 用户操作管理表单页面
     */
    @RequestMapping(value = "/userRecord", method = RequestMethod.GET)
    public String userOperation(String userId, String username, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);
        return "operationManager/single";
    }




}
