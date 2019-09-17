package net.dgg.zqq.controller.search;

import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.framework.interceptor.ConstMethod;
import net.dgg.zqq.utils.es.services.EsService;
import net.fblock.web.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static net.dgg.zqq.controller.search.CompanyItemController.indexs;
import static net.dgg.zqq.controller.search.CompanyItemController.types;

/**
 * @ClassName: BrandManagerController
 * @Description: 商标管理-搜索接口
 * @Author: jiangsh
 * @Date: 2018/10/9 9:30
 */

@RestController
@RequestMapping("/brandManager")
public class BrandManagerController extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(BrandManagerController.class);

    @Resource
    private EsService esService;

    /**
     * 获取前五条数据
     *
     * @param keyWord 关键字
     * @return
     */
    @RequestMapping(value = "/searchFive", method = RequestMethod.GET)
    @ResponseBody
    public List<String> searchFive(@RequestParam String keyWord
    ) {
        final String[] include = new String[]{PTConst.BRAND_APPLICATCN};
        final String type = PTConst.MIX_BRAND;
        return esService.getPart(indexs(type), types(type), PTConst.BRAND_APPLICATCN, keyWord, 10000, include, null);
    }


    /**
     * 商标管理-导出商标数据
     *
     * @param startCount 开始条数
     * @param endCount   结束条数
     * @param applicantCn applicantCn 数组
     * @return List<Map>数据集
     */
    @RequestMapping(value = "/exportBrandData", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getListMapBrandManager(@RequestParam int startCount
            , @RequestParam int endCount
            , @RequestParam String... applicantCn
    ) {
        final String type = PTConst.MIX_BRAND;
        return esService.getListMapBrandManager(indexs(type), types(type), startCount, endCount, PTConst.BRAND_APPLICATCN, applicantCn);
    }

}
