package net.dgg.zqq.dao.news;

import net.dgg.zqq.entity.news.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <NewsExtDao>
 * @despriction 扩展Dao
 * @create 2018/11/22 18:16
 **/
public interface NewsExtDao {

    Integer getOrderIndexById(Map map);

    Integer getOrderIndexByNumber(Map map);

    List<News> findByLabel(Map map);

    List<News> query(Map map);
}
