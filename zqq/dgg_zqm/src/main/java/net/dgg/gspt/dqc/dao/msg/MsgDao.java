package net.dgg.gspt.dqc.dao.msg;

import net.dgg.gspt.dqc.entity.msg.Msg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <MsgDao>
 * @despriction
 * @create 2018-09-27 14:10:47+
 **/
@Repository
public interface MsgDao {
    void save(Msg msg);

    void update(Msg msg);

    Msg findById(@Param("id") Long id);

    List<Msg> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);
    Integer counts(Map map);

    void deleteById(@Param("id") Long id);

    void updateMsgReaded(@Param("id") Long id);
}