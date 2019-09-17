package net.dgg.bigdata.manager.menu.service;

import net.dgg.bigdata.manager.common.filter.MenuUrlsCache;
import net.dgg.bigdata.manager.menu.dao.MenuDao;
import net.dgg.bigdata.manager.menu.dao.MenuOperatePermissionDao;
import net.dgg.bigdata.manager.menu.entity.MenuClosure;
import net.dgg.bigdata.manager.menu.entity.MenuMain;
import net.dgg.bigdata.manager.menu.entity.MenuOperatePermission;
import net.dgg.bigdata.manager.menu.entity.MenuTree;
import net.dgg.bigdata.manager.menu.entity.dto.MenuNodeDto;
import net.dgg.bigdata.manager.permission.dao.OperatePermissionDAO;
import net.dgg.bigdata.manager.permission.entity.OperatePermissionEntity;
import net.dgg.bigdata.manager.role.dao.RoleMenuDao;
import net.dgg.core.utils.common.DggKeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private MenuOperatePermissionDao menuOperatePermissionDao;

    @Autowired
    private OperatePermissionDAO operatePermissionDAO;

    @Autowired
    private MenuUrlsCache menuUrlsCache;
    /**
     * 新增菜单
     *
     * @param menuMain    增加的对象
     * @param ancestorIds 添加的关系
     * @return
     */
    public String addMenu(MenuMain menuMain, List<Long> ancestorIds) {
        if(existMenuCode(menuMain.getMenuCode(),null)>0)
            return "菜单编号已存在";
        //添加菜单
        menuDao.addMenu(menuMain);
        //将自身加入菜单节点关系
        ancestorIds.add(menuMain.getMenuId());
        //组装menuClosure 数据
        List<MenuClosure> menuClosureList = new ArrayList<>();
        for (int i = 0; i < ancestorIds.size(); i++) {
            Long ancestorId = ancestorIds.get(i);
            MenuClosure menuClosure = new MenuClosure();
            menuClosure.setId(DggKeyWorker.nextId());
            menuClosure.setMenuId(menuMain.getMenuId());
            menuClosure.setAncestorId(ancestorId);
            menuClosure.setCreatetime(menuMain.getCreatetime());
            menuClosure.setDistance(ancestorIds.size() - 1 - i);
            menuClosure.setCreatorId(menuMain.getCreatorId());
            menuClosureList.add(menuClosure);
        }
        //添加菜单关系
        menuDao.addMenuClosure(menuClosureList);
        return "success";
    }

    /**
     * 修改菜单
     *
     * @param menuMain 增加的对象
     * @return
     */
    public String updateMenu(MenuMain menuMain) {
        if(existMenuCode(menuMain.getMenuCode(),menuMain.getMenuId())>0)
            return "菜单编号已存在";
        menuDao.updateMenu(menuMain);
        return "success";
    }

    /**
     * 清楚菜单url的缓存
     */
    public void clearMenuUrls(){
        menuUrlsCache.clear("menuUrls");
    }
    /**
     * 获取所有菜单
     *
     * @return
     */
    public List<MenuNodeDto> getMenus(Long ancestorId) {
        return menuDao.getMenus(ancestorId);
    }

    /**
     * 获取菜单节点
     * @return
     */
    public List getMenusNodesByParent(Long menuId,Long roleId){
        List<Map> listMenu = new ArrayList<>();
        List<MenuNodeDto> menus = menuDao.getMenus(menuId);
        for(MenuNodeDto menuNodeDto : menus){
            Map<String,Object> map = new HashMap<>();
            map.put("id",String.valueOf(menuNodeDto.getMenuId()));
            map.put("pId",String.valueOf(menuNodeDto.getAncestorId()));
            map.put("name", menuNodeDto.getMenuName());
            map.put("open",true);
            if(menuDao.existNodeChild(menuNodeDto.getMenuId())>0){
                map.put("isParent",true);
            }
            if(roleId!=null){
                if (roleMenuDao.findByRoleMenu(roleId, menuNodeDto.getMenuId()) == 1) {
                    map.put("checked", true);
                }
            }
            listMenu.add(map);
        }
        return listMenu;
    }
    /**
     * 获取菜单下的子菜单列表
     * @param searchMap
     * @return
     */
    public List<MenuMain> getMenuPageWithPage(Map<String, Object> searchMap) {
        return menuDao.getMenuPageWithPage(searchMap);
    }

    /**
     * 获取菜单详细信息
     * @return
     */
    public MenuNodeDto getMenu(long menuId) {
        return menuDao.getMenu(menuId);
    }

    /**
     * 删除菜单和菜单节点关系
     */
    public void deleteMenus(List<Long> menuIds) {
        //需要找到删除节点的所有子节点
        List<Long> deleteIds = menuDao.getMenuNodeIds(menuIds);
        //删除菜单上下级关系
        menuDao.deleteMenuClosures(deleteIds);
        //删除菜单
        menuDao.deleteMenus(deleteIds);
        //删除菜单角色绑定关系
        roleMenuDao.removeByMenuId(deleteIds);
    }

    /**
     * 查询菜单成为一棵树
     *
     * @param userId 用户ID
     * @param pId 上级菜单ID
     * @return
     */
    public List<MenuTree> findMenuTree(long userId, long pId) {
        List<MenuTree> menuTrees = menuDao.findMenuTree(userId);
        return makeMenuTree(menuTrees, pId, true);
    }

    /**
     * 用户登录首页后，在自己权限下看到的菜单
     * @param menuTrees
     * @param pId
     * @param containChild
     * @return
     */
    private List<MenuTree> makeMenuTree(List<MenuTree> menuTrees, long pId, boolean containChild) {
        List<MenuTree> trees = new ArrayList<>();
        for (MenuTree menu : menuTrees) {
            if (menu.getpId() == pId && menu.getMenuId() != pId) {
                if (containChild) {
                    menu.setChildren(makeMenuTree(menuTrees, menu.getMenuId(), containChild));
                }
                trees.add(menu);
            }
        }
        return trees;
    }

    /**
     * 添加菜单的操作权限
     */

    public void saveMenuOperatePerms(Long menuId, String operateIds, Long userId) {
        List<MenuOperatePermission> menuOperatePermissions = new ArrayList<>();
        MenuNodeDto menu = menuDao.getMenu(menuId);
        //判断menuId是否存在,存在则进行下面操作
        if (menu != null && menu.getMenuId() == menuId) {
            //根据不同的条件来构造数据
            Date date = new Date();
            if (StringUtils.isEmpty(operateIds)) {
                //构造saveAll的数据
                List<OperatePermissionEntity> operatePermissionEntities
                        = operatePermissionDAO.findOperatePermissionByMenuId(menuId, null, 0);
                for (OperatePermissionEntity operatePermissionEntity : operatePermissionEntities) {
                    MenuOperatePermission menuOperatePermission = new MenuOperatePermission();
                    menuOperatePermission.setId(DggKeyWorker.nextId());
                    menuOperatePermission.setMenuId(menuId);
                    menuOperatePermission.setOperatePermissionId(operatePermissionEntity.getOperatePermissionId());
                    menuOperatePermission.setCreatorId(userId);
                    menuOperatePermission.setCreatetime(date);
                    menuOperatePermissions.add(menuOperatePermission);
                }
            } else {
                String[] operateIdsArray = operateIds.split(",");
                for (String operateId : operateIdsArray) {
                    MenuOperatePermission menuOperatePermission = new MenuOperatePermission();
                    menuOperatePermission.setId(DggKeyWorker.nextId());
                    menuOperatePermission.setMenuId(menuId);
                    menuOperatePermission.setOperatePermissionId(Long.valueOf(operateId));
                    menuOperatePermission.setCreatorId(userId);
                    menuOperatePermission.setCreatetime(date);
                    menuOperatePermissions.add(menuOperatePermission);
                }
            }
        }
        if (menuOperatePermissions.size() > 0) {
            menuOperatePermissionDao.save(menuOperatePermissions);
        }
    }

    /**
     * 删除菜单的操作权限
     */
    public void moveMenuOperatePerms(Long menuId, String operateIds) {
        List<Long> operateIdList = new ArrayList<>();
        if (StringUtils.isEmpty(operateIds)) {
            menuOperatePermissionDao.removeByMenuId(null, menuId);
        } else {
            String[] operateIdsArray = operateIds.split(",");
            for (String operateId : operateIdsArray) {
                operateIdList.add(Long.valueOf(operateId));
            }
            menuOperatePermissionDao.removeByMenuId(operateIdList, menuId);
        }
    }

    /**
     * 判断菜单编码是否存在
     *
     * @param menuCode
     * @return
     * @Param menuId NULL:添加操作时   不为空：更新操作
     */
    public int existMenuCode(String menuCode, Long menuId) {
        return menuDao.existMenuCode(menuCode, menuId);
    }

    /**
     * 获取所有菜单的url地址
     * @return
     */
    public Set<String> findAllUrls() {
        List<String> urls = menuDao.findAllUrls();
        Set<String> urlSet = new HashSet<>();
        for (String url : urls) {
            urlSet.add(url);
        }
        return urlSet;
    }
}
