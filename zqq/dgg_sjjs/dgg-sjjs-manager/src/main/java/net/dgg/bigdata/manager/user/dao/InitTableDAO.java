package net.dgg.bigdata.manager.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Component
@Mapper
public interface InitTableDAO {
    /**
     * 新建user_info数据表
     */
    void createUserModuleTables();

    /**
     * 新建org_main数据表
     */
    void createOrgModuleTables();

    /**
     * 判断表名为tableName的数据表是否存在
     * @param map tableName:用户自定义的表名称 tableSchema:数据表名称
     * @return
     */
    @Select("select count(1) from information_schema.tables where table_name = #{map.tableName} AND table_schema=#{map.tableSchema}")
    int hasTable(@Param("map") Map map);
}