package net.dgg.bigdata.manager.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Component
@Mapper
public interface OrgClosureDAO {
    /**
     * 通过部门ID读取distance距离
     * @param orgId
     * @return
     */
    @Select("SELECT distance FROM org_closure WHERE organization_id=#{orgId}")
    List<Integer> findDistanceByOrgId(@Param("orgId") long orgId);

    /**
     * 通过部门ID查找祖先ID
     * @param orgId
     * @return
     */
    @Select("SELECT ancestor_id FROM org_closure WHERE organization_id=#{orgId}")
    List<Long> findAncestorIdByOrgId(@Param("orgId") long orgId);

    /**
     * 通过祖先ID查找部门ID
     * @param ancestorId
     * @return
     */
    @Select("SELECT organization_id FROM org_closure WHERE ancestor_id=#{ancestorId}")
    List<Long> findOrgIdByAncestorId(@Param("ancestorId") long ancestorId);

    /**
     * 通过祖先ID统计子集数量
     * @param ancestorId
     * @return
     */
    @Select("SELECT COUNT(*) FROM org_closure a ,org_main b WHERE ancestor_id=#{ancestorId} AND distance=1 AND a.organization_id=b.org_id")
    long findTotalByAncestorId(@Param("ancestorId") long ancestorId);
}
