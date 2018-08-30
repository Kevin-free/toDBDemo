package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Cr;

public interface CrDAO {
	  public ArrayList<Cr> selectInfo(Cr cr) throws SQLException, ClassNotFoundException;//查询详细信息
	  public Boolean select(Cr cr) throws SQLException, ClassNotFoundException;//查询
	  public boolean insert(Cr cr) throws SQLException;//添加
	  public boolean delete(Cr cr) throws SQLException;//删除
	  public boolean update(Cr cr) throws SQLException;//修改
}
