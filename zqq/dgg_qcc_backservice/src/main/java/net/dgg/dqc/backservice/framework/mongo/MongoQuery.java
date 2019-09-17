package net.dgg.dqc.backservice.framework.mongo;


import org.elasticsearch.common.collect.HppcMaps;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MongoQuery {
    private static final Logger logger = LoggerFactory.getLogger(MongoQuery.class);

    private MongoCollection c;

    public MongoQuery(MongoCollection c) {
        this.c = c;
    }

    public static MongoQuery build(MongoCollection c) {
        return new MongoQuery(c);
    }

    public <T, D> Page3Dto<D> page(int pageIndex, int pageSize, Class<T> clazz, Function<T, D> f,
                                   String q, HppcMaps.Object... params) {
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        int skip = (pageIndex - 1) * pageSize;
        long num = c.count(q, params);
        Page3Dto<D> page = new Page3Dto(pageIndex, pageSize, num);
        List<D> data = new ArrayList<>();
        logger.debug("c -> {}", c);
        MongoCursor<T> cursor = c.find(q, params)
                .skip(skip)
                .limit(pageSize).as(clazz);
        while (cursor.hasNext()) {
            data.add(f.apply(cursor.next()));
        }
        page.setData(data);
        return page;
    }


    public <T, D> Page3Dto<D> page2(int pageIndex, int pageSize, Class<T> clazz, Function<T, D> f,
                                    String q, Object... params) {
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        int skip = (pageIndex - 1) * pageSize;
        long num = c.count(q, params);
        Page3Dto<D> page = new Page3Dto(pageIndex, pageSize, num);
        List<D> data = new ArrayList<>();
        logger.debug("c -> {}", c);
        MongoCursor<T> cursor = c.find(q, params)
                .skip(skip)
                .limit(pageSize).as(clazz);
        while (cursor.hasNext()) {
            data.add(f.apply(cursor.next()));
        }
        page.setData(data);
        return page;
    }

    public <T, D> Page3Dto<D> page3(int pageIndex, int pageSize, Class<T> clazz, Function<T, D> f,
                                    String q) {
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        int skip = (pageIndex - 1) * pageSize;
        long num = c.count(q);
        Page3Dto<D> page = new Page3Dto(pageIndex, pageSize, num);
        List<D> data = new ArrayList<>();
        logger.debug("c -> {}", c);
        MongoCursor<T> cursor = c.find(q)
                .skip(skip)
                .limit(pageSize).as(clazz);
        while (cursor.hasNext()) {
            data.add(f.apply(cursor.next()));
        }
        page.setData(data);
        return page;
    }

    public <T, D> Page3Dto<D> sortPage(int pageIndex, int pageSize, Class<T> clazz, Function<T, D> f,
                                       String q, String sort, HppcMaps.Object... params) {
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        int skip = (pageIndex - 1) * pageSize;
        long num = c.count(q, params);
        Page3Dto<D> page = new Page3Dto(pageIndex, pageSize, num);
        List<D> data = new ArrayList<>();
        logger.debug("c -> {}", c);
        MongoCursor<T> cursor = c.find(q, params)
                .sort(sort)
                .skip(skip)
                .limit(pageSize).as(clazz);
        while (cursor.hasNext()) {
            data.add(f.apply(cursor.next()));
        }
        page.setData(data);
        return page;
    }

    public <T, D> List<D> query(Class<T> clazz, Function<T, D> f,
                                String q, Object... params) {
        List<D> data = new ArrayList<>();
        logger.debug("c -> {}", c);
        MongoCursor<T> cursor = c.find(q, params)
                .as(clazz);
        while (cursor.hasNext()) {
            data.add(f.apply(cursor.next()));
        }
        return data;
    }

    public <T, D> List<D> sortQuery(Class<T> clazz, Function<T, D> f,
                                    String q, String sort, Object... params) {
        List<D> data = new ArrayList<>();
        logger.debug("c -> {}", c);
        MongoCursor<T> cursor = c.find(q, params)
                .sort(sort)
                .as(clazz);
        while (cursor.hasNext()) {
            data.add(f.apply(cursor.next()));
        }
        return data;
    }

    public MongoCollection getC() {
        return c;
    }

    public void setC(MongoCollection c) {
        this.c = c;
    }
}
