package net.dgg.bigdata.manager.user.dao;

import net.dgg.bigdata.common.entity.UserEntity;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 09:29
 * @Description:
 */
public class UserRecorderDAOProvider {

    public String insertUserEntitySql(UserEntity userEntity) {
        return new SQL() {{
            INSERT_INTO("user_info");
            VALUES("user_id", "#{userId}");
            VALUES("locked", "#{locked}");
            VALUES("login_name", "#{loginName}");
            VALUES("login_pwd", "#{loginPwd}");
            VALUES("org_id", "#{orgId}");
            VALUES("updatetime", "#{updatetime}");

            if (!StringUtils.isEmpty(userEntity.getRealName()))
                VALUES("real_name", "#{realName}");
            if (!StringUtils.isEmpty(userEntity.getPhone()))
                VALUES("phone", "#{phone}");
            if (!StringUtils.isEmpty(userEntity.getSex()))
                VALUES("sex", "#{sex}");
            if (!StringUtils.isEmpty(userEntity.getDescription()))
                VALUES("description", "#{description}");
            if (!StringUtils.isEmpty(userEntity.getWorkarea()))
                VALUES("workarea", "#{workarea}");
            if (!StringUtils.isEmpty(userEntity.getEmail()))
                VALUES("email", "#{email}");
            if (!StringUtils.isEmpty(userEntity.getDimissiontime()))
                VALUES("dimissiontime", "#{dimissiontime}");
            if (!StringUtils.isEmpty(userEntity.getEntrydate()))
                VALUES("entrydate", "#{entrydate}");
            if (!StringUtils.isEmpty(userEntity.getWorkage()))
                VALUES("workage", "#{workage}");
        }}.toString();
    }

    public String updateUserEntitySql(UserEntity userEntity) {
        return new SQL() {{
            UPDATE("user_info");
            SET("locked=#{locked}");
            SET("login_name=#{loginName}");
            SET("login_pwd=#{loginPwd}");
            SET("org_id=#{orgId}");
            SET("updatetime=#{updatetime}");

            if (!StringUtils.isEmpty(userEntity.getRealName()))
                SET("real_name=#{realName}");
            if (!StringUtils.isEmpty(userEntity.getPhone()))
                SET("phone=#{phone}");
            if (!StringUtils.isEmpty(userEntity.getSex()))
                SET("sex=#{sex}");
            if (!StringUtils.isEmpty(userEntity.getDescription()))
                SET("description=#{description}");
            if (!StringUtils.isEmpty(userEntity.getWorkarea()))
                SET("workarea=#{workarea}");
            if (!StringUtils.isEmpty(userEntity.getEmail()))
                SET("email=#{email}");
            if (!StringUtils.isEmpty(userEntity.getDimissiontime()))
                SET("dimissiontime=#{dimissiontime}");
            if (!StringUtils.isEmpty(userEntity.getEntrydate()))
                SET("entrydate=#{entrydate}");
            if (!StringUtils.isEmpty(userEntity.getWorkage()))
                SET("workage=#{workage}");

            WHERE("user_id=#{userId}");
        }}.toString();
    }

    public String findUserEntityListByKeyWordsSql(Map keyWords) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT user_id as userId, locked as locked, login_name as loginName, login_pwd as loginPwd, real_name as")
                .append("realName, phone as phone, org_id as orgId, sex as sex, description as description, workarea as workarea, email")
                .append("as email, dimissiontime as dimissiontime, updatetime as updatetime, entrydate as entrydate FROM user_info ")
                .append("where 1=1 ");
        if (keyWords != null && keyWords.containsKey("locked") && StringUtils.isEmpty(keyWords.get("locked"))) {
            stringBuffer.append("and locked = #{locked} ");
        }
        if (keyWords != null && keyWords.containsKey("loginName") && StringUtils.isEmpty(keyWords.get("loginName"))) {
            stringBuffer.append("and login_name = #{loginName} ");
        }
        if (keyWords != null && keyWords.containsKey("realName") && StringUtils.isEmpty(keyWords.get("realName"))) {
            stringBuffer.append("and real_name like concat('%',#{realName},'%') ");
        }
        if (keyWords != null && keyWords.containsKey("phone") && StringUtils.isEmpty(keyWords.get("phone"))) {
            stringBuffer.append("and phone = #{phone}");
        }
        if (keyWords != null && keyWords.containsKey("orgId") && StringUtils.isEmpty(keyWords.get("orgId"))) {
            stringBuffer.append("and org_id = #{orgId}");
        }
        if (keyWords != null && keyWords.containsKey("email") && StringUtils.isEmpty(keyWords.get("email"))) {
            stringBuffer.append("and email = #{email}");
        }
        return stringBuffer.toString();
    }

    public String findUserDTOByOrgIdWithPageSql(Map keyWords) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("SELECT DISTINCT(a.user_id) AS id, a.login_name AS loginName, a.real_name AS NAME, a.sex AS sex, b.name AS orgName, a.locked AS locked ")
                .append("FROM user_info a LEFT JOIN org_main b ON a.org_id = b.org_id ")
                .append("LEFT JOIN user_role c ON a.user_id = c.user_id LEFT JOIN role d ON c.role_id = d.role_id ")
                .append("where 1=1");
        if (keyWords != null && keyWords.containsKey("orgId") && StringUtils.isEmpty(keyWords.get("orgId"))) {
            stringBuffer.append("and a.org_id = #{orgId} ");
        }
        if (keyWords != null && keyWords.containsKey("loginName") && StringUtils.isEmpty(keyWords.get("loginName"))) {
            stringBuffer.append("and a.login_name=#{loginName} ");
        }
        if (keyWords != null && keyWords.containsKey("userId") && StringUtils.isEmpty(keyWords.get("userId"))) {
            stringBuffer.append("and a.user_id=#{userId}");
        }
        if (keyWords != null && keyWords.containsKey("userName") && StringUtils.isEmpty(keyWords.get("userName"))) {
            stringBuffer.append("and a.real_name=#{userName}");
        }
        return stringBuffer.toString();
    }

    public String findUserDTOByKeyWordsWithPageSql(Map keyWords) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT DISTINCT(a.user_id) AS id, a.login_name AS loginName, a.real_name AS NAME, a.sex AS sex, b.name AS orgName, a.locked AS locked ")
                .append("FROM user_info a LEFT JOIN org_main b ON a.org_id = b.org_id ")
                .append("LEFT JOIN user_role c ON a.user_id = c.user_id LEFT JOIN role d ON c.role_id = d.role_id ")
                .append("where (a.login_name LIKE concat('%',#{mainKey},'%') OR a.real_name LIKE concat('%',#{mainKey},'%')) ");
        if (keyWords != null && keyWords.containsKey("selectCode") && "1".equals(keyWords.get("selectCode"))
                && keyWords.containsKey("orgId") && !StringUtils.isEmpty(keyWords.get("orgId"))) {
            stringBuffer.append("AND a.org_id=#{orgId} ");
        }
        if (keyWords != null && keyWords.containsKey("selectCode") && "3".equals(keyWords.get("selectCode"))
                && keyWords.containsKey("orgId") && !StringUtils.isEmpty(keyWords.get("orgId"))) {
            stringBuffer.append("AND a.org_id IN (SELECT a.organization_id FROM org_closure a JOIN org_main b ON a.organization_id=b.org_id WHERE ancestor_id=#{orgId}) ");
        }

        return stringBuffer.toString();
    }
    public String findUserTotalByKeyWordsSql(Map keyWords) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT COUNT(1) FROM user_info a LEFT JOIN org_main b ON a.org_id = b.org_id ")
                .append("LEFT JOIN user_role c ON a.user_id = c.user_id LEFT JOIN role d ON c.role_id = d.role_id ")
                .append("where (a.login_name LIKE concat('%',#{keyWords.mainKey},'%') OR a.real_name LIKE concat('%',#{keyWords.mainKey},'%') ) ");
        if (keyWords != null && keyWords.containsKey("selectCode") && "1".equals(keyWords.get("selectCode"))
                && keyWords.containsKey("orgId") && !StringUtils.isEmpty(keyWords.get("orgId"))) {
            stringBuffer.append("AND a.org_id=#{orgId}} ");
        }
        if (keyWords != null && keyWords.containsKey("selectCode") && "3".equals(keyWords.get("selectCode"))
                && keyWords.containsKey("orgId") && !StringUtils.isEmpty(keyWords.get("orgId"))) {
            stringBuffer.append("AND a.org_id IN (SELECT a.organization_id FROM org_closure a JOIN org_main b ON ")
                    .append("a.organization_id=b.org_id WHERE ancestor_id=#{keyWords.orgId}) ");
        }

        return stringBuffer.toString();
    }


}
