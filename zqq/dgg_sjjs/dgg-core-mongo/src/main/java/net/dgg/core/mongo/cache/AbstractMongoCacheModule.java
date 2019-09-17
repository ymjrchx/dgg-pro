package net.dgg.core.mongo.cache;

import com.mongodb.DuplicateKeyException;
import net.dgg.core.mongo.DggMongoService;
import net.dgg.core.utils.cache.AbstractCacheModule;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 抽象mongo缓存模块，内部使用mongo作为缓存实现
 *
 * @author nature
 * @create 2018-05-14 16:01
 */
public abstract class AbstractMongoCacheModule<T> extends AbstractCacheModule<T> implements InitializingBean {

    // region 私有变量

    @Autowired
    private DggMongoService dggMongoService;

    /**
     * 集群对象
     */
    private MongoTemplate mongoTemplate;

    @Override
    public void afterPropertiesSet() throws Exception{
        try {
            mongoTemplate = dggMongoService.getMongoTemplate();
        }catch (Exception e){
            logger.error("初始化mongo服务失败,请检查!");
            throw e;
        }
    }


    /**
     * 是否是精确模式
     * 由于mongodb中数据过期可能存在几十秒的误差，如果无法容忍这个程度的误差则可以使用精确模式
     * 但是使用精确模式对性能会有所下降
     * 默认为false
     */
    private boolean isExactMode = false;

    // endregion

    // region 公有方法
    /**
     * @param keys:清空set集合
     * @Return 返回清理掉fields个数
     */
    @Override
    public Long clear(Set<String> keys) {
        if (keys == null) {
            return 0L;
        }
        return Long.valueOf(this.mongoTemplate.remove(new Query(Criteria.where("_id").in(keys)), this.getCacheCollectionName()).getDeletedCount());
    }

    /**
     * 删除field和对应的value，清空单个
     */
    @Override
    public Long clear(String key) {
        key = StringUtils.trimWhitespace(key);
        if (StringUtils.isEmpty(key)) {
            return 0L;
        }

        return Long.valueOf(this.mongoTemplate.remove(new Query(Criteria.where("_id").is(key)), this.getCacheCollectionName()).getDeletedCount());
    }

    /**
     * 清空所有开发者加入的fields
     *
     * @Return 删除fields的个数
     */
    @Override
    public Long clear() {

        return Long.valueOf(this.mongoTemplate.remove(new Query(Criteria.where("_id").regex("")), this.getCacheCollectionName()).getDeletedCount());
    }

    // endregion

    @Override
    protected void init() {
        this.initCache();
    }

    /**
     * 向缓存中增加key
     */
    protected void initCache() {
        //collection如果不存在，则创建collection
        if (!this.mongoTemplate.collectionExists(this.getCacheCollectionName())) {
            this.mongoTemplate.createCollection(this.getCacheCollectionName());
        }

        //对缓存池重建索引
        IndexOperations indexOperations = this.mongoTemplate.indexOps(this.getCacheCollectionName());
        indexOperations.dropAllIndexes();
        indexOperations.ensureIndex(new Index().on("_id", Sort.Direction.ASC).named("key"));
        indexOperations.ensureIndex(new Index().on("updateTime", Sort.Direction.ASC).named("updateTime").expire(this.getSecond_default(), TimeUnit.MILLISECONDS));

    }

    /**
     * 判断某个域的缓存是否存在
     *
     * @param key 缓存的key
     * @return 该key指向的缓存是否存在
     */
    @Override
    protected boolean keyExist(String key) {
        key = StringUtils.trimWhitespace(key);
        if (StringUtils.isEmpty(key)) {
            //如果缓存key为空，则缓存一定不存在
            return false;
        }
        return this.mongoTemplate.exists(new Query(Criteria.where("_id").is(key)), this.getCacheCollectionName());
    }

    /**
     * 从缓存中获取数据
     *
     * @param key 要加载的缓存的key
     * @return
     */
    @Override
    protected T getFromeCache(String key) {

        MongoCacheDataContainer<T> dataContainer = this.mongoTemplate.findOne(
                new Query(Criteria.where("_id").is(key))
                , MongoCacheDataContainer.class
                , this.getCacheCollectionName());

        if (dataContainer == null) {
            return null;
        }
        return dataContainer.getData();
    }

    /**
     * 将数据存储到缓存
     *
     * @param key  数据对应的key
     * @param data 要存储的数据
     */
    @Override
    protected void saveToCache(String key, T data) {
        MongoCacheDataContainer<T> dataContainer = new MongoCacheDataContainer<>();
        dataContainer.setKey(key);
        Date expireTime = getExpireTime();
        dataContainer.setUpdateTime(expireTime);
        dataContainer.setData(data);

        for (int i = 0; i < 3; i++) {
            try {
                this.mongoTemplate.save(dataContainer, this.getCacheCollectionName());
                break;
            } catch (DuplicateKeyException e) {
                logger.info("缓存" + this.getCacheCollectionName() + "主键" + key + "重复");
                continue;
            }
        }
    }

    /**
     * 获取mongo中建立的collection名称
     *
     * @return
     */
    private String getCacheCollectionName() {
        return "cache_" + this.getCacheCollectionKey();
    }

    /**
     * 刷新数据
     *
     * @param key 需要被刷新的数据的key
     */
    @Override
    protected void flushData(String key) {
        //如果数据存在才刷新数据，否则直接跳过
        if (this.keyExist(key)) {
            Date expireTime = getExpireTime();
            this.mongoTemplate.updateFirst(
                    new Query(Criteria.where("_id").is(key))
                    , new Update().set("updateTime", expireTime)
                    , this.getCacheCollectionName());
        }
    }

    /**
     * 获取过期时间
     *
     * @return
     */
    protected Date getExpireTime() {
        Long second = this.getSecond_default();
        Calendar expireTime = Calendar.getInstance();
        long currentTime = System.currentTimeMillis() + second;
        expireTime.setTimeInMillis(currentTime);
        return expireTime.getTime();
    }

}
