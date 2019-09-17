package net.dgg.tmd.foundation.platform.menu.dao;

import net.dgg.tmd.foundation.platform.menu.entity.MenuTree;
import net.dgg.tmd.foundation.platform.menu.entity.dto.MenuNodeDto;
import net.dgg.tmd.foundation.platform.menu.entity.MenuClosure;
import net.dgg.tmd.foundation.platform.menu.entity.MenuMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuDao {
    /**
     * 添加菜单
     * @param menuMain
     */
    void addMenu(MenuMain menuMain);

    /**
     * 添加菜单关系
     * @param menuClosures
     */
    void addMenuClosure(@Param("menuClosures")List<MenuClosure> menuClosures);

    /**
     * 修改菜单
     * @param menuMain
     */
    void updateMenu(MenuMain menuMain);
    /**
     * 获取所有菜单
     * @return
     */
    List<MenuNodeDto> getMenus(@Param("ancestorId") Long ancestorId);

    List<Long> getMenuNodeIds(@Param("menuIds")List<Long> menuIds);
    /**
     * 根据分页条件返回menu
     * @param searchMap
     * @return
     */
    List<MenuMain> getMenuPageWithPage(Map searchMap);
    /**
     * 获取菜单Dto
     * @return
     */
    MenuNodeDto getMenu(@Param("menuId")Long menuId);

    /**
     * 删除菜单
     */
    void deleteMenus(@Param("menuIds")List<Long> menuIds);
    void deleteMenuClosures(@Param("menuIds")List<Long> menuIds);

    /**
     * 获取菜单
     * @return
     */
    List<MenuTree> findMenuTree(long userId);

    /**
     * 判断是否有子节点
     */
    int existNodeChild(@Param("menuId")Long menuId);

    /**
     * 判断menu_code的唯一性
     * @param menuCode
     * @param menuId
     * @return
     */
    int existMenuCode(@Param("menuCode") String menuCode,@Param("menuId") Long menuId);

    /**
     * 查找所有菜单的URL
     * @return
     */
    List<String> findAllUrls();
}
