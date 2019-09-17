package net.dgg.zqq.controller.forbidIpRecord;

import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.constant.ForbidTypeConstant;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.services.ForbidIpRecordService;
import net.fblock.web.common.BaseController;
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
 * @author 刘阳
 * @ClassName <ForbidIpRecordController>
 * @despriction
 * @create 2018/10/21 14:57
 **/
@Controller
@RequestMapping("/forbidRecord")
public class ForbidIpRecordController extends BaseController {
    @Autowired
    private ForbidIpRecordService forbidIpRecordService;
    @Autowired
    private TranslateUtil translateUtil;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("IP", ForbidTypeConstant.IP);
        model.addAttribute("USER", ForbidTypeConstant.USER);
        return "forbid/forbidRecord";
    }

    //查询操作
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse queryPage(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());

        String[] transColumn = {"typeName"};
        List<Map> list = this.forbidIpRecordService.pageQuery(params);
        for (Map map : list) {
            map.put("typeName", map.get("type"));
        }

        translateUtil.translateList(transColumn, list);

        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    //查询操作
    @RequestMapping(value = "/cancelForbid", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelForbid(Long id) {
        try {


            forbidIpRecordService.cancelForbid(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }



}
