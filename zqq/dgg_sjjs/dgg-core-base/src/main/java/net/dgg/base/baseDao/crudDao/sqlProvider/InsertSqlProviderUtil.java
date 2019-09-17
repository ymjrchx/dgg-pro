package net.dgg.base.baseDao.crudDao.sqlProvider;


import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <InsertSqlProvider>
 * @despriction
 * @create 2018/12/05 15:16
 **/
public class InsertSqlProviderUtil implements SqlProviderUtil {


    /**
     * 返回插入SQL
     *
     * @param map
     * @return
     */
    public String insertSql(Map map) {
        StringBuilder sb = new StringBuilder(150);
        sb.append(INSERT_INTO).append(SEPARATOR_V).append(map.get(TABLE_KEY)).append(SEPARATOR_V).append(BLANK)
                .append(PARENTHESES_L);
        List<String> tableFieldNameList = (List<String>) map.get(TABLE_FIELD_NAME_LIST_KEY);
        for (String tableField : tableFieldNameList) {
            sb.append(SEPARATOR_V).append(tableField).append(SEPARATOR_V).append(COMMER);
        }
        if (!tableFieldNameList.isEmpty()) {
            sb.deleteCharAt(sb.lastIndexOf(COMMER));
        }
        sb.append(PARENTHESES_R)
                .append(VALUES)
                .append(PARENTHESES_L);

        List<String> fieldNameList = (List<String>) map.get(FIELD_NAME_LIST_KEY);
        for (String fieldName : fieldNameList) {
            sb.append(SHARP).append(BRACKET_L).append(fieldName).append(BRACKET_R).append(COMMER);
        }
        if (!fieldNameList.isEmpty()) {
            sb.deleteCharAt(sb.lastIndexOf(COMMER));
        }
        sb.append(PARENTHESES_R);

        return sb.substring(0);
    }


}
