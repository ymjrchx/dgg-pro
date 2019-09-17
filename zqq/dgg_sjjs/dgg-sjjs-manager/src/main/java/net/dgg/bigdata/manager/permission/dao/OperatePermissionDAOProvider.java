package net.dgg.bigdata.manager.permission.dao;


import net.dgg.bigdata.manager.permission.entity.OperatePermissionEntity;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/7 10:30
 * @Description:
 */
public class OperatePermissionDAOProvider {

    public String insertPermissionInfoSql(OperatePermissionEntity operatePermissionEntity) {
        return new SQL() {{
            INSERT_INTO("operate_permission");
            VALUES("operate_permission_id", "#{operatePermissionId}");
            VALUES("operate_permission_name", "#{operatePermissionName}");
            VALUES("remark", "#{remark}");
            VALUES("creator_id", "#{creatorId}");
            VALUES("createtime", "#{createtime}");
            if (!StringUtils.isEmpty(operatePermissionEntity.getCode()))
                VALUES("code", "#{code}");
        }}.toString();
    }

    public String updatePermissionInfoSql(OperatePermissionEntity operatePermissionEntity) {
        return new SQL() {{
            UPDATE("operate_permission");
            if (!StringUtils.isEmpty(operatePermissionEntity.getOperatePermissionName())) {
                SET("operate_permission_name=#{operatePermissionName}");
            }
            if (!StringUtils.isEmpty(operatePermissionEntity.getCode())) {
                SET(" code=#{code}");
            }
            if (!StringUtils.isEmpty(operatePermissionEntity.getCreatorId())) {
                SET(" creator_id=#{creatorId}");
            }
            if (!StringUtils.isEmpty(operatePermissionEntity.getRemark())) {
                SET(" remark=#{remark}");
            }
            if (!StringUtils.isEmpty(operatePermissionEntity.getCreatetime())) {
                SET(" createtime=#{createtime}");
            }
            WHERE("operate_permission_id=#{operatePermissionId}");
        }}.toString();
    }

    public String findPermsEntityBykeyWordsSql(Map keyWords) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT operate_permission_id AS operatePermissionId, operate_permission_name AS operatePermissionName, code AS code, creator_id AS creatorId, createtime AS createtime FROM operate_permission");
        sb.append("where 1=1 ");
        if (keyWords != null && keyWords.containsKey("permsName") && keyWords.get("permsName") != null) {
            sb.append("and operate_permission_name = #{permsName}");
        }
        if (keyWords != null && keyWords.containsKey("code") && keyWords.get("code") != null) {
            sb.append("and code = #{code}");
        }
        if (keyWords != null && keyWords.containsKey("creatorId") && keyWords.get("creatorId") != null) {
            sb.append("and creator_id = #{creatorId}");
        }
        if (keyWords != null && keyWords.containsKey("createtime") && keyWords.get("createtime") != null) {
            sb.append("and createtime = #{createtime}");
        }
        return sb.toString();
    }

    public String findOperatePermissionByMenuIdSql(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("select op.operate_permission_id as operatePermissionId,op.operate_permission_name as operatePermissionName, ");
        sb.append("op.code as code  from operate_permission op where 1=1 ");
        if (map != null && map.containsKey("operatePermissionName") && map.get("operatePermissionName") != null) {
            sb.append("and op.operate_permission_name LIKE CONCAT('%',CONCAT(#{operatePermissionName},'%')) ");
        }
        if (map != null && map.containsKey("isMenu") && (int) map.get("isMenu") == 1) {
            sb.append("and op.operate_permission_id in ")
                    .append("(select operate_permission_id from menu_operate_permission where 1=1 ");
            if (map != null && map.containsKey("menuId") && !StringUtils.isEmpty(map.get("menuId"))) {
                sb.append("and menu_id = #{menuId} ");
            }
            sb.append(")");
        }
        if (map != null && map.containsKey("isMenu") && (int) map.get("isMenu") == 0) {
            sb.append("and op.operate_permission_id not in ")
                    .append("(select operate_permission_id from menu_operate_permission where 1=1 ");
            if (map != null && map.containsKey("menuId") && !StringUtils.isEmpty(map.get("menuId"))) {
                sb.append("and menu_id = #{menuId} ");
            }
            sb.append(") ");
        }
        sb.append("ORDER BY op.createtime desc");
        return sb.toString();
    }

    public String findOperatePermissionByMenuRoleSql(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("select op.operate_permission_id as operatePermissionId,op.operate_permission_name as operatePermissionName,op.code as code,");
        if (map != null && map.containsKey("roleId") && !StringUtils.isEmpty(map.get("roleId"))) {
            sb.append(" case when a.operate_permission_id = op.operate_permission_id then  1 else 0 end ");
        } else {
            sb.append(" 0 ");
        }
        sb.append("as roleOpm from operate_permission op ");
        if (map != null && map.containsKey("roleId") && !StringUtils.isEmpty(map.get("roleId"))) {
            sb.append("LEFT JOIN (select operate_permission_id from role_operate_permission ")
                    .append("where role_id=#{roleId}) a ")
                    .append("on op.operate_permission_id = a.operate_permission_id ");
        }
        sb.append("where op.operate_permission_id NOT IN ( ")
                .append("SELECT operate_permission_id FROM menu_operate_permission ) ");
        if (map != null && map.containsKey("operatePermissionName") && !StringUtils.isEmpty(map.get("operatePermissionName"))) {
            sb.append("and op.operate_permission_name LIKE CONCAT('%',CONCAT(#{operatePermissionName},'%')) ");
        }
        sb.append("ORDER BY op.createtime desc");
        return sb.toString();
    }

    public String findMenuOpsSql(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("select op.operate_permission_id as operatePermissionId, ")
                .append("op.operate_permission_name as operatePermissionName, op.code as code,");
        if (map != null && map.containsKey("roleId") && !StringUtils.isEmpty(map.get("roleId"))) {
            sb.append(" case when a.operate_permission_id = op.operate_permission_id then  1 else 0 end ");
        } else {
            sb.append(" 0 ");
        }
        sb.append("as LAY_CHECKED  from operate_permission op ");
        if (map != null && map.containsKey("roleId") && !StringUtils.isEmpty(map.get("roleId"))) {
            sb.append("LEFT JOIN (select operate_permission_id from role_operate_permission ")
                    .append("where role_id=#{roleId} ) a ")
                    .append("on op.operate_permission_id = a.operate_permission_id ");
        }
        sb.append("where op.operate_permission_id IN ( SELECT operate_permission_id FROM ")
                .append("menu_operate_permission where menu_id = #{menuId} ) ORDER BY op.createtime desc");
        return sb.toString();
    }

    public String queryBySearchSql(Map searchMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("select operate_permission_id as operatePermissionId,operate_permission_name as operatePermissionName, " +
                "    code ,remark,createtime ,creator_id as creatorId " +
                "    from operate_permission where 1=1 ");
        if (searchMap != null && searchMap.containsKey("operatePermissionName") && !StringUtils.isEmpty(searchMap.get("operatePermissionName"))) {
            sb.append("and operate_permission_name LIKE CONCAT('%',CONCAT(#{operatePermissionName},'%')) ");
        }
        sb.append(" ORDER BY createtime desc");
        return sb.toString();
    }

    public String existCodeSql(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) from operate_permission where code=#{code} ");
        if (map != null && map.containsKey("id") && !StringUtils.isEmpty(map.get("id"))) {
            sb.append("and operate_permission_id != #{id}");
        }
        return sb.toString();
    }

}
