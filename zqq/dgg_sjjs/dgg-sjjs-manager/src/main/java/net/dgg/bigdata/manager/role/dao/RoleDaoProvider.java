package net.dgg.bigdata.manager.role.dao;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/7 14:45
 * @Description:
 */
public class RoleDaoProvider {

    public String existRoleSql(Map map) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select count(*) from role where role_name=#{roleName}");
        if (map != null && map.containsKey("roleId")) {
            stringBuffer.append("and role_id != #{roleId}");
        }
        return stringBuffer.toString();
    }

    public String queryRolesSql(Map map) {
        return new SQL() {{
            SELECT("role_id as roleId", "role_name as roleName", "creator_id as creatorId", "createtime as createtime", "updator_id as updatorId", "updatetime as updatetime", "remark", "enable");
            FROM("role");
            if (map != null && map.containsKey("roleName") && !StringUtils.isEmpty(map.get("roleName"))) {
                WHERE("role_name LIKE CONCAT('%',CONCAT(#{roleName},'%'))");
            }
        }}.toString();
    }


}
