package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;




public class BaseDao {
	
	/** 查询多行多列  */
	public Vector<Vector<Object>> selectSomeNote(String sql) {
		Vector<Vector<Object>> vector = new Vector<Vector<Object>>();	// 创建结果集向量
		Connection conn = JDBC.getConnection();			// 获得数据库连接
		try {
			Statement stmt = conn.createStatement();	// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);		// 执行SQL语句获得查询结果
			int columnCount = rs.getMetaData().getColumnCount();	// 获得查询数据表的列数
			int row = 1;								// 定义行序号
			while (rs.next()) {							// 遍历结果集
				Vector<Object> rowV = new Vector<Object>();			// 创建行向量
				rowV.add(new Integer(row++));			// 添加行序号
				for (int column = 1; column <= columnCount; column++) {
					rowV.add(rs.getObject(column));		// 添加列值
				}
				vector.add(rowV);						// 将行向量添加到结果集向量中
			}
			rs.close();									// 关闭结果集对象
			stmt.close();								// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;									// 返回结果集向量
	}
	
	/** 查询一行多值 **/
	public Vector<Object> selectOneNote(String sql){     
		Connection conn = JDBC.getConnection();			//获得数据库连接
		Vector<Object> vector = null;					//声明向量
		try {											
			Statement stmt = conn.createStatement();	//创建连接
			ResultSet rs = stmt.executeQuery(sql);		//执行SQL语句获取查询结果
			int colnumCount = rs.getMetaData().getColumnCount(); //获取查询数据表的条数
			while(rs.next()){							//遍历结果集
				vector = new Vector<Object>(); 			//创建行向量
				for (int i = 1; i <= colnumCount; i++) {
					vector.add(rs.getObject(i));		//添加行值
				}
			}
			rs.close();									//关闭结果集
			stmt.close();								//关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return vector;									//返回结果集向量
	}
	
	/** 查询一列多值*/
	public Vector<Object> selectSomeValue(String sql) {
		Vector<Object> vector = new Vector<Object>();	// 创建查询结果集向量
		Connection conn = JDBC.getConnection();			// 获得数据库连接
		try {
			Statement stmt = conn.createStatement();	// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);		// 执行SQL语句获得查询结果
			while (rs.next()) {
				vector.add(rs.getObject(1));			// 封装查询结果集向量
			}
			rs.close();									// 关闭结果集对象
			stmt.close();								// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;									// 返回查询结果集向量
	}

	/** 查询单个值 */
	public Object selectOnlyValue(String sql) {
		Object value = null;							// 声明查询结果对象
		Connection conn = JDBC.getConnection();			// 获得数据库连接
		try {
			Statement stmt = conn.createStatement();	// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);		// 执行SQL语句获得查询结果
			while (rs.next()) {
				value = rs.getObject(1);				// 获得查询结果
			}
			rs.close();									// 关闭结果集对象
			stmt.close();								// 关闭连接状态对象
		} catch (SQLException e) {	}
		return value;									// 返回查询结果
	}

	/** 插入、修改、删除记录 */
	public boolean longHaul(String sql) {
		boolean isLongHaul = true;						// 默认持久化成功
		Connection conn = JDBC.getConnection();			// 获得数据库连接
		try {
			conn.setAutoCommit(false);					// 设置为手动提交
			Statement stmt = conn.createStatement();	// 创建连接状态对象
			stmt.executeUpdate(sql);					// 执行SQL语句
			stmt.close();								// 关闭连接状态对象
			conn.commit();								// 提交持久化
		} catch (SQLException e) {
			isLongHaul = false;							// 持久化失败
			try {
				conn.rollback();						// 回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return isLongHaul;								// 返回持久化结果
	}

	/** 插入记录 */
	public long insert(String sql) {
		Connection conn = JDBC.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long id = 0; 						// 存放数据库返回的用户注册过后的id
		try {
			conn.setAutoCommit(false);		// 设置为手动提交
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			conn.commit();					// 提交持久化
			rs = ps.getGeneratedKeys();		// 得到插入的记录的id
			while (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;

	}

	/** 修改记录 */
	public boolean update(String sql) {
		return longHaul(sql);
	}

	/** 删除记录 */
	public boolean delete(String sql) {
		return longHaul(sql);
	}
}
