package net.dgg.bigdata.manager.common.filter;

import net.dgg.bigdata.manager.menu.service.MenuService;
import net.dgg.core.mongo.cache.AbstractMongoCacheModule;
import net.dgg.core.spring.DggSpringContext;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 管理menuUrls的缓存
 * Create by chenz on 2018/4/3
 */
@Component
public class MenuUrlsCache extends AbstractMongoCacheModule<Set<String>> {
    /**
     * 闲置过期时间间隔，单位秒
     */
    private long authFilterExpireInterval = 60 * 5;

    public MenuUrlsCache(){
        this.setCacheCollectionKey("net.dgg.bigdata.manager.common.CommonCache");
        this.setDataSecondDefault(authFilterExpireInterval);
    }


    /**
     * 自定义加载menusUrls
     * @param field
     * @return
     */
    @Override
    protected Set<String> loadValue(String field) {
        MenuService menuService = DggSpringContext.getBean("menuService", MenuService.class);
        Set<String> menuUrls = menuService.findAllUrls();
        return menuUrls;
    }
}
