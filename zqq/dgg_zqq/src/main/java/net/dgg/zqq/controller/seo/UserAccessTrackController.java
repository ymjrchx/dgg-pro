package net.dgg.zqq.controller.seo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.dgg.iboss.base.util.StringUtils;
import net.dgg.zqq.controller.seo.dto.NewsWordsQueryWrapper;
import net.dgg.zqq.controller.seo.dto.RankQueryWrapper;
import net.dgg.zqq.controller.seo.dto.UserAccessEventWrapper;
import net.dgg.zqq.services.seo.MessageQueueService;
import net.dgg.zqq.utils.Toolkit;
import net.dgg.zqq.utils.es.EsClientUtils;
import net.fblock.web.common.BaseController;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/11/9.
 */
@Api(description = "用户访问记录")
@RestController
@RequestMapping("/seo")
@Slf4j
public class UserAccessTrackController extends BaseController {

    @Value("${nature.messagequeue.host}")
    String host;

    @Value("${nature.messagequeue.username}")
    String username;

    @Value("${nature.messagequeue.password}")
    String password;

    @Value("${nature.messagequeue.port}")
    Integer port;

    @Value("${nature.messagequeue.vhost}")
    String vhost;

    @Value("${app.user.access.event}")
    String userAccessEvent;

    private MessageQueueService messageQueueService;

    @PostConstruct
    public void init() {
        messageQueueService = MessageQueueService.builder().queue(userAccessEvent).host(host).password(password).port(port).username(username).vhost(vhost).build();
    }

    @ApiOperation(value = "访问事件记录器", notes = "记录用户访问事件")
    @RequestMapping(value = "/accessEvent", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, consumes = {"application/json;charset=UTF-8"})
    public Object userAccess(@RequestBody @Valid UserAccessEventWrapper userAccessEventWrapper, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> messages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            String message = messages.stream().reduce((a, b) -> a.concat(",").concat(b)).get();
            return this.getFailResponse(message);
        } else {
            Map<String, Object> hash = new HashMap<>();
            userAccessEventWrapper.setCurrentTime(Clock.systemUTC().millis());
            messageQueueService.putMessage(userAccessEvent, new Gson().toJson(userAccessEventWrapper));
            return this.getSuccessResponse(hash);
        }
    }

    @ApiOperation(value = "搜索词排名", notes = "用户在网站内进行的商标专利搜索排名")
    @RequestMapping(value = "/rankOfKeyword", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, consumes = {"application/json;charset=UTF-8"})
    public Object rankOfKeyword(@RequestBody @Valid @ApiParam RankQueryWrapper rankQueryWrapper, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> messages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            String message = messages.stream().reduce((a, b) -> a.concat(",").concat(b)).get();
            return this.getFailResponse(message);
        } else {
            String keyWordLogIndex = "dgg_zqq_es_db_search_log";
            String keyWordLogType = "keyWordSearchLog";
            TransportClient client = EsClientUtils.getClient();
            SearchRequestBuilder searchRequestBuilder = client.prepareSearch(keyWordLogIndex).setTypes(keyWordLogType).addAggregation(AggregationBuilders.terms("keyWord").size(rankQueryWrapper.getPullCount()).field("keyWord.keyword").order(Terms.Order.count(false)));
            BoolQueryBuilder bool = QueryBuilders.boolQuery();
            boolean haveStartEnd = false;
            if (StringUtils.isNotEmpty(rankQueryWrapper.getStart())) {
                haveStartEnd = true;
                Long startTime = Toolkit.DateUtils.fromDate(rankQueryWrapper.getStart());
                bool.must(QueryBuilders.rangeQuery("timestamp").gte(startTime));
            }
            if (StringUtils.isNotEmpty(rankQueryWrapper.getEnd())) {
                haveStartEnd = true;
                Long endTime = Toolkit.DateUtils.fromDate(rankQueryWrapper.getEnd());
                bool.must(QueryBuilders.rangeQuery("timestamp").lt(endTime + 24L * 3600L * 1000L));
            }
            if (StringUtils.isNotEmpty(rankQueryWrapper.getSearchType())) {
                haveStartEnd = true;
                if (rankQueryWrapper.getSearchType().equalsIgnoreCase("brand") || rankQueryWrapper.getSearchType().equalsIgnoreCase("patent")) {
                    bool.must(QueryBuilders.termQuery("searchType.keyword", rankQueryWrapper.getSearchType()));
                } else if (!rankQueryWrapper.getSearchType().equalsIgnoreCase("all")) {
                    return this.getFailResponse("SearchType的值只能是允许的值：brand,patent,all 或者为空");
                }
            }
            if (haveStartEnd) {
                searchRequestBuilder.setQuery(bool);
            }
            searchRequestBuilder.setSize(0);
            SearchResponse searchResponse = searchRequestBuilder.get();
            Aggregations aggs = searchResponse.getAggregations();
            List<Aggregation> aggList = aggs.asList();
            Map<String, Long> countMap = new LinkedHashMap<>();
            for (Aggregation agg : aggList) {
                StringTerms teamSum = (StringTerms) agg;
                for (StringTerms.Bucket bucket : teamSum.getBuckets()) {
                    String keyWord = bucket.getKeyAsString();
                    Long count = bucket.getDocCount();
                    countMap.put(keyWord, count);
                }
            }
            return this.getSuccessResponse(countMap);
        }
    }


    @ApiOperation(value = "最近搜索词", notes = "最近搜索词")
    @RequestMapping(value = "/nearWords", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, consumes = {"application/json;charset=UTF-8"})
    public Object nearWords(@RequestBody @Valid @ApiParam NewsWordsQueryWrapper query, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> messages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            String message = messages.stream().reduce((a, b) -> a.concat(",").concat(b)).get();
            return this.getFailResponse(message);
        } else {
            String index = "dgg_zqq_es_db_search_distinct_log";
            String type = "keyWordSearchDistinctLog";
            TransportClient client = EsClientUtils.getClient();
            SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
            searchRequestBuilder.addSort("timestamp", SortOrder.DESC);
            Integer offset = query.getPage() * query.getSize();
            Integer size = query.getSize();
            searchRequestBuilder.setFrom(offset);
            searchRequestBuilder.setSize(size);
            SearchResponse searchResponse = searchRequestBuilder.get();
            SearchHits searchHits = searchResponse.getHits();
            List<Map<String, Object>> list = Lists.newArrayList(searchHits.iterator()).stream().map(SearchHit::getSource).collect(Collectors.toList());
            Map<String, Object> resp = new LinkedHashMap<>();
            resp.put("total", searchHits.getTotalHits());
            resp.put("size", list.size());
            resp.put("list", list);
            return this.getSuccessResponse(resp);
        }
    }
}
