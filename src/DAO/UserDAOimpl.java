package DAO;

import java.sql.*;
import java.util.ArrayList;

import POJO.User;

public class UserDAOimpl implements UserDAO {

	private Connection conn = null;
	private PreparedStatement pst = null;
	String dbuser = null; 

	/**
	 * 定义构造方法，实例化的时候完成连接的注入
	 */
	public UserDAOimpl(Connection conn) {
		super();
		this.conn = conn;
	}

	// 查询记录
	public ArrayList<User> select(User user) throws SQLException {
		String sql = "select * from user where 1=1";
		String condition = user.getCondition();
		String pageNo = user.getPageNo();
		if (condition != null && !condition.equals("")) {
			sql += " and " + condition;
		}
		if (pageNo != null && !pageNo.equals("")) {
			sql += " limit " + (Integer.parseInt(pageNo) - 1) * 5 + ",5";
		}
		pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ArrayList<User> userList = new ArrayList<User>();
		if (rs.next()) {
			for (int i = 0; i <= rs.getRow(); i++) {
				User userTmp = new User();
				userTmp.setUserName(rs.getString("userName"));
				userTmp.setUserPhone(rs.getString("userPhone"));
				userTmp.setUserPassword(rs.getString("userPassword"));
				userTmp.setItem(rs.getString("item"));
				userTmp.setRoom(rs.getString("room"));
				userTmp.setAddTxt(rs.getString("addTxt"));
				userTmp.setMyPhone(rs.getString("myPhone"));
				userTmp.setTeam(rs.getString("team"));
				//userTmp.setMyEmail(rs.getString("myEmail"));
				userTmp.setDcmy(rs.getString("dcmy"));
				userList.add(userTmp);
				rs.next();
			}
		}
		return userList;
	}

	// 插入记录
	public boolean insert(User user) throws SQLException {
		String team = user.getTeam();
		//TODO 队伍更改此处更新
		switch (team) {
		case "九院本部A4队":
			dbuser = "usera4";
			System.out.println("插入到表a4");
			break;
		case "九院本部A7队":
			dbuser = "usera7";
			System.out.println("插入到表a7");
			break;
		case "九院本部B9队":
			dbuser = "userb9";
			System.out.println("插入到表b9");
			break;
		default:
			break;
		}
		try {
			// 插入数据改变时，insert SQL到数据库也需要相应改变
			String sql = "insert into "+ dbuser +"(userName,userPhone,userPassword,imageUrl,item,room,addTxt,myPhone,myName,team,dcmy) values(?,?,?,?,?,?,?,?,?,?,?) ";
			pst = conn.prepareStatement(sql);
			// pst.setString(1, user.getDate());
			pst.setString(1, user.getUserName());
			pst.setString(2, user.getUserPhone());
			pst.setString(3, user.getUserPassword());
			pst.setString(4, user.getImageUrl());
			System.out.println("insert user imgUrl:" + user.getImageUrl());
			pst.setString(5, user.getItem());
			pst.setString(6, user.getRoom());
			pst.setString(7, user.getAddTxt());
			pst.setString(8, user.getMyPhone());
			pst.setString(9, user.getMyName());
			pst.setString(10, user.getTeam());
			//pst.setString(10, user.getMyEmail());
			pst.setString(11, user.getDcmy());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(User user) throws SQLException {
		return false;
	}

	public boolean update(User user) throws SQLException {
		return false;
	}

	public int count(User user) throws SQLException {
		return 0;
	}

}
