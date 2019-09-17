package net.dgg.bigdata.manager.menu.dao;

import net.dgg.bigdata.manager.menu.entity.MenuOperatePermission;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/7 09:03
 * @Description:
 */
public class MenuOperatePermissionDaoProvider {

    public String saveSql(Map map) {
        List<MenuOperatePermission> menuOperatePermissions = (List<MenuOperatePermission>) map.get("menuOperatePermissions");
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO menu_operate_permission(")
                .append("id,operate_permission_id,menu_id,creator_id,createtime) VALUES ");
        MessageFormat messageFormat = new MessageFormat("(#'{'menuOperatePermissions[{0}].id},#'{'menuOperatePermissions[{0}].operatePermissionId}," +
                "#'{'menuOperatePermissions[{0}].menuId},#'{'menuOperatePermissions[{0}].creatorId},#'{'menuOperatePermissions[{0}].createtime})");
        for (int i = 0; i < menuOperatePermissions.size(); i++) {
            sb.append(messageFormat.format(new Object[]{i}));
            if (i < menuOperatePermissions.size() - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    public String removeByMenuIdSql(Map map) {

        StringBuffer sb = new StringBuffer();
        sb.append("DELETE  from menu_operate_permission where menu_id=#{menuId} ");
        if (map.containsKey("operatePermissionIds")) {
            sb.append("and operate_permission_id in ( ");
            List<Long> operatePermissionIds = (List<Long>) map.get("operatePermissionIds");
            MessageFormat messageFormat = new MessageFormat("#'{'operatePermissionIds[{0}]}");
            for (int i = 0; i < operatePermissionIds.size(); i++) {
                sb.append(messageFormat.format(new Object[]{i}));
                if (i < operatePermissionIds.size() - 1)
                    sb.append(",");
            }
            sb.append(" )");
        }
        return sb.toString();
    }

}
