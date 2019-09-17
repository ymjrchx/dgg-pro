package net.dgg.bigdata.sjjs.web.condition.dao;

import net.dgg.bigdata.common.entity.condition.ConditionGroups;
import net.dgg.bigdata.sjjs.web.condition.dto.ConditionGroupDto;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 15:03
 * @Description:
 */
@Repository
public interface ConditionGroupsDao {


    /**
     * 根据用户id获取历史条件组
     */
    @Select("select id, name,used_count,sort as 'order' ,info,filter,create_time as createTime from yk_condition_groups where type = 0 and creater_id = #{createrId} order by create_time desc limit 20 ")
    List<ConditionGroupDto> getConditionGroups(Long createrId);


    @InsertProvider(type = ConditionGroupsProvider.class,method = "add")
    void addConditionGroups(ConditionGroups conditionGroups);


    @Select("select id, name,used_count,sort as 'order' ,info,filter from yk_condition_groups where type = 1 and industry_id = #{industryId} order by sort")
    List<ConditionGroupDto> getConditionGroupDtoForIndustry(Long industryId);

}
