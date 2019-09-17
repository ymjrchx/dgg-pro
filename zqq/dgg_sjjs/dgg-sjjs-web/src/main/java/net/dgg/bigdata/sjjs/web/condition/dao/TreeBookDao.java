package net.dgg.bigdata.sjjs.web.condition.dao;

import net.dgg.bigdata.common.entity.TreeBook;
import org.apache.ibatis.annotations.*;
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
    @Select("select name from t_qcc_tree_book where code=#{code}")
    String getNameByCode(@Param("code") String code);

    /**
     * 根据level删除字典及其子集
     *
     * @param level 用like模糊匹配,子集的level包含父级的level,所以用like会删除本身及其子集
     */
    @Delete("delete from t_qcc_tree_book where levels like CONCAT(#{level},'%')")
    void deleteByLevel(@Param("level") String level);

    /**
     * 根据ids获取levels
     *
     * @param bookIdList 待查询的id集合
     * @return 返回查询所得的levels集合
     */
    @SelectProvider(type = TreeBookDaoProvider.class, method = "getLevelsByIdsSql")
    List<String> getLevelsByIds(@Param("bookIdList") List<Long> bookIdList);

    /**
     * 根据pid查询出ids
     *
     * @param pid 父节点pid 不可为空
     * @return 返回查询所得的id集合
     */
    @Select("select id from t_qcc_tree_book where pid=#{pid}")
    List<String> getIdsByPid(@Param("pid") Long pid);

    /**
     * 查询满足条件的所有数据字典
     *
     * @param params {id:字典id,code:字典编码,name:字典名称}
     * @return
     */
    @SelectProvider(type = TreeBookDaoProvider.class, method = "getTreeBooksByConditionWithPageSql")
    List<TreeBook> getTreeBooksByConditionWithPage(Map params);

    /**
     * 根据条件查询子集
     * @param params
     * @return
     */
    @SelectProvider(type = TreeBookDaoProvider.class, method = "getTreeBooksByConditionSql")
    List<TreeBook> getTreeBooksByCondition(Map params);
    /**
     * 根据pid查询数据字典
     *
     * @param pid 不可为空
     * @return 返回数据字典集合
     */
    @Select("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
            "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
            "from t_qcc_tree_book where pid = #{pid} order by sort")
    List<TreeBook> getTreeBooksByPid(@Param("pid") Long pid);

    /**
     * 根据pid查询子集数量
     *
     * @param pid 不可为空
     * @return 返回子集数量
     */
    @Select("select count(1) from t_qcc_tree_book where pid=#{pid}")
    Integer getTreeBookCountByPid(@Param("pid") Long pid);

    /**
     * 查询满足条件的数据字典数量
     *
     * @param book 对象作为查询条件，所需的条件都set到book里面
     * @return
     */
    @SelectProvider(type = TreeBookDaoProvider.class, method = "getTreeBookCountByConditionSql")
    Integer getTreeBookCountByCondition(TreeBook book);

    /**
     * 查询满足条件的字典数量
     *
     * @param levels 层级，采用模糊匹配 不可为空
     * @param status 状态，不可为空
     * @return
     */
    @Select("select count(1) from t_qcc_tree_book where levels like CONCAT(#{levels},'%') and status=#{status}")
    int countChildTreeBook(@Param("levels") String levels, @Param("status") Integer status);

    /**
     * 用于校验字典编码是否已存在
     *
     * @param code 不可为空
     * @return
     */
    @Select("select count(1) from t_qcc_tree_book where code=#{code}")
    int countTreeBookByCode(@Param("code") String code);


    /**
     * 插入一条完整的数据字典
     *
     * @param book
     * @return
     */

    @InsertProvider(type = TreeBookDaoProvider.class, method = "insertSql")
    int insert(TreeBook book);


    /**
     * 根据ID查询单条数据字典
     *
     * @param id
     * @return
     */
    @Select("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
            "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
            "from t_qcc_tree_book where id = #{id,jdbcType=BIGINT}")
    TreeBook selectByPrimaryKey(Long id);


    /**
     * 根据主键ID来更新一条数据字典的所有字段
     *
     * @param book
     * @return
     */

    @UpdateProvider(type = TreeBookDaoProvider.class, method = "updateByPrimaryKeySql")
    int updateByPrimaryKey(TreeBook book);

    /**
     * 根据编码查询下级
     *
     * @param code   编码
     * @param status 状态
     * @param type   类型
     * @return
     */
    @SelectProvider(type = TreeBookDaoProvider.class, method = "getTreeBookListByCodeSql")
    List<TreeBook> getTreeBookListByCode(@Param("code") String code, @Param("status") Integer status, @Param("type") Integer type, @Param("level") Integer level);

    /**
     * 根据编码查询字典
     *
     * @param code
     * @return
     */
    @Select("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
            "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
            "from t_qcc_tree_book where code = #{code,jdbcType=VARCHAR} limit 1")
    TreeBook getTreeBookByCode(@Param("code") String code);

    @Select("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
            "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
            "from t_qcc_tree_book a where FIND_IN_SET(a.`code`,#{codes,jdbcType=VARCHAR}) and status = 1")
    List<TreeBook> findTreeBookByCodes(@Param("codes") String codes);

    @Update("update t_qcc_tree_book set levels = replace(levels,#{oldLevel},#{newLevel}) where levels like " +
            "    CONCAT(#{oldLevel},'%')")
    void updateTreebookDrop(@Param("oldLevel") String oldLevel, @Param("newLevel") String newLevel);


    /**
     * 查询部门业态
     *
     * @param orgId 部门ID
     * @return
     */
    @Select("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
            "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
            "from t_qcc_tree_book a where FIND_IN_SET(a.`code`,(select bus_type from sys_org_main where id =#{orgId,jdbcType=BIGINT})) and status = 1")
    List<TreeBook> queryOrgBusTypeByOrgId(@Param("orgId") Long orgId);


    /**
     * 根据Pid获取code
     *
     * @param pid
     * @return
     */
    @Select("select code from t_qcc_tree_book where pid=#{pid}")
    List<String> getCodeByPid(String pid);

    /**
     * 根据code获取TreeBook
     *
     * @param code
     * @return
     */
    @Select("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
            "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
            "from t_qcc_tree_book where pcode=#{code} ORDER BY sort ASC")
    List<TreeBook> getNameAndCodeByCode(String code);

    /**
     * 获取下级节点name
     *
     * @param code
     * @return
     */
    @Select("select name from t_qcc_tree_book where pcode=#{code}")
    List<String> getChildNameByCode(String code);

    /**
     * 获取所有子级
     *
     * @param id
     * @return
     */
    @Select("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
            "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
            "from t_qcc_tree_book where 1=1 and levels LIKE CONCAT('%','${id}','%')")
    List<TreeBook> getAllSonTreeBook(@Param("id") Long id);

}