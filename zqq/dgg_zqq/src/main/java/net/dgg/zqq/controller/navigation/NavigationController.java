package net.dgg.zqq.controller.navigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.services.TreeBookService;
import net.dgg.zqq.utils.MapUtils;
import net.dgg.zqq.utils.StringUtils;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <NavigationController>
 * @despriction 导航接口
 * @create 2018/10/08 10:29
 **/
@Controller
@RequestMapping("/navigation")
@Api(description = "网站导航")
public class NavigationController extends BaseController {
    @Autowired
    private TreeBookService treeBookService;

    @RequestMapping(value = "/getDataByParentCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通过父级code获取网站导航数据", notes = "获取网站导航JON树形数据", httpMethod = "POST")
    public RestResponse getData(
            @ApiParam(name = "code", value = "字典的配置父级code") @RequestParam String code,
            @ApiParam(name = "type", value = "1：根据分类编码查询下级第一级分类  2：根据分类编码查询所有下级分类 ") @RequestParam Integer type,
            @ApiParam(name = "dataType", value = " 1或null：默认值，获取所有类别数据  2：只获取业务数据，不包含查询页面") @RequestParam(required = false) Integer dataType) {
        try {
            dataType = dataType == null ? 1 : dataType;
            List<TreeBook> resultList = treeBookService.getTreeBookListByCode(code, 1, type, null);
            if (2 == dataType.intValue()) {
                List<TreeBook> removeList = new ArrayList<>(resultList.size());
                for (TreeBook treeBook : resultList) {
                    if (!StringUtils.isEmpty(treeBook.getExt3()) && "0".equals(treeBook.getExt3().trim())) {
                        removeList.add(treeBook);
                    }
                }
                resultList.removeAll(removeList);
            }
            return this.getSuccessResponse(resultList);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }

    @RequestMapping(value = "/getTreeData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取网站导航数据", notes = "获取网站导航JON树形数据", httpMethod = "GET")
    public RestResponse getData() {
        try {
            String topCode = "navigation_data";
            List<TreeBook> resultList = treeBookService.getTreeBookListByCode(topCode, 1, 3, null);
            Map<String, TreeBook> codeMap = new HashMap<>(resultList.size());
            Map<String, List<TreeBook>> pCodeMap = new HashMap<>();
            for (TreeBook treeBook : resultList) {
                codeMap.put(treeBook.getCode(), treeBook);
                List<TreeBook> treeBooks = pCodeMap.containsKey(treeBook.getPcode()) ? pCodeMap.get(treeBook.getPcode()) : new ArrayList<>();
                treeBooks.add(treeBook);
                treeBooks.sort((o1, o2) -> {
                    Integer s1 = o1 == null ? null : o1.getSort(), s2 = o2 == null ? null : o2.getSort();
                    if (s1 == null && s2 == null) {
                        return 0;
                    }
                    if (s1 != null && s2 == null) {
                        return -1;
                    }
                    if (s1 == null && s2 != null) {
                        return 1;
                    }
                    return s1.compareTo(s2);
                });
                pCodeMap.put(treeBook.getPcode(), treeBooks);
            }
            Map treeMap = initTreeMap(topCode, codeMap, pCodeMap);
            return this.getSuccessResponse(treeMap);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }


    @RequestMapping(value = "/getAllTypeLevel3Data", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有网站导航3级服务分类", notes = "获取所有网站导航3级分类", httpMethod = "GET")
    public RestResponse getAllTypeLevel3Data(@ApiParam(name = "code", value = "服务所属一级级类别(商标：navigation_trademark 专利：navigation_patent 版权：navigation_copyright)"
            , required = true) @RequestParam(required = true) String code) {
        try {
            String topCode = code; // "navigation_data";
            List<TreeBook> resultList = treeBookService.getTreeBookListByCode(topCode, 1, 2, null);
            List<TreeBook> reList = new ArrayList<>(resultList.size());
            List<String> typeLevel2codeList = new ArrayList<>();
            for (TreeBook treeBook : resultList) {
                if (topCode.equals(treeBook.getCode())) {
                    continue;
                }
                if (topCode.equals(treeBook.getPcode())) {
                    typeLevel2codeList.add(treeBook.getCode());
                    continue;
                }
                reList.add(treeBook);
            }
            return this.getSuccessResponse(reList);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统出现异常！");
        }
    }


    private Map initTreeMap(String code, Map<String, TreeBook> codeMap, Map<String, List<TreeBook>> pCodeMap) {
        TreeBook treeBook = codeMap.get(code);
        Map treeBookMap = this.convertMap(treeBook);
        String sonKey = "sonArr";
        if (!pCodeMap.containsKey(treeBook.getCode())) {
            return treeBookMap;
        }
        this.initSonTreeMap(treeBookMap, treeBook.getCode(), sonKey, pCodeMap);
        return treeBookMap;
    }

    //递归 初始化 树形 数据
    private void initSonTreeMap(Map pMap, String pCode, String sonKey, Map<String, List<TreeBook>> pCodeMap) {
        List<Map> sonMapArr = new ArrayList<>();
        for (TreeBook treeBook : pCodeMap.get(pCode)) {
            Map treeBookMap = this.convertMap(treeBook);
            if (pCodeMap.containsKey(treeBook.getCode())) {
                this.initSonTreeMap(treeBookMap, treeBook.getCode(), sonKey, pCodeMap);
            }
            sonMapArr.add(treeBookMap);
        }
        pMap.put(sonKey, sonMapArr);
    }

    private Map convertMap(TreeBook treeBook) {
        Map treeBookMap = MapUtils.convertBean(treeBook);
        Assert.notNull(treeBookMap, "转化Map出现异常！");
        return treeBookMap;
    }

}
