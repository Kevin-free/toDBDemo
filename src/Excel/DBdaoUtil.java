package Excel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DBUtil.DBUtil;
import POJO.Cr;

public class DBdaoUtil {

	/**
	 * @param sql
	 */
	public static void insert(String sql, Cr cr) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, cr.getPhone());
			ps.setString(2, cr.getName());
			boolean flag = ps.execute();
			conn.commit();
			if (!flag) {
				System.out.println("Save data : phone:" + cr.getPhone() + ",name:" + cr.getName() + " succeed!");
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List selectOne(String sql, Cr cr) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("phone").equals(cr.getPhone())) {
					list.add(1);
				} else {
					list.add(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return list;
	}

	public static ResultSet selectAll(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return rs;
	}
}
