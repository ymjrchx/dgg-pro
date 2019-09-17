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
public interface NewsLabelExtDao {

    List<Map<String, Object>> find(Map map);

    NewsLabel findByNewsId(@Param("newsId") Long newsId);

}