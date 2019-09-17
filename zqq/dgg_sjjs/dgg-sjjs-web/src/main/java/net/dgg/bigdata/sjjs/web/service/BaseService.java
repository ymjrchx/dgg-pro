package net.dgg.bigdata.sjjs.web.service;

import java.util.List;
import java.util.Map;

/**
 * Created by wu on 2017/7/31.
 */
public interface BaseService<T> {


    /**
     * 查询全部数据
     *
     * @return
     */
    List<T> findAll(Map condition);

    /**
     * 根据ID查询单条记录
     *
     * @param id
     * @return
     */
    T findOne(String id);

    /**
     * 插入数据
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 更新数据
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 根据ID删除数据
     *
     * @param id
     * @return
     */
    int delete(String id);

   /**
    * 查询第后面1001条
    * @param map
    * @return
    */
	T findEndThousand(Map map);

	/**
	 * 统计条数
	 * @param map
	 * @return
	 */
	int findCount(Map map);

	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<T> searchPage(Map map);

}
