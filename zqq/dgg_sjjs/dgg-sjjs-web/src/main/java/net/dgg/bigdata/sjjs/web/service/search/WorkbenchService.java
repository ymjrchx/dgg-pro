package net.dgg.bigdata.sjjs.web.service.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.constant.CluesStatus;
import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.entity.dto.search.workbench.WorkBenchParam;
import net.dgg.bigdata.sjjs.web.service.SysUserService;
import net.dgg.core.es.services.EsService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static net.dgg.bigdata.common.constant.BConst.T_DATESUFIX;
import static net.dgg.bigdata.common.constant.BConst.T_DATESUFIX_END;

/**
 * @ClassName: WorkbenchService
 * @Description: 工作台服务
 * @Author: jiangsh
 * @Date: 2018/12/17 15:33
 */
@Service
public class WorkbenchService implements ConstMethod {

    @Resource
    private EsService esService;
    @Resource
    private SysUserService sysUserService;


    /**
     * 线索状态
     */
    public Map<String, Long> filterAggregationCount(String index, String type,  WorkBenchParam wb, String key, boolean in) {
        return esService.aggreage(filterAggregation(values(), key, in), qb(wb, 1), index, type, BConst.AGGLIST);
    }

    /**
     * 线索领取数量
     */
    public long totalClues(String index, String type, WorkBenchParam wb) {
        return esService.totalCount(index, type, qb(wb, 1));
    }

    /**
     * 线索动态记录
     */
    public List<Map> searchCluesRecord(String index, String type, WorkBenchParam wb, int startCount, int endCount) {
        SearchRequestBuilder srb = esService.getClient().prepareSearch(index).setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        final String[] include = {"comName", "status", "toWhere", "reason", "chargePerson", "chargeDept", "statusUpdateTime"};
        final String[] exclude = {""};
        return esService.getDataMapList(index, type, startCount, endCount, searchRequestBuilder(srb, qbr(wb), include, exclude, startCount, endCount, BConst.F_TRUE).addSort(BConst.CLUSE_RECORD_UPDATE_TIME, SortOrder.DESC));
    }

    /**
     * 线索动态记录总数
     */
    public long searchCluesRecordCount(String index, String type, WorkBenchParam wb, int startCount, int endCount) {
        SearchRequestBuilder srb = esService.getClient().prepareSearch(index).setTypes(type).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        final String[] include = {""};
        final String[] exclude = {""};
        return searchRequestBuilder(srb, qbr(wb), include, exclude, startCount, endCount, BConst.F_FALSE).get().getHits().getTotalHits();
    }

    /**
     * 销售排行榜
     */
    public Map<String, Long> searchSaleRank(String index, String type, WorkBenchParam wb, String key, boolean in) {
        Map<String, Long> result = new LinkedHashMap<>();
        Map<String, Long> unsortMap = esService.aggreage(filterAggregation(sysUserService.getAllUserId(), key, in), qb(wb, 2), index, type, BConst.AGGLIST);
        combainMap(unsortMap, sysUserService.getAll()).entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        return result;
    }

    protected BoolQueryBuilder qb(WorkBenchParam wb, int n) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (wb != null) {
            final String name = wb.getKey().equals("emp") ? BConst.USERID : BConst.CLUSE_DEPT;
            if (n == 1) qb.must(QueryBuilders.matchQuery(name + ".keyword", wb.getVal())); //非销售排行榜使用
            if (StringUtils.isNotEmpty(wb.getStatus())) qb.must(QueryBuilders.matchPhraseQuery(BConst.CLUSE_STATUS, wb.getStatus())); //销售排行榜添加状态条件
            qb.must(QueryBuilders.rangeQuery(BConst.RECORD_UPDATE_TIME).from(wb.getStartDate().concat(T_DATESUFIX)).to(wb.getEndDate().concat(T_DATESUFIX_END)));
        }
        return qb;
    }

    protected BoolQueryBuilder qbr(WorkBenchParam wb) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (wb != null) {
            final String name = wb.getKey().equals("emp") ? BConst.USERID : BConst.CLUSE_RECORD_DEPT;
            qb.must(QueryBuilders.matchQuery(name + ".keyword", wb.getVal()));
            qb.must(QueryBuilders.rangeQuery(BConst.CLUSE_RECORD_UPDATE_TIME).from(wb.getStartDate().concat(T_DATESUFIX)).to(wb.getEndDate().concat(T_DATESUFIX_END)));
        }
        return qb;
    }

    private static List<String> values() {
        List<String> values = new ArrayList<>();
        values.add(CluesStatus.CLUES_HAS_SUCCESS);
        values.add(CluesStatus.CLUES_HAS_IDEA);
        values.add(CluesStatus.CLUES_HASNOT_SURE);
        return values;
    }

    private Map<String, Long> combainMap(Map<String, Long> unsortMap, List<SysUser> list) {
        Map<String, Long> result = new HashMap<>();
        for (SysUser sysUser : list) {
            String name = sysUser.getLegenPerson();
            String userId = sysUser.getUserId();
            for (String key : unsortMap.keySet()) {
                if (userId.equals(key)) {
                    result.put(name, unsortMap.get(key));
                }
            }
        }
        return result;
    }
}
