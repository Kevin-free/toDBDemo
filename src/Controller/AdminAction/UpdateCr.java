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
 * 管理员修改橙人
 * @author Kevin
 *
 */
@WebServlet(name = "AdminActionUpd", urlPatterns = "/AdminAction/UpdCr")
public class UpdateCr extends HttpServlet{
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
	    //接收参数
	    String crPhone = req.getParameter("crPhone");
		String crTeam = req.getParameter("crTeam");
		String butName = req.getParameter("butName");
		String butPhone = req.getParameter("butPhone");
		Cr cr = new Cr(crPhone, crTeam, butName, butPhone);
		Boolean isUpd = false;
		isUpd = crSD.update(cr);
		
		Map<String,String> map=new HashMap<String,String>();
		if(isUpd) map.put("isUpd", "ok");
		else map.put("isUpd", "err");
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().write(json);
	}

}
