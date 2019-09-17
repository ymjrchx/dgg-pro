package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

import java.util.List;

/**
 * Created by wu on 2017-11-03.
 */
public class YearReportResultCallBack {

    private String status;

    private String message;

    private List<YearReportResult> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<YearReportResult> getResult() {
        return result;
    }

    public void setResult(List<YearReportResult> result) {
        this.result = result;
    }
}
