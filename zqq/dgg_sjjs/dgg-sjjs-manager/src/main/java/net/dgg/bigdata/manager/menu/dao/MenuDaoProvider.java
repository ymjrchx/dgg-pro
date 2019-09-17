package net.dgg.bigdata.manager.menu.dao;

import net.dgg.bigdata.manager.menu.entity.MenuClosure;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/6 15:41
 * @Description:
 */
public class MenuDaoProvider {
    public String addMenuClosureSql(Map map) {
        List<MenuClosure> menuClosures = (List<MenuClosure>) map.get("menuClosures");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO menu_closure(id,menu_id,ancestor_id,distance,creator_id,createtime) VALUES");
        MessageFormat mf = new MessageFormat("(#'{'menuClosures[{0}].id}, #'{'menuClosures[{0}].menuId}, #'{'menuClosures[{0}].ancestorId}," +
                " #'{'menuClosures[{0}].distance}, #'{'menuClosures[{0}].creatorId}, #'{'menuClosures[{0}].createtime})");
        for (int i = 0; i < menuClosures.size(); i++) {
            stringBuffer.append(mf.format(new Object[]{i}));
            if (i < menuClosures.size() - 1) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    public String getMenusSql(Map map) {
        Long ancestorId = (Long) map.get("ancestorId");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select" +
                "         a.menu_id as menuId,a.menu_name as menuName,a.menu_url as menuUrl,a.menu_code as menuCode," +
                "         a.sort_num as sortNum,a.remark,a.icon,a.creator_id as creatorId,a.createtime," +
                "         a.updator_id as updatorId,a.updatetime,b.ancestor_id as ancestorId" +
                "         from menu_closure b ,menu_main a" +
                "         where a.menu_id = b.menu_id");
        if (ancestorId == null) {
            stringBuffer.append(" and b.distance=1 and b.ancestor_id = #{ancestorId}");
        } else {
            stringBuffer.append(" and b.distance=1 and b.ancestor_id = #{ancestorId}");
        }
        stringBuffer.append(" order by a.sort_num asc");
        return stringBuffer.toString();
    }

    public String getMenuNodeIdsSql(Map map) {
        List<Long> menuIds = (List<Long>) map.get("menuIds");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" select" +
                " b.menu_id" +
                " from menu_closure b" +
                " where b.ancestor_id in (");
        MessageFormat mf = new MessageFormat("#'{'menuIds[{0}]}");
        for (int i = 0; i < menuIds.size(); i++) {
            stringBuffer.append(mf.format(new Object[]{i}));
            if (i < menuIds.size() - 1) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    public String getMenuPageWithPageSql(Map searchMap) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" select" +
                "        a.menu_id as menuId,a.menu_name as menuName,a.menu_url as menuUrl,a.menu_code as menuCode," +
                "        a.sort_num as sortNum,a.remark,a.icon,a.creator_id as creatorId,a.createtime," +
                "        a.updator_id as updatorId,a.updatetime" +
                "        from menu_main a,menu_closure b" +
                "        where a.menu_id = b.menu_id and b.distance=1 and a.menu_id !=1");
        if (searchMap.containsKey("menuId") ) {
            stringBuffer.append(" and b.ancestor_id = #{menuId}");
        }
        if (searchMap.containsKey("menuName") ) {
            stringBuffer.append(" and a.menu_name LIKE CONCAT('%',CONCAT(#{menuName},'%'))");
        }
        if (searchMap.containsKey("menuUrl") ) {
            stringBuffer.append(" and a.menu_url LIKE CONCAT('%',CONCAT(#{menuUrl},'%'))");
        }
        stringBuffer.append(" order by a.sort_num asc");
        return stringBuffer.toString();
    }

    public String deleteMenusSql(Map map) {
        List<Long> menuIds = (List<Long>) map.get("menuIds");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DELETE FROM menu_main where menu_id in (");
        MessageFormat mf = new MessageFormat("#'{'menuIds[{0}]}");
        for (int i = 0; i < menuIds.size(); i++) {
            stringBuffer.append(mf.format(new Object[]{i}));
            if (i < menuIds.size() - 1) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    public String deleteMenuClosuresSql(Map map) {
        List<Long> menuIds = (List<Long>) map.get("menuIds");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DELETE FROM menu_closure where (menu_id in (");
        MessageFormat mf = new MessageFormat("#'{'menuIds[{0}]}");
        for (int i = 0; i < menuIds.size(); i++) {
            stringBuffer.append(mf.format(new Object[]{i}));
            if (i < menuIds.size() - 1) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append(")");
        stringBuffer.append("OR ancestor_id in (");
        for (int i = 0; i < menuIds.size(); i++) {
            stringBuffer.append(mf.format(new Object[]{i}));
            if (i < menuIds.size() - 1) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append(")");
        stringBuffer.append(" )");
        return stringBuffer.toString();
    }

    public String existMenuCodeSql(Map map) {
        //String menuCode = (String) map.get("menuCode");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select count(1) from menu_main where menu_code = #{menuCode}");
        if (map != null && map.containsKey("menuId")) {
            //Long menuId = (Long) map.get("menuId");
            stringBuffer.append("and menu_id != #{menuId}");
        }
        return stringBuffer.toString();

    }

    public static void main(String[] a) {

    }
}
