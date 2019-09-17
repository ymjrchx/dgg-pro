package net.dgg.bigdata.manager.role.dao;

import net.dgg.bigdata.manager.role.entity.RoleMenuPermission;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/7 15:25
 * @Description:
 */
public class RoleMenuDaoProvider {

    public String saveSql(Map map) {
        List<RoleMenuPermission> roleMenuPermissions = (List<RoleMenuPermission>) map.get("roleMenuPermissions");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO role_menu_permission(id,role_id,menu_id,creator_id,createtime) VALUES");
        MessageFormat messageFormat = new MessageFormat(" (#'{'roleMenuPermissions[{0}].id},#'{'roleMenuPermissions[{0}].roleId},#'{'roleMenuPermissions[{0}].menuId},#'{'roleMenuPermissions[{0}].creatorId},#'{'roleMenuPermissions[{0}].createtime})");
        for (int i = 0; i < roleMenuPermissions.size(); i++) {
            stringBuffer.append(messageFormat.format(new Object[]{i}));
            if (i < roleMenuPermissions.size() - 1)
                stringBuffer.append(",");
        }
        return stringBuffer.toString();
    }

    public String removeByMenuIdSql(Map map) {
        List<Long> menuIds = (List<Long>) map.get("menuIds");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DELETE  from role_menu_permission where menu_id in ( ");
        MessageFormat messageFormat = new MessageFormat("#'{'menuIds[{0}]}");
        for (int i = 0; i < menuIds.size(); i++) {
            stringBuffer.append(messageFormat.format(new Object[]{i}));
            if (i < menuIds.size() - 1) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
}
