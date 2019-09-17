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
 * @create 2018-09-27 14:03:52+
 **/
@Repository
public interface QuestionExtDao {

    List<Question> selectByKeyWord(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    Integer countKeyword(Map map);

}