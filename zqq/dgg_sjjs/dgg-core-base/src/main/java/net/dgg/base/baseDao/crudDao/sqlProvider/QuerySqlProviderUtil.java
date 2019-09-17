package net.dgg.base.baseDao.crudDao.sqlProvider;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <QuerySqlProviderUtil>
 * @despriction
 * @create 2018/12/06 16:47
 **/
public class QuerySqlProviderUtil implements SqlProviderUtil {

    /**
     * 集合查询SQL
     *
     * @param map
     * @return
     */
    public String querySql(Map map) {
        StringBuilder sb = new StringBuilder();
        return sb.append(this.getEntitySelectSql(map))
                .append(OTH_LINE)
                .append(this.getEntityWhereTemplateSql(map))
                .append(OTH_LINE)
                .substring(0);
    }

}
