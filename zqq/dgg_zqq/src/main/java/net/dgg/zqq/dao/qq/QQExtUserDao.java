package net.dgg.zqq.dao.qq;

import net.dgg.zqq.entity.qq.QQExtUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <QQExtUserDao>
 * @despriction
 * @create 2018-10-20 10:10:08+
 **/
@Repository
public interface QQExtUserDao {
    void save(QQExtUser qQExtUser);

    void update(QQExtUser qQExtUser);

    QQExtUser findById(@Param("id") Long id);

    List<QQExtUser> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}