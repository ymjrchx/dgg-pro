package net.dgg.bigdata.sjjs.web.dao;

import net.dgg.bigdata.sjjs.web.entity.WebConfParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <WebConfParamDao>
 * @despriction
 * @create 2018-10-12 11:08:33+
 **/
@Repository
@Mapper
public interface WebConfParamDao {

    void save(WebConfParam webConfParam);

    void update(WebConfParam webConfParam);

    WebConfParam findById(@Param("id") Long id);

    List<WebConfParam> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}