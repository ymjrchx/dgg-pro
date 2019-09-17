package net.dgg.bigdata.sjjs.web.condition.dao;/*
package net.dgg.bigdata.manager.condition.dao;

import net.dgg.bigdata.manager.condition.entity.ConditionGroups;
import org.apache.ibatis.jdbc.SQL;

*/

import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.condition.ConditionGroups;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/6 14:14
 * @Description:
 */

public class ConditionGroupsProvider {
    public static String add(ConditionGroups conditionGroups) {
        return new SQL() {{
            INSERT_INTO("yk_condition_groups");
            VALUES("id", "#{id}");
            VALUES("name", "#{name}");
            VALUES("used_count", "#{usedCount}");
            VALUES("sort", "#{sort}");
            //VALUES("industry_id", "#{industryId}");
            VALUES("filter", "#{filter}");
            VALUES("info", "#{info}");
            VALUES("type", ConditionConstant.CONDITION_TYPE_0 + "");
            VALUES("creater_id", "#{createrId}");
            VALUES("creater_name", "#{createrName}");
            VALUES("creater_org_id", "#{createrOrgId}");
            VALUES("create_time", "#{createTime}");
        }}.toString();
    }

    public static void main(String[] a) {
        ConditionGroups conditionGroups = new ConditionGroups();
        conditionGroups.setCreaterName("''or 1=1");
        //String sql = ConditionGroupsProvider.selectOne(conditionGroups);
        //System.out.println(sql);
    }
}
