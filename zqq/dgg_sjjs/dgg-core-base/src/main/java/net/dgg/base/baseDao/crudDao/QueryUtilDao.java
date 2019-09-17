package net.dgg.base.baseDao.crudDao;

import net.dgg.base.baseDao.crudDao.sqlProvider.QuerySqlProviderUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <QueryDao>
 * @despriction 返回 多个集合
 * @create 2018/12/05 11:48
 **/
@Mapper
public interface QueryUtilDao {

    @SelectProvider(type = QuerySqlProviderUtil.class, method = "querySql")
    @ResultType(Map.class)
    List<Map> queryMap(Map map);


}
