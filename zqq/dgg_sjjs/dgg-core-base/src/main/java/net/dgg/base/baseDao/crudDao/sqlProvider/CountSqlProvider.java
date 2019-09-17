package net.dgg.base.baseDao.crudDao.sqlProvider;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <CountSqlProcider>
 * @despriction
 * @create 2018/12/07 18:12
 **/
public class CountSqlProvider implements SqlProviderUtil {
    /**
     * 集合查询SQL
     *
     * @param map
     * @return
     */
    public String countSql(Map map) {
        StringBuilder sb = new StringBuilder();
        return sb.append(getEntityCountSql(map))
                .append(OTH_LINE)
                .append(getEntityWhereTemplateSql(map))
                .append(OTH_LINE)
                .substring(0);
    }
}
