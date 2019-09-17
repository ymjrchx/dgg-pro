package net.dgg.base.baseDao.crudDao;


import net.dgg.base.baseDao.crudDao.sqlProvider.InsertSqlProviderUtil;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <InsertDao>
 * @despriction
 * @create 2018/12/05 15:53
 **/
@Mapper
public interface SaveUtilDao {

    /**
     * 保存
     *
     * @param map
     */
    @InsertProvider(type = InsertSqlProviderUtil.class, method = "insertSql")
    void save(Map map);

}
