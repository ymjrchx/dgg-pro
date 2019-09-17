package net.dgg.tmd.foundation.platform.permission.service;

import net.dgg.tmd.foundation.platform.menu.dao.MenuOperatePermissionDao;
import net.dgg.tmd.foundation.platform.permission.dao.OperatePermissionDAO;
import net.dgg.tmd.foundation.platform.permission.entity.OperatePermissionEntity;
import net.dgg.tmd.foundation.platform.role.dao.RoleOperatePermissionDao;
import net.fblock.core.common.KeyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作权限Service类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
@Service

public class OperatePermissionService {
    @Autowired
    OperatePermissionDAO operatePermissionDAO;

    @Autowired
    RoleOperatePermissionDao roleOperatePermissionDao;

    @Autowired
    MenuOperatePermissionDao menuOperatePermissionDao;
    /**
     * 保存OperatePermission实体到数据库
     * @param operatePermissionEntity
     */
    public String saveOperatePermission(OperatePermissionEntity operatePermissionEntity){
        if(null != operatePermissionEntity){
            if(exsitCode(operatePermissionEntity.getCode(),null)>0)
                return "添加失败，权限编号已存在";
            operatePermissionDAO.insertPermissionInfo(operatePermissionEntity);
        }else{
            return "添加失败，添加数据不可为空";
        }
        return "success";
    }
    
    public void isPermissionToSave(OperatePermissionEntity operatePermissionEntity,Long userId) throws RuntimeException{
        if(exsitCode(operatePermissionEntity.getCode(),null)>0) {
            throw new RuntimeException("添加失败，权限编号已存在");
        }
        operatePermissionEntity.setOperatePermissionId(KeyWorker.nextId());
        operatePermissionEntity.setCreatorId(userId);
        operatePermissionEntity.setCreatetime(new Date());
        String result = saveOperatePermission(operatePermissionEntity);
        if(!result.equals("success")) {
            throw new RuntimeException(result);
        }
    }
    
    public void isOperatePermissionToUpdate(OperatePermissionEntity operatePermissionEntity,Long userId) throws RuntimeException{
        if(exsitCode(operatePermissionEntity.getCode(),operatePermissionEntity.getOperatePermissionId())>0) {
            throw new RuntimeException("添加失败，权限编号已存在");
        }
        operatePermissionEntity.setCreatorId(userId);
        operatePermissionEntity.setCreatetime(new Date());
        String result = updateOperatePermission(operatePermissionEntity);
        if(!result.equals("success")) {
            throw new RuntimeException(result);
        }
    }

    /**
     * 更新OperatePermission实体到数据库
     * @param operatePermissionEntity
     */

    public String updateOperatePermission(OperatePermissionEntity operatePermissionEntity){
        if(null != operatePermissionEntity){
            if(exsitCode(operatePermissionEntity.getCode(),operatePermissionEntity.getOperatePermissionId())>0)
                return "添加失败，权限编号已存在";
            operatePermissionDAO.updatePermissionInfo(operatePermissionEntity);
        }else{
            return "更新失败，更新数据不可为空";
        }
        return "success";
    }

    /**
     * 从数据库移除指定ID的权限
     * @param operatePermissionId
     */

    public void deleteOperatePermission(long operatePermissionId){
        //删除权限
        operatePermissionDAO.deletePermissionById(operatePermissionId);
        //删除角色-权限的关系
        roleOperatePermissionDao.removeByPermId(operatePermissionId);
        //删除菜单-权限
        menuOperatePermissionDao.removeByPermId(operatePermissionId);
    }

    public List<OperatePermissionEntity> queryOperatePermission(Map<String,Object> keyWords){
        if(null == keyWords){
            return null;
        }else{
            return operatePermissionDAO.findPermsEntityBykeyWords(keyWords);
        }
    }

    public List<OperatePermissionEntity> queryAll(Map searchMap){
        return operatePermissionDAO.queryBySearch(searchMap);
    }
    /**
     * 通过权限ID查找实体
     * @param permsId
     * @return
     */
    public OperatePermissionEntity findPermsEntityByPermsId(long permsId){
        return operatePermissionDAO.findPermsEntityByPermsId(permsId);
    }

    /**
     *根据菜单id查询绑定或者未绑定的权限
     * @param menuId
     * @param operatePermissionName
     * @param isMenu
     * @return
     */
    public List<OperatePermissionEntity> findOperatePermissionByMenuId(Long menuId, String operatePermissionName, int isMenu){
        return operatePermissionDAO.findOperatePermissionByMenuId(menuId,operatePermissionName,isMenu);
    }
    /**
     *根据菜单id查询绑定或者未绑定的权限
     * @param roleId
     * @param operatePermissionName
     * @return
     */
    public List<Map> findOperatePermissionByMenuRole(Long roleId, String operatePermissionName){
        return operatePermissionDAO.findOperatePermissionByMenuRole(roleId,operatePermissionName);
    }

    /**
     *根据菜单id查询绑定的权限,返回值中含有LAY_CHECKED 字段，前端用于勾选
     * @param roleId
     * @param menuId
     * @return
     */
    public List<Map> findMenuOps(Long roleId, Long menuId){
        return operatePermissionDAO.findMenuOps(roleId,menuId);
    }

    /**
     * code查询是否有重复的
     * isSave 1: 保存时带入参数  0：更新时带入参数
     */
    public int exsitCode(String code ,Long permsId){
        return operatePermissionDAO.existCode(code,permsId);
    }

    /**
     * 根据用户ID查询所具有的所有操作权限CODE
     * @param userId
     * @return
     */
    public List<String> findOperationCodeByUserId(long userId) {
        return operatePermissionDAO.findOperationCodeByUserId(userId);
    }
}
