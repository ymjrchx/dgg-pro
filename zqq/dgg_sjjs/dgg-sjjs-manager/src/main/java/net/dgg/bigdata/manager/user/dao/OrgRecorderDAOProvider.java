package net.dgg.bigdata.manager.user.dao;

import net.dgg.bigdata.manager.user.entity.OrganizationEntity;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 09:03
 * @Description:
 */
public class OrgRecorderDAOProvider {

    public String insertOrgEntitySql(OrganizationEntity organizationEntity) {
        return new SQL() {
            {
                INSERT_INTO("org_main");
                VALUES("org_id", "#{orgId}");
                VALUES("sort", "#{sort}");
                VALUES("leader_id", "#{leaderId}");
                VALUES("enabled", "#{enabled}");
                VALUES("updatetime", "#{updateTime}");
                VALUES("name", "#{name}");
                if (!StringUtils.isEmpty(organizationEntity.getCode()))
                    VALUES("code", "#{code}");
                if (!StringUtils.isEmpty(organizationEntity.getDescription()))
                    VALUES("description", "#{description}");

            }
        }.toString();
    }

    public String updateOrgEntitySql(OrganizationEntity organizationEntity) {
        return new SQL() {
            {
                UPDATE("org_main");
                SET(" name=#{name}");
                SET(" sort=#{sort}");
                SET(" leader_id=#{leaderId}");
                SET(" enabled=#{enabled}");
                SET(" updatetime=#{updateTime}");
                if (!StringUtils.isEmpty(organizationEntity.getCode()))
                    SET(" code=#{code}");
                if (!StringUtils.isEmpty(organizationEntity.getDescription()))
                    SET(" description=#{description}");
                WHERE("WHERE org_id=#{orgId}");
            }
        }.toString();
    }

    public String findOrgEntityListByKeyWordsSql(Map keyWords) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT org_id AS orgId, code AS code, name AS name, description AS description, ")
                .append("sort AS sort, leader_id AS leaderId, enabled AS enabled, updatetime AS updatetime FROM org_main")
                .append("where 1=1 ");
        if (keyWords != null && keyWords.containsKey("code") && StringUtils.isEmpty(keyWords.get("code"))) {
            stringBuffer.append("and code = #{code} ");
        }
        if (keyWords != null && keyWords.containsKey("name") && StringUtils.isEmpty(keyWords.get("name"))) {
            stringBuffer.append("and name like CONCAT('%',CONCAT(#{name},'%')) ");
        }
        if (keyWords != null && keyWords.containsKey("leaderId") && StringUtils.isEmpty(keyWords.get("leaderId"))) {
            stringBuffer.append("and leader_id = #{leaderId} ");
        }
        if (keyWords != null && keyWords.containsKey("enabled") && StringUtils.isEmpty(keyWords.get("enabled"))) {
            stringBuffer.append("and enabled = #{enabled} ");
        }
        if (keyWords != null && keyWords.containsKey("start") && StringUtils.isEmpty(keyWords.get("start"))
                && keyWords.containsKey("limit") && StringUtils.isEmpty(keyWords.get("limit"))) {
            stringBuffer.append("limit #{start},#{limit} ");
        }
        return stringBuffer.toString();
    }
}
