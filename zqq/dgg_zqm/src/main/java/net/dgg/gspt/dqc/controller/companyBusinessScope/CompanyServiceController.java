package net.dgg.gspt.dqc.controller.companyBusinessScope;

import com.google.gson.Gson;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.params.ResourceParam;
import net.dgg.gspt.dqc.services.OperationRecordService;
import net.dgg.gspt.dqc.services.UserService;
import net.dgg.gspt.dqc.services.companyBusinessScope.CompanyService;
import net.dgg.gspt.dqc.services.searchWord.SearchWordService;
import net.dgg.gspt.dqc.utils.IpAddressUtils;
import net.dgg.gspt.dqc.utils.esOld.EsConst;
import net.dgg.gspt.dqc.utils.esOld.services.EsServiceOld;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.dgg.gspt.dqc.constant.OperationRecordConstant.BUSINESS_SCOPE;

/**
 * @ClassName: CompanyServiceController
 * @Description: 服务接口
 * @Author: jiangsh
 * @Date: 2018/9/6 14:12
 */

@RestController
@RequestMapping("/api")
public class CompanyServiceController extends BaseController implements ConstMethod {

    Logger logger = LoggerFactory.getLogger(CompanyServiceController.class);
    private static Gson gson = new Gson();

    @Autowired
    private EsServiceOld esService;
    @Resource
    private CompanyService companyService;
    @Autowired
    private SearchWordService searchWordService;
    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private UserService userService;
    /*@Resource
    private ApiKeyAndUrlCheckService apiKeyAndUrlCheckService;
*/

    /**
     * 资源生成器（生成经营范围）
     */
    @RequestMapping(value = "/resourcePro", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse resourcePro(HttpServletRequest request, @RequestBody ResourceParam rp) {
        String token = request.getHeader(PTConst.USER_TOKEN);

        Map<String, Object> dataMap = pages(rp.getPageIndex(), rp.getPageSize());
        final int startCount = Integer.valueOf(dataMap.get("startCount").toString());
        final int endCount = Integer.valueOf(dataMap.get("endCount").toString());

        final String[] exclude = {"", ""};
        dataMap.put("listData", esService.getListMapResPro(EsConst.INDEX, EsConst.TYPE_COMPANY,
                companyService.maps(rp.getCity(), rp.getIndustry()), startCount, endCount, companyService.includesPro(), exclude, null));
        dataMap.put("totalCount", esService.getTotalCountResPro(EsConst.INDEX, EsConst.TYPE_COMPANY, companyService.maps(rp.getCity(), rp.getIndustry())));

        List<Map> dataList = (List<Map>) dataMap.get("listData");
        this.searchWordService.search("生成经营范围", IpAddressUtils.getIP(request));
        if (dataList != null && dataList.size() > 0) {
            if (StringUtils.isEmpty(token) || !RedisUtils.exists(token)) {
                return this.getSuccessResponse(new HashMap<String, Object>() {{
                    put("totalCount", dataMap.get("totalCount"));
                    put("listData", Arrays.asList(dataList.get(0)));
                }});
            }
            this.operationRecordService.saveRecord(token, BUSINESS_SCOPE, rp.getCity() + "|" + rp.getIndustry());
            return getSuccessResponse(dataMap);
        } else {
            return getFailResponse(PTConstOld.PROMPT);
        }
    }

    /**
     * 资源生成器（生成经营范围），对外服务
     *
     * @param appKey    服务请求凭证appkey
     * @param city      城市
     * @param industry  行业类别
     * @param pageIndex 开始页
     * @param pageSize  每页多少条
     * @return
     */
    @RequestMapping(value = "/COM/resourcePro", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse resourceProGet(@RequestParam String appKey,
                                       @RequestParam String city,
                                       @RequestParam String industry,
                                       @RequestParam int pageIndex,
                                       @RequestParam int pageSize,
                                       HttpServletRequest request) {

        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
        /*try {
            apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }*/
        return restResponse(companyService.resourceProGet(appKey, url, ip, gson, pageIndex, pageSize, city, industry));
    }


    /**
     * 商标、专利 等等服务接口
     *
     * @param appKey    服务请求凭证appkey
     * @param keyWord   搜索关键字
     * @param type      类型（区别商标 brand、专利 patent、裁判文书 cpws, 税号 tax）
     * @param caseType  裁判文书类别
     * @param pageIndex 第几页
     * @param pageSize  每页多少条
     * @return 商标列表信息
     */
    @RequestMapping(value = "/CPWS/mix", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse mix(@RequestParam String appKey,
                            @RequestParam String keyWord,
                            @RequestParam String type,
                            @RequestParam(required = false) String caseType,
                            @RequestParam int pageIndex,
                            @RequestParam int pageSize,
                            HttpServletRequest request) {
        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
        /*try {
            apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }*/
        return restResponse(companyService.mix(appKey, url, ip, gson, pageIndex, pageSize, keyWord, type, caseType));
    }

    /**
     * 商标、专利 等等详情服务接口
     *
     * @param appKey  服务请求凭证appkey
     * @param typeKey 传值情况： 税号 companyId; 商标 esId;  专利 applicationNumber; 裁判文书 id;
     * @param val     具体值
     * @param type    类型（区别商标 brand、专利 patent、裁判文书 cpws、税号 tax）
     */
    @RequestMapping(value = "/CPWS/mixDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse mixDetail(@RequestParam String appKey,
                                  @RequestParam String typeKey,
                                  @RequestParam String val,
                                  @RequestParam String type,
                                  HttpServletRequest request) {

        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
       /* try {
            apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }*/
        return restResponseDetail(companyService.mixDetail(appKey, url, val, ip, gson, typeKey, type));
    }

    /**
     * 裁判文书 分类统计 服务接口
     *
     * @param appKey   服务请求凭证appkey
     * @param key      按key搜索 (裁判文书 ajsf)
     * @param keyWord  按关键字搜索
     * @param type     类型（区别商标 brand、专利 patent、裁判文书-服务接口 cpwsts）
     * @param caseType 裁判文书类型， 传值1-5
     */
    @RequestMapping(value = "/CPWS/aggCount", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse aggCount(@RequestParam String appKey,
                                 @RequestParam String key,
                                 @RequestParam String keyWord,
                                 @RequestParam String type,
                                 @RequestParam(required = false, defaultValue = "0") int caseType,
                                 HttpServletRequest request) {

        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
        try {
            // apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
            return getSuccessResponse(companyService.aggCount(appKey, url, ip, gson, type, key, keyWord, caseType));
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }
    }

    /**
     * 商标成功率测算
     *
     * @param appKey   服务请求凭证appkey
     * @param brand    商标名称
     * @param industry 行业类别
     */
    @RequestMapping(value = "/BRAND/measure", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse brandMeasure(@RequestParam String appKey,
                                     @RequestParam String brand,
                                     @RequestParam String industry,
                                     HttpServletRequest request) {

        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
        try {
            //apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
            return getSuccessResponse(companyService.brandMeasure(appKey, url, ip, gson, brand, industry));
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }
    }

    /**
     * 企业查询
     *
     * @param appKey     服务请求凭证appkey
     * @param keyWord    关键字
     * @param pageIndex  开始页
     * @param pageSize   每页多少条
     * @param econKind   企业类型
     * @param status     企业状态
     * @param registCapi 注册资金
     * @param startDate  成立日期
     * @param province   省份
     * @param industry   行业
     */
    @RequestMapping(value = "/COM/search", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse comSearch(@RequestParam String appKey,
                                  @RequestParam String keyWord,
                                  @RequestParam int pageIndex,
                                  @RequestParam int pageSize,
                                  @RequestParam(required = false) String econKind,
                                  @RequestParam(required = false) String status,
                                  @RequestParam(required = false) String registCapi,
                                  @RequestParam(required = false) String startDate,
                                  @RequestParam(required = false) String province,
                                  @RequestParam(required = false) String industry,
                                  HttpServletRequest request) {

        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
        try {
            //apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
            Map<String, String> map = companyService.comSearchMap(econKind, status, registCapi, startDate, province, industry); //组装查询条件
            return getSuccessResponse(companyService.comSearch(appKey, url, ip, gson, map, keyWord, pageIndex, pageSize));
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }
    }

    /**
     * 企业详情
     *
     * @param appKey    申请凭证
     * @param companyId 公司id
     */
    @RequestMapping(value = "/COM/comSearchDetail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse comSearchDetail(@RequestParam String appKey,
                                        @RequestParam String companyId,
                                        HttpServletRequest request) {

        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
        try {
            // apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
            return getSuccessResponse(companyService.comSearchDetail(appKey, url, ip, gson, companyId));
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }
    }

    /**
     * 商标健康检查
     *
     * @param appKey 服务请求凭证appkey
     * @param regNo  注册号
     * @param type   类别
     */
    @RequestMapping(value = "/BRAND/health", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse brandHealth(@RequestParam String appKey,
                                    @RequestParam String regNo,
                                    @RequestParam String type,
                                    HttpServletRequest request) {

        final String url = request.getRequestURI();
        final String ip = IpAddressUtils.getIP(request);
        try {
            // apiKeyAndUrlCheckService.checkAndLogin(appKey, url);
            return getSuccessResponse(companyService.brandHealth(appKey, url, ip, gson, regNo, type));
        } catch (Exception e) {
            return getFailResponse(e.getMessage());
        }
    }


    /**
     * 服务接口响应
     */
    private RestResponse restResponse(Map<String, Object> dataMap) {
        List<Map> dataList = (List<Map>) dataMap.get("listData");
        if (dataList != null && dataList.size() > 0)
            return getSuccessResponse(dataMap);
        else
            return getFailResponse(PTConstOld.PROMPT);
    }

    /**
     * 详情服务接口响应
     */
    private RestResponse restResponseDetail(List<Map> dataList) {
        try {
            return this.getSuccessResponse(dataList.get(0));
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

}
