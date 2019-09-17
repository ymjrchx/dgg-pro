package net.dgg.gspt.dqc.controller.searchWord;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.services.searchWord.SearchWordService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/27 11:18
 */

@Controller
@RequestMapping("/searchWord")
public class SearchWordController extends BaseController{

    @Autowired
    private SearchWordService searchWordService;

    @RequestMapping(value = "/pageQuery", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询搜索词条", notes = "分页查询搜索词条",httpMethod = "GET")
    @ResponseBody
    public RestResponse pageQueryByTypeLevel3Code(
            @ApiParam(name = "pageSize", value = "每页查询条数") @RequestParam(required = true) Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页") @RequestParam(required = true) Integer pageNum){
        try{
            return this.getSuccessResponse(searchWordService.pageQuery(pageSize,pageNum));
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }




}
