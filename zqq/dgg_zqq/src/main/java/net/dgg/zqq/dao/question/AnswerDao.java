package net.dgg.zqq.dao.question;

import net.dgg.zqq.entity.question.Answer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <AnswerDao>
 * @despriction
 * @create 2018-09-27 14:04:28+
 **/
@Repository
public interface AnswerDao {
    void save(Answer answer);

    void update(Answer answer);

    Answer findById(@Param("id") Long id);

    List<Answer> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}