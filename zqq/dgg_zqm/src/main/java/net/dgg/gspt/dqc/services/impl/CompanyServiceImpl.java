package net.dgg.gspt.dqc.services.impl;

import net.dgg.gspt.dqc.entity.business.search.BrandHealth;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName: CompanyServiceImpl
 * @Description: 对外服务接口辅助
 * @Author: jiangsh
 * @Date: 2018/11/2 10:43
 */

@Service
public class CompanyServiceImpl {

    /**
     * 商标健康检查
     *
     * @return
     */
    public BrandHealth checkBrandHealth(Map map, String regNo, String type, String key) {
        int score = 100;
        BrandHealth brandHealth = new BrandHealth();
        final String v = map.get(key).toString();
//        if (v.contains())
        return brandHealth;
    }
}
