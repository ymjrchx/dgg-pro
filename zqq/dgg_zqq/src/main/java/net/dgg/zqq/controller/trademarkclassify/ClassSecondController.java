package net.dgg.zqq.controller.trademarkclassify;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.entity.order.ClassSecond;
import net.dgg.zqq.entity.order.ClassThird;
import net.dgg.zqq.services.trademarkclassify.ClassSecondService;
import net.dgg.zqq.services.trademarkclassify.ClassThirdService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author huanggai
 * @creatTime 11:40 2018/9/30
 */
@RestController
@RequestMapping(value = "/classsecond")
@Api(description = "二级分类")
public class ClassSecondController extends BaseController
{
    @Autowired
    private ClassSecondService classSecondService;
    @Autowired
    private ClassThirdService classThirdService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ApiOperation(value = "商标二级分类查询", notes = "商标二级分类查询",httpMethod = "GET")
    public Object query(@ApiParam(name = "id", value = "商标二级分类id", required = true)@RequestParam String id) {
        try {
            Assert.notNull(id,"查询数据为空!");
            List<Map> result = new ArrayList<>();
            Map resultMap = new LinkedHashMap();

            Map map =  new HashMap();
            map.put("firstId",id);
            List<ClassSecond> query = classSecondService.query(map);
            query.sort(new Comparator<ClassSecond>() {
                @Override
                public int compare(ClassSecond o1, ClassSecond o2) {
                    return Integer.parseInt(o1.getNumber()) - Integer.parseInt(o2.getNumber());
                }
            });
            for (ClassSecond classSecond:query) {
                Map thirdMap =  new HashMap();
                thirdMap.put("secondNumber",classSecond.getNumber());
                List<ClassThird> classThirds = classThirdService.query(thirdMap);
                String name = classSecond.getNumber()+classSecond.getName();
                resultMap.put(name,classThirds);
            }
            result.add(resultMap);
            return this.getSuccessResponse(result);
        }catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }
}
