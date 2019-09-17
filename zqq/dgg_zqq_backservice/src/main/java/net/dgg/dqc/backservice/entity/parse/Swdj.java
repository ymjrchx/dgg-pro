package net.dgg.dqc.backservice.entity.parse;

/**
 * 税务登记号查询
 * Created by jiangsh on 2018/6/4 16:25
 */
public class Swdj {
    private String name; //企业名称
    private String creditCode; //社会统一信用代码（纳税人识别号）
    private String econKind; //企业类型
    private String status; //企业状态
    private String address; //地址
    private String tel; //联系电话
    private String bank; //开户行
    private String bankAccount; //开户行账号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getEconKind() {
        return econKind;
    }

    public void setEconKind(String econKind) {
        this.econKind = econKind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
