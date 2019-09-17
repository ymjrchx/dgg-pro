package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

import net.dgg.yk.platform.backend.business.a1.dependencies.PTConst;

/**
 * Created by jiangsh on 2018/8/9 17:29
 */
public class ErrorRecord {

    private String url;
    private String name;
    private String reason;

    public ErrorRecord() {
    }

    public ErrorRecord(String url, String name, String reason, String type) {
        if (type.equals("EqcDetailData")) {
            url = url.replace("pre", PTConst.EQC_WEBSITE_URL).replace("su", PTConst.HTML);
        } else if (type.equals("QccDetailData")) {
            url = url.replace("pre", PTConst.QCC_WEBSITE_URL).replace("su", PTConst.HTML);
        } else if (type.equals("ResumeData")) {
            url = url.replace("pre", PTConst.RESUME_WEBSITE_URL).replace("su", PTConst.HTML);
        } else if (type.equals("ZcglData")) {
            url = url.replace("pre", PTConst.ZCGL_WEBSITE_URL).replace("su", PTConst.HTML);
        }
        this.url = url;
        this.name = name;
        this.reason = reason;
    }
}
