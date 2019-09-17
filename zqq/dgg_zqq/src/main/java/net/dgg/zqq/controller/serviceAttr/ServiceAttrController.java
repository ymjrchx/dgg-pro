package net.dgg.zqq.controller.serviceAttr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttr;
import net.dgg.zqq.services.serviceAndAttr.ServiceAttrService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <ServiceAttrController>
 * @despriction 服务属性控制器
 * @create 2018/09/28 20:07
 **/
@Controller
@RequestMapping("/serviceAttr")
public class ServiceAttrController extends BaseController {

    @Autowired
    private ServiceAttrService serviceAttrService;
    @Autowired
    private TranslateUtil translateUtil;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "serviceAttr/serviceAttrList";
    }

    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "serviceAttr/addServiceAttr";
    }

    @RequestMapping(value = "/updatePage", method = RequestMethod.GET)
    public String updatePage(@RequestParam Long id, Model model) {
        String path = "serviceAttr/updateServiceAttr";
        if (id == null) {
            return path;
        }
        ServiceAttr serviceAttr = this.serviceAttrService.findById(id);
        model.addAttribute("serviceAttr", serviceAttr);
        return path;
    }

    //查询操作
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse queryPage(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.serviceAttrService.pageQuery(params);
        translateUtil.translateList(new String[]{"status"}, list);

        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }


    //查询操作
    @RequestMapping(value = "/queryCode", method = RequestMethod.POST)
    @ResponseBody
    public Object queryCode(@RequestParam String code) {
        try {
            return this.getSuccessResponse(this.serviceAttrService.queryCode(code));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    //保存操作
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestParam String data) {
        try {
            Assert.hasText(data, "data不能为空！");
            List<ServiceAttr> serviceAttrList = new Gson().fromJson(data, new TypeToken<List<ServiceAttr>>() {
            }.getType());
            Assert.isTrue(serviceAttrList.size() > 0, "data不能为空");
            this.serviceAttrService.saveList(serviceAttrList);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    //更新操作
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam String data) {
        try {
            Assert.hasText(data, "data不能为空！");
            List<ServiceAttr> serviceAttrList = new Gson().fromJson(data, new TypeToken<List<ServiceAttr>>() {
            }.getType());
            Assert.isTrue(serviceAttrList.size() > 0, "data不能为空");
            ServiceAttr serviceAttr = serviceAttrList.get(0);
            this.serviceAttrService.update(serviceAttr);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    //删除操作
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam Long id) {
        try {
            this.serviceAttrService.delete(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
