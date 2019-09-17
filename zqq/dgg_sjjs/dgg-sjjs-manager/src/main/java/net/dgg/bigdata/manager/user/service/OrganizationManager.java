package net.dgg.bigdata.manager.user.service;

import net.dgg.bigdata.manager.common.http.HttpUtils;
import net.dgg.bigdata.manager.user.dao.InitTableDAO;
import net.dgg.bigdata.manager.user.dao.OrgClosureDAO;
import net.dgg.bigdata.manager.user.dao.OrgRecorderDAO;
import net.dgg.bigdata.manager.user.entity.OrganizationDTO;
import net.dgg.bigdata.manager.user.entity.OrganizationEntity;
import net.dgg.bigdata.manager.user.exception.UserModuleException;
import net.dgg.core.utils.DggJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 组织机构Service类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Service
public class OrganizationManager {
    @Autowired
    OrgRecorderDAO orgRecorderDAO;

    @Autowired
    OrgClosureDAO orgClosureDAO;

    @Autowired
    InitTableDAO initTableDAO;

    @Value("${usercenter.apptoken}")
    private String USERCENTER_APPTOKEN;

    @Value("${usercenter.url}/${usercenter.getorg.url}")
    private String USERCENTER_ORG_APIURL;

    @Value("${table_schema}")
    private String TABLESCHEMA;

    /**
     * 判断用户数据表是否存在，如果不存在则自动创建
     */
    public void hasOrgModuleTable(){
        Map<String,String> map = new HashMap<>();
        map.put("tableName","org_main");
        map.put("tableSchema",TABLESCHEMA);
        if(initTableDAO.hasTable(map) < 1){
            initTableDAO.createOrgModuleTables();
        }
    }

    /**
     * 通过部门ID从数据库读取组织机构实体
     * @param orgId
     * @return
     */
    public OrganizationEntity findOrgEntityByOrgId(long orgId) {
        return orgRecorderDAO.findOrgEntityByOrgId(orgId);
    }

    /**
     * 通过关键字从数据库中查找组织机构实体
     * @param keyWords code:组织Code name:组织名称 leaderId:父级ID enabled:是否可用 start:分页起始 limit:显示条数
     * @return
     */
    public List<OrganizationEntity> queryOrganization(Map keyWords){
        if(null == keyWords || keyWords.isEmpty()){
            throw new UserModuleException("组织机构查询关键字不允许为空！");
        }else{
            List<OrganizationEntity> orgList = new ArrayList<>();
            orgList = orgRecorderDAO.findOrgEntityListByKeyWords(keyWords);
            return orgList;
        }
    }

    /**
     * 通过部门ID从数据库删除对应的组织机构
     * @param orgId
     */
    public void removeOrgByOrgId(long orgId){
        orgRecorderDAO.deleteOrgByOrgId(orgId);
    }

    /**
     * 通过祖先ID查找并返回子类组织机构实体
     * @param pId
     * @return
     */
    public List<OrganizationDTO> findOrgEntityListByPId(long pId){
        return orgRecorderDAO.findOrgEntityListByPId(pId);
    }

    /**
     * 通过部门ID读取distance距离
     * @param orgId
     * @return
     */
    public List<Integer> findDistanceByOrgId(long orgId){
        return orgClosureDAO.findDistanceByOrgId(orgId);
    }

    /**
     * 通过部门ID查找祖先ID
     * @param orgId
     * @return
     */
    public List<Long> findAncestorIdByOrgId(long orgId){
        return orgClosureDAO.findAncestorIdByOrgId(orgId);
    }

    /**
     * 通过祖先ID查找部门ID
     * @param ancestorId
     * @return
     */
    public List<Long> findOrgIdByAncestorId(long ancestorId){
        return orgClosureDAO.findOrgIdByAncestorId(ancestorId);
    }

    /**
     * 通过祖先ID统计子集数量
     * @param ancestorId
     * @return
     */
    public long findTotalByAncestorId(long ancestorId) {
        return orgClosureDAO.findTotalByAncestorId(ancestorId);
    }

    /**
     * 从用户中心服务器加载并保存组织机构实体
     * @param orgId
     * @return
     */
    public boolean loadAndSaveOrgFromServer(long orgId){
        boolean flag = false;
        //像用户中心URL发送一个包含APPTOKEN以及组织机构ID的请求
        HashMap responseData = DggJsonUtils.json2Obj(HttpUtils.sendPost(USERCENTER_ORG_APIURL, USERCENTER_APPTOKEN, "orgId=" + orgId), HashMap.class);
        HashMap orgFromServer = DggJsonUtils.json2Obj(responseData.get("dataobject").toString(), HashMap.class);
        //将服务器返回数据转为组织机构实体
        OrganizationEntity organizationEntity = new OrganizationEntity();
        try {
            organizationEntity.setOrgId(Long.parseLong(String.valueOf(orgFromServer.get("id"))));
            organizationEntity.setCode(String.valueOf(orgFromServer.get("code")));
            organizationEntity.setName(String.valueOf(orgFromServer.get("name")));
            organizationEntity.setDescription(String.valueOf(orgFromServer.get("description")));
            if(null != String.valueOf(orgFromServer.get("sort"))){
                organizationEntity.setSort(Integer.parseInt(String.valueOf(orgFromServer.get("sort"))));
            }
            if(null != String.valueOf(orgFromServer.get("leaderId"))){
                organizationEntity.setLeaderId(Long.parseLong(String.valueOf(orgFromServer.get("leaderId"))));
            }
            if(null != String.valueOf(orgFromServer.get("enabled"))){
                organizationEntity.setEnabled(Boolean.parseBoolean(String.valueOf(orgFromServer.get("enabled"))));
            }
            organizationEntity.setUpdateTime(new Date());
            //如果存在则更新，不存在则插入
            if(null == orgRecorderDAO.findOrgEntityByOrgId(orgId)){
                orgRecorderDAO.insertOrgEntity(organizationEntity);
            }else{
                orgRecorderDAO.updateOrgEntity(organizationEntity);
            }
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
