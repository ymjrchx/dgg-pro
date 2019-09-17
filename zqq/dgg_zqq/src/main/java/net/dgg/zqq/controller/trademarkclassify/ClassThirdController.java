package net.dgg.zqq.controller.trademarkclassify;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.services.trademarkclassify.ClassThirdService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huanggai
 * @creatTime 11:43 2018/9/30
 */
@RestController
@RequestMapping(value = "/classthird",method = RequestMethod.POST)
@Api(description = "三级分类")
public class ClassThirdController extends BaseController
{
    @Autowired
    private ClassThirdService classThirdService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ApiOperation(value = "商标分类查询", notes = "商标分类查询",httpMethod = "GET")
    @ResponseBody
    public Object query(@ApiParam(name = "params", value = "查询条件", required = true)@RequestParam Map params) {
        try {
            Assert.notNull(params,"查询数据为空!");
            return this.getSuccessResponse(this.classThirdService.queryMap(params));
        }catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }
    @RequestMapping(value = "/secondidquery",method = RequestMethod.POST)
    @ApiOperation(value = "商标分类二级分类id查询", notes = "商标分类二级分类id查询",httpMethod = "POST")
    @ResponseBody
    public Object queryByFatherId(@ApiParam(name = "id", value = "商标二级分类id", required = true)Long id)
    {
        try {
            Assert.notNull(id,"查询数据为空!");
            Map conditionMap = new HashMap();
            conditionMap.put("secondId",id);
            return this.getSuccessResponse(this.classThirdService.query(conditionMap));
        }catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    /**
     * 通过关键字搜索并高亮显示关键字
     * @param key 关键字
     * @return 结果
     */
    @RequestMapping(value = "/keyquery",method = RequestMethod.GET)
    @ApiOperation(value = "商标分类关键字查询", notes = "商标分类关键字查询",httpMethod = "GET")
    @ResponseBody
    public Object queryByKey(@ApiParam(name = "key", value = "商标分类关键字查询", required = true)String key) {
        try {
            return this.getSuccessResponse(classThirdService.queryByKey(key));
        }catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

}
