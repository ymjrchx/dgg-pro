package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.CommentRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/9/30 10:46
 */
@Repository
public interface CommentRecordExtDao {

    void save(CommentRecord commentRecord);

    void update(CommentRecord commentRecord);

    CommentRecord findById(@Param("id") Long id);

    List<CommentRecord> query(Map map);

    List<Map> queryMap(Map map);

    //获取评价条数
    Integer selectGoodRecord(Map query);

    Integer selectMidRecord(Map query);

    Integer selectBadRecord(Map query);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

    //更改审核状态
    void updateT(@Param("id") Long id);

    void updateF(@Param("id") Long id);
}
