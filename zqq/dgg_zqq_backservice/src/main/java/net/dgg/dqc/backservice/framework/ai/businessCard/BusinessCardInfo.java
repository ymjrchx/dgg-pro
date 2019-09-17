package net.dgg.dqc.backservice.framework.ai.businessCard;


import net.dgg.dqc.backservice.framework.mongo.AbstractModel;

/**
 * 名片信息
 * Created by jiangsh on 2018/5/9
 */
public class BusinessCardInfo extends AbstractModel {

    private String id;
    private String companyName; //公司名称
    private String job; //职位
    private String name; //姓名
    private String phone; //手机号

    private String source; //源文件

    public BusinessCardInfo() {
    }

    public BusinessCardInfo(String id, String companyName, String job, String name, String phone, String source) {
        this.id = id;
        this.companyName = companyName;
        this.job = job;
        this.name = name;
        this.phone = phone;
        this.source = source;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
