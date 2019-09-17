package net.dgg.tmd.foundation.platform.user.dao;

import net.dgg.tmd.foundation.platform.user.entity.OrganizationDTO;
import net.dgg.tmd.foundation.platform.user.entity.OrganizationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Component
public interface OrgRecorderDAO {
    /**
     * 向数据库添加组织机构实体
     * @param organizationEntity
     * @return
     */
    int insertOrgEntity(OrganizationEntity organizationEntity);

    /**
     * 向数据库更新组织机构实体
     * @param organizationEntity
     * @return
     */
    int updateOrgEntity(OrganizationEntity organizationEntity);

    /**
     * 从数据库删除对应ID的组织机构
     * @param orgId
     * @return
     */
    int deleteOrgByOrgId(@Param("orgId") long orgId);

    /**
     * 通过部门ID从数据库读取组织架构实体
     * @param orgId
     * @return
     */
    OrganizationEntity findOrgEntityByOrgId(@Param("orgId") long orgId);

    /**
     * 通过关键字查找对应组织架构
     * @param keyWords
     * @return
     */
    List<OrganizationEntity> findOrgEntityListByKeyWords(@Param("keyWords") Map keyWords);

    /**
     * 通过祖先ID查找子类
     * @param pId
     * @return
     */
    List<OrganizationDTO> findOrgEntityListByPId(@Param("pId") long pId);
}
