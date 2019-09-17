package net.dgg.tmd.foundation.platform.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Component
public interface OrgClosureDAO {
    /**
     * 通过部门ID读取distance距离
     * @param orgId
     * @return
     */
    List<Integer> findDistanceByOrgId(@Param("orgId") long orgId);

    /**
     * 通过部门ID查找祖先ID
     * @param orgId
     * @return
     */
    List<Long> findAncestorIdByOrgId(@Param("orgId") long orgId);

    /**
     * 通过祖先ID查找部门ID
     * @param ancestorId
     * @return
     */
    List<Long> findOrgIdByAncestorId(@Param("ancestorId") long ancestorId);

    /**
     * 通过祖先ID统计子集数量
     * @param ancestorId
     * @return
     */
    long findTotalByAncestorId(@Param("ancestorId") long ancestorId);
}
