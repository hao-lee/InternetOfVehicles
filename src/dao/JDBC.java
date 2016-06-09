package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC {
	private static String DRIVERCLASS = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql:fabric://115.28.172.55:1521;databaseName=IOC";
	private static String USERNAME = "netOfVech";
	private static String PASSWORD = "caco";
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	static {											// 在静态代码块中加载数据库驱动
		try {
			Properties properties = new Properties();
			InputStream is = new FileInputStream(new File("jdbc.properties"));
			properties.load(is);
			
			DRIVERCLASS = properties.getProperty("driver");
			URL = properties.getProperty("url");
			USERNAME = properties.getProperty("username");
			PASSWORD = properties.getProperty("password");
			
			Class.forName(DRIVERCLASS).newInstance();	// 加载数据库驱动
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("属性配置读取失败，初始化失败");
		}
	}
	
	
	public static Connection getConnection() {	// 创建数据库连接的方法
		Connection conn = threadLocal.get();	// 从线程中获得数据库连接
		if (conn == null) {						// 没有可用的数据库连接
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 创建新的数据库连接
				threadLocal.set(conn);			// 将数据库连接保存到线程中
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (conn == null) {
	             System.err.println("警告: DriverManager.getConnection() 获得数据库链接失败.\r\n\r\n链接类型:" + DRIVERCLASS + "\r\n链接位置:" + URL
	                    + "\r\n用户/密码" + USERNAME + "/" + PASSWORD);
	        }
		}
		return conn;							// 返回数据库连接
	}
	
	public static boolean closeConnection() {	// 关闭数据库连接的方法
		boolean isClosed = true;				// 默认关闭成功
		Connection conn = threadLocal.get();	// 从线程中获得数据库连接
		if (conn != null) {						// 数据库连接可用
			threadLocal.set(null);				// 清空线程中的数据库连接
			try {
				conn.close();					// 关闭数据库连接
			} catch (SQLException e) {
				isClosed = false;				// 关闭失败
				e.printStackTrace();
			}
		}
		return isClosed;						// 返回关闭结果
	}

}
