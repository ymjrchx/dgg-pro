package net.dgg.base.baseDao.crudDao;

import net.dgg.base.baseDao.crudDao.sqlProvider.FindSqlProviderUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <FindDao>
 * @despriction 返回 唯一结果
 * @create 2018/12/06 10:44
 **/
@Mapper
public interface FindUtilDao {

    /**
     * 通过ID查询
     *
     * @param map
     * @return
     */
    @SelectProvider(type = FindSqlProviderUtil.class, method = "findByIdSql")
    @ResultType(Map.class)
    Map findById(Map map);

    /**
     * 通过 属性查询
     *
     * @param map
     * @return
     */
    @SelectProvider(type = FindSqlProviderUtil.class, method = "findByAttrSql")
    @ResultType(Map.class)
    Map findByAttr(Map map);

}
