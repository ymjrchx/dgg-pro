package net.dgg.dqc.backservice.entity;

import java.util.Date;

/**
 * @author 刘阳
 * @ClassName <ImportDataRecord>
 * @despriction 数据导入记录   数据表映射规则  t_import_data_record_${index}_${type}  说明：index 和 type 是实体中的属性值
 * <p>
 * @create 2018/08/23 8:58
 **/
public class ImportDataRecord {
    private Long id;
    private String esServer; // es 地址
    private String index; // es index
    private String type;  // es index 下的 type
    private Date importStartTime; // 开始导入时间
    private Date importEndTime; // 结束导入时间

    private Date queryTimeStart; // 查询条件起始时间
    private Date queryTimeEnd; //  查询条件结束时间

    private Long queryCount; // 总数

    private Integer complete; // 是否导入完成 0否 1是

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsServer() {
        return esServer;
    }

    public void setEsServer(String esServer) {
        this.esServer = esServer;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getImportStartTime() {
        return importStartTime;
    }

    public void setImportStartTime(Date importStartTime) {
        this.importStartTime = importStartTime;
    }

    public Date getImportEndTime() {
        return importEndTime;
    }

    public void setImportEndTime(Date importEndTime) {
        this.importEndTime = importEndTime;
    }

    public Date getQueryTimeStart() {
        return queryTimeStart;
    }

    public void setQueryTimeStart(Date queryTimeStart) {
        this.queryTimeStart = queryTimeStart;
    }

    public Date getQueryTimeEnd() {
        return queryTimeEnd;
    }

    public void setQueryTimeEnd(Date queryTimeEnd) {
        this.queryTimeEnd = queryTimeEnd;
    }

    public Long getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(Long queryCount) {
        this.queryCount = queryCount;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }
}
