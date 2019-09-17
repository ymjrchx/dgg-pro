package net.dgg.zqq.dao.news;

import net.dgg.zqq.entity.news.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <NewsDao>
 * @despriction
 * @create 2018-09-27 14:10:14+
 **/
@Repository
public interface NewsDao {
    void save(News news);

    void update(News news);

    News findById(@Param("id") Long id);

    List<News> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

    void updateViewTimes(Long id);

    void updatePraise(Long id);


}