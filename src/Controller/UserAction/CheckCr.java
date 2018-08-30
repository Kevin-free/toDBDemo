package Controller.UserAction;

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

import DAO.CheckCrDAO;
import POJO.Cr;

/**
 * 检验是否有橙人提交权限，相当于登录功能
 * @author Kevin
 *
 */
@WebServlet(name="CheckCrLogin", urlPatterns="/UserAction/CheckCr"
)

public class CheckCr extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CheckCr.class);
	
	//查询数据建议用Get(安全性低，效率高)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String teamTable = null;
		//接收小程序传过来的team
		String team = req.getParameter("team");
		//TODO 更新队伍时要更新查询的数据库
		switch (team) {
		case "0":
			teamTable = "a4cr";
			break;
		case "1":
			teamTable = "a7cr";
			break;
		case "2":
			teamTable = "b9cr";
			break;
		default:
			break;
		}
		//接收小程序传过来的phone
		String phone = req.getParameter("phone");
		Cr cr = new Cr(phone);
		CheckCrDAO checkcrdao = new CheckCrDAO();
		//初始化为false
		Boolean isCheck = false;
		try {
			//判断是否登陆成功
			isCheck = checkcrdao.Login(cr,teamTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map<String,String> map=new HashMap<String,String>();
		if(isCheck) map.put("isCheck", "ok");
		else map.put("isCheck", "err");
		//要将Map转化为JSON，才可以传数据返回小程序
		/*JSONObject mapObject=JSONObject.fromObject(map);
//		System.out.println(phone+"验证消息返回前台:"+mapObject.toString());
		logger.debug(phone+"验证消息返回前台:"+mapObject.toString());
		//从服务器传mapObject数据到小程序
		resp.getWriter().write(mapObject.toString());*/  	
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		logger.debug(json);
		resp.getWriter().write(json);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
