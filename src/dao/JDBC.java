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

	static {											// �ھ�̬������м������ݿ�����
		try {
			Properties properties = new Properties();
			InputStream is = new FileInputStream(new File("jdbc.properties"));
			properties.load(is);
			
			DRIVERCLASS = properties.getProperty("driver");
			URL = properties.getProperty("url");
			USERNAME = properties.getProperty("username");
			PASSWORD = properties.getProperty("password");
			
			Class.forName(DRIVERCLASS).newInstance();	// �������ݿ�����
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�������ö�ȡʧ�ܣ���ʼ��ʧ��");
		}
	}
	
	
	public static Connection getConnection() {	// �������ݿ����ӵķ���
		Connection conn = threadLocal.get();	// ���߳��л�����ݿ�����
		if (conn == null) {						// û�п��õ����ݿ�����
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// �����µ����ݿ�����
				threadLocal.set(conn);			// �����ݿ����ӱ��浽�߳���
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (conn == null) {
	             System.err.println("����: DriverManager.getConnection() ������ݿ�����ʧ��.\r\n\r\n��������:" + DRIVERCLASS + "\r\n����λ��:" + URL
	                    + "\r\n�û�/����" + USERNAME + "/" + PASSWORD);
	        }
		}
		return conn;							// �������ݿ�����
	}
	
	public static boolean closeConnection() {	// �ر����ݿ����ӵķ���
		boolean isClosed = true;				// Ĭ�Ϲرճɹ�
		Connection conn = threadLocal.get();	// ���߳��л�����ݿ�����
		if (conn != null) {						// ���ݿ����ӿ���
			threadLocal.set(null);				// ����߳��е����ݿ�����
			try {
				conn.close();					// �ر����ݿ�����
			} catch (SQLException e) {
				isClosed = false;				// �ر�ʧ��
				e.printStackTrace();
			}
		}
		return isClosed;						// ���عرս��
	}

}
