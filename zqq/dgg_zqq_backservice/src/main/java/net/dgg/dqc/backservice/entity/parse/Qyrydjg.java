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
    private String name; //企业名称
    private String regNo; //注册号
    private String regCap; //注册资本
    private String regCapCur; //注册资本币种
    private String status; //企业状态
    private String ecoKind; //企业类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegCap() {
        return regCap;
    }

    public void setRegCap(String regCap) {
        this.regCap = regCap;
    }

    public String getRegCapCur() {
        return regCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        this.regCapCur = regCapCur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEcoKind() {
        return ecoKind;
    }

    public void setEcoKind(String ecoKind) {
        this.ecoKind = ecoKind;
    }
}

class CIAForeignInvestments{
    private String subConAmt; //认缴出资额
    private String subCurrency; //认缴出资币种
    private String ecoKind; //企业类型
    private String name; //企业名称
    private String regNo; //注册号
    private String regCap; //注册资本
    private String regCapCur; //注册资本币种
    private String status; //企业状态

    public String getSubConAmt() {
        return subConAmt;
    }

    public void setSubConAmt(String subConAmt) {
        this.subConAmt = subConAmt;
    }

    public String getSubCurrency() {
        return subCurrency;
    }

    public void setSubCurrency(String subCurrency) {
        this.subCurrency = subCurrency;
    }

    public String getEcoKind() {
        return ecoKind;
    }

    public void setEcoKind(String ecoKind) {
        this.ecoKind = ecoKind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegCap() {
        return regCap;
    }

    public void setRegCap(String regCap) {
        this.regCap = regCap;
    }

    public String getRegCapCur() {
        return regCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        this.regCapCur = regCapCur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class CIAForeignOffices{
    private String position; //职位
    private String ecoKind; //企业类型
    private String name; //企业名称
    private String regNo; //注册号
    private String regCap; //注册资本
    private String regCapCur; //注册资本币种
    private String status; //企业状态

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEcoKind() {
        return ecoKind;
    }

    public void setEcoKind(String ecoKind) {
        this.ecoKind = ecoKind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegCap() {
        return regCap;
    }

    public void setRegCap(String regCap) {
        this.regCap = regCap;
    }

    public String getRegCapCur() {
        return regCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        this.regCapCur = regCapCur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}