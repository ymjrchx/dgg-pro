package net.dgg.bigdata.sjjs.web.constant;

/**
 * @ClassName: CluesStatus
 * @Description: 线索状态
 * @Author: jiangsh
 * @Date: 2018/12/27 11:09
 */
public interface CluesStatus {

    /**
     * 线索领取
     */
    String CLUES_GET = "领取";

    /**
     * 线索处理
     */
    String CLUES_PROCESS = "处理";

    /**
     * 线索分配
     */
    /*String CLUES_PROCESS = "分配";*/

    /**
     * 转至公海
     */
    String CLUES_SEAS = "公海";


    /**
     * 线索结果状态
     */
    String CLUES_HAS_SUCCESS = "已成交";
    String CLUES_HAS_IDEA = "有意向";
    String CLUES_HASNOT_SURE = "未确认";

}
