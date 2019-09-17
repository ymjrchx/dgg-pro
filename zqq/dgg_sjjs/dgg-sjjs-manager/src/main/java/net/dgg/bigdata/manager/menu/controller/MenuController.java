package net.dgg.bigdata.manager.menu.controller;

import net.dgg.bigdata.manager.annotation.AuthOpt;
import net.dgg.bigdata.manager.common.util.LayuiTableResponse;
import net.dgg.bigdata.manager.menu.entity.dto.MenuNodeDto;
import net.dgg.bigdata.manager.menu.entity.MenuMain;
import net.dgg.bigdata.manager.menu.service.MenuService;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import net.dgg.core.utils.common.DggKeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 菜单请求web接口
 */
@Controller
@RequestMapping(value = "menu")
public class MenuController extends DggBaseController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private SessionManager sessionManager;//用来获取当前登录用户id

    /**
     * menu首页
     * @return
     */
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        //初始化zTree数据
        /*List listMenu = menuService.getMenusNodesByParent(null,null);
        List listMenuChild = menuService.getMenusNodesByParent(1l,null);
        listMenu.addAll(listMenuChild);
        request.setAttribute("menuTree", JsonUtil.obj2Json(listMenu));*/
        request.setAttribute("sortNum",1);
        request.setAttribute("menuId",1);
        return "menu/menu";
    }

    /**
     * 添加菜单
     */
    @AuthOpt(code = "menu-add")
    @RequestMapping(value = "add_menu",method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse<String> addMenu(MenuMain menuMain, String ancestorIds){
        // 判断ancestorIds 是否为空,如果为空则菜单没有选择
        if(StringUtils.isEmpty(ancestorIds)){
            return this.getFailResponse("请选择上级菜单");
        }
        String[] ids = ancestorIds.split(",");
        if(ids==null || ids.length<1){
            return this.getFailResponse("请选择上级菜单");
        }
        if(ids.length>4){
            return this.getFailResponse("不能为该菜单添加子菜单");
        }
        String flag = checkParam(menuMain);
        if(!flag.equals("pass")){
            return this.getFailResponse(flag);
        }
        try{
            List<Long> lIds = new ArrayList<>();
            //切换id顺序
            for(int i=0 ; i< ids.length ; i++){
                lIds.add(Long.valueOf(ids[ids.length-1-i]));
            }
            // 从session里面获取userid
            Long userId = sessionManager.getCurrentSession().getValue("userId",Long.class);
            //组装menu数据
            Date createDate = new Date();
            Long menuId = DggKeyWorker.nextId();
            menuMain.setMenuId(menuId);
            menuMain.setCreatorId(userId);
            menuMain.setUpdatetime(createDate);
            menuMain.setUpdatorId(userId);
            menuMain.setCreatetime(createDate);
            String result = menuService.addMenu(menuMain, lIds);
            if(!result.equals("success")){
                return this.getFailResponse(result);
            }else{
                menuService.clearMenuUrls();
            }
        }catch(Exception e){
            logger.error("添加菜单数据异常", e);
            return this.getFailResponse("添加菜单数据异常");
        }
        return this.getSuccessResponse("菜单添加成功");

    }

    /**
     * 获取菜单信息
     * @param menuId
     * @return
     */
    @RequestMapping(value = "menu_info")
    @ResponseBody
    public DggRestResponse<MenuNodeDto> getMenuInfo(@RequestParam String menuId){
        return this.getSuccessResponse(menuService.getMenu(Long.valueOf(menuId)));
    }

    /**
     * 菜单树异步加载子菜单
     */
    @RequestMapping(value = "menu_next_node")
    @ResponseBody
    public DggRestResponse<List> getNextNode(String menuId){
        List listMenuNode = new ArrayList();
        if(menuId.isEmpty()){
            listMenuNode =  menuService.getMenusNodesByParent(null,null);
            listMenuNode.addAll(menuService.getMenusNodesByParent(1l,null));
        }else {
            listMenuNode = menuService.getMenusNodesByParent(Long.valueOf(menuId),null);
        }
        return this.getSuccessResponse(listMenuNode);
    }
    /**
     * 修改菜单
     * @param menuMain 修改的内容
     * @return
     */
    @AuthOpt(code = "menu-modify")
    @RequestMapping(value = "update_menu")
    @ResponseBody
    public DggRestResponse<String> updateMenu(MenuMain menuMain){
        String flag = checkParam(menuMain);
        if(!flag.equals("pass")){
            return this.getFailResponse(flag);
        }
        try {
            //组装menu数据
            Date createDate = new Date();
            menuMain.setUpdatorId(sessionManager.getCurrentSession().getValue("userId",Long.class));
            menuMain.setUpdatetime(createDate);
            String result = menuService.updateMenu(menuMain);
            if(!result.equals("success")){
                return this.getFailResponse(result);
            }else{
                menuService.clearMenuUrls();
            }
        }catch (Exception e){
            logger.error("更新菜单数据异常", e);
            return this.getFailResponse("更新菜单数据异常");
        }
        return this.getSuccessResponse("菜单更新成功");
    }
    /**
     * 菜单表格数据加载
     * @return
     */
    @RequestMapping(value = "menu_page")
    @ResponseBody
    public LayuiTableResponse getMenuPage(@RequestParam Map<String, Object> params){
        // 根据页码和每页数据来获取数据
        List<MenuMain> menus = menuService.getMenuPageWithPage(params);
        //获取所有的菜单总数
        return  new LayuiTableResponse().data(params.get("count"),menus);
    }
    /**
     * 删除选择的菜单
     * @param menuIds 菜单的id集合
     * @return
     */
    @AuthOpt(code = "menu-del")
    @RequestMapping(value = "delete_menu")
    @ResponseBody
    public DggRestResponse<String> deleteMenu(String menuIds){
        if(StringUtils.isEmpty(menuIds)){
            return this.getFailResponse("请选择需要删除的菜单");
        }
        try{
            String[] menuIdsAraay = menuIds.split(",");
            List<Long> menuIdList = new ArrayList<>();
            for(String menuId : menuIdsAraay){
                menuIdList.add(Long.valueOf(menuId));
            }
            menuService.deleteMenus(menuIdList);
            menuService.clearMenuUrls();
        }catch (Exception e){
            logger.error("删除菜单数据异常", e);
            return this.getFailResponse("删除菜单数据异常");
        }

        return this.getSuccessResponse("删除成功");
    }
    /**
     * 校验字段
     * @param menuMain
     * @return
     */
   private String checkParam(MenuMain menuMain){
       if(StringUtils.isEmpty(menuMain.getMenuName()))
           return "菜单名称不能为空";
       if(menuMain.getMenuName().trim().length()>8)
           return "菜单名称最大限制8，输入超过限制，请重新输入";
       if(StringUtils.isEmpty(menuMain.getSortNum()))
           return "菜单排序不能为空";
       if(menuMain.getSortNum().intValue()>1000 || menuMain.getSortNum().intValue()<1)
           return "菜单排序为1到1000以内";
       if(StringUtils.isEmpty(menuMain.getMenuCode()))
           return "菜单编号不能为空";
       if(menuMain.getMenuCode().trim().length()>100)
           return "菜单编号最大限制100，输入超过限制，请重新输入";
       if(menuMain.getMenuUrl().trim().length()>300)
           return "菜单地址最大限制300，输入超过限制，请重新输入";
       if(menuMain.getIcon().trim().length()>100)
           return "菜单图标最大限制100，输入超过限制，请重新输入";
       if(menuMain.getRemark().trim().length()>200)
           return "菜单备注最大限制200，输入超过限制，请重新输入";
       return "pass";
   }
}
