package net.dgg.zqq.controller.user;

import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.services.UserExtService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/10/11 15:59
 */
@Controller
@RequestMapping(value = "/userManager")
public class UserManagerController extends BaseController{

    @Autowired
    UserExtService userExtService;

    @Autowired
    TranslateUtil translateUtil;


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "userManager/index";
    }


    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse queryPage(@RequestParam Map params, HttpServletRequest request) {
        //获取页面参数请求
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.userExtService.pageQuery(params);
        translateUtil.translateList(new String[]{ "status"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }



    //更改用户状态
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Object updateStatus(String userId) {
        try {
            this.userExtService.updateStatus(userId);
            return getSuccessResponse("success");
        }catch (IllegalArgumentException e){
            return this.getFailResponse(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return this.getFailResponse("系统错误");
        }
    }



}

