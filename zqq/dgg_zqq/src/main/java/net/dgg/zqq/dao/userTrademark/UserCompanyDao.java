package net.dgg.zqq.dao.userTrademark;

import net.dgg.zqq.entity.userTrademark.UserCompany;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <UserCompanyDao>
 * @despriction 
 * @create 2018-10-16 16:38:43+
 **/ 
@Repository
public interface UserCompanyDao {
    void save(UserCompany userCompany);

    void update(UserCompany userCompany);

    UserCompany findById(@Param("id") Long id);

    List<UserCompany> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}