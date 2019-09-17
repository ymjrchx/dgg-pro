package net.dgg.gspt.dqc.framework.redis.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wu on 2017-09-13.
 */
public interface RedisDao {

    public <T> String addByKey(String key, T object) throws IOException;

    public <T> String add(T object) throws IOException;

    public Object getObject(String key) throws IOException;

    public <T> List<String> addList(List<T> list) throws IOException;

    public <T> String addListKey(List<String> strList, List<T> list) throws IOException;

    public <T> Long addListKey(Map<String, T> map) throws IOException;

    public Long deleteByKey(String key) throws IOException;

    public Long batchDelete(List<String> strList) throws IOException;
}
