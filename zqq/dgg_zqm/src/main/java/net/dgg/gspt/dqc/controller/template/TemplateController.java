package net.dgg.gspt.dqc.controller.template;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.controller.treebook.TranslateUtil;
import net.dgg.gspt.dqc.entity.template.Template;
import net.dgg.gspt.dqc.services.fast_dfs.FastDfsService;
import net.dgg.gspt.dqc.services.fileUpload.FileUploadService;
import net.dgg.gspt.dqc.services.template.TemplateService;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;

import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/template")
@Api(description = "模板-后台管理")
public class TemplateController  extends BaseController{

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TranslateUtil translateUtil;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FastDfsService fastDfsService;

    @ApiOperation(value = "页面跳转-列表页面", notes = "页面跳转-列表页面", httpMethod = "GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "template/templateList";
    }

    @ApiOperation(value = "页面跳转-添加页面", notes = "页面跳转-添加页面", httpMethod = "GET")
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "template/addTemplate";
    }

    @ApiOperation(value = "页面跳转-修改页面", notes = "页面跳转-修改页面", httpMethod = "GET")
    @RequestMapping(value = "/updatePage", method = RequestMethod.GET)
    public String updatePage(@ApiParam(name = "id", value = "问题ID", required = true) @RequestParam Long id, Model model) {
        String path = "template/updateTemplate";
        if (id == null) {
            return path;
        }
        Template template = this.templateService.findById(id);
        model.addAttribute("template", template);
        return path;
    }


    //查询操作
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "查询模板", notes = "查询模板", httpMethod = "POST")
    @ResponseBody
    public DataTableResponse queryPage(@ApiParam(name = "params", value = "查询条件map", required = true) @RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.templateService.pageQuery(params);

        for (Map map : list) {
            map.put("fileUrl",this.fileUploadService.getHost().concat((String) map.get("fileUrl")));
        }

        translateUtil.translateList(new String[]{"status", "templateType"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    //添加操作
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "添加模板", notes = "添加模板", httpMethod = "POST")
    @ResponseBody
    public Object save(@ApiParam(name = "templateType", value = "模板类型", required = true) @RequestParam("templateType") String templateType,
                       @ApiParam(name = "templateName", value = "模板名称", required = true) @RequestParam("templateName") String templateName,
                       @ApiParam(name = "status", value = "状态", required = true) @RequestParam("status") String status,
                       @ApiParam(name = "file", value = "文件", required = true) @RequestParam("file") MultipartFile file) {

        try {
            Assert.isTrue(!file.isEmpty(), "不能上传空文件！");
            String url = fastDfsService.upload(file.getBytes(), file.getOriginalFilename(), getExtName(file.getOriginalFilename()));
            this.templateService.save(templateType,templateName,status,url,file.getOriginalFilename());
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }


    }

    //修改操作
    @ApiOperation(value = "修改模板基础信息", notes = "修改模板基础信息", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@ApiParam(name = "data", value = "问题JSON串", required = true) @RequestParam String data) {
        try {
            Assert.hasText(data, "data不能为空！");
            Template template = new Gson().fromJson(data, new TypeToken<Template>() {
            }.getType());
            Assert.isTrue(template != null, "data不能为空");
            Assert.isTrue(template.getId() != null, "模板ID不能为空");
            this.templateService.updateTemplate(template);
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
    @ApiOperation(value = "删除模板", notes = "删除模板", httpMethod = "POST")
    @ResponseBody
    public Object delete(@ApiParam(name = "id", value = "模板ID", required = true) @RequestParam Long id) {
        try {
            this.templateService.delete(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    // 获取文件扩展名
    private String getExtName(String name) {
        String dot = ".";
        if (StringUtils.isEmpty(name) || name.indexOf(dot) < 0) {
            return null;
        }
        return name.substring(name.lastIndexOf(dot) + 1, name.length());
    }


}
