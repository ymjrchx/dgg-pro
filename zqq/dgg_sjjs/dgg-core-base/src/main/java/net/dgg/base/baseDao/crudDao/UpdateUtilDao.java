package net.dgg.base.baseDao.crudDao;

import net.dgg.base.baseDao.crudDao.sqlProvider.UpdateSqlProviderUtil;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <UpdateDao>
 * @despriction
 * @create 2018/12/06 10:05
 **/
@Mapper
public interface UpdateUtilDao {

    /**
     * 更新
     *
     * @param map
     */
    @InsertProvider(type = UpdateSqlProviderUtil.class, method = "updateSql")
    void update(Map map);

}
