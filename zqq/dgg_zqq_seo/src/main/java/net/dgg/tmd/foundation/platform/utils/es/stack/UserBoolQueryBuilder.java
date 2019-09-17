package net.dgg.tmd.foundation.platform.utils.es.stack;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * Created by 李程 on 2018/10/22.
 */
@FunctionalInterface
public interface UserBoolQueryBuilder {

    void exec(String key, BoolQueryBuilder boolQueryBuilder);

}
