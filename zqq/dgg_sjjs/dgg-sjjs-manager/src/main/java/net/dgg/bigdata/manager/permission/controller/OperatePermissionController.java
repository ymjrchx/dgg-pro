package net.dgg.bigdata.manager.permission.controller;

import net.dgg.bigdata.manager.annotation.AuthOpt;
import net.dgg.bigdata.manager.permission.entity.OperatePermissionEntity;
import net.dgg.bigdata.manager.permission.service.OperatePermissionService;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.utils.DggValidateUtil;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import net.dgg.core.utils.common.DggKeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作权限Controller类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
@Controller
@RequestMapping("role/opratepermission")
public class OperatePermissionController extends DggBaseController {
    @Autowired
    OperatePermissionService operatePermissionService;

    @Autowired
    SessionManager sessionManager;

    /**
     * 操作权限首页
     *
     * @param operatePermissionName 操作权限名称
     * @param operatePermissionId 操作权限ID
     * @param request
     * @return
     */
    @RequestMapping(value = "index")
    public String index(String operatePermissionName,String operatePermissionId, HttpServletRequest request){
        if(DggValidateUtil.strNotEmpty(operatePermissionName, null,"操作权限名称不允许为空！")){
            request.setAttribute("operatePermissionName",request.getParameter("operatePermissionName").toString());
        }
        if(DggValidateUtil.strNotEmpty(operatePermissionId, null,"操作权限ID不允许为空！")){
            OperatePermissionEntity operatePermissionEntity = operatePermissionService.findPermsEntityByPermsId(Long.valueOf(operatePermissionId));
            request.setAttribute("operatePermission",operatePermissionEntity);
            request.setAttribute("operatePermissionId",operatePermissionId);
        }
        return "permission/operate_permission";
    }

    /**
     *
     *
     * @param operatePermissionEntity
     * @return
     */
    @AuthOpt(code = "optpermission-add")
    @RequestMapping(value = "add")
    @ResponseBody
    public DggRestResponse add(OperatePermissionEntity operatePermissionEntity){
        try {
            String flag = checkParam(operatePermissionEntity);
            if(!flag.equals("pass")){
                return this.getSuccessResponse(flag);
            }
            operatePermissionEntity.setOperatePermissionId(DggKeyWorker.nextId());
            Long userId = sessionManager.getCurrentSession().getValue("userId",Long.class);
            try {
                operatePermissionService.isPermissionToSave(operatePermissionEntity, userId);
            } catch (Exception e) {
                return this.getFailResponse(e.getMessage());
            }
        }catch (Exception e){
            logger.error("添加权限数据异常");
            return this.getFailResponse("添加权限数据异常");
        }
        return this.getSuccessResponse("添加权限成功");
    }

    @AuthOpt(code = "optpermission-modify")
    @RequestMapping(value = "update")
    @ResponseBody
    public DggRestResponse update(OperatePermissionEntity operatePermissionEntity){
        if(operatePermissionEntity.getOperatePermissionId()==null){
            return this.getFailResponse("请选择需要更新的权限");
        }
        try {
            String flag = checkParam(operatePermissionEntity);
            if(!flag.equals("pass")){
                return this.getSuccessResponse(flag);
            }
            Long userId = sessionManager.getCurrentSession().getValue("userId",Long.class);
            try {
                operatePermissionService.isOperatePermissionToUpdate(operatePermissionEntity, userId);
            } catch (Exception e) {
                return this.getFailResponse(e.getMessage());
            }
        }catch (Exception e){
            logger.error("修改权限数据异常");
            return this.getFailResponse("修改权限数据异常");
        }
        return this.getSuccessResponse("修改成功");
    }

    @AuthOpt(code = "optpermission-del")
    @RequestMapping(value = "delete")
    @ResponseBody
    public DggRestResponse delete(String operatePermissionId){
        if(StringUtils.isEmpty(operatePermissionId)) {
            return this.getFailResponse("请选择需要删除的权限");
        }
        try {
            operatePermissionService.deleteOperatePermission(Long.valueOf(operatePermissionId));
        }catch (Exception e){
            logger.error("权限删除失败");
            return this.getFailResponse("删除失败");
        }
        return this.getSuccessResponse("删除成功");
    }

    @RequestMapping(value = "search")
    @ResponseBody
    public DggRestResponse search(String operatePermissionName){
        Map<String,Object> searchMap = new HashMap<String,Object>();
        searchMap.put("operatePermissionName",operatePermissionName);
        List<OperatePermissionEntity> operatePermissionEntities = null;
        try {
            operatePermissionEntities = operatePermissionService.queryAll(searchMap);
        }catch (Exception e){
            return this.getFailResponse("查询失败");
        }
        return this.getSuccessResponse(operatePermissionEntities);
    }

    private String checkParam(OperatePermissionEntity operatePermissionEntity){
        if(StringUtils.isEmpty(operatePermissionEntity.getOperatePermissionName())) {
            return "请填写权限名称";
        }
        if(operatePermissionEntity.getOperatePermissionName().trim().length()>20) {
            return "权限名称最大限制20，输入超过限制，请重新输入";
        }
        if(StringUtils.isEmpty(operatePermissionEntity.getCode())) {
            return "请填写权限编号";
        }
        if(operatePermissionEntity.getCode().trim().length()>100) {
            return "权限编号最大限制100，输入超过限制，请重新输入";
        }
        if(operatePermissionEntity.getRemark().trim().length()>200) {
            return "权限备注最大限制200，输入超过限制，请重新输入";
        }

        return "pass";
    }
}
