package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 失信被执行人信息-获取失信人详细信息
 * Created by jiangsh on 2018/6/5 11:43
 */
public class Sxbzxr {
    private String id; //主键
    private String sourceid; //官网主键
    private String uniqueno; //唯一编号
    private String name; //被执行人姓名/名称
    private String liandate; //立案时间
    private String anno; //案号
    private String orgno; //身份证号码/组织机构代码
    private String ownername; //法定代表人或者负责人姓名
    private String executegov; //执行法院
    private String province; //所在省份缩写
    private String executeunite; //做出执行依据单位
    private String yiwu; //生效法律文书确定的义务
    private String executestatus; //被执行人的履行情况
    private String actionremark; //失信被执行人行为具体情形
    private String publicdate; //发布时间
    private String age; //年龄
    private String sexy; //性别
    private String updatedate; //记录更新时间
    private String executeno; //执行依据文号
    private String performedpart; //已履行
    private String unperformpart; //未履行
    private String orgType; //组织类型（1：自然人，2：企业，3：社会组织，空白：无法判定）
    private String orgTypeName; //组织类型名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getUniqueno() {
        return uniqueno;
    }

    public void setUniqueno(String uniqueno) {
        this.uniqueno = uniqueno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLiandate() {
        return liandate;
    }

    public void setLiandate(String liandate) {
        this.liandate = liandate;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getOrgno() {
        return orgno;
    }

    public void setOrgno(String orgno) {
        this.orgno = orgno;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getExecutegov() {
        return executegov;
    }

    public void setExecutegov(String executegov) {
        this.executegov = executegov;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getExecuteunite() {
        return executeunite;
    }

    public void setExecuteunite(String executeunite) {
        this.executeunite = executeunite;
    }

    public String getYiwu() {
        return yiwu;
    }

    public void setYiwu(String yiwu) {
        this.yiwu = yiwu;
    }

    public String getExecutestatus() {
        return executestatus;
    }

    public void setExecutestatus(String executestatus) {
        this.executestatus = executestatus;
    }

    public String getActionremark() {
        return actionremark;
    }

    public void setActionremark(String actionremark) {
        this.actionremark = actionremark;
    }

    public String getPublicdate() {
        return publicdate;
    }

    public void setPublicdate(String publicdate) {
        this.publicdate = publicdate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSexy() {
        return sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getExecuteno() {
        return executeno;
    }

    public void setExecuteno(String executeno) {
        this.executeno = executeno;
    }

    public String getPerformedpart() {
        return performedpart;
    }

    public void setPerformedpart(String performedpart) {
        this.performedpart = performedpart;
    }

    public String getUnperformpart() {
        return unperformpart;
    }

    public void setUnperformpart(String unperformpart) {
        this.unperformpart = unperformpart;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }
}
