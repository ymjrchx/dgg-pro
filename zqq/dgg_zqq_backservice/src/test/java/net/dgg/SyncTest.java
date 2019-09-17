package net.dgg;

import lombok.extern.slf4j.Slf4j;
import net.dgg.dqc.backservice.utils.Tookit;
import net.dgg.dqc.backservice.utils.Toolkit;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.util.Iterator;

@Slf4j
public class SyncTest {

    TransportClient client = Tookit.EsClientUtil.getClient("10.2.1.121:9300", "dcq");

    @Test
    public void testSync() {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("dgg_zqq_es_db_search_log").setTypes("keyWordSearchLog").addSort("timestamp", SortOrder.DESC);
        searchRequestBuilder.setSize(10000);
        Iterator<SearchHit> hits = searchRequestBuilder.get().getHits().iterator();
        Long offset = 0L;
        while (hits.hasNext()) {
            SearchHit hit = hits.next();
            String id = Toolkit.StringHelper.md5((String) hit.getSource().get("keyWord"));
            client.prepareIndex("dgg_zqq_es_db_search_distinct_log", "keyWordSearchDistinctLog", id).setSource(hit.getSource()).get();
            offset++;
            log.info("当前导入：{}", offset);
        }

    }

}
