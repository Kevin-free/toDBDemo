package ServiceDAO;

import java.util.ArrayList;

import POJO.Cr;

public interface CrServiceDAO {
	public ArrayList<Cr> selectInfo(Cr cr);//查询业务
	public Boolean select(Cr cr);//查询业务
	public boolean insert(Cr cr);//添加业务
	public boolean delete(Cr cr);//删除业务
	public boolean update(Cr cr);//修改业务
}
