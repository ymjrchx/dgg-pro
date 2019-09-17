package net.dgg.tmd.foundation.platform.role.controller;

import net.dgg.tmd.foundation.platform.annotation.AuthOpt;
import net.dgg.tmd.foundation.platform.common.util.CommonStringUtils;
import net.dgg.tmd.foundation.platform.common.util.LayuiTableResponse;
import net.dgg.tmd.foundation.platform.menu.service.MenuService;
import net.dgg.tmd.foundation.platform.permission.service.OperatePermissionService;
import net.dgg.tmd.foundation.platform.role.entity.Role;
import net.dgg.tmd.foundation.platform.role.entity.UserRoleDTO;
import net.dgg.tmd.foundation.platform.role.service.RoleService;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.fblock.core.common.KeyWorker;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 角色管理Controller类
 */
@Controller
@RequestMapping(value = "role/role_manager")
public class RoleController extends BaseController {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OperatePermissionService operatePermissionService;

    @RequestMapping(value = "index")
    public String index(String roleId,String roleName,HttpServletRequest request) {
        if (roleName!=null && !roleName.isEmpty()) {
            request.setAttribute("roleNameSearch", roleName);
        }
        //获取选中角色
        if (roleId !=null && !roleId.isEmpty()) {
            request.setAttribute("roleId", roleId);
            request.setAttribute("roleForm", roleService.queryRoleById(Long.valueOf(roleId)));
        }
        return "permission/role";
    }


    /**
     * 添加角色
     * @return
     */
    @AuthOpt(code = "rolemanage-create")
    @RequestMapping(value = "add_role")
    @ResponseBody
    public RestResponse<String> addRole(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getParameter("roleName").toString())) {
            return this.getFailResponse("角色名称不能为空");
        }
        if(request.getParameter("roleName").toString().trim().length()>20){
            return this.getFailResponse("角色名称最大20，输入超过限制，请重新输入");
        }
        if(request.getParameter("remark").toString().trim().length()>200){
            return this.getFailResponse("角色备注最大200，输入超过限制，请重新输入");
        }
        try {
            //组装角色
            Role role = new Role();
            Date date = new Date();
            Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
            role.setRoleName(request.getParameter("roleName").toString());
            role.setRemark(request.getParameter("remark").toString());
            role.setCreatorId(userId);
            role.setUpdatorId(userId);
            role.setCreatetime(date);
            role.setUpdatetime(date);
            role.setEnable(true);
            long roleId = KeyWorker.nextId();
            role.setRoleId(roleId);
            String nodeIds = request.getParameter("nodeIds").toString();
            String operatePermission = request.getParameter("operatePermission").toString();
            String result = roleService.addRole(role,
                    CommonStringUtils.splitIdToSet(nodeIds), CommonStringUtils.splitIdToSet(operatePermission));
            if(!result.equals("success"))
                return this.getFailResponse(result);
        } catch (Exception e) {
            logger.error("添加角色数据异常", e);
            return this.getFailResponse("添加角色数据异常");
        }
        return this.getSuccessResponse("添加成功！");
    }

    /**
     * 修改角色
     *
     * @param request
     * @return
     */
    @AuthOpt(code = "rolemanage-modify")
    @RequestMapping(value = "update_role")
    @ResponseBody
    public RestResponse<String> updateRole(HttpServletRequest request) {
        String roleName = request.getParameter("roleName").toString();
        if (StringUtils.isEmpty(roleName)) {
            return this.getFailResponse("角色名称不能为空");
        }
        if(request.getParameter("roleName").toString().trim().length()>100){
            return this.getFailResponse("角色名称最大100，输入超过限制，请重新输入");
        }
        if(request.getParameter("remark").toString().trim().length()>200){
            return this.getFailResponse("角色备注最大200，输入超过限制，请重新输入");
        }
        Long roleId = Long.valueOf(request.getParameter("roleId").toString());
        try {
            Role updateRole = roleService.queryRoleById(roleId);
            Date date = new Date();
            Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
            updateRole.setRoleName(roleName);
            updateRole.setRemark(request.getParameter("remark").toString());
            updateRole.setUpdatorId(userId);
            updateRole.setUpdatetime(date);
            String nodeIds = request.getParameter("nodeIds").toString();
            String operatePermissionIds = request.getParameter("operatePermission").toString();
            String result = roleService.updateRole(updateRole, CommonStringUtils.splitIdToSet(nodeIds), CommonStringUtils.splitIdToSet(operatePermissionIds));
            if(!result.equals("success"))
                return this.getFailResponse(result);
        } catch (Exception e) {
            logger.error("更新角色数据异常", e);
            return this.getFailResponse("更新角色数据异常");
        }
        return this.getSuccessResponse("修改成功！");
    }

    /**
     * 保存角色权限
     *
     * @return
     */
    @AuthOpt(code = "rolemanage-menuopt")
    @RequestMapping(value = "save_rolepms")
    @ResponseBody
    public RestResponse saveRolePms(String roleId,String menuId,String operatePermission) {
        if (StringUtils.isEmpty(roleId)) {
            return this.getFailResponse("请选择角色");
        }
        if (StringUtils.isEmpty(menuId)) {
            return this.getFailResponse("请选择菜单");
        }
        try {
            Role updateRole = roleService.queryRoleById(Long.valueOf(roleId));
            Date date = new Date();
            Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
            updateRole.setUpdatorId(userId);
            updateRole.setUpdatetime(date);
            roleService.saveRolePerms(updateRole, Long.valueOf(menuId), CommonStringUtils.splitIdToSet(operatePermission));
        } catch (Exception e) {
            logger.error("保存菜单权限数据异常",e);
            return this.getFailResponse("保存菜单权限数据异常");
        }
        return this.getSuccessResponse("保存成功");
    }

    /**
     * 启动和禁用功能
     *
     * @param roleId
     * @return
     */
    @AuthOpt(code = "rolemanage-enable")
    @RequestMapping(value = "update_role_enable")
    @ResponseBody
    public RestResponse<String> updateRoleEnable(@RequestParam String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            return this.getFailResponse("请选择角色");
        }
        String msg = "禁用";
        try {
            //获取角色信息
            Role role = roleService.queryRoleById(Long.valueOf(roleId));
            int enable = 0;
            if (!role.isEnable()) {
                enable = 1;
                msg = "启用";
            }
            roleService.updateRoleEnable(role.getRoleId(), enable);
        } catch (Exception e) {
            logger.error(msg + "角色异常", e);
            return this.getFailResponse(msg + "角色异常");
        }
        return this.getSuccessResponse(msg + "成功");
    }

    @RequestMapping(value = "role_search")
    @ResponseBody
    public RestResponse searchRole(@RequestParam String roleName) {
        Map<String, Object> search = new HashMap<>();
        List<Role> roles = new ArrayList<>();
        if (!StringUtils.isEmpty(roleName)) {
            search.put("roleName", roleName);
            roles = roleService.queryRoles(search);
        } else {
            roles = roleService.queryRoles(null);
        }
        return this.getSuccessResponse(roles);
    }

    /**
     * 菜单树异步加载子菜单
     */
    @RequestMapping(value = "menu_next_node")
    @ResponseBody
    public RestResponse<List> getNextNode( String menuId, String roleId) {
        List listMenuNode = new ArrayList();
        if(roleId.isEmpty()){
            listMenuNode = getMenuNodes(menuId,roleId);
        }else {
            listMenuNode = getMenuNodes(menuId,roleId);
        }
        return this.getSuccessResponse(listMenuNode);
    }

    /**
     * 获取子集菜单私有方法
     * @param menuId
     * @param roleId
     * @return
     */
    private List getMenuNodes(String menuId ,String roleId){
        List listMenuNode = new ArrayList();
        Long roleIdl = roleId.isEmpty()?null:Long.valueOf(roleId);
        if(menuId.isEmpty()){
            listMenuNode = menuService.getMenusNodesByParent(null,roleIdl);
            listMenuNode.addAll(menuService.getMenusNodesByParent(1l,roleIdl));
        }else{
            listMenuNode = menuService.getMenusNodesByParent(Long.valueOf(menuId),roleIdl);
        }
        return listMenuNode;
    }
    /**
     * 与菜单有关联关系的权限列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "menu_operate")
    @ResponseBody
   public LayuiTableResponse getMenuOperate(HttpServletRequest request) {
        List<Map> operatePermissionEntities = new ArrayList<>();
        String menuId = request.getParameter("menuId").toString();
        Long roleId = null;
        //查询条件
        if (request.getParameterMap().containsKey("roleId")) {
            roleId = Long.valueOf(request.getParameter("roleId").toString());
        }
        if (!StringUtils.isEmpty(menuId)) {
            operatePermissionEntities = operatePermissionService.findMenuOps(roleId, Long.valueOf(menuId));
            //
            if (operatePermissionEntities.size() > 0) {
                for (Map operatePermissionEntitie : operatePermissionEntities) {
                    if (operatePermissionEntitie.get("LAY_CHECKED").toString().equals("0")) {
                        operatePermissionEntitie.remove("LAY_CHECKED");
                    }
                }
            }
        }
        return new LayuiTableResponse().data(operatePermissionEntities);
    }

    /**
     * 独立权限
     */
    @RequestMapping(value = "operate_permission_search")
    @ResponseBody
    public RestResponse operatePermissionSearch(String roleId,String operatePermissionName) {
        Long roleIdl = null;
        if (!StringUtils.isEmpty(roleId)) {
            roleIdl = Long.valueOf(roleId);
        }
        List<Map> roleOperatePermissions = operatePermissionService.findOperatePermissionByMenuRole(roleIdl, operatePermissionName);
        return this.getSuccessResponse(roleOperatePermissions);
    }

    /**
     * 显示选定人员已有的角色
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "showownedroles")
    @ResponseBody
    public RestResponse showRolesForUser(@RequestParam(name = "id") long id) {
        List<Map> allRoleList = new ArrayList<>();
        List<Map> ownedRoleList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        if (!roleService.findAllRoleWithoutExist(id).isEmpty()) {
            for (Role role : roleService.findAllRoleWithoutExist(id)) {
                Map<String, Object> map = new HashMap<>();
                map.put("roleId", role.getRoleId());
                map.put("roleName", roleService.findRoleNameByRoleId(role.getRoleId()));
                allRoleList.add(map);
            }
            resultMap.put("allRole", allRoleList);
        } else {
            resultMap.put("allRole", null);
        }
        if (!roleService.findUserRoleDTOByUserId(id).isEmpty()) {
            for (UserRoleDTO userRoleDTO : roleService.findUserRoleDTOByUserId(id)) {
                Map<String, Object> map = new HashMap<>();
                map.put("roleId", userRoleDTO.getRoleId());
                map.put("roleName", roleService.findRoleNameByRoleId(userRoleDTO.getRoleId()));
                ownedRoleList.add(map);
            }
            resultMap.put("ownedRole", ownedRoleList);
        } else {
            resultMap.put("ownedRole", null);
        }
        return this.getSuccessResponse(resultMap);
    }

    /**
     * 保存选定人员编辑后的角色
     *
     * @param roleIds
     * @return
     */
    @AuthOpt(code = "usermanager-managerole")
    @RequestMapping(value = "editroles")
    @ResponseBody
    public RestResponse editRolesForUser(@RequestParam long id, @RequestParam String roleIds) {
        if ("".equals(roleIds) || null == roleIds) {
            roleService.delAllRoleByUserId(id);
        } else {
            List<String> newRoleIdList = new ArrayList<>();//新列表
            List<String> oldRoleIdList = new ArrayList<>();
            String[] roleIdsArr = roleIds.split(",");
            for (String s : roleIdsArr) {
                newRoleIdList.add(s);
            }

            if (null != roleService.findUserRoleDTOByUserId(id)) {
                List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
                userRoleDTOList = roleService.findUserRoleDTOByUserId(id);
                for (UserRoleDTO userRoleDTO : userRoleDTOList) {
                    oldRoleIdList.add(String.valueOf(userRoleDTO.getRoleId()));//老列表
                }
            }
            for (String item : newRoleIdList) {
                if (!oldRoleIdList.contains(item)) {
                    Long creatorId = sessionManager.getCurrentSession().getValue("userId", Long.class);
                    roleService.saveUserRole(KeyWorker.nextId(),id,Long.parseLong(item),creatorId,new Date());
                }
            }
            for (String oldItem : oldRoleIdList) {
                if (!newRoleIdList.contains(oldItem)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", id);
                    map.put("roleId", oldItem);
                    roleService.delRoleWithUserNameAndRoleId(map);
                }
            }
        }
        return this.getSuccessResponse(null);
    }

    /**
     * 批量增加权限
     * @param ids
     * @param roleIds
     * @return
     */
    @AuthOpt(code = "usermanager-addrole")
    @RequestMapping(value = "addroles")
    @ResponseBody
    public RestResponse addRolesBatch(@RequestParam long[] ids, @RequestParam String roleIds) {
        List<Long> idList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        String[] roleArr = roleIds.split(",");
        for (long id : ids) {
            idList.add(id);
        }
        for (String s : roleArr) {
            roleList.add(s);
        }
        for (Long id : idList) {
            for (String role : roleList) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", id);
                map.put("roleId", role);
                roleService.delRoleWithUserNameAndRoleId(map);
                Long creatorId = sessionManager.getCurrentSession().getValue("userId", Long.class);
                roleService.saveUserRole(KeyWorker.nextId(),id,Long.parseLong(role),creatorId,new Date());
            }
        }
        return this.getSuccessResponse(null);
    }

    /**
     * 批量移除权限
     * @param ids
     * @param roleIds
     * @return
     */
    @AuthOpt(code = "usermanager-moverole")
    @RequestMapping(value = "delroles")
    @ResponseBody
    public RestResponse delRolesBatch(@RequestParam long[] ids, @RequestParam String roleIds) {
        List<Long> idList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        String[] roleArr = roleIds.split(",");
        for (long id : ids) {
            idList.add(id);
        }
        for (String s : roleArr) {
            roleList.add(s);
        }
        for (Long id : idList) {
            for (String role : roleList) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", id);
                map.put("roleId", role);
                roleService.delRoleWithUserNameAndRoleId(map);
            }
        }
        return this.getSuccessResponse(null);
    }

    /**
     * 查询所有角色
     * @return
     */
    @RequestMapping(value = "showallroles")
    @ResponseBody
    public RestResponse showAllRoles() {
        List<Role> allRoleList = roleService.findAllRoles();
        List<Map> allRole = new ArrayList<>();
        for (Role role : allRoleList) {
            Map<String, Object> map = new HashMap<>();
            map.put("roleId", role.getRoleId());
            map.put("roleName", role.getRoleName());
            allRole.add(map);
        }
        return this.getSuccessResponse(allRole);
    }
}
