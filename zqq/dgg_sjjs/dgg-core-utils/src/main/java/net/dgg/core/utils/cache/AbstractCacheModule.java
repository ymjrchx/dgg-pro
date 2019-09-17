package net.dgg.core.utils.cache;

import net.dgg.core.utils.DggStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 抽象缓存模块类，实现缓存模块需要继承该类
 * @param <T>
 */
public abstract class AbstractCacheModule<T> {

    // region 私有变量

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 缓存模块key
     */
    private String cacheCollectionKey = null;

    //数据过期时间，单位毫秒
    private Long second_default = 60 * 60 * 1000L;

    // endregion

    // region 公有方法

    /**
     * 返回所有key所指向的数据，集合的key值为入参的key
     *
     * @param keys 模块下key的set集合
     * @return
     */
    public Map<String, T> get(Set<String> keys) {
        if(keys==null) return null;
        Map<String, T> mapData = new HashMap<>();
        for(String key:keys){
            T data=this.get(key);
            if(data!=null) {
                mapData.put(key, this.get(key));
            }
        }
        return mapData;
    }

    /**
     * @param key 模块下key
     * @return 未找到则返回null，否则返回对应的对象
     */
    public T get(String key) {
        //判断field是否为空或者null
        key= DggStringUtils.trimWhitespace(key);
        if (key==null || key.length()==0) {
            return null;
        }

        //从缓存中获取数据
        T value = this.getFromeCache(key);

        //如果为空，则加载后放入缓存，否则，刷新缓存时间
        if (value == null) {
            //加载数据
            T data=loadValue(key);
            //将数据存到缓存
            this.saveToCache(key,data);
            //返回数据
            return data;
        }else{
            //刷新数据时间
            this.flushData(key);
        }

        //返回数据
        return value;
    }

    /**
     * @param keys:清空set集合
     * @Return 返回清理掉fields个数
     */
    public abstract Long clear(Set<String> keys);

    /**
     * 删除field和对应的value，清空单个
     */
    public abstract Long clear(String key);

    /**
     * 清空所有开发者加入的fields
     *
     * @Return 删除fields的个数
     */
    public abstract Long clear();

    /**
     * 获取当前业务key
     *
     * @return
     */
    public String getCacheCollectionKey() {
        return cacheCollectionKey;
    }

    // endregion

    /**
     * 开发者自定义方法，用户加载新的或者过期的缓存
     *
     * @param
     */
    protected abstract T loadValue(String key);

    /**
     * 根据自己模块所配置的key，设置模块cacheCollectionKey
     *
     * @param cacheCollectionKey
     */
    protected void setCacheCollectionKey(String cacheCollectionKey) {
        this.cacheCollectionKey = cacheCollectionKey;
    }

    /**
     * 根据自己模块所配置的second，设置模块数据过期时间
     *
     * @param second
     */
    protected void setDataSecondDefault(Long second) {
        this.second_default = second;
    }

    /**
     * 初始化缓存模块
     */
    protected abstract void init();

    /**
     * 判断某个域的缓存是否存在
     *
     * @param key 缓存的key
     * @return 该key指向的缓存是否存在
     */
    protected abstract boolean keyExist(String key);

    /**
     * 从缓存中获取数据
     *
     * @param key 要加载的缓存的key
     * @return
     */
    protected abstract T getFromeCache(String key);

    /**
     * 将数据存储到缓存
     * @param key 数据对应的key
     * @param data 要存储的数据
     */
    protected abstract void saveToCache(String key,T data);


    /**
     * 刷新数据
     * @param key 需要被刷新的数据的key
     */
    protected abstract void flushData(String key);

    protected Long getSecond_default() {
        return second_default;
    }

    /**
     * 获取泛型的Class实例
     * @return
     */
    protected Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
