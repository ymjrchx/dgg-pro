package net.dgg.zqq.controller.question;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.entity.question.Answer;
import net.dgg.zqq.entity.question.Question;
import net.dgg.zqq.services.question.AnswerService;
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

@Controller
@RequestMapping("/answer")
@Api(description = "答案-后台管理")
public class AnswerAfterController extends BaseController{

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @ApiOperation(value = "页面跳转-关联答案", notes = "页面跳转-关联答案", httpMethod = "GET")
    @RequestMapping(value = "/openAnswerPage", method = RequestMethod.GET)
    public String openAnswerPage(@ApiParam(name = "id", value = "问题ID", required = true) @RequestParam Long id, Model model) {
        String path = "question/addAnswer";
        if (id == null) {
            return path;
        }
        Question question = this.questionService.findById(id);
        if(question!=null && (question.getBestAnswerId()!=null && question.getBestAnswerId()!="")){
            Answer answer = answerService.findById(question.getBestAnswerId());
            model.addAttribute("answer",answer);
        }
        model.addAttribute("question", question);

        return path;
    }

    //保存操作
    @ApiOperation(value = "保存答案", notes = "保存答案", httpMethod = "POST")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@ApiParam(name = "data", value = "答案JSON串", required = true) @RequestParam String data) {
        //System.out.println(data);
        try {
            Assert.hasText(data, "data不能为空！");
            Answer answer= new Gson().fromJson(data, new TypeToken<Answer>() {
            }.getType());
            Assert.isTrue(answer!=null, "data不能为空");
            answerService.save(answer);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }


    }

}
