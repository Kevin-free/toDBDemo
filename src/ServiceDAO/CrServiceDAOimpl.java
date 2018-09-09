package ServiceDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.CrDAOimpl;
import DBUtil.DBUtil;
import POJO.Cr;

public class CrServiceDAOimpl implements CrServiceDAO {

	@Override
	public ArrayList<Cr> selectInfo(Cr cr) {
		Connection conn = DBUtil.getConnection();
		CrDAOimpl crD = new CrDAOimpl(conn,cr);
		try {
			ArrayList<Cr> crList = new ArrayList<Cr>();
			crList = crD.selectInfo(cr);
			conn.commit();
			return crList;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return null;
		} finally {
			if (conn != null) {
				DBUtil.closeConnection(conn);
			}
		}
	}

	@Override
	public Boolean select(Cr cr) {
		Connection conn = DBUtil.getConnection();
		CrDAOimpl crD = new CrDAOimpl(conn, cr);
		try {
			if (crD.select(cr)) {
				// conn.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			System.out.println("catch exce");
			return false;
		} finally {
			if (conn != null) {
				DBUtil.closeConnection(conn);
			}
		}
	}

	@Override
	public boolean insert(Cr cr) {
		Connection conn = DBUtil.getConnection();
		CrDAOimpl crD = new CrDAOimpl(conn, cr);
		try {
			Boolean isInsert = crD.insert(cr);
			conn.commit();
			return isInsert;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return false;
		} finally {
			if (conn != null) {
				DBUtil.closeConnection(conn);
			}
		}
	}

	@Override
	public boolean delete(Cr cr) {
		Connection conn = DBUtil.getConnection();
		CrDAOimpl crD = new CrDAOimpl(conn, cr);
		try {
			Boolean isDel = crD.delete(cr);
			conn.commit();
			return isDel;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return false;
		} finally {
			if (conn != null) {
				DBUtil.closeConnection(conn);
			}
		}
	}

	@Override
	public boolean update(Cr cr) {
		Connection conn = DBUtil.getConnection();
		CrDAOimpl crD = new CrDAOimpl(conn,cr);
	    try{
	      Boolean isUpd = crD.update(cr);
	      conn.commit();
	      return isUpd;
	    }catch(Exception e){
	      try {conn.rollback();} 
	      catch (SQLException ex) {ex.printStackTrace();}
	      return false;
	    }finally{
	      if(conn != null){DBUtil.closeConnection(conn);}
	    }
	}

}
