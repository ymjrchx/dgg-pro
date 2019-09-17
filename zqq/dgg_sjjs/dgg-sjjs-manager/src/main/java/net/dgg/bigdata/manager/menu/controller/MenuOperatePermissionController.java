package net.dgg.bigdata.manager.menu.controller;

import net.dgg.bigdata.manager.annotation.AuthOpt;
import net.dgg.bigdata.manager.common.util.LayuiTableResponse;
import net.dgg.bigdata.manager.menu.service.MenuService;
import net.dgg.bigdata.manager.permission.entity.OperatePermissionEntity;
import net.dgg.bigdata.manager.permission.service.OperatePermissionService;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "menu/operate")
public class MenuOperatePermissionController extends DggBaseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private OperatePermissionService operatePermissionService;

    @Autowired
    private SessionManager sessionManager;
    @RequestMapping(value = "index.html")
    public String index(){
        //获取菜单数据
        /*List listMenu = menuService.getMenusNodesByParent(null,null);
        List listMenuChild = menuService.getMenusNodesByParent(1l,null);
        listMenu.addAll(listMenuChild);
        //初始化zTree数据
        request.setAttribute("menuTree", JsonUtil.obj2Json(listMenu));*/
        return "permission/menu_operate";
    }
    /**
     * 与菜单有关联关系的权限列表
     * @param request
     * @return
     */
    @RequestMapping(value = "menu_operate")
    @ResponseBody
    public LayuiTableResponse getMenuOperate(HttpServletRequest request){
        List<OperatePermissionEntity> operatePermissionEntities = new ArrayList<>();
        String menuId = request.getParameter("menuId").toString();
        String operateName = "";
        //查询条件
        if(request.getParameterMap().containsKey("menuOperateName")){
            operateName =request.getParameter("menuOperateName").toString();
        }
        if(!StringUtils.isEmpty(menuId)){
            operatePermissionEntities = operatePermissionService.findOperatePermissionByMenuId(Long.valueOf(menuId),operateName,1);
        }
        return new LayuiTableResponse().data(operatePermissionEntities);
    }

    /**
     * 与菜单无关的权限列表
     * @param request
     * @return
     */
    @RequestMapping(value = "operate")
    @ResponseBody
   public LayuiTableResponse getOperate(HttpServletRequest request){
        List<OperatePermissionEntity> operatePermissionEntities = new ArrayList<>();
        String menuId = request.getParameter("menuId").toString();
        String operateName = "";
        //查询条件
        if(request.getParameterMap().containsKey("operateName")){
            operateName =request.getParameter("operateName").toString();
        }
        if(!StringUtils.isEmpty(menuId)){
            operatePermissionEntities = operatePermissionService.findOperatePermissionByMenuId(Long.valueOf(menuId),operateName,0);
        }
        return new LayuiTableResponse().data(operatePermissionEntities);
    }

    /**
     * 添加菜单权限
     * @param menuId
     * @param operateIds
     */
    @AuthOpt(code = "menupermission-bind")
    @RequestMapping(value = "save")
    @ResponseBody
    public DggRestResponse saveMenuPerms(@RequestParam String menuId, @RequestParam String operateIds){
        if(StringUtils.isEmpty(menuId)){
            return this.getFailResponse("请选择菜单");
        }
        if(StringUtils.isEmpty(operateIds)){
            return this.getFailResponse("请选择移除的权限");
        }

        Long userId = sessionManager.getCurrentSession().getValue("userId",Long.class);
        menuService.saveMenuOperatePerms(Long.valueOf(menuId),operateIds,userId);
        return this.getSuccessResponse("操作成功");
    }

    /**
     * 添加菜单所有权限
     * @param menuId
     */
    @AuthOpt(code = "menupermission-bind")
    @RequestMapping(value = "save_all")
    @ResponseBody
    public DggRestResponse saveMenuPermsAll(@RequestParam String menuId){
        if(StringUtils.isEmpty(menuId)){
            return this.getFailResponse("请选择菜单");
        }
        Long userId = sessionManager.getCurrentSession().getValue("userId",Long.class);
        menuService.saveMenuOperatePerms(Long.valueOf(menuId),null,userId);
        return this.getSuccessResponse("操作成功");

    }

    /**
     * 删除菜单权限
     * @param menuId
     * @param operateIds
     */
    @AuthOpt(code = "menupermission-unbind")
    @RequestMapping(value = "move")
    @ResponseBody
    public DggRestResponse moveMenuPerms(@RequestParam String menuId,@RequestParam String operateIds){
        if(StringUtils.isEmpty(menuId)){
            return this.getFailResponse("请选择菜单");
        }
        if(StringUtils.isEmpty(operateIds)){
            return this.getFailResponse("请选择移除的权限");
        }
        menuService.moveMenuOperatePerms(Long.valueOf(menuId),operateIds);
        return this.getSuccessResponse("操作成功");

    }

    /**
     * 删除菜单所有权限
     * @param menuId
     */
    @AuthOpt(code = "menupermission-unbind")
    @RequestMapping(value = "move_all")
    @ResponseBody
    public DggRestResponse moveMenuPermsAll(@RequestParam String menuId){
        if(StringUtils.isEmpty(menuId)){
            return this.getFailResponse("请选择菜单");
        }
        menuService.moveMenuOperatePerms(Long.valueOf(menuId),null);
        return this.getSuccessResponse("操作成功");
    }
}
