package net.dgg.tmd.foundation.platform.user.controller;

import net.dgg.tmd.foundation.platform.common.util.LayuiTableResponse;
import net.dgg.tmd.foundation.platform.role.service.RoleService;
import net.dgg.tmd.foundation.platform.user.entity.OrganizationDTO;
import net.dgg.tmd.foundation.platform.user.entity.OrganizationEntity;
import net.dgg.tmd.foundation.platform.user.entity.UserDTO;
import net.dgg.tmd.foundation.platform.user.service.OrganizationManager;
import net.dgg.tmd.foundation.platform.user.service.UserManager;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/3/2
 * Time:10:01
 */
@Controller
@RequestMapping("role/usermanager")
public class UserController extends BaseController {
    @Autowired
    private UserManager userManager;

    @Autowired
    private OrganizationManager organizationManager;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "index")
    public String index() {
        return "permission/user";
    }

    /**
     * 获取用户管理页面左侧树的下一个节点信息
     *
     * @param orgId
     * @return
     */
    @RequestMapping(value = "orgnextnode")
    @ResponseBody
    public RestResponse<List> getNextNode(@RequestParam Long orgId) {
        List<Map> orgList = new ArrayList<>();
        if (orgId == null) {
            List<OrganizationDTO> orgDTOlist = organizationManager.findOrgEntityListByPId(1);
            OrganizationEntity organizationEntity = organizationManager.findOrgEntityByOrgId(1);
            Map<String, Object> orgRoot = new HashMap<>();
            orgRoot.put("id", organizationEntity.getOrgId());
            orgRoot.put("name", organizationEntity.getName());
            orgRoot.put("pId", 0);
            orgRoot.put("open", true);
            orgRoot.put("isParent", true);
            orgList.add(orgRoot);
            for (OrganizationDTO organizationDTO : orgDTOlist) {
                orgList.add(getUserNodesDto(organizationDTO));
            }
        } else {
            List<OrganizationDTO> list = organizationManager.findOrgEntityListByPId(orgId);
            for (OrganizationDTO organizationDTO : list) {
                orgList.add(getUserNodesDto(organizationDTO));
            }
        }
        return this.getSuccessResponse(orgList);
    }

    /**
     * 用户节点数据组装
     *
     * @param organizationDTO
     * @return
     */
    private Map getUserNodesDto(OrganizationDTO organizationDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", organizationDTO.getId());
        map.put("name", organizationDTO.getName());
        map.put("pId", organizationDTO.getpId());
        if (organizationManager.findTotalByAncestorId(organizationDTO.getId()) > 0) {
            map.put("isParent", true);
        }
        return map;
    }

    /**
     * 根据组织编号获取用户List
     *
     * @param params: orgId组织机构编号 page页码 limit每页显示数量
     * @return
     */
    @RequestMapping(value = "userpage")
    @ResponseBody
    public LayuiTableResponse getUsers(@RequestParam Map params) {
        List<UserDTO> list = userManager.findUserDTOByOrgIdWithPage(params);
        for (UserDTO userDTO : list) {
            userDTO.setRoleName(roleService.findOwnedRoleNameByUserId(userDTO.getId()));
        }
        return new LayuiTableResponse().data(params.get("count"), list);
    }

    /**
     * 根据关键字搜索用户
     *
     * @param params: selectCode搜索编号1当前部门 2全部部门 3当前部门及子部门 mainKey关键字 orgId组织机构编码 page页码 limit每页显示数量
     * @return
     */
    @RequestMapping(value = "search")
    @ResponseBody
    public LayuiTableResponse searchUserWithOrg(@RequestParam Map params) {
        List<UserDTO> list = userManager.findUserDTOByKeyWordsWithPage(params);
        return new LayuiTableResponse().data(params.get("count"), list);
    }
}
