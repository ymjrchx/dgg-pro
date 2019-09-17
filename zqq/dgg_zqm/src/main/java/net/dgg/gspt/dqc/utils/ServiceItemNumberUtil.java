package net.dgg.gspt.dqc.utils;

import net.fblock.core.common.KeyWorker;

/**
 * @author 刘阳
 * @ClassName <ServiceItemNumberUtil>
 * @despriction 服务项编号生产器
 * @create 2018/10/08 16:06
 **/
public interface ServiceItemNumberUtil {
    static String getNumber() {
        return "S".concat(String.valueOf(KeyWorker.nextId()));
    }
}
