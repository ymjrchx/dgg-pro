package net.dgg.bigdata.sjjs.web.service.search;

import com.alibaba.fastjson.JSON;
import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.TreeBook;
import net.dgg.bigdata.sjjs.web.condition.service.TreeBookService;
import net.dgg.bigdata.sjjs.web.service.impl.search.EnterpriseSearchServiceImpl;
import net.dgg.bigdata.sjjs.web.entity.dto.FatherChildDto;
import net.dgg.bigdata.sjjs.web.entity.dto.search.enterprise.EnterpriseParam;
import net.dgg.core.es.services.EsService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: EnterpriseSearchService
 * @Description: 企业信息搜索服务
 * @Author: jiangsh
 * @Date: 2018/12/11 11:14
 */
@Service
public class EnterpriseSearchService {

    @Resource
    private EsService esService;
    @Resource
    private EnterpriseSearchServiceImpl essi;

    @Resource
    private RedisTemplate redisTemplate;

    public List<Map> getPart(String index, String type, String keyWord, int count, String companyRegTime) {
        if (count <= 0) count = 5;
        String[] include = new String[2];
        include[0] = BConst.COMPANY_NAME;
        include[1] = BConst.COMPANY_ID;
        return esService.getPart(index, type, keyWord, count, include, putAllCondition(keyWord, null), companyRegTime);
    }


    public List<Map> search(String index, String type, int startCount, int endCount, EnterpriseParam ep, boolean isphrase) {
        Map sort = new HashMap();
//        sort.put(BConst.REG_MONEY, SortOrder.DESC);
        final String[] exclude = {""};
        final SearchRequestBuilder searchRequestBuilder = essi.searchRequestBuilder(index, type, condition(ep),
                sort, startCount, endCount, mapConditions(ep), isphrase, include(), exclude, esService.getClient(), BConst.F_TRUE);
        return esService.getDataMapList(index, type, startCount, endCount, searchRequestBuilder);
    }

    public long totalCount(String index, String type, int startCount, int endCount, EnterpriseParam ep, boolean isphrase) {
        final String[] exclude = {""};
        return essi.searchRequestBuilder(index, type, condition(ep),
                null, startCount, endCount, mapConditions(ep), isphrase, include(), exclude, esService.getClient(), BConst.F_FALSE).get().getHits().getTotalHits();
    }

    /**
     * 获取意向关键字
     */
    public String keyWord(String index, String type, String accountName) {
        if (StringUtils.isEmpty(accountName)) return null; //首次使用系统时
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchQuery(BConst.USERID + ".keyword", accountName));
        qb.must(QueryBuilders.existsQuery(BConst.CLUSE_RECORD_COMNAME));
        List<Map> list = esService.getMsgByKeyWord(index, type, 1, qb);
        if (list.size() > 0)
            return list.get(0).get(BConst.CLUSE_RECORD_COMNAME).toString();
        return null;
    }

    /**
     * 组装搜索框key 并集
     */
    protected static Map<String, String> condition(EnterpriseParam ep) {
        return putAllCondition(ep.getKeyWord(), ep.getQueryScope());
    }

    /**
     * 拦截组装请求参数 交集
     */
    private static Map<String, String> mapConditions(EnterpriseParam sqp) {
        Map<String, String> map = new HashMap<>();

        if (StringUtils.isNotEmpty(sqp.getStatus())) //企业状态
            map.put(BConst.BUSINESS_STATUS, sqp.getStatus());

        if (StringUtils.isNotEmpty(sqp.getRegCapital())) //注册资本
            map.put(BConst.REG_MONEY, sqp.getRegCapital());

        if (StringUtils.isNotEmpty(sqp.getRegTime())) //注册时长
            map.put(BConst.REG_TIME, sqp.getRegTime());

        if (StringUtils.isNotEmpty(sqp.getProvince())) //省份
            map.put(BConst.PROVINCE, sqp.getProvince());

        if (StringUtils.isNotEmpty(sqp.getIndustry())) //行业
            map.put(BConst.INDUSTRY, sqp.getIndustry());

        if (StringUtils.isNotEmpty(sqp.getPhone())) //联系电话
            map.put(BConst.TEL, sqp.getPhone());

        if (StringUtils.isNotEmpty(sqp.getEmail())) //邮箱
            map.put(BConst.EMAIL, sqp.getEmail());

        return map;
    }

    /**
     * 列表展示列
     */
    private static String[] include() {
        final String[] include = {BConst.COMPANY_NAME, BConst.COMPANY_ID, BConst.LEGEN_PERSON, BConst.ADDRESS, BConst.REG_MONEY, BConst.REG_TIME, BConst.BUSINESS_SCOPE, BConst.BUSINESS_STATUS};
        return include;
    }

    private static Map<String, String> putAllCondition(String keyWord, String scope) {
        Map<String, String> condition = new HashMap<>();
        if (StringUtils.isEmpty(keyWord)) return condition;
        if (StringUtils.isNotEmpty(scope)) {
            if (scope.contains("name")) {
                condition.put(BConst.COMPANY_NAME, keyWord);
            }
            if (scope.contains("headPerson")) {
                condition.put(BConst.LEGEN_PERSON, keyWord);
                condition.put(BConst.HINAME, keyWord);
            }
            if (scope.contains("major")) {
                condition.put(BConst.MAJOR_NAME, keyWord);
            }
            if (scope.contains("brand")) {
                condition.put(BConst.TRADEMARK, keyWord);
            }
            if (scope.contains("tel")) {
                condition.put(BConst.TEL, keyWord);
            }
        } else {
            condition.put(BConst.COMPANY_NAME, keyWord);
            condition.put(BConst.LEGEN_PERSON, keyWord);
            condition.put(BConst.HINAME, keyWord);
            condition.put(BConst.MAJOR_NAME, keyWord);
            condition.put(BConst.TEL, keyWord);
        }
        return condition;
    }

    public Map<String, Object> comSearchTreeBook(TreeBookService treeBookService) {
        Map<String, Object> map = new HashMap<>();
        map.put("comStatus", treeBookService.getTreeBooksByPid(Long.valueOf(BConst.COMSTATUSPID))); //企业状态
        map.put("comRegMoney", treeBookService.getTreeBooksByPid(Long.valueOf(BConst.COMREGMONEY))); //注册资金
        map.put("comScope", treeBookService.getTreeBooksByPid(Long.valueOf(BConst.COMSCOPE))); //企业范围
        map.put("comRegTime", treeBookService.getTreeBooksByPid(Long.valueOf(BConst.COMREGTIME))); //注册时长

        List<TreeBook> industrys = treeBookService.getNameAndCodeByCode(ConditionConstant.INDUSTRY);
        List<FatherChildDto> list = changeFatherChildDto(treeBookService, industrys);

        List<TreeBook> provinces = treeBookService.getNameAndCodeByCode(ConditionConstant.CHINA_PROVINCE);
        List<FatherChildDto> proList = changeFatherChildDto(treeBookService, provinces);

        map.put("industrys", list);//行业
        map.put("provinces", proList);//省事
        return map;
    }

    private List<FatherChildDto> changeFatherChildDto(TreeBookService treeBookService,List<TreeBook> treeBooks){
        List<FatherChildDto> list = new ArrayList<>();
        FatherChildDto fatherChildDto = null;
        for (TreeBook treeBook : treeBooks) {
            fatherChildDto = new FatherChildDto();
            List cTreeBooks = treeBookService.getNameAndCodeByCode(treeBook.getCode());
            fatherChildDto.setFather(treeBook);
            fatherChildDto.setChild(cTreeBooks);
            list.add(fatherChildDto);
        }
        return list;
    }


    /**
     * 存redis缓存
     * @param treeBookService
     * @return
     */
    public Map<String, Object> comSearchTreeBookToCache(TreeBookService treeBookService) {
        Map<String, Object> stringObjectMap = null;
        if (redisTemplate.hasKey(ConditionConstant.COM_SEARCH_TREEBOOK)) {
            String string = (String) redisTemplate.opsForValue().get(ConditionConstant.COM_SEARCH_TREEBOOK);
            stringObjectMap = JSON.parseObject(string, HashMap.class);
        } else {
            stringObjectMap = comSearchTreeBook(treeBookService);
            redisTemplate.opsForValue().set(ConditionConstant.COM_SEARCH_TREEBOOK, JSON.toJSONString(stringObjectMap), 60 * 60 * 24, TimeUnit.SECONDS);
        }
        return stringObjectMap;

    }

}
