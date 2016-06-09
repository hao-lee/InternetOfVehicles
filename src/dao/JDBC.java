package dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
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
			

			Class.forName(DRIVERCLASS).newInstance();	// 加载数据库驱动
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
