package net.dgg.zqq.dao.order;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <ApplicantDao>
 * @despriction 查询申请人信息
 * @create 2018/10/13 11:24
 **/
@Repository
public interface ApplicantDao {
    /**
     * 查询订单申请人和联系人信息
     *
     * @param map
     * @return
     */
    List<Map> queryApplicantMap(Map map);

}
