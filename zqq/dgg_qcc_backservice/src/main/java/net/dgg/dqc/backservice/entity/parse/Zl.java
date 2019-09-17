package net.dgg.dqc.backservice.entity.parse;

/**
 * 专利查询-专利多重查询
 * Created by jiangsh on 2018/6/5 11:26
 */
public class Zl {
    private String Id;
    private String IPCList;
    private String ApplicationNumber;
    private String ApplicationDate;
    private String PublicationNumber;
    private String PublicationDate;
    private String KindCodeDesc;
    private String LegalStatusDesc;
    private String IPCDesc;
    private String Title;
    private String InventorStringList;
    private String Agency;
    private String AssigneestringList;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIPCList() {
        return IPCList;
    }

    public void setIPCList(String IPCList) {
        this.IPCList = IPCList;
    }

    public String getApplicationNumber() {
        return ApplicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        ApplicationNumber = applicationNumber;
    }

    public String getApplicationDate() {
        return ApplicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        ApplicationDate = applicationDate;
    }

    public String getPublicationNumber() {
        return PublicationNumber;
    }

    public void setPublicationNumber(String publicationNumber) {
        PublicationNumber = publicationNumber;
    }

    public String getPublicationDate() {
        return PublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        PublicationDate = publicationDate;
    }

    public String getKindCodeDesc() {
        return KindCodeDesc;
    }

    public void setKindCodeDesc(String kindCodeDesc) {
        KindCodeDesc = kindCodeDesc;
    }

    public String getLegalStatusDesc() {
        return LegalStatusDesc;
    }

    public void setLegalStatusDesc(String legalStatusDesc) {
        LegalStatusDesc = legalStatusDesc;
    }

    public String getIPCDesc() {
        return IPCDesc;
    }

    public void setIPCDesc(String IPCDesc) {
        this.IPCDesc = IPCDesc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInventorStringList() {
        return InventorStringList;
    }

    public void setInventorStringList(String inventorStringList) {
        InventorStringList = inventorStringList;
    }

    public String getAgency() {
        return Agency;
    }

    public void setAgency(String agency) {
        Agency = agency;
    }

    public String getAssigneestringList() {
        return AssigneestringList;
    }

    public void setAssigneestringList(String assigneestringList) {
        AssigneestringList = assigneestringList;
    }
}
