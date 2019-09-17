package net.dgg.zqq.controller.question;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.entity.question.Question;
import net.dgg.zqq.services.question.QuestionService;
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

@Controller
@RequestMapping("/question")
@Api(description = "问题-后台管理")
public class QuestionAfterController extends BaseController{

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TranslateUtil translateUtil;

    @ApiOperation(value = "页面跳转-列表页面", notes = "页面跳转-列表页面", httpMethod = "GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "question/questionList";
    }

    @ApiOperation(value = "页面跳转-添加页面", notes = "页面跳转-添加页面", httpMethod = "GET")
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "question/addQuestion";
    }

    @ApiOperation(value = "页面跳转-修改页面", notes = "页面跳转-修改页面", httpMethod = "GET")
    @RequestMapping(value = "/updatePage", method = RequestMethod.GET)
    public String updatePage(@ApiParam(name = "id", value = "问题ID", required = true) @RequestParam Long id, Model model) {
        String path = "question/updateQuestion";
        if (id == null) {
            return path;
        }
        Question question = this.questionService.findById(id);
        model.addAttribute("question", question);
        return path;
    }


    //查询操作
    @ApiOperation(value = "查询问题", notes = "查询问题", httpMethod = "POST")
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse queryPage(@ApiParam(name = "params", value = "查询条件map", required = true) @RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.questionService.pageQuery(params);
        translateUtil.translateList(new String[]{"status", "typeLevel1Code","typeLevel2Code","typeLevel3Code"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }


    //保存操作
    @ApiOperation(value = "保存问题", notes = "保存问题", httpMethod = "POST")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@ApiParam(name = "data", value = "问题JSON串", required = true) @RequestParam String data) {
        System.out.println(data);
        try {
            Assert.hasText(data, "data不能为空！");
            Question question = new Gson().fromJson(data, new TypeToken<Question>() {
            }.getType());
            Assert.isTrue(question!=null, "data不能为空");
            //System.out.println(question);
            questionService.saveQuestion(question);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }


    }

    //修改操作
    @ApiOperation(value = "修改问题", notes = "修改问题", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@ApiParam(name = "data", value = "问题JSON串", required = true) @RequestParam String data) {
        try {
            Assert.hasText(data, "data不能为空！");
            Question question = new Gson().fromJson(data, new TypeToken<Question>() {
            }.getType());
            Assert.isTrue(question!=null, "data不能为空");
            Assert.isTrue(question.getId()!=null, "问题ID不能为空");
            this.questionService.updateQuestion(question);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    //删除操作
    @ApiOperation(value = "删除问题", notes = "删除问题", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@ApiParam(name = "id", value = "问题ID", required = true) @RequestParam Long id) {
        try {
            this.questionService.delete(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


}
