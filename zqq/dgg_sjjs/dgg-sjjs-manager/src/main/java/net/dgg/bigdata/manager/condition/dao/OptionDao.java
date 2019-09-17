package net.dgg.bigdata.manager.condition.dao;

import net.dgg.bigdata.common.entity.condition.Option;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 13:56
 * @Description:
 */
@Repository
public interface OptionDao {

    @Select("select id,label_name as labelName,`value`,action_id as actionId from yk_option where action_id = #{actionId}")
    List<Option> getOptionByTreeBookId(Long actionId);
}
