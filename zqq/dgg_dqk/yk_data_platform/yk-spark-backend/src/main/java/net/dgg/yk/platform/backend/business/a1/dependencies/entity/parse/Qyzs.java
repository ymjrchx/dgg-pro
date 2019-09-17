package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 企业证书查询
 * Created by jiangsh on 2018/6/6 09:59
 */
public class Qyzs {
    private String id; //主键
    private String name; //产品名称
    private String type; //证书类型
    private String startDate; //证书生效时间
    private String endDate; //证书截止日期
    private String no; //证书编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
