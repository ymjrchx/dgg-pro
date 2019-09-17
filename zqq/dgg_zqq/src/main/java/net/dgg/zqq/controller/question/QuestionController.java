package net.dgg.zqq.controller.question;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.services.question.QuestionService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/*
 *  问题模块控制层
 */

@Controller
@RequestMapping("/question")
@Api(description = "问题前台展示")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;


    /*
     * typeLevel3Code：服务三级分类编码
     * pageSize：每页显示条数
     * pageNum：当前页码
     *
     */
    @RequestMapping(value = "/pageQueryByTypeLevel3Code", method = RequestMethod.GET)
    @ApiOperation(value = "问答分页查询接口(服务三级分类编码)", notes = "查询服务下的问答列表",httpMethod = "GET")
    @ResponseBody
    public RestResponse pageQueryByTypeLevel3Code(
            @ApiParam(name = "typeLevel1Code", value = "服务一级分类编码") @RequestParam(required = false) String typeLevel1Code,
            @ApiParam(name = "typeLevel3Code", value = "服务三级分类编码") @RequestParam(required = false) String typeLevel3Code,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页码", required = true)@RequestParam Integer pageNum){

        try{
            List<Map<String, Object>> data = questionService.pageQueryByTypeLevel3Code(typeLevel1Code, typeLevel3Code, pageSize, pageNum);
            return this.getSuccessResponse(data);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/pageQueryByTypeLevel1Code", method = RequestMethod.GET)
    @ApiOperation(value = "问答分页查询接口(服务一级分类编码)", notes = "查询服务下的问答列表",httpMethod = "GET")
    @ResponseBody
    public RestResponse pageQueryByTypeLevel1Code(
            @ApiParam(name = "typeLevel1Code", value = "服务一级分类编码",required = true) @RequestParam String typeLevel1Code,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页码", required = true)@RequestParam Integer pageNum){

        try{
            Map<String, Object> data = questionService.pageQueryByTypeLevel1Code(typeLevel1Code, pageSize, pageNum);
            return this.getSuccessResponse(data);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /*
     *  根据关键字查询服务下问题以及答案（分页）
     *  keyword:关键字 不输入的话查询服务分类下的问题
     *  typeLevel3Code：服务三级分类编码
     *  pageSize：每页显示条数
     *  pageNum：当前页码
     *
     */
    @RequestMapping(value = "/pageQueryByKeyWord", method = RequestMethod.GET)
    @ApiOperation(value = "用关键字进行问题搜索", notes = "根据keyword查询对应的问答列表",httpMethod = "GET")
    @ResponseBody
    public RestResponse pageQueryByKeyWordAndTypeLevel3Code(
            @ApiParam(name = "keyword", value = "查询问答的关键字", required = true) @RequestParam String keyword,
            @ApiParam(name = "typeLevel1Code", value = "服务一级分类编码", required = true) @RequestParam String typeLevel1Code,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页码", required = true)@RequestParam Integer pageNum){

        try{
            Map<String, Object> data = questionService.pageQueryByKeyWord(keyword, typeLevel1Code, pageSize, pageNum);
            return this.getSuccessResponse(data);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


   /* @RequestMapping(value = "/pageQueryByKeyWord", method = RequestMethod.GET)
    @ApiOperation(value = "用关键字进行问题搜索", notes = "根据keyword查询对应的问答列表",httpMethod = "GET")
    @ResponseBody
    public RestResponse pageQueryByKeyWordAndTypeLevel1Code(
            @ApiParam(name = "keyword", value = "查询问答的关键字", required = true) @RequestParam String keyword,
            @ApiParam(name = "typeLevel1Code", value = "服务一级分类编码", required = true) @RequestParam String typeLevel1Code,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页码", required = true)@RequestParam Integer pageNum){

        try{
            List<Map<String, Object>> data = questionService.pageQueryByKeyWord(keyword,typeLevel1Code,typeLevel3Code,pageSize,pageNum);
            return this.getSuccessResponse(data);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }*/


    /*
     *  根据问题ID查询问答
     */
    @RequestMapping(value = "/findByQuestionId", method = RequestMethod.GET)
    @ApiOperation(value = "详情信息接口", notes = "根据问题ID查询对应的问答详情",httpMethod = "GET")
    @ResponseBody
    public RestResponse findByQuestionId(@ApiParam(name = "id", value = "问题ID", required = true) @RequestParam Long id){
        try{
            Map<String,Object> map =  questionService.findQuestionAndAnswerById(id);
            return this.getSuccessResponse(map);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


}
