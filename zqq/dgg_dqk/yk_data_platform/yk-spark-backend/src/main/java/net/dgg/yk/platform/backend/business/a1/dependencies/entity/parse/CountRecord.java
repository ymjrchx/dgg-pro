package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * Created by jiangsh on 2018/8/23 14:15
 */
public class CountRecord {
    private String comType; //类别
    private String count; //条数

    public CountRecord(String comType, String count) {
        this.comType = comType;
        this.count = count;
    }

    public CountRecord() {
    }
}
