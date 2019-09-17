package net.dgg.tmd.foundation.platform.role.service;

import net.dgg.tmd.foundation.platform.menu.dao.MenuOperatePermissionDao;
import net.dgg.tmd.foundation.platform.role.dao.RoleDao;
import net.dgg.tmd.foundation.platform.role.dao.RoleMenuDao;
import net.dgg.tmd.foundation.platform.role.dao.RoleOperatePermissionDao;
import net.dgg.tmd.foundation.platform.role.entity.*;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 角色管理Service类
 */
@Service

public class RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private MenuOperatePermissionDao menuOperatePermissionDao;
    @Autowired
    private RoleOperatePermissionDao roleOperatePermissionDao;

    /**
     * exist类的方法的返回值
     * @param roleName
     * @param roleId
     * @return true 存在
     */
    public boolean existRole(String roleName, Long roleId) {
        if(roleDao.existRole(roleName, roleId)>0){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 查询所有的角色
     * @param search roleName:角色名
     * @return
     */
    public List<Role> queryRoles(Map search) {
        return roleDao.queryRoles(search);
    }

    /**
     * 添加角色
     * @param role
     */
    public String addRole(Role role, Set<Long> nodeIds, Set<Long> operatePermissionIds) {
        // 检查角色是否存在
        if (existRole(role.getRoleName(), null)) {
            return "角色名称已经存在";
        }
        //报存角色
        roleDao.addRole(role);
        List<RoleMenuPermission> roleMenuPermissions = roleMenuDataPackag(role, nodeIds);
        //如果角色菜单存在关系保存起来
        if (roleMenuPermissions != null && roleMenuPermissions.size() > 0) {
            roleMenuDao.save(roleMenuPermissions);
        }
        //添加角色权限关系
        List<RoleOperatePermission> roleOperatePermissions = roleOperateDataPackag(role, operatePermissionIds);
        if (roleOperatePermissions != null && roleOperatePermissions.size() > 0) {
            roleOperatePermissionDao.save(roleOperatePermissions);
        }
        return "success";
    }

    /**
     * 修改角色
     * @param role
     */
    @Transactional
    public String updateRole(Role role, Set<Long> nodeIds,  Set<Long> operatePermissionIds) {
        // 检查角色是否存在
        if (existRole(role.getRoleName(), role.getRoleId())) {
            return "角色名称已经存在";
        }
        roleDao.updateRole(role);
        //更新时删除菜单角色关系
        roleMenuDao.removeByRoleId(role.getRoleId());
        List<RoleMenuPermission> roleMenuPermissions = roleMenuDataPackag(role, nodeIds);
        //如果角色菜单存在关系保存起来
        if (roleMenuPermissions != null && roleMenuPermissions.size() > 0) {
            roleMenuDao.save(roleMenuPermissions);
        }
        //更新角色权限关系，先删除角色相关的权限，在新增
        //角色菜单下权限不能删除
        List<Long> notDelOpems = roleOperatePermissionDao.findRoleMenuOperate(role.getRoleId());
        if(notDelOpems != null && notDelOpems.size()>0)
            roleOperatePermissionDao.removeByRoleIdMenuOperms(notDelOpems, role.getRoleId());
        List<RoleOperatePermission> roleOperatePermissions = roleOperateDataPackag(role, operatePermissionIds);
        if (roleOperatePermissions != null && roleOperatePermissions.size() > 0) {
            roleOperatePermissionDao.save(roleOperatePermissions);
        }
        return "success";
    }

    /**
     * 保存角色 与菜单权限相关的权限
     */

    public void saveRolePerms(Role role, Long menuId, Set<Long> operatePermissionIds) {
        //根据菜单id获取待删除角色权限id
        List<Long> deletOpIds = menuOperatePermissionDao.queryOpmsIdsByMenuId(menuId);
        if(deletOpIds.size()>0){
            //删除角色权限
            roleOperatePermissionDao.removeByRoleId(deletOpIds, role.getRoleId());
        }else{
            //删除角色权限
            roleOperatePermissionDao.removeByRoleId(null, role.getRoleId());
        }
        List<RoleOperatePermission> roleOperatePermissions = roleOperateDataPackag(role, operatePermissionIds);
        if (roleOperatePermissions != null && roleOperatePermissions.size() > 0) {
            roleOperatePermissionDao.save(roleOperatePermissions);
        }
    }

    /**
     * 根据id获取角色
     *
     * @param roleId
     * @return
     */
    public Role queryRoleById(Long roleId) {
        return roleDao.queryRoleById(roleId);
    }


    public void updateRoleEnable(Long roleId, int enable) {
        roleDao.updateRoleEnable(enable, roleId);
    }

    /**
     * 组装角色菜单关系数据
     */
    private List<RoleMenuPermission> roleMenuDataPackag(Role role, Set<Long> nodeIds) {
        List<RoleMenuPermission> roleMenuPermissions = new ArrayList<>();
        if(StringUtils.isEmpty(nodeIds)){
            return roleMenuPermissions;
        }
        //组装角色-菜单关系
        //菜单选择为空的话则不进行添加
        if (nodeIds.size() > 0) {
            for (Long nodeId : nodeIds) {
                RoleMenuPermission roleMenuPermission = new RoleMenuPermission();
                roleMenuPermission.setId(KeyWorker.nextId());
                roleMenuPermission.setMenuId(nodeId);
                roleMenuPermission.setRoleId(role.getRoleId());
                roleMenuPermission.setCreatorId(role.getCreatorId());
                roleMenuPermission.setCreatetime(new Date());
                roleMenuPermissions.add(roleMenuPermission);
            }
        }
        return roleMenuPermissions;
    }

    /**
     * 获取角色操作权限数据
     */
    private List<RoleOperatePermission> roleOperateDataPackag(Role role, Set<Long> operatePermissionIds) {
        List<RoleOperatePermission> roleOperatePermissions = new ArrayList<>();
        if(StringUtils.isEmpty(operatePermissionIds)){
            return roleOperatePermissions;
        }
        if (operatePermissionIds.size() > 0) {
            Iterator<Long> it = operatePermissionIds.iterator();
            while (it.hasNext()) {
                Long operatePermissionId = it.next();
                //判断operatePermissionId与role是否存在关系，如果不存在关系则添加
                if (roleOperatePermissionDao.exitsByRoleOperate(role.getRoleId(),operatePermissionId) == 0) {
                    RoleOperatePermission roleOperatePermission = new RoleOperatePermission();
                    roleOperatePermission.setId(KeyWorker.nextId());
                    roleOperatePermission.setRoleId(role.getRoleId());
                    roleOperatePermission.setOperatePermissionId(operatePermissionId);
                    roleOperatePermission.setCreatetime(new Date());
                    roleOperatePermission.setCreatorId(role.getCreatorId());
                    roleOperatePermissions.add(roleOperatePermission);
                }
            }
        }
        return roleOperatePermissions;
    }



    /**
     * 根据角色ID查询角色名称
     * @param userId
     * @return
     */
    public String findOwnedRoleNameByUserId(Long userId) {
        if (null == userId) {
            return null;
        } else {
            return roleDao.findOwnedRoleNameByUserId(userId);
        }
    }

    public boolean exitsOperate4User(Long userId, String code) {
        Map condition = new HashMap();
        condition.put("userId", userId);
        condition.put("code", code);
        int count = roleOperatePermissionDao.exitsOperate4User(condition);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existsMenuPermissionUser(Long userId, String url) {
        Map condition = new HashMap();
        condition.put("userId", userId);
        condition.put("url", url);
        int count = roleDao.existsMenuPermissionUser(condition);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向数据库插入角色信息
     */
    public void saveUserRole(Long id,Long userId,Long roleId,Long creatorId,Date date){
        roleDao.insertUserRole(id,userId,roleId,creatorId,date);
    }

    /**
     * 根据用户ID和角色名称删除相应角色
     * @param map userId:所要删除的用户ID roleId:所要删除的角色ID
     */

    public void delRoleWithUserNameAndRoleId(Map map){
        if(null != map){
            roleDao.delRoleWithUserNameAndRoleId(map);
        }
    }

    /**
     * 根据用户ID删除所有角色
     * @param userId
     */

    public void delAllRoleByUserId(long userId){
        roleDao.delAllRoleByUserId(userId);
    }

    /**
     * 根据用户ID查询UserRoleDTO实体
     * @param userId
     * @return
     */
    public List<UserRoleDTO> findUserRoleDTOByUserId(long userId){
        return roleDao.findUserRoleDTOByUserId(userId);
    }

    /**
     * 查询所有RoleDTO实体
     * @param userId
     * @return
     */
    public List<Role> findAllRoleWithoutExist(long userId){
        return roleDao.findAllRoleWithoutExist(userId);
    }

    /**
     * 根据RoleID查询角色名称
     * @param roleId
     * @return
     */
    public String findRoleNameByRoleId(long roleId){
        return roleDao.findRoleNameByRoleId(roleId);
    }

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> findAllRoles(){
        return roleDao.findAllRoles();
    }
}
