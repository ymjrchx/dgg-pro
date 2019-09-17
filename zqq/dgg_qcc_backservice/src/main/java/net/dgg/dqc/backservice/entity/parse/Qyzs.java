package net.dgg.dqc.backservice.entity.parse;

/**
 * 企业证书查询
 * Created by jiangsh on 2018/6/6 09:59
 */
public class Qyzs {
    private String Id; //主键
    private String Name; //产品名称
    private String Type; //证书类型
    private String StartDate; //证书生效时间
    private String EndDate; //证书截止日期
    private String No; //证书编号

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }
}
