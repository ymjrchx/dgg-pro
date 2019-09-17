package net.dgg.base.baseDao.crudDao.sqlProvider;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <SqlProvider>
 * @despriction
 * @create 2018/12/05 15:13
 **/
public class FindSqlProviderUtil implements SqlProviderUtil {

    /**
     * h获取ID查询SQL
     *
     * @param map
     * @return
     */
    public String findByIdSql(Map map) {
        return this.getFindSql(map, ID_TABLE_KEY, ID_FIELD_NAME_KEY);
    }

    /**
     * 获取属性查询SQL
     *
     * @param map
     * @return
     */
    public String findByAttrSql(Map map) {
        return this.getFindSql(map, QUERY_TABLE_KEY, QUERY_FIELD_KEY);
    }

    /**
     * select ... from table where attr = ?
     *
     * @param map
     * @param attrKey
     * @param fieldKey
     * @return
     */
    private String getFindSql(Map map, String attrKey, String fieldKey) {
        StringBuilder sb = new StringBuilder(150);
        return sb.append(this.getEntitySelectSql(map))
                .append(WHERE)
                .append(SEPARATOR_V).append(map.get(attrKey)).append(SEPARATOR_V)
                .append(BLANK).append(EQ).append(BLANK)
                .append(SHARP).append(BRACKET_L).append(map.get(fieldKey)).append(BRACKET_R)
                .substring(0);
    }


}
