package net.dgg.bigdata.manager.user.dao;

import net.dgg.bigdata.manager.user.entity.OrganizationDTO;
import net.dgg.bigdata.manager.user.entity.OrganizationEntity;
import org.apache.ibatis.annotations.*;
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
@Mapper
public interface OrgRecorderDAO {
    /**
     * 向数据库添加组织机构实体
     *
     * @param organizationEntity
     * @return
     */
    @SelectProvider(type = OrgRecorderDAOProvider.class, method = "insertOrgEntitySql")
    int insertOrgEntity(OrganizationEntity organizationEntity);

    /**
     * 向数据库更新组织机构实体
     *
     * @param organizationEntity
     * @return
     */
    @UpdateProvider(type = OrgRecorderDAOProvider.class, method = "updateOrgEntitySql")
    int updateOrgEntity(OrganizationEntity organizationEntity);

    /**
     * 从数据库删除对应ID的组织机构
     *
     * @param orgId
     * @return
     */
    @Delete("DELETE FROM org_main WHERE org_id=#{orgId}")
    int deleteOrgByOrgId(@Param("orgId") long orgId);

    /**
     * 通过部门ID从数据库读取组织架构实体
     *
     * @param orgId
     * @return
     */
    @Select("SELECT org_id AS orgId, code AS code, name AS name, description AS description, sort AS sort, " +
            "leader_id AS leaderId, enabled AS enabled, updatetime AS updatetime FROM org_main WHERE org_id=#{orgId}")
    OrganizationEntity findOrgEntityByOrgId(@Param("orgId") long orgId);

    /**
     * 通过关键字查找对应组织架构
     *
     * @param keyWords
     * @return
     */
    @SelectProvider(type = OrgRecorderDAOProvider.class,method = "findOrgEntityListByKeyWordsSql")
    List<OrganizationEntity> findOrgEntityListByKeyWords(@Param("keyWords") Map keyWords);

    /**
     * 通过祖先ID查找子类
     *
     * @param pId
     * @return
     */
    @Select("SELECT org_id AS id, name AS name, ancestor_id AS pid FROM org_closure oa, org_main ob" +
            " WHERE ancestor_id=#{pId} AND distance=1 AND oa.organization_id=ob.org_id ORDER BY ob.name")
    List<OrganizationDTO> findOrgEntityListByPId(@Param("pId") long pId);
}
