package net.dgg.bigdata.manager.condition.dao;

import net.dgg.bigdata.common.entity.condition.Action;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 13:53
 * @Description:
 */
@Repository
public interface ActionDao {

    @Select("select id,action_type as actionType ,type_value as typeValue,treebook_id as treebookId from yk_action where treebook_id = #{treeBookID}")
    List<Action> getActionByTreeBookId(Long treeBookID);
}
