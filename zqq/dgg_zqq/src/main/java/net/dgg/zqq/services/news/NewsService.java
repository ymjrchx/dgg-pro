package net.dgg.zqq.services.news;

import net.dgg.zqq.entity.news.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NewsService {
    void save(News news, String content, String newsPhoto);

    void excSave(News news);

    void update(News news, String content, String newsPhoto);

    News findById(@Param("id") Long id);

    List<News> query(Map map);

    List<Map> queryMap(Map map);

    Map showAllNews(int pageSize,int pageNum,String type,String order);

    Integer praise(Long id);

    Map showProtalData(String typeOne,String typeTwo,String typeThree);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

    Map getNewsContent(Long id, String type, Integer pageSize, Integer pageNum);

    Map pageQueryByLabel(Integer pageSize, Integer pageNum, String label);

    Map getNewsContentByLabel(Long id, String label, Integer pageSize, Integer pageNum);

    List<News> findByLabel(Integer limit, String newsLabels);
}
