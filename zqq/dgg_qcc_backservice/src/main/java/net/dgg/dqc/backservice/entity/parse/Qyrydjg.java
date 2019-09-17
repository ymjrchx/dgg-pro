package net.dgg.dqc.backservice.entity.parse;

import java.util.List;

/**
 * 企业人员董监高信息
 * Created by jiangsh on 2018/6/5 11:50
 */
public class Qyrydjg {
    private List<CIACompanyLegal> cIACompanyLegal; //担任法人公司信息
    private List<CIAForeignInvestments> cIAForeignInvestments; //对外投资信息
    private List<CIAForeignOffices> cIAForeignOffices; //在外任职信息

    public List<CIACompanyLegal> getcIACompanyLegal() {
        return cIACompanyLegal;
    }

    public void setcIACompanyLegal(List<CIACompanyLegal> cIACompanyLegal) {
        this.cIACompanyLegal = cIACompanyLegal;
    }

    public List<CIAForeignInvestments> getcIAForeignInvestments() {
        return cIAForeignInvestments;
    }

    public void setcIAForeignInvestments(List<CIAForeignInvestments> cIAForeignInvestments) {
        this.cIAForeignInvestments = cIAForeignInvestments;
    }

    public List<CIAForeignOffices> getcIAForeignOffices() {
        return cIAForeignOffices;
    }

    public void setcIAForeignOffices(List<CIAForeignOffices> cIAForeignOffices) {
        this.cIAForeignOffices = cIAForeignOffices;
    }
}

class CIACompanyLegal{
    private String Name; //企业名称
    private String RegNo; //注册号
    private String RegCap; //注册资本
    private String RegCapCur; //注册资本币种
    private String Status; //企业状态
    private String EcoKind; //企业类型

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getRegCap() {
        return RegCap;
    }

    public void setRegCap(String regCap) {
        RegCap = regCap;
    }

    public String getRegCapCur() {
        return RegCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        RegCapCur = regCapCur;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEcoKind() {
        return EcoKind;
    }

    public void setEcoKind(String ecoKind) {
        EcoKind = ecoKind;
    }
}

class CIAForeignInvestments{
    private String SubConAmt; //认缴出资额
    private String SubCurrency; //认缴出资币种
    private String EcoKind; //企业类型
    private String Name; //企业名称
    private String RegNo; //注册号
    private String RegCap; //注册资本
    private String RegCapCur; //注册资本币种
    private String Status; //企业状态

    public String getSubConAmt() {
        return SubConAmt;
    }

    public void setSubConAmt(String subConAmt) {
        SubConAmt = subConAmt;
    }

    public String getSubCurrency() {
        return SubCurrency;
    }

    public void setSubCurrency(String subCurrency) {
        SubCurrency = subCurrency;
    }

    public String getEcoKind() {
        return EcoKind;
    }

    public void setEcoKind(String ecoKind) {
        EcoKind = ecoKind;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getRegCap() {
        return RegCap;
    }

    public void setRegCap(String regCap) {
        RegCap = regCap;
    }

    public String getRegCapCur() {
        return RegCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        RegCapCur = regCapCur;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

class CIAForeignOffices{
    private String Position; //职位
    private String EcoKind; //企业类型
    private String Name; //企业名称
    private String RegNo; //注册号
    private String RegCap; //注册资本
    private String RegCapCur; //注册资本币种
    private String Status; //企业状态

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getEcoKind() {
        return EcoKind;
    }

    public void setEcoKind(String ecoKind) {
        EcoKind = ecoKind;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getRegCap() {
        return RegCap;
    }

    public void setRegCap(String regCap) {
        RegCap = regCap;
    }

    public String getRegCapCur() {
        return RegCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        RegCapCur = regCapCur;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}