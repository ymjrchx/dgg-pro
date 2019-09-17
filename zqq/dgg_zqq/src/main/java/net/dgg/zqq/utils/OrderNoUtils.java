package net.dgg.zqq.utils;

import net.fblock.core.common.KeyWorker;

/**
 * @author 刘阳
 * @ClassName <OrderNoUtils>
 * @despriction 订单编号生成器
 * @create 2018/10/11 10:59
 **/
public interface OrderNoUtils {

    static String getOrderNo() {
        String date = DateUtils.getCurrentByFormat("yyyyMMdd");
        return date.concat(String.valueOf(KeyWorker.nextId()));
    }
}
