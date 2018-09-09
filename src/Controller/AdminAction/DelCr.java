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
 * 管理员删除橙人
 * @author Kevin
 *
 */
@WebServlet(name = "AdminActionDel", urlPatterns = "/AdminAction/DelCr")

public class DelCr extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CrServiceDAOimpl crSD = new CrServiceDAOimpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
	    resp.setContentType("text/html;charset=utf-8");
	    //1、接收参数
		String crPhone = req.getParameter("crPhone");
		String crTeam = req.getParameter("crTeam");
		Cr cr = new Cr(crPhone,crTeam);
	    //3、调用ServiceDAO中的删除业务
	    Boolean isDel = false;
		//is add
		isDel = crSD.delete(cr);
		// 3、调用ServiceDAO中的添加业务
		Map<String,String> map=new HashMap<String,String>();
		if(isDel) map.put("isDel", "ok");
		else map.put("isDel", "err");
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().write(json);
	}
}
