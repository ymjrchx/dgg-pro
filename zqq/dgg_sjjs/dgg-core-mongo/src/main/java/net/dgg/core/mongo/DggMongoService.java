package net.dgg.core.mongo;

import net.dgg.core.utils.DggStringUtils;
import net.dgg.core.utils.bean.DggPageCondition;
import net.dgg.core.utils.bean.DggPageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DggMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * mongo连接地址
     */
    @Value("${spring.data.mongodb.uri:}")
    private String mongoUrl;
    /**
     * mongo数据库
     */
    @Value("${spring.data.mongodb.Database:}")
    private String mongoCollection;
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DggMongoService() {
        logger.info("dgg-core-mongo DggMongoService执行初始化!");
    }

    /**
     * 获取Mongo模板对象
     *
     * @return
     */
    public MongoTemplate getMongoTemplate() {
        if(unAvailable()){
            throw new DggMongoExeption("mongo集群不可用!");
        }
        return mongoTemplate;
    }

    /**
     * 判断是否添加mongo配置
     *
     * @return
     */
    public boolean unAvailable() {
        boolean flag = false;
        if (DggStringUtils.isEmpty(mongoUrl)) {
            flag = true;
        }
        if (DggStringUtils.isEmpty(mongoCollection)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据条件更新数据
     *
     * @param criteria
     * @param update
     * @param clazz
     * @param <T>
     */
    public <T> void update(Criteria criteria, Update update, Class<T> clazz) {
        Query query = new Query();
        query.addCriteria(criteria);
        getMongoTemplate().upsert(query, update, clazz);
    }

    /**
     * 根据条件删除数据
     *
     * @param criteria
     * @param clazz
     * @param <T>
     */
    public <T> void delete(Criteria criteria, Class<T> clazz) {
        Query query = Query.query(criteria);
        getMongoTemplate().remove(query, clazz);
    }

    /**
     * 根据条件删除
     *
     * @param query
     * @param collectionName
     * @return
     */
    public Long remove(Query query, String collectionName) {
        return Long.valueOf(getMongoTemplate().remove(query, collectionName).getDeletedCount());
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T queryById(String id, Class<T> clazz) {
        return getMongoTemplate().findById(id, clazz);
    }

    /**
     * 根据查询条件查询数据
     *
     * @param clazz    查询对象Class
     * @param criteria 查询条件字段
     * @return 返回List查询结果
     */
    public <T> List<T> queryList(Class<T> clazz, Criteria criteria) {
        return queryList(clazz, criteria, null);
    }

    /**
     * 根据查询条件查询数据
     *
     * @param clazz    查询对象Class
     * @param criteria 查询条件字段
     * @param sort     排序字段
     * @return 返回List查询结果
     */
    public <T> List<T> queryList(Class<T> clazz, Criteria criteria, Sort sort) {
        Query query = new Query();
        //查询条件
        if (criteria != null) {
            query = new Query(criteria);
        }
        //排序条件
        if (sort != null) {
            query.with(sort);
        }
        List<T> tList = getMongoTemplate().find(query, clazz);
        return tList;
    }

    /**
     * 没有查询条件分页查询
     *
     * @param clazz         查询对象Class
     * @param pageCondition 分页条件
     * @param sort          排序字段-如何不需要排序传递null
     */
    public <T> DggPageResult<T> queryPageList(Class<T> clazz, DggPageCondition pageCondition, Sort sort) {
        return queryPageList(clazz, null, pageCondition, sort);
    }

    /**
     * 没有查询条件分页查询
     *
     * @param clazz         查询对象Class
     * @param criteria      查询条件字段
     * @param pageCondition 分页条件
     */
    public <T> DggPageResult<T> queryPageList(Class<T> clazz, Criteria criteria, DggPageCondition pageCondition) {
        return queryPageList(clazz, criteria, pageCondition, null);
    }

    /**
     * 分页查询
     *
     * @param clazz         查询对象Class
     * @param criteria      查询字段-没有查询条件传null
     * @param pageCondition 分页条件
     * @param sort          排序字段-如何不需要排序传递null
     */
    public <T> DggPageResult<T> queryPageList(Class<T> clazz, Criteria criteria, DggPageCondition pageCondition, Sort sort) {
        DggPageResult<T> pageData = new DggPageResult<>();
        Query query = new Query();
        //查询条件
        if (criteria != null) {
            query = new Query(criteria);
        }
        //排序条件
        if (sort != null) {
            query.with(sort);
        }
        // 获取总条数
        long totalCount = getMongoTemplate().count(query, clazz);
        pageData.setTotal(totalCount);
        //取分页数据
        //取分页数据
        query.skip(pageCondition.getStart());// skip相当于从那条记录开始
        query.limit(pageCondition.getLimit());// 从skip开始,取多少条记录
        List<T> dateList = getMongoTemplate().find(query, clazz);
        pageData.settList(dateList);
        return pageData;
    }

    /**
     * 根据条件判断是否存在数据
     *
     * @param clazz    查询对象Class
     * @param criteria 查询字段
     * @return
     */
    public <T> boolean exist(Class<T> clazz, Criteria criteria) {
        boolean flag = false;
        try {
            Query query = new Query();
            //查询条件
            if (criteria != null) {
                query = new Query(criteria);
            }
            flag = getMongoTemplate().exists(query, clazz);
        } catch (Exception e) {
            logger.error("dgg mongo exist Class exception:" + e);
        }
        return flag;
    }

    /**
     * 根据条件判断是否存在数据
     *
     * @param criteria
     * @param collectionName
     * @return
     */
    public boolean exist(Criteria criteria, String collectionName) {
        boolean flag = false;
        try {
            Query query = new Query();
            //查询条件
            if (criteria != null) {
                query = new Query(criteria);
            }
            flag = getMongoTemplate().exists(query, collectionName);
        } catch (Exception e) {
            logger.error("dgg mongo exist collectionName exception:" + e);
        }
        return flag;
    }

}
