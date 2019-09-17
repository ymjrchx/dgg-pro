package net.dgg.tmd.foundation.platform.permission.cache;

import net.dgg.tmd.foundation.platform.permission.entity.UserAccessPermissionEntity;
import net.fblock.cachemodule.AbstractMongoCacheModule;

/**
 * 用户Access权限缓存类，提供loadValue方法
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/3/6
 * Time:10:14
 */
public class UserAccessPermissionCache extends AbstractMongoCacheModule<UserAccessPermissionEntity> {
    @Override
    protected UserAccessPermissionEntity loadValue(String field) {
        if (null == field || "".equals(field)){
            return null;
        }else{
            UserAccessPermissionEntity userAccessPermissionEntity = new UserAccessPermissionEntity();
            String[] params = field.split(":");
            if("".equals(params[0])){

            }
        }
        return null;
    }
}
