package net.dgg.gspt.dqc.dao.searchWord;

import net.dgg.gspt.dqc.entity.searchWord.SearchWord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <SearchWordDao>
 * @despriction
 * @create 2018-11-27 11:16:49+
 **/
@Repository
public interface SearchWordExtDao {

    List<SearchWord> query(Map map);


}