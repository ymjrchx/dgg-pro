package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.CommentRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <CommentRecordDao>
 * @despriction
 * @create 2018-09-29 15:42:54+
 **/
@Repository
public interface CommentRecordDao {
    void save(CommentRecord commentRecord);

    void update(CommentRecord commentRecord);

    CommentRecord findById(@Param("id") Long id);

    List<CommentRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    Integer total(Map map);//返回评价总数（与user关联）

    void deleteById(@Param("id") Long id);

}