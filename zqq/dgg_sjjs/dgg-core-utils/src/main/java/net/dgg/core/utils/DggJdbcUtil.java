package net.dgg.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * @Description JDBC工具类
 * @author Ma Hong   
 * @date 2018-09-10 14:15  
 * @version 1.0
 */
public class DggJdbcUtil {

	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public static Connection getConnection(String driverClazz, String url, String username, String password) {
		Objects.requireNonNull(driverClazz);
		Objects.requireNonNull(url);
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			Class.forName(driverClazz);
			Connection conn = threadLocal.get();
			if (conn == null) {
				conn = DriverManager.getConnection(url, username, password);
				threadLocal.set(conn);
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void close(Statement psmt) {
		if (psmt != null) {
			try {
				psmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs, Statement psmt) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (psmt != null) {
			try {
				psmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnection() {
		Connection conn = threadLocal.get();
		if (conn != null) {
			threadLocal.set(null);
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void beginTx(Connection conn) {
		try {
			if (conn != null) {
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commitTx(Connection conn) {
		try {
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollbackTx(Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
