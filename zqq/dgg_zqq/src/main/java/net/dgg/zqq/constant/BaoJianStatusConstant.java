package net.dgg.zqq.constant;

/**
 * @author 刘阳
 * @ClassName <BaoJianStatusConstant>
 * @despriction 报件状态
 * @create 2018/11/07 15:37
 **/
public interface BaoJianStatusConstant {
    /**
     * 待报件
     */
    String WAITING = "baojian_status_01";

    /**
     * 已发送报件
     */
    String SENDED = "baojian_status_00";

    /**
     * 报件审核失败
     */
    String CHECK_FAIL = "baojian_status_02";

    /**
     * 报件审核成功
     */
    String CHECK_SUCCESS = "baojian_status_03";

    /**
     * 报件失败
     */
    String BAO_JIAN_FAIL = "baojian_status_04";

    /**
     * 报件成功
     */
    String BAO_JIAN_SUCCESS = "baojian_status_05";

}
