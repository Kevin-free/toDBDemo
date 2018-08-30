package DAO;

import java.sql.*;


import DBUtil.DBUtil;
import POJO.Admin;

/**
 * 登录：即操作数据库 查询
 * @author Kevin
 *
 */
public class LoginAdminDAO {

	Connection conn = DBUtil.getConnection();
	
	public Boolean Login(Admin admin) throws SQLException{
		PreparedStatement ps=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		String sql="select * from admin where account=? and password=?";//admin是数据库的表名，account和password是表所对应的属性
		ps=conn.prepareStatement(sql);
		ps.setString(1,admin.getAccount());
		ps.setString(2,admin.getPassword());
		rs=ps.executeQuery();
		if(rs.next())	{
			String sql2 = "select * from admin where account = "+ admin.getAccount();
			try {
				pst = conn.prepareStatement(sql2);
				rs = pst.executeQuery();
				if (rs.next()) {
					for (int i = 0; i <= rs.getRow(); i++) {
						admin.setTeam(rs.getString("team"));
						System.out.println("admin team:"+rs.getString("team"));
						rs.next();
					}
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			return true;
		}
		else return false;
	}
}
