package net.dgg.bigdata.manager.common.page;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

/**
 * 分页插件
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {

	private String endWord = "WithPage";// 以该词结尾的为需要分页的对象
	private final String pageNo = "page";//起始条数参数名
	private final String pageSize = "limit";//页面大小参数名

	public Object intercept(Invocation invocation) throws Throwable {
		// 获取方法名
		StatementHandler statementHandler = this.getUnProxyObject(invocation);
		MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
		//获取selectid
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		String selectId = mappedStatement.getId();
		int count = 0;
		// 如果以指定词结尾则处理
		if (selectId.endsWith(this.endWord)) {

			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			//获取参数
			Map params = (Map) (boundSql.getParameterObject());

			count = this.getTotal(invocation, metaStatementHandler, boundSql);
			params.put("count", count);

			return this.getLimitedData(invocation, metaStatementHandler, boundSql, params);

		}
		Object result = invocation.proceed();
		return result;
	}
	
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	public void setProperties(Properties p) {

	}

	/**
	 * *****************************************************************
	 *
	 * @param sql
	 * @return ****************************************************************
	 * @Description 获取查询总条数的sql</br>
	 * @Version V1.0.0</br>
	 * @author nature </br>
	 * @Time May 4, 2017 3:02:39 PM</br>
	 * @Email linghuanxu@qq.com</br>
	 */
	private String concatCountSql(String sql) {
		StringBuffer sb = new StringBuffer("select count(1) as total from (");
		sb.append(sql);
		sb.append(") as _pageing");
		//sql = sql.toLowerCase();
//
//		if (sql.lastIndexOf("order") > sql.lastIndexOf(")")) {
//			sb.append(sql.substring(sql.indexOf("from") + 4, sql.lastIndexOf("order")));
//		} else {
//			sb.append(sql.substring(sql.indexOf("from") + 4));
//		}
		return sb.toString();
	}

	/**
	 * *****************************************************************
	 *
	 * @param sql
	 * @param param
	 * @return ****************************************************************
	 * @Description 改造sql进行分页</br>
	 * @Version V1.0.0</br>
	 * @author nature </br>
	 * @Time May 4, 2017 3:03:02 PM</br>
	 * @Email linghuanxu@qq.com</br>
	 */
	private String concatPageSql(String sql, Map param) {
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		int pageNo = Integer.valueOf(param.get(this.pageNo).toString());
		int pageSize = Integer.valueOf(param.get(this.pageSize).toString());
		sb.append(" limit ")
		.append((pageNo - 1) * pageSize).append(" , ").append(pageSize);
		return sb.toString();
	}

	/**
	 * *****************************************************************
	 *
	 * @param ivt
	 * @return ****************************************************************
	 * @Description 从代理对象中分理处真实对象</br>
	 * @Version V1.0.0</br>
	 * @author nature </br>
	 * @Time May 5, 2017 1:10:07 PM</br>
	 * @Email linghuanxu@qq.com</br>
	 */
	private StatementHandler getUnProxyObject(Invocation ivt) {
		StatementHandler statementHandler = (StatementHandler) ivt.getTarget();
		MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
		//分离代理对象连（由于目标类可能被多个拦截器拦截，从而形成多次代理，通过循环可以分离出最原始的目标类
		Object object = null;
		while (metaStatementHandler.hasGetter("h")) {
			object = metaStatementHandler.getValue("h");
		}
		if (object == null) {
			return statementHandler;
		}
		return (StatementHandler) object;
	}

	private int getTotal(Invocation ivt, MetaObject metaStatementHandler, BoundSql boundSql) throws SQLException {
		//获取当前的mappedStatement
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		//配置对象
		Configuration cfg = mappedStatement.getConfiguration();
		//当前需要执行的sql
		String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		//改写sql
		String countSql = this.concatCountSql(sql);
		//获取拦截方法参数，我们知道是Connection对象
		Connection connection = (Connection) ivt.getArgs()[0];
		PreparedStatement ps = null;
		int total = 0;
		try {
			//预编译统计总数sql
			ps = connection.prepareStatement(countSql);
			//构建统计总数BoundSql
			BoundSql countBoundSql = new BoundSql(cfg, countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			//构建Mybatis的ParameterHandler用来设置总数sql参数
			ParameterHandler handler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);
			//设置总数sql参数
			handler.setParameters(ps);
			//执行查询
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}

		} finally {
			//这里不能关闭Connection，否则后续的sql就没法继续了
//			if (ps != null) {
//				ps.close();
//			}
		}

		return total;
	}
	

	private Object getLimitedData(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql, Map param) throws InvocationTargetException, IllegalAccessException {
		//获取当前需要执行的sql
		String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		//修改sql，这里使用的是mysql，如果是其他数据库则需要修改
		if(null != param.get(this.pageNo) && null != param.get(this.pageSize)){
		     sql = this.concatPageSql(sql, param);
        }
		//修改当前需要执行的sql
		metaStatementHandler.setValue("delegate.boundSql.sql", sql);
		//相当于调用StatementeHandler的prepare方法，预编译了当前sql，病设置原有的参数，但是少了两个分页参数，它返回的是一个PreparedStatement对象
		PreparedStatement ps = (PreparedStatement) invocation.proceed();
		return ps;

	}

}
