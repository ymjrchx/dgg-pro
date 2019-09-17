package net.dgg.dqc.backservice.utils;/**
 * Created by Administrator on 2018/5/2.
 */


import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author 刘阳
 * @ClassName <SqlUtils>
 * @despriction
 * @create 2018/05/02 9:01
 **/
public class SqlUtils {
    private String sj = ""; // 缩进
    private String blank = " ";
    private String g = "`";
    private String comer = ",";
    private String othLine = "\n";
    private String dot = ".";
    private String and = " and ";
    private String eq = "=";
    private String sharpS = "#{";
    private String sharpE = "}";
    private String le = "&lt;=";
    private String ge = "&gt;=";
    private String where = " WHERE ";


    private boolean usePublicWhereTemplate = true; // 使用公共where 模版

    private String whereTemplateName = "where";

    private Class<?> entity = null;     //生产sql 的实体

    private boolean formatDate = false; //查询时 格式化日期

    private String[] dateSuffix = {"Start", "End"}; // 日期字段 自动分成 2个条件时，字段条件值名自动附件的后缀


    public static void main(String[] a) {
        // 创建 查询
        //System.out.println(new SqlUtils().createSelectSql(CheckCompanyRecord.class, "t_check_company_record","t"));
        // 创建统计
        // System.out.println(new SqlUtils().createCountSql(CheckCompanyRecord.class, "t_check_company_record","t"));
        // 创建 插入
        //System.out.println(new SqlUtils().createInsertSql(CheckCompanyRecord.class,"t_check_company_record"));
        // 创建 更新
        //System.out.println(new SqlUtils().createUpdateSql(CheckCompanyRecord.class, "t_check_company_record", "id"));
        // 创建 where 公共模版
        //System.out.println(new SqlUtils().createWhereTemplate(CheckCompanyRecord.class, "t"));
        // 创建 findByIdSql
        //System.out.println(new SqlUtils().createFindByIdSql(CheckCompanyRecord.class, "t_check_company_record","t","id"));
        // 创建 deleteByIdSql
        //System.out.println(new SqlUtils().createDeleteByIdSql(CheckCompanyRecord.class, "t_check_company_record","id"));
    }

    SqlUtils() {
    }

    SqlUtils(String sj) {
        this.sj = sj;
    }


    /**
     * 创建 更新SQL
     *
     * @param entity    实体类
     * @param tableName 表名
     * @param idName    条件key 的 name
     * @return
     */
    public String createUpdateSql(Class<?> entity, String tableName, String idName) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        List<Field> fieldList = this.getAllFilds();
        StringBuilder sb = new StringBuilder(fieldList.size() * 10);
        sb.append(sj).append(" UPDATE ");
        sb.append(tableName).append(" SET \n");
        for (Field field : fieldList) {
            if (idName.equalsIgnoreCase(field.getName())) {
                continue;
            }
            sb.append(sj).append(this.getFieldAlias(field, "")).append(blank).append(eq).append(blank).append(sharpS).append(field.getName()).append(sharpE).append(comer).append(othLine
            );
        }
        if (fieldList.size() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(comer));
        }
        sb.append(sj).append(" WHERE ").append(idName).append(blank).append(eq).append(blank).append(sharpS).append(idName).append(sharpE);
        return sb.substring(0);
    }


    /**
     * 创建 插入 SQL
     *
     * @param entity    实体类
     * @param tableName 表名
     * @return
     */
    public String createInsertSql(Class<?> entity, String tableName) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        List<Field> fieldList = this.getAllFilds();
        StringBuilder sb = new StringBuilder(fieldList.size() * 10);
        sb.append(sj).append(" INSERT INTO ");
        sb.append(tableName).append(othLine).append(sj).append("(");
        for (Field field : fieldList) {
            sb.append(this.getFieldAlias(field, "")).append(comer);
        }
        if (fieldList.size() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(comer));
        }
        sb.append(")").append(othLine).append(sj).append(" VALUES ").append(othLine).append(sj).append("(");
        for (Field field : fieldList) {
            sb.append(sharpS).append(field.getName()).append(sharpE).append(comer);
        }
        if (fieldList.size() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(comer));
        }
        sb.append(")");
        return sb.substring(0);
    }


    /**
     * 创建 查询sql  与 mybits 条件模版
     *
     * @param entity
     * @param tableName
     * @param alias
     */
    public String createSelectSql(Class<?> entity, String tableName, String alias) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        sb.append(getSelectSql(entity, tableName, alias));

        sb.append(this.getWhereCondition(this.getAllFilds(), alias));

        sb.append(othLine)
                .append(sj).append("<if test=\"null!=start and ''!=start and null!=limit and ''!=limit\">\n")
                .append(sj).append("   limit #{start},#{limit}\n")
                .append(sj).append("</if>\n")
                .append(sj).append("<if test=\"(null==start or ''==start) and null!=limit and ''!=limit\">\n")
                .append(sj).append("   limit #{limit}\n")
                .append(sj).append("</if>\n")
                .append(othLine);
        return sb.substring(0);
    }

    /**
     * 创建统计SQL
     *
     * @param entity
     * @param tableName
     * @param alias
     * @return
     */
    public String createCountSql(Class<?> entity, String tableName, String alias) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        List<Field> fieldList = this.getAllFilds();
        StringBuilder sb = new StringBuilder(fieldList.size() * 10);
        sb.append(sj).append("SELECT COUNT(*) ");
        alias = StringUtils.isEmpty(alias) ? "" : (alias.replaceAll(blank, "").concat(dot));
        sb.append(othLine).append(othLine)
                .append(sj).append("FROM ").append(tableName).append(blank).append(alias)
                .deleteCharAt(sb.lastIndexOf(dot))
                .append(othLine)
                .append(othLine);


        sb.append(this.getWhereCondition(fieldList, alias));

        return sb.substring(0);
    }

    public String createWhereTemplate(Class<?> entity, String alias) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        alias = StringUtils.isEmpty(alias) ? "" : alias.concat(dot);
        List<Field> fieldList = this.getAllFilds();
        StringBuilder sb = new StringBuilder(fieldList.size() * 10);
        sb.append(sj).append("<sql id=\"" + whereTemplateName + "\">").append(othLine);
        sb.append(this.getWhereTemplate(fieldList, alias));
        sb.append(sj).append("</sql>").append(othLine);
        return sb.substring(0);
    }

    /**
     * 根据ID 查询的SQL
     *
     * @param entity
     * @param tableName
     * @param idName
     * @return
     */
    public String createFindByIdSql(Class<?> entity, String tableName, String alias, String idName) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.getSelectSql(entity, tableName, alias));
        sb.append(sj).append("WHERE").append(blank).append(alias).append(dot).append(g).append(idName).append(g).append(eq).append(sharpS).append(idName).append(sharpE);
        return sb.substring(0);
    }

    /**
     * 创建 删除sql
     *
     * @param entity
     * @param tableName
     * @param idName
     * @return
     */
    public String createDeleteByIdSql(Class<?> entity, String tableName, String idName) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(sj).append("DELETE FROM ").append(tableName).append(blank).append(where)
                .append(blank).append(g).append(idName).append(g).append(eq).append(sharpS).append(idName).append(sharpE);
        return sb.substring(0);
    }




    /**
     * 获取表名
     *
     * @return
     */
    private String getSqlTable() {
        return this.entity == null ? null : this.transUpperToLineAndLower(this.entity.getSimpleName(), true);
    }

    /**
     * 获取别名
     *
     * @param field
     * @param alias
     * @return
     */
    private String getSelectAlias(Field field, String alias) {
        String sqlAlias = alias.concat(g).concat(this.getSqlAlias(field)).concat(g);
        if (this.formatDate && field.getType().equals(Date.class)) {
            return "date_format(".concat(sqlAlias).concat(" , '%Y-%m-%d %H:%i:%s' )");
        }
        return sqlAlias;
    }

    /**
     * 获取别名
     *
     * @return
     */
    private String getFieldAlias(Field field, String alias) {
        String g = "`";
        String sqlAlias = alias.concat(g).concat(this.getSqlAlias(field)).concat(g);
        return sqlAlias;
    }

    //获取sql字段名
    private String getSqlAlias(Field field) {
        return field == null ? null : this.transUpperToLineAndLower(field.getName());
    }


    /**
     * 获取所有实体映射字段
     *
     * @return
     */
    private List<Field> getAllFilds() {
        List<Field> fieldSet = new ArrayList<>();
        Field[] fields = this.entity.getDeclaredFields();
        for (Field f : fields) {
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers())) {
                fieldSet.add(f);
            }
        }
        this.getParentField(fieldSet, this.entity);
        Collections.sort(fieldSet, (o1, o2) -> {
            String idKey = "id";
            if (o1.getName().equals(idKey) && !o2.getName().equals(idKey)) {
                return -1;
            }
            if (!o1.getName().equals(idKey) && o2.getName().equals(idKey)) {
                return 1;
            }
            return 0;
        });
        return fieldSet;
    }


    // 递归获取父类实体字段
    private void getParentField(List<Field> fieldSet, Class<?> entity) {
        Class<?> superClass = null;
        if (fieldSet == null || entity == null || (superClass = entity.getSuperclass()).equals(Object.class)) {
            return;
        }
        Field[] fields = superClass.getDeclaredFields();
        for (Field f : fields) {
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers())) {
                fieldSet.add(f);
            }
        }
        getParentField(fieldSet, superClass);
    }

    /**
     * 大写转下横线加小写
     *
     * @param str
     * @return
     * @Param first
     */
    private String transUpperToLineAndLower(String str, Boolean... first) {
        StringBuilder sb = new StringBuilder(50);
        char[] charArr = str.toCharArray();
        for (int i = 0, len = charArr.length; i < len; i++) {
            char c = charArr[i];
            if (i == 0 && first.length > 0 && first[0]) {
                sb.append(Character.toLowerCase(c));
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.substring(0);
    }

    /**
     * 查询 select SQL 段
     *
     * @param entity
     * @param tableName
     * @param alias
     * @return
     */
    private String getSelectSql(Class<?> entity, String tableName, String alias) {
        this.entity = entity;
        if (this.entity == null) {
            return null;
        }
        List<Field> fieldList = this.getAllFilds();
        StringBuilder sb = new StringBuilder(fieldList.size() * 10);
        sb.append(sj).append(" SELECT \n");
        alias = StringUtils.isEmpty(alias) ? "" : (alias.replaceAll(blank, "").concat(dot));

        for (Field field : fieldList) {
            sb.append(sj).append(this.getSelectAlias(field, alias)).append(" as ").append(g).append(field.getName()).append(g).append(comer).append(othLine);
        }
        if (fieldList.size() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(comer));
        }
        sb.append(othLine).append(sj).append("FROM ").append(tableName).append(blank).append(alias)
                .deleteCharAt(sb.lastIndexOf(dot))
                .append(othLine)
                .append(othLine);
        return sb.substring(0);
    }

    /**
     * @return
     */
    private String getWhereCondition(List<Field> fieldList, String alias) {
        StringBuilder sb = new StringBuilder();
        sb.append(sj).append("<where>").append(othLine);
        if (this.usePublicWhereTemplate) {
            sb.append(sj).append("<include refid=\"" + this.whereTemplateName + "\"></include>").append(othLine);
        } else {
            sb.append(this.getWhereTemplate(fieldList, alias));
        }
        sb.append(sj).append("</where>").append(othLine);
        return sb.substring(0);
    }


    /**
     * @return
     */
    private String getWhereTemplate(List<Field> fieldList, String alias) {
        StringBuilder sb = new StringBuilder();
        for (Field field : fieldList) {
            if (!field.getType().equals(Date.class)) {
                String fieldName = field.getName();
                sb.append(sj).append(sj).append("<if test=\"null!=%s and ''!=%s\">".replaceAll("%s", fieldName)).append(othLine);
                sb.append(sj).append(sj).append(blank).append(" AND ").append(this.getFieldAlias(field, alias)).append(blank).append(eq).append(blank).append(sharpS).append(fieldName).append(sharpE).append(othLine);
                sb.append(sj).append(sj).append("</if>").append(othLine);
            } else {
                String[] conNames = {field.getName().concat(this.dateSuffix[0]), field.getName().concat(this.dateSuffix[1])};
                for (int i = 0, len = conNames.length; i < len; i++) {
                    sb.append(sj).append(sj).append("<if test=\"null!=%s and ''!=%s\">".replaceAll("%s", conNames[i])).append(othLine);
                    sb.append(sj).append(sj).append(blank).append(" AND ").append(this.getFieldAlias(field, alias)).append(blank).append(i == 0 ? ge : le).append(blank).append("CONCAT(").append(sharpS).append(conNames[i]).append(sharpE).append(comer).append("' ").append(i == 0 ? "00:00:00" : "23:59:59").append("'").append(")").append(othLine);
                    sb.append(sj).append(sj).append("</if>").append(othLine);
                }

            }
        }
        return sb.substring(0);
    }

}
