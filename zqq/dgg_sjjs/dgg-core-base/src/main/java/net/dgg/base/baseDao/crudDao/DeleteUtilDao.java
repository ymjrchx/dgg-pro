package net.dgg.base.baseDao.crudDao;

import net.dgg.base.baseDao.crudDao.sqlProvider.DeleteSqlProviderUtil;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <DeleteDao>
 * @despriction
 * @create 2018/12/06 10:25
 **/
@Mapper
public interface DeleteUtilDao {

    @DeleteProvider(type = DeleteSqlProviderUtil.class, method = "deleteSql")
    void delete(Map map);
}
