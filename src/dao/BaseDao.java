package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;




public class BaseDao {
	
	/** ��ѯ���ж���  */
	public Vector<Vector<Object>> selectSomeNote(String sql) {
		Vector<Vector<Object>> vector = new Vector<Vector<Object>>();	// �������������
		Connection conn = JDBC.getConnection();			// ������ݿ�����
		try {
			Statement stmt = conn.createStatement();	// ��������״̬����
			ResultSet rs = stmt.executeQuery(sql);		// ִ��SQL����ò�ѯ���
			int columnCount = rs.getMetaData().getColumnCount();	// ��ò�ѯ���ݱ������
			int row = 1;								// ���������
			while (rs.next()) {							// ���������
				Vector<Object> rowV = new Vector<Object>();			// ����������
				rowV.add(new Integer(row++));			// ��������
				for (int column = 1; column <= columnCount; column++) {
					rowV.add(rs.getObject(column));		// �����ֵ
				}
				vector.add(rowV);						// ����������ӵ������������
			}
			rs.close();									// �رս��������
			stmt.close();								// �ر�����״̬����
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;									// ���ؽ��������
	}
	
	/** ��ѯһ�ж�ֵ **/
	public Vector<Object> selectOneNote(String sql){     
		Connection conn = JDBC.getConnection();			//������ݿ�����
		Vector<Object> vector = null;					//��������
		try {											
			Statement stmt = conn.createStatement();	//��������
			ResultSet rs = stmt.executeQuery(sql);		//ִ��SQL����ȡ��ѯ���
			int colnumCount = rs.getMetaData().getColumnCount(); //��ȡ��ѯ���ݱ������
			while(rs.next()){							//���������
				vector = new Vector<Object>(); 			//����������
				for (int i = 1; i <= colnumCount; i++) {
					vector.add(rs.getObject(i));		//�����ֵ
				}
			}
			rs.close();									//�رս����
			stmt.close();								//�ر�����״̬����
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return vector;									//���ؽ��������
	}
	
	/** ��ѯһ�ж�ֵ*/
	public Vector<Object> selectSomeValue(String sql) {
		Vector<Object> vector = new Vector<Object>();	// ������ѯ���������
		Connection conn = JDBC.getConnection();			// ������ݿ�����
		try {
			Statement stmt = conn.createStatement();	// ��������״̬����
			ResultSet rs = stmt.executeQuery(sql);		// ִ��SQL����ò�ѯ���
			while (rs.next()) {
				vector.add(rs.getObject(1));			// ��װ��ѯ���������
			}
			rs.close();									// �رս��������
			stmt.close();								// �ر�����״̬����
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;									// ���ز�ѯ���������
	}

	/** ��ѯ����ֵ */
	public Object selectOnlyValue(String sql) {
		Object value = null;							// ������ѯ�������
		Connection conn = JDBC.getConnection();			// ������ݿ�����
		try {
			Statement stmt = conn.createStatement();	// ��������״̬����
			ResultSet rs = stmt.executeQuery(sql);		// ִ��SQL����ò�ѯ���
			while (rs.next()) {
				value = rs.getObject(1);				// ��ò�ѯ���
			}
			rs.close();									// �رս��������
			stmt.close();								// �ر�����״̬����
		} catch (SQLException e) {	}
		return value;									// ���ز�ѯ���
	}

	/** ���롢�޸ġ�ɾ����¼ */
	public boolean longHaul(String sql) {
		boolean isLongHaul = true;						// Ĭ�ϳ־û��ɹ�
		Connection conn = JDBC.getConnection();			// ������ݿ�����
		try {
			conn.setAutoCommit(false);					// ����Ϊ�ֶ��ύ
			Statement stmt = conn.createStatement();	// ��������״̬����
			stmt.executeUpdate(sql);					// ִ��SQL���
			stmt.close();								// �ر�����״̬����
			conn.commit();								// �ύ�־û�
		} catch (SQLException e) {
			isLongHaul = false;							// �־û�ʧ��
			try {
				conn.rollback();						// �ع�
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return isLongHaul;								// ���س־û����
	}

	/** �����¼ */
	public long insert(String sql) {
		Connection conn = JDBC.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long id = 0; 						// ������ݿⷵ�ص��û�ע������id
		try {
			conn.setAutoCommit(false);		// ����Ϊ�ֶ��ύ
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			conn.commit();					// �ύ�־û�
			rs = ps.getGeneratedKeys();		// �õ�����ļ�¼��id
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

	/** �޸ļ�¼ */
	public boolean update(String sql) {
		return longHaul(sql);
	}

	/** ɾ����¼ */
	public boolean delete(String sql) {
		return longHaul(sql);
	}
}
