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
 * 管理员查询是否有该橙人
 * @author Kevin
 *
 */
@WebServlet(name = "AdminActionSel", urlPatterns = "/AdminAction/SelCr")

public class SelectCr extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CrServiceDAOimpl crSD = new CrServiceDAOimpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String crName = req.getParameter("crName");
		String crPhone = req.getParameter("crPhone");
		String crTeam = req.getParameter("crTeam");
		Cr cr = new Cr(crPhone, crName,crTeam);
		//初始化为false
		Boolean isCon = false;
		//判断是否含有橙人
		isCon = crSD.select(cr);
		Map<String,String> map=new HashMap<String,String>();
		if(isCon) map.put("conCr", "isCon");
		else map.put("conCr", "notCon");
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().write(json);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
