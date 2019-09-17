package net.dgg.gspt.dqc.controller.companyNews;

import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.params.NewsParam;
import net.dgg.gspt.dqc.utils.esOld.EsConst;
import net.dgg.gspt.dqc.utils.esOld.services.EsServiceOld;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CompanyNewsController
 * @Description: 新闻接口
 * @Author: jiangsh
 * @Date: 2018/7/21 10:38
 */

@RestController
@RequestMapping("/companyNews")
public class CompanyNewsController extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(CompanyNewsController.class);

    @Autowired
    private EsServiceOld esService;

    /**
     * 获取新闻列表
     */
    @RequestMapping(value = "/news", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse news(@RequestBody NewsParam np) {
        Map<String, Object> dataMap = pages(np.getPageIndex(), np.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] include = {"title", "articleSub", "part", "newsType", "titleImg"};
        final String[] exclude = {"", ""};
        final String timeKey = "pubdate";
        dataMap.put("listData", esService.getListMap(EsConst.INDEX_NEW, EsConst.TYPE_COMPANY_NEWS, PTConstOld.NEWS_TYPE, np.getType(), startCount, endCount, include, exclude, timeKey));
        dataMap.put("totalCount", esService.getTotalCount(EsConst.INDEX_NEW, EsConst.TYPE_COMPANY_NEWS, PTConstOld.NEWS_TYPE, np.getType(), timeKey));

        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConstOld.PROMPT);
    }


    /**
     * 新闻详情
     *
     * @param title 新闻标题（唯一值）
     */
    @RequestMapping(value = "/newDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<Map>> newDetail(@RequestParam String title) {
        try {
            title = URLDecoder.decode(title, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Map> dataList = esService.getMsgByKeyWordCommon("title", title, EsConst.INDEX_NEW, EsConst.TYPE_COMPANY_NEWS);
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataList);
        else return getFailResponse(PTConstOld.PROMPT);
    }

}
