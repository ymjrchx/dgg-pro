package net.dgg.zqq.dao.news;

import net.dgg.zqq.entity.news.NewsLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <NewsLabelDao>
 * @despriction
 * @create 2018-11-09 11:49:30+
 **/
@Repository
public interface NewsLabelDao {
    void save(NewsLabel newsLabel);

    void update(NewsLabel newsLabel);

    NewsLabel findById(@Param("id") Long id);

    List<NewsLabel> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}