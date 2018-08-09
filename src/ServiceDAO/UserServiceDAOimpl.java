package ServiceDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.UserDAOimpl;
import DBUtil.DBUtil;
import POJO.User;

public class UserServiceDAOimpl implements UserServiceDAO{

	/*查询业务*/
	  public ArrayList<User> select(User user) {
	  Connection conn = DBUtil.getConnection();
	  UserDAOimpl userD = new UserDAOimpl(conn);
	    try{
	      ArrayList<User> userList = new ArrayList<User>();
	      userList = userD.select(user);
	      conn.commit();
	      return userList;
	    }catch(Exception e){
	      try {conn.rollback();} 
	      catch (SQLException ex) {ex.printStackTrace();}
	      return null;
	    }finally{
	      if(conn != null){DBUtil.closeConnection(conn);}
	    }
	  }

	  /*添加业务*/
	  public boolean insert(User user){
	    Connection conn = DBUtil.getConnection();
	    UserDAOimpl userD = new UserDAOimpl(conn);
	    try{
	      userD.insert(user);
	      conn.commit();
	      return true;
	    }catch(Exception e){
	      try {conn.rollback();} 
	      catch (SQLException ex) {ex.printStackTrace();}
	      return false;
	    }finally{
	      if(conn != null){DBUtil.closeConnection(conn);}
	    }
	  }

}
