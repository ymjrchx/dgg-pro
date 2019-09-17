package net.dgg.bigdata.manager.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author nature
 * @create 2018-02-21 18:59
 */
@Mapper
public interface DemoDao {
	/**
	 * 样例查询
	 * @return
	 */
	List<Map> demoSelect();

	/**
	 * 使用分页拦截器的dao需要以WithPage结尾
	 * @param params
	 * @return
	 */
	List<Map> selectDemoWithPage(Map params);
}
