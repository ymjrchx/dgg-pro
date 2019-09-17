package net.dgg.core.es.stack;

import org.elasticsearch.search.SearchHit;

import java.util.List;

/**
 * Created by 李程 on 2018/10/22.
 */
public interface Console {

    void write(SearchHit object);

    void stop();

    boolean isStop();

    List<SearchHit> getList();

}