package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * Created by jiangsh on 2018/7/23 11:32
 */
public class Fptt {
    private String name; //企业名称
    private String no; //税号
    private String address; //企业地址
    private String phoneNo; //电话号码
    private String bank; //开户银行
    private String bankAccount; //银行账户

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        if (phoneNo == null) phoneNo = "未知";
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBank() {
        if (bank == null) bank = "未知";
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        if (bankAccount == null) bankAccount = "未知";
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
