package net.dgg.bigdata.manager.role.dao;

import net.dgg.bigdata.manager.role.entity.RoleOperatePermission;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/7 15:45
 * @Description:
 */
public class RoleOperatePermissionDaoProvider {

    public String saveSql(Map map){
        List<RoleOperatePermission> roleOperatePermissions = (List<RoleOperatePermission>)map.get("roleOperatePermissions");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO role_operate_permission(id,role_id,operate_permission_id,creator_id,createtime) VALUES ");
        MessageFormat messageFormat = new MessageFormat("(#'{'roleOperatePermissions[{0}].id},#'{'roleOperatePermissions[{0}].roleId},#'{'roleOperatePermissions[{0}].operatePermissionId},#'{'roleOperatePermissions[{0}].creatorId},#'{'roleOperatePermissions[{0}].createtime})");
        for (int i = 0; i < roleOperatePermissions.size(); i++) {
            stringBuffer.append(messageFormat.format(new Object[]{i}));
            if (i < roleOperatePermissions.size() - 1)
                stringBuffer.append(",");
        }
        return stringBuffer.toString();
    }


    public String removeByRoleIdSql(Map map){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DELETE from role_operate_permission where role_id=#{roleId} ");
        if(map!=null && map.containsKey("operatePermissionIds")){
            List<Long> operatePermissionIds = (List<Long>)map.get("operatePermissionIds");
            if(!operatePermissionIds.isEmpty()){
                stringBuffer.append("and operate_permission_id in ( ");
                MessageFormat messageFormat = new MessageFormat("#'{'operatePermissionIds[{0}]}");
                for (int i = 0; i < operatePermissionIds.size(); i++) {
                    stringBuffer.append(messageFormat.format(new Object[]{i}));
                    if (i < operatePermissionIds.size() - 1)
                        stringBuffer.append(",");
                }
                stringBuffer.append(" )");
            }
        }
        return stringBuffer.toString();
    }
    public String removeByRoleIdMenuOpermsSql(Map map){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DELETE from role_operate_permission where role_id=#{roleId} ");
        if(map!=null && map.containsKey("operatePermissionIds")){
            List<Long> operatePermissionIds = (List<Long>)map.get("operatePermissionIds");
            if(!operatePermissionIds.isEmpty()){
                stringBuffer.append("and operate_permission_id not in ( ");
                MessageFormat messageFormat = new MessageFormat("#'{'operatePermissionIds[{0}]}");
                for (int i = 0; i < operatePermissionIds.size(); i++) {
                    stringBuffer.append(messageFormat.format(new Object[]{i}));
                    if (i < operatePermissionIds.size() - 1)
                        stringBuffer.append(",");
                }
                stringBuffer.append(" )");
            }
        }
        return stringBuffer.toString();
    }
}
