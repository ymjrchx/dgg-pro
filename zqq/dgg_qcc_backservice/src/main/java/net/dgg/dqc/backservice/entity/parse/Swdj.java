package net.dgg.dqc.backservice.entity.parse;

/**
 * 税务登记号查询
 * Created by jiangsh on 2018/6/4 16:25
 */
public class Swdj {
    private String Name; //企业名称
    private String CreditCode; //社会统一信用代码（纳税人识别号）
    private String EconKind; //企业类型
    private String Status; //企业状态
    private String Address; //地址
    private String Tel; //联系电话
    private String Bank; //开户行
    private String BankAccount; //开户行账号

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreditCode() {
        return CreditCode;
    }

    public void setCreditCode(String creditCode) {
        CreditCode = creditCode;
    }

    public String getEconKind() {
        return EconKind;
    }

    public void setEconKind(String econKind) {
        EconKind = econKind;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }
}
