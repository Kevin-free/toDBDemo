package ServiceDAO;

import java.util.ArrayList;
import POJO.User;

public interface UserServiceDAO {
	public ArrayList<User> select(User user);//查询业务
	public boolean insert(User user);//添加业务
}
