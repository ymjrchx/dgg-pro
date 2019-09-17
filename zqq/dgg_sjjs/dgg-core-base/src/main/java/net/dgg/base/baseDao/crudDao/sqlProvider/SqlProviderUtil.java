package net.dgg.base.baseDao.crudDao.sqlProvider;

import net.dgg.base.baseDao.DaoUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 刘阳
 * @ClassName <ProviderUtil>
 * @despriction
 * @create 2018/12/05 15:37
 **/
public interface SqlProviderUtil {
    String SELECT = "SELECT ";
    String COUNT = "COUNT(*) ";
    String FROM = "FROM ";
    String AS = " AS ";
    String UPDATE = "UPDATE ";
    String SET = "SET ";
    String DELETE_FROM = "DELETE FROM ";
    String INSERT_INTO = "INSERT INTO ";
    String VALUES = "VALUES";
    String WHERE = "WHERE ";

    String SCRIPT_S = "<script>";
    String SCRIPT_E = "</script>";

    String ALIAS = "t";

    String DOT = ".";
    String EQ = "=";
    String NOT_EQ = "!=";
    String COMMER = ",";
    String SHARP = "#";
    String DOLLAR = "$";
    String BRACKET_L = "{";
    String BRACKET_R = "}";
    String G_E = ">=";
    String L_E = "<=";
    String LIKE = "LIKE";
    String NOT_LIKE = "NOT LIKE";
    String IS_NULL = "IS NULL";
    String IS_NOT_NULL = "IS NOT NULL";
    String IN = "IN ";
    String NOT_IN = "NOT IN ";

    String FIRST_CONDITION = "1=1 ";


    String BLANK = " ";
    String PARENTHESES_L = "(";
    String PARENTHESES_R = ")";

    String SEPARATOR_V = "`";

    String OTH_LINE = "\n";


    String EQUAL_KEY = "";
    String NOT_EQUAL_KEY = "#NotEqual";
    String LIKE_KEY = "$Like";
    String NOT_LIKE_KEY = "$NotLike";
    String IS_NULL_KEY = "$IsNull";
    String IS_NOT_NULL_KEY = "$IsNotNull";
    String GE_KEY = "$Ge";
    String LE_KEY = "$Le";
    String IN_KEY = "$In";
    String NOT_IN_KEY = "$NotInKey";

    String DATE_S = "00:00:00";
    String DATE_E = "23:59:59";

    int PARAM_START_INDEX = 0;


    /**
     * 等于
     */
    String FIELD_WHERE_TEMPLATE_SQL = "\n AND `%s` %s #{%s}\n";

    /**
     * 扩展 条件查询
     */

    String DATE_FIELD_EXT_WHERE_TEMPLATE_SQL = "\n AND `%s` %s CONCAT(#{%s},' %s')\n";

    /**
     * like ,not like  '%...%'
     */
    String FIELD_LIKE_WHERE_TEMPLATE_SQL = "\n AND `%s` %s CONCAT('%%',#{%s},'%%')\n";

    /**
     * is NULL , is not null
     */
    String FIELD_IS_NULL_WHERE_TEMPLATE_SQL = "\n AND `%s` %s \n";

    /**
     * 表名key
     */
    String TABLE_KEY = "$table";

    /**
     * id key
     */
    String ID_TABLE_KEY = "$idTableNameKey";

    /**
     * id field name
     */
    String ID_FIELD_NAME_KEY = "$idFieldNameKey";

    /**
     * 字段集合
     */
    String FIELD_LIST_KEY = "$FieldListKey";

    /**
     * 字段MAP
     */
    String FIELD_MAP_KEY = "$FieldMapKey";

    /**
     * 表字段数组key名
     */
    String TABLE_FIELD_NAME_LIST_KEY = "$tableFieldNameList";

    /**
     *
     */
    String FIELD_NAME_LIST_KEY = "$fieldNameList";

    /**
     * 属性查询
     */
    String QUERY_TABLE_KEY = "$queryTableKey";
    String QUERY_FIELD_KEY = "$queryFieldKey";

    /**
     * where
     */
    String WHERE_S = "<where>";
    String WHERE_E = "</where>";


    /**
     * 统计SQL
     *
     * @param map
     * @return
     */
    default String getEntityCountSql(Map map) {
        StringBuilder sb = new StringBuilder(150);
        sb.append(SELECT).append(COUNT);
        sb.append(FROM).append(SEPARATOR_V).append(map.get(TABLE_KEY)).append(SEPARATOR_V).append(BLANK);
        return sb.substring(0);
    }

    /**
     * 获取select ... from table 共同 SQL
     *
     * @param map
     * @return
     */
    default String getEntitySelectSql(Map map) {
        StringBuilder sb = new StringBuilder(150);
        sb.append(SELECT).append(OTH_LINE);
        List<String> tableFieldNameList = (List<String>) map.get(TABLE_FIELD_NAME_LIST_KEY);
        List<String> fieldNameList = (List<String>) map.get(FIELD_NAME_LIST_KEY);

        for (int i = 0, len = tableFieldNameList.size(); i < len; i++) {
            sb.append(SEPARATOR_V).append(tableFieldNameList.get(i)).append(SEPARATOR_V).append(AS)
                    .append(SEPARATOR_V).append(fieldNameList.get(i)).append(SEPARATOR_V)
                    .append(i < len - 1 ? COMMER : BLANK).append(OTH_LINE);
        }

        sb.append(FROM).append(SEPARATOR_V).append(map.get(TABLE_KEY)).append(SEPARATOR_V).append(BLANK);
        return sb.substring(0);
    }


    /**
     * 获取 where 模版
     *
     * @param map
     * @return
     */
    default String getEntityWhereTemplateSql(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder(500);
        sb.append(WHERE).append(FIRST_CONDITION);

        Map<String, Field> fieldMap = (Map<String, Field>) map.get(FIELD_MAP_KEY);

        // 记录需要处理的in或not in  字段
        List<String> fieldNameIn = new LinkedList();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = checkField(entry.getKey(), fieldMap);
            if (StringUtils.isEmpty(fieldName)) {
                continue;
            }
            String ope = entry.getKey().replace(fieldName, "");
            Integer len = null;
            // 参数转化
            if (IN_KEY.equals(ope) || NOT_IN_KEY.equals(ope)) {
                len = this.getCollectionOrArrayLength(fieldName, ope, map);
                fieldNameIn.add(entry.getKey());
            }
            sb.append(this.getFieldWhereTemplateSql(fieldMap.get(fieldName), DaoUtil.transUpperToLineAndLower(fieldName), fieldName, ope, len));
        }

        // in 或 not in  条件处理
        for (String str : fieldNameIn) {
            String fieldName = checkField(str, fieldMap);
            String ope = str.replace(fieldName, "");
            this.dealCollectionOrArrayParam(fieldName, ope, map);
        }

        return sb.substring(0);
    }

    /**
     * 检查Field 是否可以生成 条件 SQL
     *
     * @return
     */
    default String checkField(String fieldName, Map<String, Field> fieldMap) {
        if (fieldName.startsWith(DOLLAR)) {
            return null;
        }
        String t = fieldName.indexOf(DOLLAR) < 0 ? fieldName : fieldName.substring(0, fieldName.indexOf(DOLLAR));
        return fieldMap.containsKey(t) ? t : null;
    }

    /**
     * 获取 字段的where 模版
     *
     * @param field
     * @param fieldTableName
     * @param fieldName
     * @return
     */
    default String getFieldWhereTemplateSql(Field field, String fieldTableName, String fieldName, String ope, Integer len) {
        StringBuilder sb = new StringBuilder();
        if (Date.class.equals(field.getType())) {    // 日期类型处理
            sb.append(this.getDateFieldWhereTemplateSql(fieldTableName, fieldName, ope));
        } else if (Number.class.isAssignableFrom(field.getType())) { // 数值类型
            sb.append(this.getFieldGeAndLeWhereTemplateSql(fieldTableName, fieldName, ope))
                    .append(this.getFieldEqAndNotEqWhereTemplateSql(fieldTableName, fieldName, ope))
                    .append(this.getFieldInWhereTemplateSql(fieldTableName, fieldName, ope, len));
        } else if (CharSequence.class.isAssignableFrom(field.getType())) {
            sb.append(this.getFieldLikeWhereTemplateSql(fieldTableName, fieldName, ope))
                    .append(this.getFieldEqAndNotEqWhereTemplateSql(fieldTableName, fieldName, ope))
                    .append(this.getFieldInWhereTemplateSql(fieldTableName, fieldName, ope, len));
        }
        // 空 或 非空
        sb.append(this.getFieldIsNullAndNotNullWhereTemplateSql(fieldTableName, fieldName, ope));
        return sb.substring(0);
    }


    /**
     * 获取 字段 空 和非空  is null  ， is not null
     *
     * @param fieldTableName
     * @param fieldName
     * @return
     */
    default String getFieldIsNullAndNotNullWhereTemplateSql(String fieldTableName, String fieldName, String ope) {
        String isNullKey = fieldName.concat(IS_NULL_KEY), isNotNullKey = fieldName.concat(IS_NOT_NULL_KEY);

        switch (ope) {
            case IS_NULL_KEY:
                return String.format(FIELD_IS_NULL_WHERE_TEMPLATE_SQL, fieldTableName, IS_NULL);
            case IS_NOT_NULL_KEY:
                return String.format(FIELD_IS_NULL_WHERE_TEMPLATE_SQL, fieldTableName, IS_NOT_NULL);
            default:
                return BLANK;
        }
    }

    /**
     * 获取 字段 等于和不等   =  ， !=
     *
     * @param fieldTableName
     * @param fieldName
     * @return
     */
    default String getFieldEqAndNotEqWhereTemplateSql(String fieldTableName, String fieldName, String ope) {
        String eqKey = fieldName.concat(EQUAL_KEY), notEqKey = fieldName.concat(NOT_EQUAL_KEY);

        switch (ope) {
            case EQUAL_KEY:
                return String.format(FIELD_WHERE_TEMPLATE_SQL, fieldTableName, EQ, eqKey);
            case NOT_EQUAL_KEY:
                return String.format(FIELD_WHERE_TEMPLATE_SQL, fieldTableName, NOT_EQ, notEqKey);
            default:
                return BLANK;
        }


    }

    /**
     * 获取字符串类型字段扩展模版 like , not like
     *
     * @param fieldTableName
     * @param fieldName
     * @return
     */
    default String getFieldLikeWhereTemplateSql(String fieldTableName, String fieldName, String ope) {
        String likeKey = fieldName.concat(LIKE_KEY), notLikeKey = fieldName.concat(NOT_LIKE_KEY);

        switch (ope) {
            case LIKE_KEY:
                return String.format(FIELD_LIKE_WHERE_TEMPLATE_SQL, fieldTableName, LIKE, likeKey);
            case NOT_LIKE_KEY:
                return String.format(FIELD_LIKE_WHERE_TEMPLATE_SQL, fieldTableName, NOT_LIKE, notLikeKey);
            default:
                return BLANK;
        }


    }


    /**
     * 获取 数值 字段 扩展 sql 模版   >= , <=
     *
     * @param fieldTableName
     * @param fieldName
     * @return
     */
    default String getFieldGeAndLeWhereTemplateSql(String fieldTableName, String fieldName, String ope) {
        String minKey = fieldName.concat(GE_KEY), maxKey = fieldName.concat(LE_KEY);

        switch (ope) {
            case GE_KEY:
                return String.format(FIELD_WHERE_TEMPLATE_SQL, fieldTableName, G_E, minKey);
            case LE_KEY:
                return String.format(FIELD_WHERE_TEMPLATE_SQL, fieldTableName, L_E, maxKey);
            default:
                return BLANK;
        }
    }


    /**
     * 获取 日期类型 字段 扩展 sql 模版
     *
     * @param
     * @param fieldTableName >= , <=
     * @param fieldName
     * @return
     */
    default String getDateFieldWhereTemplateSql(String fieldTableName, String fieldName, String ope) {
        String minKey = fieldName.concat(GE_KEY), maxKey = fieldName.concat(LE_KEY);

        switch (ope) {
            case GE_KEY:
                return String.format(DATE_FIELD_EXT_WHERE_TEMPLATE_SQL, fieldTableName, G_E, minKey, DATE_S);
            case LE_KEY:
                return String.format(DATE_FIELD_EXT_WHERE_TEMPLATE_SQL, fieldTableName, L_E, maxKey, DATE_E);
            default:
                return BLANK;
        }

    }

    /**
     * @param fieldTableName
     * @param fieldName
     * @param ope
     * @return
     */
    default String getFieldInWhereTemplateSql(String fieldTableName, String fieldName, String ope, Integer len) {
        if (len == null) {
            return BLANK;
        }
        String keys = DOLLAR.concat(fieldName).concat(ope);
        int i = PARAM_START_INDEX;
        StringBuilder sb = new StringBuilder("\n AND ").append(SEPARATOR_V).append(fieldTableName).append(SEPARATOR_V).append(BLANK);
        switch (ope) {
            case IN_KEY:
                sb.append(IN);
                break;
            case NOT_IN_KEY:
                sb.append(NOT_IN);
                break;
            default:
                return BLANK;
        }
        sb.append(PARENTHESES_L);
        for (int s = 0; s < len; s++) {
            sb.append(SHARP).append(BRACKET_L).append(keys).append(i + s).append(BRACKET_R).append(COMMER);
        }
        if (len > 0) {
            sb.deleteCharAt(sb.lastIndexOf(COMMER));
        }
        return sb.append(PARENTHESES_R).append(OTH_LINE).substring(0);

    }


    /**
     * 处理 数组 或 集合参数
     *
     * @param fieldName
     * @param map
     */
    default void dealCollectionOrArrayParam(String fieldName, String ope, Map<String, Object> map) {
        String fieldKey = fieldName.concat(ope);
        Object fileValues = map.get(fieldKey);
        Assert.notNull(fileValues, String.format("%s查询条件不能为空！", fieldKey));
        String InKeys = DOLLAR.concat(fieldKey);
        int i = PARAM_START_INDEX;
        // 集合
        if (fileValues instanceof List || fileValues instanceof Set) {
            Assert.isTrue(!((Collection) fileValues).isEmpty(), String.format("%s查询条件不能为空！", fieldKey));
            for (Object value : (fileValues instanceof List ? (List) fileValues : (Set) fileValues)) {
                map.put(InKeys.concat(String.valueOf(i++)), value);
            }
            // 数组
        } else if (fileValues instanceof Object[]) {
            Assert.isTrue(((Object[]) fileValues).length > 0, String.format("%s查询条件不能为空！", fieldKey));
            for (Object value : (Object[]) fileValues) {
                map.put(InKeys.concat(String.valueOf(i++)), value);
            }
            // obj
        } else {
            map.put(InKeys.concat(String.valueOf(i++)), fileValues);
        }
    }

    /**
     * @param fieldName
     * @param ope
     * @param map
     */
    default int getCollectionOrArrayLength(String fieldName, String ope, Map<String, Object> map) {
        String fieldKey = fieldName.concat(ope);
        Object fileValues = map.get(fieldKey);
        Assert.notNull(fileValues, String.format("%s查询条件不能为空！", fieldKey));
        int i = PARAM_START_INDEX;
        // 集合
        if (fileValues instanceof List || fileValues instanceof Set) {
            return ((Collection) fileValues).size();
            // 数组
        } else if (fileValues instanceof Object[]) {
            return ((Object[]) fileValues).length;
            // obj
        } else {
            return 1;
        }
    }


}
