package net.dgg.bigdata.sjjs.web.condition.dao;

import net.dgg.bigdata.sjjs.web.condition.dto.OptionDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/12 11:34
 * @Description:
 */
@Repository
public interface OptionDao {

    //select label_name as labelName ,value  from yk_option where action_id = 1
    @Select("select label_name as labelName ,value  from yk_option where action_id = #{actionId}")
    public List<OptionDto> selectOptionDtos(@Param("actionId") Long actionId);
}
