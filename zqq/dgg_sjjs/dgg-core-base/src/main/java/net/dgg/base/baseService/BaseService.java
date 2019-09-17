package net.dgg.base.baseService;

import net.dgg.base.baseDao.BaseDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <BaseService>
 * @despriction
 * @create 2018/12/10 10:40
 **/
public abstract class BaseService<T> extends BaseDao<T> {
    /**
     * 保存
     *
     * @param t
     */
    @Transactional
    @Override
    public void save(T t) {
        super.save(t);
    }

    /**
     * 更新
     *
     * @param t
     */
    @Transactional
    @Override
    public void update(T t) {
        super.update(t);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Transactional
    @Override
    public void delete(Object id) {
        super.delete(id);
    }

    /**
     * 统计
     *
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Long count(Map<String, Object> param) {
        return super.count(param);
    }

    /**
     * 查询
     *
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> query(Map<String, Object> param) {
        return super.query(param);
    }

    /**
     * 查询
     *
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map> queryMap(Map<String, Object> param) {
        return super.queryMap(param);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public T findById(Object id) {
        return super.findById(id);
    }

    /**
     * 查询
     *
     * @param value
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public T findByAttr(String attr, Object value) {
        return super.findByAttr(attr, value);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map findMapById(Object id) {
        return super.findMapById(id);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map findMapByAttr(String attr, Object id) {
        return super.findMapByAttr(attr, id);
    }


}
