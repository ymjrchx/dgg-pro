package net.dgg.bigdata.manager.menu.dao;

import net.dgg.bigdata.manager.menu.entity.MenuClosure;
import net.dgg.bigdata.manager.menu.entity.MenuMain;
import net.dgg.bigdata.manager.menu.entity.MenuTree;
import net.dgg.bigdata.manager.menu.entity.dto.MenuNodeDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuDao {
    /**
     * 添加菜单
     *
     * @param menuMain
     */
    @Insert("INSERT INTO menu_main(menu_id,menu_name,menu_url,menu_code,sort_num,remark,icon,creator_id,createtime,updator_id,updatetime) " +
            "VALUES " +
            "(#{menuId},#{menuName},#{menuUrl},#{menuCode},#{sortNum},#{remark},#{icon},#{creatorId},#{createtime},#{updatorId},#{updatetime})")
    void addMenu(MenuMain menuMain);

    /**
     * 添加菜单关系
     *
     * @param menuClosures
     */
    @InsertProvider(type = MenuDaoProvider.class, method = "addMenuClosureSql")
    void addMenuClosure(@Param("menuClosures") List<MenuClosure> menuClosures);

    /**
     * 修改菜单
     *
     * @param menuMain
     */
    @Update("UPDATE menu_main SET menu_name = #{menuName},menu_url =#{menuUrl},menu_code=#{menuCode},sort_num=#{sortNum},remark=#{remark},icon=#{icon},updator_id= #{updatorId},updatetime=#{updatetime}where menu_id = #{menuId}")
    void updateMenu(MenuMain menuMain);

    /**
     * 获取所有菜单
     *
     * @return
     */
    @SelectProvider(type = MenuDaoProvider.class, method = "getMenusSql")
    List<MenuNodeDto> getMenus(@Param("ancestorId") Long ancestorId);

    @SelectProvider(type = MenuDaoProvider.class, method = "getMenuNodeIdsSql")
    List<Long> getMenuNodeIds(@Param("menuIds") List<Long> menuIds);

    /**
     * 根据分页条件返回menu
     *
     * @param searchMap
     * @return
     */
    @SelectProvider(type = MenuDaoProvider.class, method = "getMenuPageWithPageSql")
    List<MenuMain> getMenuPageWithPage(Map searchMap);

    /**
     * 获取菜单Dto
     *
     * @return
     */
    @Select("select  a.menu_id as menuId,a.menu_name as menuName,a.menu_url as menuUrl,a.menu_code as menuCode," +
            "        a.sort_num as sortNum,a.remark,a.icon,a.creator_id as creatorId,a.createtime," +
            "        a.updator_id as updatorId,a.updatetime" +
            "        from menu_main a where a.menu_id = #{menuId}")
    MenuNodeDto getMenu(@Param("menuId") Long menuId);

    /**
     * 删除菜单
     */
    @DeleteProvider(type = MenuDaoProvider.class, method = "deleteMenusSql")
    void deleteMenus(@Param("menuIds") List<Long> menuIds);

    @DeleteProvider(type = MenuDaoProvider.class, method = "deleteMenuClosuresSql")
    void deleteMenuClosures(@Param("menuIds") List<Long> menuIds);

    /**
     * 获取菜单
     *
     * @return
     */
    @Select("SELECT DISTINCT a.menu_id AS menuId, a.menu_name AS menuName, a.menu_url AS menuUrl," +
            " a.menu_code AS menu_code, b.ancestor_id AS pId, a.icon AS icon" +
            " FROM menu_main a" +
            " JOIN menu_closure b JOIN user_role c JOIN role e JOIN role_menu_permission d ON a.menu_id = b.menu_id" +
            " AND c.role_id = d.role_id AND c.role_id = e.role_id AND d.menu_id = a.menu_id" +
            " WHERE b.distance = 1 AND e.enable = 1 AND user_id = #{userId}" +
            " ORDER BY a.sort_num ASC")
    List<MenuTree> findMenuTree(long userId);

    /**
     * 判断是否有子节点
     */
    @Select(" select count(1)  from menu_main a,menu_closure b where a.menu_id = b.menu_id and b.distance=1 and b.ancestor_id = #{menuId}")
    int existNodeChild(@Param("menuId") Long menuId);

    /**
     * 判断menu_code的唯一性
     *
     * @param menuCode
     * @param menuId
     * @return
     */
    @SelectProvider(type = MenuDaoProvider.class,method = "existMenuCodeSql")
    int existMenuCode(@Param("menuCode") String menuCode, @Param("menuId") Long menuId);

    /**
     * 查找所有菜单的URL
     *
     * @return
     */
    @Select("select menu_url from menu_main")
    List<String> findAllUrls();
}
