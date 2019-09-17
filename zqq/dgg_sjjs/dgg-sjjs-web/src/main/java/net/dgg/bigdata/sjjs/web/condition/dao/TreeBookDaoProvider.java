package net.dgg.bigdata.sjjs.web.condition.dao;

import net.dgg.bigdata.common.entity.TreeBook;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/7 17:27
 * @Description:
 */
public class TreeBookDaoProvider {

    public String getLevelsByIdsSql(Map map) {
        List<Long> bookIdList = (List<Long>) map.get("bookIdList");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select b.levels from t_qcc_tree_book b where b.id in ( ");
        MessageFormat messageFormat = new MessageFormat("#'{'bookIdList[{0}]}");
        if (map != null && map.containsKey("bookIdList")) {
            for (int i = 0; i < bookIdList.size(); i++) {
                stringBuffer.append(messageFormat.format(new Object[]{i}));
                if (i < bookIdList.size() - 1)
                    stringBuffer.append(",");
            }
        }
        stringBuffer.append(" )");
        return stringBuffer.toString();
    }

    public String getTreeBooksByConditionWithPageSql(Map map) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, ")
                .append("create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name ")
                .append("from t_qcc_tree_book where 1=1 ");
        if (map != null && map.containsKey("id") && !StringUtils.isEmpty(map.containsKey("id"))) {
            stringBuffer.append("and pid=#{id} ");
        }
        if (map != null && map.containsKey("code") && !StringUtils.isEmpty(map.containsKey("code"))) {
            stringBuffer.append("and code like CONCAT('%',#{code},'%') ");
        }
        if (map != null && map.containsKey("name") && !StringUtils.isEmpty(map.containsKey("name"))) {
            stringBuffer.append("and name like CONCAT('%',#{name},'%') ");
        }
        stringBuffer.append(" order by sort");
        return stringBuffer.toString();
    }

    public String getTreeBooksByConditionSql(Map map) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, ")
                .append("create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name ")
                .append("from t_qcc_tree_book where 1=1 ");
        if (map != null && map.containsKey("id") && !StringUtils.isEmpty(map.containsKey("id"))) {
            stringBuffer.append("and pid=#{id} ");
        }
        if (map != null && map.containsKey("code") && !StringUtils.isEmpty(map.containsKey("code"))) {
            stringBuffer.append("and code = #{code} ");
        }
        if (map != null && map.containsKey("pcode") && !StringUtils.isEmpty(map.containsKey("pcode"))) {
            stringBuffer.append("and pcode = #{pcode} ");
        }
        if (map != null && map.containsKey("name") && !StringUtils.isEmpty(map.containsKey("name"))) {
            stringBuffer.append("and name = #{name} ");
        }
        stringBuffer.append(" order by sort");
        return stringBuffer.toString();
    }

    public String insertSql(TreeBook book) {
        return new SQL() {{
            INSERT_INTO("t_qcc_tree_book");
            VALUES("id", "#{id}");
            VALUES("pid", "#{pid}");
            VALUES("pcode", "#{pcode}");
            VALUES("code", "#{code}");
            VALUES("name", "#{name}");
            VALUES("description", "#{description}");
            VALUES("levels", "#{levels}");
            VALUES("sort", "#{sort}");
            VALUES("status", "#{status}");
            VALUES("ext1", "#{ext1}");
            VALUES("ext2", "#{ext2}");
            VALUES("ext3", "#{ext3}");
            VALUES("ext4", "#{ext4}");
            VALUES("ext5", "#{ext5}");
            VALUES("create_time", "#{createTime}");
            VALUES("creater_id", "#{createrId}");
            VALUES("creater_org_id", "#{createrOrgId}");
            VALUES("creater_name", "#{createrName}");
            VALUES("update_time", "#{updateTime}");
            VALUES("updater_id", "#{updaterId}");
            VALUES("updater_org_id", "#{updaterOrgId}");
            VALUES("updater_name", "#{updaterName}");
        }}.toString();
    }

    public String updateByPrimaryKeySql(TreeBook book) {
        return new SQL() {{
            UPDATE("t_qcc_tree_book");
            SET("id=#{id}");
            SET("pid=#{pid}", "");
            SET("pcode=#{pcode}");
            SET("code=#{code}");
            SET("name=#{name}");
            SET("description=#{description}");
            SET("levels=#{levels}");
            SET("sort=#{sort}");
            SET("status=#{status}");
            SET("ext1=#{ext1}");
            SET("ext2=#{ext2}");
            SET("ext3=#{ext3}");
            SET("ext4=#{ext4}");
            SET("ext5=#{ext5}");
            SET("create_time=#{createTime}");
            SET("creater_id=#{createrId}");
            SET("creater_org_id=#{createrOrgId}");
            SET("creater_name=#{createrName}");
            SET("update_time=#{updateTime}");
            SET("updater_id=#{updaterId}");
            SET("updater_org_id=#{updaterOrgId}");
            SET("updater_name=#{updaterName}");
        }}.toString();
    }

    public static String getTreeBookCountByConditionSql(TreeBook book) {
        return new SQL() {{
            SELECT("count(1)");
            FROM("t_qcc_tree_book");
            WHERE("1=1");
            if (book.getId() != null)
                AND().WHERE("id = #{id}");
            if (book.getPid() != null)
                AND().WHERE("pid = #{pid}");
            if (!StringUtils.isEmpty(book.getPcode()))
                AND().WHERE("pcode = #{pcode}");
            if (!StringUtils.isEmpty(book.getCode()))
                AND().WHERE("code = #{code}");
            if (!StringUtils.isEmpty(book.getName()))
                AND().WHERE("name = #{name}");
            if (!StringUtils.isEmpty(book.getDescription()))
                AND().WHERE("description = #{description}");
            if (!StringUtils.isEmpty(book.getLevels()))
                AND().WHERE("levels = #{levels}");
            if (book.getSort() != null)
                AND().WHERE("sort = #{sort}");
            if (book.getStatus() != null)
                AND().WHERE("status = #{status}");
        }}.toString();
    }

    public String getTreeBookListByCodeSql(Map map) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select id, pid, pcode, code, name, description, levels, sort, status, ext1, ext2, ext3, ext4, ext5, " +
                "create_time, creater_id, creater_org_id, creater_name, update_time, updater_id, updater_org_id, updater_name " +
                "from t_qcc_tree_book  where  1=1 ");
        if (map != null && map.containsKey("type") && map.get("type") != null && (Integer) map.get("type") == 1) {
            stringBuffer.append("and pcode = #{code} ");
        }
        if (map != null && map.containsKey("type") && map.get("type") != null && (Integer) map.get("type") == 2) {
            stringBuffer.append("and levels like CONCAT((select CONCAT(a.levels,'_',a.id) from t_qcc_tree_book a where a.code=  #{code} ),'%') ");
            if (map != null && map.containsKey("levels") && !StringUtils.isEmpty(map.get("levels"))) {
                stringBuffer.append("and (length(levels)-length(replace(levels,'_',''))-2) &lt;=#{level}");
            }
        }
        if (map != null && map.containsKey("type") && map.get("type") != null && (Integer) map.get("type") == 3) {
            stringBuffer.append("and (levels like CONCAT((select CONCAT(b.levels,'_',b.id) from t_qcc_tree_book b where b.code=\n" +
                    "      #{code} ),'%') or code = #{code}) ");
            if (map != null && map.containsKey("level") && map.get("level") != null ) {
                stringBuffer.append("and (length(levels)-length(replace(levels,'_',''))-1) &lt;=#{level} ");
            }
        }
        if (map != null && map.containsKey("status") && map.get("status") != null ) {
            stringBuffer.append("and status = #{status} ");
        }
        stringBuffer.append("order by sort asc");
        return stringBuffer.toString();
    }


}
