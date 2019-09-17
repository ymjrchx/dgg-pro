package net.dgg.gspt.dqc.dao.searchWord;

import net.dgg.gspt.dqc.entity.searchWord.SearchWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <SearchWordDao>
 * @despriction
 * @create 2018-12-24 11:05:57+
 **/ 
@Repository
public interface SearchWordDao {
    void save(SearchWord searchWord);

    void update(SearchWord searchWord);

    SearchWord findById(@Param("id") Long id);

    List<SearchWord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}