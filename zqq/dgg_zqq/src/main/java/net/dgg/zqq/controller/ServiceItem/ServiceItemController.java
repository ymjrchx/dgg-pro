package net.dgg.zqq.controller.ServiceItem;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.constant.UploadFileType;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttrRelation;
import net.dgg.zqq.entity.serviceAndAttr.ServiceItem;
import net.dgg.zqq.services.ServiceItem.ServiceItemService;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.dgg.zqq.services.serviceAndAttr.ServiceAttrRelationService;
import net.dgg.zqq.services.serviceAndAttr.ServiceAttrService;
import net.dgg.zqq.utils.GsonUtils;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/serviceItem")
@Api(description = "服务项")
public class ServiceItemController extends BaseController {

    @Autowired
    private ServiceItemService serviceItemService;
    @Autowired
    private ServiceAttrRelationService serviceAttrRelationService;
    @Autowired
    private ServiceAttrService serviceAttrService;

    @Autowired
    private TranslateUtil translateUtil;
    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "serviceItem/serviceItemList";
    }

    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage(Model model) {
        model.addAttribute("ENABLE", StatusConstant.ENABLE);
        model.addAttribute("type", UploadFileType.SERVICE_ITEM);
        model.addAttribute("host", fileUploadService.getHost());
        return "serviceItem/addServiceItem";
    }

    @RequestMapping(value = "/updatePage", method = RequestMethod.GET)
    public String updatePage(@RequestParam Long id, Model model) {
        model.addAttribute("ENABLE", StatusConstant.ENABLE);
        String path = "serviceItem/addServiceItem";
        if (id == null) {
            return path;
        }
        ServiceItem serviceItem = this.serviceItemService.findById(id);
        model.addAttribute("serviceItem", serviceItem);
        List<ServiceAttrRelation> serviceAttrRelationList = this.serviceAttrRelationService.query(new HashMap() {{
            put("serviceItemId", id);
        }});
        model.addAttribute("type", UploadFileType.SERVICE_ITEM);
        model.addAttribute("host", fileUploadService.getHost());
        if (serviceAttrRelationList.isEmpty()) {
            model.addAttribute("serviceAttrArrJson", GsonUtils.toJson(Collections.emptyList()));
            return path;
        }
        List<Long> attrIdList = new ArrayList<>();
        for (ServiceAttrRelation serviceAttrRelation : serviceAttrRelationList) {
            attrIdList.add(serviceAttrRelation.getServiceAttrId());
        }
        List<Map> serviceAttrList = this.serviceAttrService.pageQuery(new HashMap() {{
            put("idArr", attrIdList);
        }});
        model.addAttribute("serviceAttrArrJson", GsonUtils.toJson(serviceAttrList));

        return path;
    }

    //查询操作
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse queryPage(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());

        String[] transColumn = {"typeLevel1CodeName", "typeLevel2CodeName", "typeLevel3CodeName", "status"};
        List<Map> list = this.serviceItemService.pageQuery(params);
        for (Map map : list) {
            map.put("typeLevel1CodeName", map.get("typeLevel1Code"));
            map.put("typeLevel3CodeName", map.get("typeLevel3Code"));
            map.put("typeLevel2CodeName", map.get("typeLevel2Code"));
        }
        translateUtil.translateList(transColumn, list);

        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    //保存操作
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestParam String data, @RequestParam String serviceAttrId) {
        try {
            Assert.hasText(data, "数据不能为空！");
            ServiceItem serviceItem = new Gson().fromJson(data, ServiceItem.class);
            serviceItemService.save(serviceItem, serviceAttrId);
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
    public Object update(@RequestParam String data, @RequestParam String serviceAttrId) {
        try {
            Assert.hasText(data, "数据不能为空！");
            /* Assert.hasText(serviceAttrId, "服务属性ID不能为空！");*/
            ServiceItem serviceItem = new Gson().fromJson(data, ServiceItem.class);
            serviceItemService.update(serviceItem, serviceAttrId);
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
            this.serviceItemService.delete(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/queryServiceItemList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过三级分类code查询服务项", notes = "通过三级分类code查询服务项", httpMethod = "GET")
    public Object queryServiceItemList(
            @ApiParam(name = "code", value = "通过三级分类code") @RequestParam String code,
            @ApiParam(name = "serviceItemNum", value = "选中的服务项编号，用于首页和二级页服务项跳转") @RequestParam String serviceItemNum) {
        try {
            List<Map> maps = this.serviceItemService.queryByTypeLevel3Code(code, serviceItemNum);
            return this.getSuccessResponse(maps);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /*
     *  根据服务编码查询服务价格
     *  参数number:服务编码
     */
    @RequestMapping(value = "/findServiceItemByNumber", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过服务编号查询服务项", notes = "通过服务编号（用逗号分隔）查询服务项", httpMethod = "GET")
    public RestResponse findServiceItemByNumber(
            @ApiParam(name = "numbers", value = "服务项编号") @RequestParam String numbers) {
        try {
            List<Map<String, Object>> list = serviceItemService.findServiceItemByNumber(numbers);
            return this.getSuccessResponse(list);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }

    /**
     * 小程序首页—返回服务项
     */
    @RequestMapping(value = "/findServiceItemByType", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过一级分类查询服务项", notes = "通过一级分类查询服务项", httpMethod = "GET")
    public Object findServiceItemByType(@ApiParam(name = " typeLevel1Code", value = "服务所属一级类别code( 商标:navigation_trademark;专利:navigation_patent;版权:navigation_copyright)")
                                        @RequestParam(value = "typeLevel1Code", required = false) String typeLevel1Code) {
        try {
            List<Map> list = this.serviceItemService.pageQuery(new HashMap() {{
                put("typeLevel1Code", typeLevel1Code);
                put("status", "status1");
                put("flag", 1);
            }});

            for (Map map : list) {
                map.put("fhost",fileUploadService.getHost());
            }
            
            return this.getSuccessResponse(list);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("信息获取失败");
        }
    }

}
