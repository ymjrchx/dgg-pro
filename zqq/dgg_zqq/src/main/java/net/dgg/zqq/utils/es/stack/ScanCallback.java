package net.dgg.zqq.utils.es.stack;

import org.elasticsearch.search.SearchHit;

/**
 * Created by 李程 on 2018/10/22.
 */
@FunctionalInterface
public interface ScanCallback {

    void arrived(Console console, SearchHit data);

}