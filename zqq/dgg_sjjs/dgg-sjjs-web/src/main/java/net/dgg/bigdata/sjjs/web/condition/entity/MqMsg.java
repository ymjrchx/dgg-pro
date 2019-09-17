package net.dgg.bigdata.sjjs.web.condition.entity;

import net.dgg.base.baseDao.Table;

import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2019/1/2 10:53
 * @Description:
 */
@Table(name = "yk_mq_mes")
public class MqMsg {
    private Long id;

    private String message;

    private String error_reason;

    private Date date;

    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError_reason() {
        return error_reason;
    }

    public void setError_reason(String error_reason) {
        this.error_reason = error_reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
