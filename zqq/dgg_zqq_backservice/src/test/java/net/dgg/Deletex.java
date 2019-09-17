package net.dgg;

import net.dgg.dqc.backservice.utils.Tookit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import javax.naming.directory.SearchResult;
import java.util.Iterator;

public class Deletex {

    @Test
    public void testDel() {
        TransportClient client = Tookit.EsClientUtil.getClient("192.168.254.142:9300", "testdcq");

        SearchResponse response = client.prepareSearch("dgg_zqq_es_db_search_log").setQuery(QueryBuilders.termQuery("keyWord", "66")).setSize(10000).get();
        SearchHits hits = response.getHits();
        Iterator<SearchHit> hit = hits.iterator();
        while (hit.hasNext()) {
            //client.prepareDelete().setIndex("dgg_zqq_es_db_search_log").setType("keyWordSearchLog").setId(hit.next().getId()).get();
        }

    }

}
