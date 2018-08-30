package Controller.AdminAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import POJO.Cr;
import ServiceDAO.CrServiceDAOimpl;

/**
 * 管理员增加橙人
 * @author Kevin
 *
 */
@WebServlet(name = "AdminActionAdd", urlPatterns = "/AdminAction/AddCr")

public class AddCr extends HttpServlet{
	private static final long serialVersionUID = 1L;
	CrServiceDAOimpl crSD = new CrServiceDAOimpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置编码，否则中文乱码
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String crName = req.getParameter("crName");
		String crPhone = req.getParameter("crPhone");
		String crTeam = req.getParameter("crTeam");
		Cr cr = new Cr(crPhone, crName,crTeam);//调用定义的构造方法，即set方法
		System.out.println(cr.toString());
		Boolean isAdd = false;
		//is add
		isAdd = crSD.insert(cr);
		// 3、调用ServiceDAO中的添加业务
		Map<String,String> map=new HashMap<String,String>();
		if(isAdd) map.put("isAdd", "ok");
		else map.put("isAdd", "err");
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().write(json);
	}
}
