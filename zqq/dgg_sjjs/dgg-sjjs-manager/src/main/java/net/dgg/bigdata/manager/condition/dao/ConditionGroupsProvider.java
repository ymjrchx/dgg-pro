/*
package net.dgg.bigdata.manager.condition.dao;

import net.dgg.bigdata.manager.condition.entity.ConditionGroups;
import org.apache.ibatis.jdbc.SQL;

*/
/**
 * @Auther: 陈万国
 * @Date: 2018/12/6 14:14
 * @Description:
 *//*

public class ConditionGroupsProvider {
    public static String selectOne(ConditionGroups conditionGroups) {
        return new SQL() {{
            SELECT("*");
            FROM("role a");
            WHERE("a.role_name =" + conditionGroups.getCreaterName());
        }}.toString();
    }

    public static void main(String[] a) {
        ConditionGroups conditionGroups = new ConditionGroups();
        conditionGroups.setCreaterName("''or 1=1");
        String sql = ConditionGroupsProvider.selectOne(conditionGroups);
        System.out.println(sql);
    }
}
*/
