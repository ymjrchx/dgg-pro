package net.dgg.dqc.backservice.dao_mybatis;


import net.dgg.dqc.backservice.entity.treebook.TreeBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据字典Dao接口
 *
 * @author ZYJ
 */
@Repository
public interface TreeBookDao {
    /**
     * 根据code取name,缓存查询使用
     *
     * @param code 数据字典code，不可为空
     * @return
     */
    String getNameByCode(@Param("code") String code);

    /**
     * 根据level删除字典及其子集
     *
     * @param level 用like模糊匹配,子集的level包含父级的level,所以用like会删除本身及其子集
     */
    void deleteByLevel(@Param("level") String level);

    /**
     * 根据ids获取levels
     *
     * @param bookIdList 待查询的id集合
     * @return 返回查询所得的levels集合
     */
    List<String> getLevelsByIds(@Param("bookIdList") List<Long> bookIdList);

    /**
     * 根据pid查询出ids
     *
     * @param pid 父节点pid 不可为空
     * @return 返回查询所得的id集合
     */
    List<String> getIdsByPid(@Param("pid") Long pid);

    /**
     * 查询满足条件的所有数据字典
     *
     * @param params {id:字典id,code:字典编码,name:字典名称}
     * @return
     */
    List<TreeBook> getTreeBooksByConditionWithPage(Map params);

    /**
     * 根据pid查询数据字典
     *
     * @param pid 不可为空
     * @return 返回数据字典集合
     */
    List<TreeBook> getTreeBooksByPid(@Param("pid") Long pid);

    /**
     * 根据pid查询子集数量
     *
     * @param pid 不可为空
     * @return 返回子集数量
     */
    Integer getTreeBookCountByPid(@Param("pid") Long pid);

    /**
     * 查询满足条件的数据字典数量
     *
     * @param book 对象作为查询条件，所需的条件都set到book里面
     * @return
     */
    Integer getTreeBookCountByCondition(TreeBook book);

    /**
     * 查询满足条件的字典数量
     *
     * @param levels 层级，采用模糊匹配 不可为空
     * @param status 状态，不可为空
     * @return
     */
    int countChildTreeBook(@Param("levels") String levels, @Param("status") Integer status);

    /**
     * 用于校验字典编码是否已存在
     *
     * @param code 不可为空
     * @return
     */
    int countTreeBookByCode(@Param("code") String code);


    /**
     * 插入一条完整的数据字典
     *
     * @param book
     * @return
     */
    int insert(TreeBook book);


    /**
     * 根据ID查询单条数据字典
     *
     * @param id
     * @return
     */
    TreeBook selectByPrimaryKey(Long id);


    /**
     * 根据主键ID来更新一条数据字典的所有字段
     *
     * @param book
     * @return
     */
    int updateByPrimaryKey(TreeBook book);

    /**
     * 根据编码查询下级
     *
     * @param code   编码
     * @param status 状态
     * @param type   类型
     * @return
     */
    List<TreeBook> getTreeBookListByCode(@Param("code") String code, @Param("status") Integer status, @Param("type") Integer type, @Param("level") Integer level);

    /**
     * 根据编码查询字典
     *
     * @param code
     * @return
     */
    TreeBook getTreeBookByCode(@Param("code") String code);

    List<TreeBook> findTreeBookByCodes(@Param("codes") String codes);

    void updateTreebookDrop(@Param("oldLevel") String oldLevel, @Param("newLevel") String newLevel);


    /**
     * 查询部门业态
     *
     * @param orgId 部门ID
     * @return
     */
    List<TreeBook> queryOrgBusTypeByOrgId(@Param("orgId") Long orgId);


    /**
     * 根据Pid获取code
     *
     * @param pid
     * @return
     */
    List<String> getCodeByPid(String pid);

    /**
     * 根据code获取TreeBook
     *
     * @param code
     * @return
     */
    List<TreeBook> getNameAndCodeByCode(String code);

    /**
     * 获取下级节点name
     *
     * @param code
     * @return
     */
    List<String> getChildNameByCode(String code);

    /**
     * 获取所有子级
     *
     * @param id
     * @return
     */
    List<TreeBook> getAllSonTreeBook(@Param("id") Long id);

}