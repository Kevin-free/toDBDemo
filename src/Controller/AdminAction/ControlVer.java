package Controller.AdminAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import Controller.UserAction.CheckCr;

/**
 * 动态控制版本
 * @author Kevin
 *
 */
@WebServlet(name="ControlVer", urlPatterns="/AdminAction/ControlVer"
)
public class ControlVer extends HttpServlet{ 
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CheckCr.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String,String> map=new HashMap<String,String>();
		map.put("ver", "3");
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
