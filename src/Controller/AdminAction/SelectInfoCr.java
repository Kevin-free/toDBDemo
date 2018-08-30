package Controller.AdminAction;

import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "AdminActionSelInfo", urlPatterns = "/AdminAction/SelInfoCr")
public class SelectInfoCr extends HttpServlet{
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
	    String crName = req.getParameter("crName");
		String crPhone = req.getParameter("crPhone");
		System.out.println("crname:"+crName+"crphone:"+crPhone);
		String crTeam = req.getParameter("crTeam");
		Cr cr = new Cr(crPhone, crName,crTeam);
		ArrayList<Cr> crList = crSD.selectInfo(cr);
		Map<String,String> map=new HashMap<String,String>();
		if(crList.isEmpty())
		{
			map.put("conInfoCr", "notCon");
			return;
		}else {
			map.put("conInfoCr", "isCon");
			map.put("crName", crList.get(0).getName());
			map.put("crPhone", crList.get(0).getPhone());
		}
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().write(json);
				
	}

}
