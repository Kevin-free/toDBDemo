package Controller.UserAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qiniu.util.Auth;

/**
 * 将图片上传至七牛，获取token
 * @author Kevin
 *
 */
@WebServlet(name = "UserActionUpImg", urlPatterns="/image/upToken")
public class TokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {		    				
		String accessKey = "FVUVsWKatamyTmSh0dJn6SohOePJbVo0hiiz7b0q";		    				
		String secretKey = "asnKQoCjIElPs3dWV9nKj8-F2COq9G--jq5nwyGH";
		// 要上传的空间名--
		String bucket = "kevincrjdbucket";
		//默认不指定key的情况下，以文件内容的hash值作为文件名
//		String key = "zcm******/"+fileName;
		String  key = null;
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket, key);
	    System.out.println(upToken);
	    response.setContentType("text/html;charset=UTF-8");
	    
	    Map<String,String> map=new HashMap<String,String>();
	    map.put("uptoken",upToken);
	    Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		response.getWriter().write(json);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}
