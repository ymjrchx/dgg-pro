package net.dgg.base.baseDao.crudDao.sqlProvider;

import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <DeleteSqlProvider>
 * @despriction
 * @create 2018/12/05 15:16
 **/
public class DeleteSqlProviderUtil implements SqlProviderUtil {


    /**
     * 返回deleteSQL
     *
     * @param map
     * @return
     */
    public String deleteSql(Map map) {
        StringBuilder sb = new StringBuilder(50);
        sb.append(DELETE_FROM).append(SEPARATOR_V).append(map.get(TABLE_KEY)).append(SEPARATOR_V).append(BLANK)
                .append(WHERE).append(map.get(ID_TABLE_KEY)).append(EQ).append(BLANK).append(SHARP).append(BRACKET_L)
                .append(map.get(ID_FIELD_NAME_KEY)).append(BRACKET_R);
        return sb.substring(0);
    }

}
