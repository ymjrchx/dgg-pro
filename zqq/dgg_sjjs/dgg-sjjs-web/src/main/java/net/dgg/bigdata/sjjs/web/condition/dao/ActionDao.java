package net.dgg.bigdata.sjjs.web.condition.dao;

import net.dgg.bigdata.common.entity.condition.Action;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/12 11:21
 * @Description:
 */
@Repository
public interface ActionDao {

    @Select("select id ,action_type AS actionType,type_value as typeValue from yk_action where treebook_id = #{categorieId} limit 1")
    public Action selectAction(@Param("categorieId") Long categorieId);


}
