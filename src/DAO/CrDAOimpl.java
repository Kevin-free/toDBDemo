package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Cr;

public class CrDAOimpl implements CrDAO {

	private Connection conn = null;
	private PreparedStatement pst = null;
	String tableCr = null;
	
	public CrDAOimpl() {
		super();
	}

	/**
	 * 定义构造方法，实例化的时候完成连接的注入
	 */
	public CrDAOimpl(Connection conn,Cr cr) {
		super();
		this.conn = conn;
		String team = cr.getTeam();
		System.out.println("whileCrDAO insert team:" + team);
		switch (team) {
		// TODO 是否设置超级管理员
		// case "all":
		// tableCr = "all";
		// break;
		case "a4":
			tableCr = "a4cr";
			break;
		case "a7":
			tableCr = "a7cr";
			break;
		case "b9":
			tableCr = "b9cr";
			break;
		default:
			break;
		}
	}

	@Override
	public ArrayList<Cr> selectInfo(Cr cr) throws SQLException, ClassNotFoundException {
		String sql = "select * from " + tableCr + " where phone=?";
		System.out.println("sleInfo cr" + sql);
	    pst = conn.prepareStatement(sql);
		pst.setString(1, cr.getPhone());//记得要先设置pst.set,在调用rs = pst.exe
	    ResultSet rs = pst.executeQuery();
	    ArrayList<Cr> crList = new ArrayList<Cr>();
	    if(rs.next()){
	      for(int i=0;i<=rs.getRow();i++){
	          Cr crTmp = new Cr();
	          crTmp.setName(rs.getString("name"));
	          crTmp.setPhone(rs.getString("phone"));
	          crList.add(crTmp);
	        rs.next();
	      }
	    }
	    return crList;
	}
	
	@Override
	public Boolean select(Cr cr) throws SQLException, ClassNotFoundException {
		String sql = "select * from " + tableCr + " where phone=?";
		System.out.println("sle cr" + sql);
		pst = conn.prepareStatement(sql);
		pst.setString(1, cr.getPhone());//记得要先设置pst.set,在调用rs = pst.exe,否则No value specified for parameter 1
		ResultSet rs = pst.executeQuery();
		if (tableCr != null) {
			if (rs.next())
				return true;
			else
				return false;
		} else {
			// System.out.println("teamTable不存在，不执行查询橙人");
			return false;
		}
	}

	@Override
	public boolean insert(Cr cr) throws SQLException {
		
		try {
			String sql = "insert into " + tableCr + "(phone,name) values(?,?) ";
			pst = conn.prepareStatement(sql);
			pst.setString(1, cr.getPhone());
			pst.setString(2, cr.getName());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(Cr cr) throws SQLException {
		try {
			String sql = "delete from " + tableCr + " where phone=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, cr.getPhone());
			pst.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Cr cr) throws SQLException {
		try{
		      String sql = "update " + tableCr + " set name=?,phone=? where phone=?";
		      pst = conn.prepareStatement(sql);
		      pst.setString(1, cr.getButName());
		      pst.setString(2, cr.getButPhone());
		      pst.setString(3, cr.getPhone());
		      pst.executeUpdate();
		      return true;
		    }catch(Exception e){
		      return false;
		    }
	}


}
