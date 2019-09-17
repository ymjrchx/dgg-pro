package net.dgg.base.baseDao.crudDao.sqlProvider;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <UpdateSqlProvider>
 * @despriction
 * @create 2018/12/05 15:15
 **/
public class UpdateSqlProviderUtil implements SqlProviderUtil {

    /**
     * 返回更新SQL
     *
     * @param map
     * @return
     */
    public String updateSql(Map map) {
        StringBuilder sb = new StringBuilder(150);
        sb.append(UPDATE).append(SEPARATOR_V).append(map.get(TABLE_KEY)).append(SEPARATOR_V).append(BLANK)
                .append(SET);
        List<String> tableFieldNameList = (List<String>) map.get(TABLE_FIELD_NAME_LIST_KEY);
        List<String> fieldNameList = (List<String>) map.get(FIELD_NAME_LIST_KEY);
        for (int i = 0, len = tableFieldNameList.size(); i < len; i++) {
            sb.append(SEPARATOR_V).append(tableFieldNameList.get(i)).append(SEPARATOR_V).append(EQ).append(BLANK)
                    .append(SHARP).append(BRACKET_L).append(fieldNameList.get(i))
                    .append(BRACKET_R).append(i < len - 1 ? COMMER : BLANK);
        }
        sb.append(WHERE).append(map.get(ID_TABLE_KEY)).append(EQ).append(BLANK).append(SHARP).append(BRACKET_L)
                .append(map.get(ID_FIELD_NAME_KEY)).append(BRACKET_R);
        return sb.substring(0);
    }
}
