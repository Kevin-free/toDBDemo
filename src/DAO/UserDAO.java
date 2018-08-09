package DAO;
import java.sql.*;
import java.util.ArrayList;

import POJO.User;

public interface UserDAO {
  public ArrayList<User> select(User user) throws SQLException, ClassNotFoundException;//查询
  public boolean insert(User user) throws SQLException;//添加
  public boolean delete(User user) throws SQLException;//删除
  public boolean update(User user) throws SQLException;//修改
  public int count(User user) throws SQLException;//统计记录数  
}
