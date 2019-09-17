package net.dgg.zqq.dao.question;

import net.dgg.zqq.entity.question.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <QuestionDao>
 * @despriction
 * @create 2018-10-22 12:01:39+
 **/ 
@Repository
public interface QuestionDao {
    void save(Question question);

    void update(Question question);

    Question findById(@Param("id") Long id);

    List<Question> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}