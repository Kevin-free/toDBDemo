package Controller.AdminAction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import DAO.LoginAdminDAO;
import POJO.Admin;

/**
 * 查询是否为管理员判断登录
 * @author Kevin
 *
 */
@WebServlet(name="LoginAdmin", urlPatterns="/AdminAction/Login"
)
public class LoginAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginAdminServlet.class);

	public LoginAdminServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收小程序传过来的account和password
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		Admin admin = new Admin(account, password);
		LoginAdminDAO lad = new LoginAdminDAO();
		// 初始化为false
		Boolean isLogin = false;
		try {
			//判断是否登陆成功
			isLogin = lad.Login(admin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,String> map=new HashMap<String,String>();
		if(isLogin) {
			map.put("isLogin", "ok");
			map.put("team", admin.getTeam());
		}
		else map.put("isLogin", "err");
		//要将Map转化为JSON，才可以传数据返回小程序
		Gson gson = new Gson();
		String json = gson.toJson(map);
		logger.debug(json);
		resp.getWriter().write(json);	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
