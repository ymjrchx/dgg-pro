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
 * @create 2018-10-16 09:51:31+
 **/ 
@Repository
public interface UserCompanyExtDao {

   UserCompany findByMap (Map map);

   List<UserCompany> selectByFlag(Map map);


}