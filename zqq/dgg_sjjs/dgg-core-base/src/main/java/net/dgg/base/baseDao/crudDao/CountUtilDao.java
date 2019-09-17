package net.dgg.base.baseDao.crudDao;

import net.dgg.base.baseDao.crudDao.sqlProvider.CountSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <CountUtilDao>
 * @despriction
 * @create 2018/12/06 16:45
 **/
@Mapper
public interface CountUtilDao {

    @SelectProvider(type = CountSqlProvider.class, method = "countSql")
    @ResultType(Long.class)
    Long count(Map<String, Object> param);
}
