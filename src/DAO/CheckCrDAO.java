package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import DBUtil.DBUtil;
import POJO.Cr;

/**
 * 查询某队是否有该橙人
 * @author Kevin
 *
 */
public class CheckCrDAO {
	Connection conn = DBUtil.getConnection();
	private static final Logger logger = Logger.getLogger(CheckCrDAO.class);

	// String teamTable = null;//定义变量队伍表动态查询哪个队
	public Boolean Login(Cr cr, String teamTable) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from " + teamTable + " where phone=?";// a4cr是数据库的表名
//		System.out.println("查询的sql：" + sql);
		logger.debug("查询的sql：" + sql);
		ps = conn.prepareStatement(sql);
		ps.setString(1, cr.getPhone());
		if (teamTable != null) {
			rs = ps.executeQuery();
			if (rs.next())
				return true;
			else
				return false;	
		} else {
//System.out.println("teamTable不存在，不执行查询橙人");	
			logger.debug("teamTable不存在，不执行查询橙人");
			return false;
		}
	}

}
