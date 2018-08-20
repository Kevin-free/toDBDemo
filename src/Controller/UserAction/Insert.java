package Controller.UserAction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBUtil.DBUtil;
import POJO.User;
import ServiceDAO.UserServiceDAOimpl;
import ToExcel.TestEmail;
import ToExcel.toExcelUser;

/**
 * 插入数据至数据库，并调用导表发邮件
 * @author Kevin
 *
 */
public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserServiceDAOimpl userSD = new UserServiceDAOimpl();
	User user = new User();
	UploadPic up = new UploadPic();
	int n = 0;// 定义成员（全局）变量n记录输入数据数

	// ToDo ??获取时间后但不会更新 解决：date需要放方法里局部变量！才会重新获取

	String KevinEmail = "1215894562@qq.com";
	String oyqEmail = "1404055432@qq.com";
	String yjEmail = "787766819@qq.com";
	String teamEmail = null;
	String teamA4 = "xwt1215894562@163.com";
	String teamA7 = "15727546130@163.com";
	String teamB1 = "xwt1215894562@163.com";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 1、接收参数
		String userName = request.getParameter("userName");
		String userPhone = request.getParameter("userPhone");
		String userPassword = request.getParameter("userPassword");
		String imgPhone = request.getParameter("imgPhone");
System.out.println("receive:"+imgPhone);
		String item = request.getParameter("item");
		String room = request.getParameter("room");
		String addTxt = request.getParameter("addTxt");
		String myPhone = request.getParameter("myPhone");
		String team = request.getParameter("team");
		String myEmail = request.getParameter("myEmail");
		String dcmy = request.getParameter("dcmy");
		String pageNo = request.getParameter("pageNo");
		// 2、封装到对象user中
		user.setUserName(userName);
		user.setUserPhone(userPhone);
		user.setUserPassword(userPassword);
		if(imgPhone == null || imgPhone.equals("null")) {
			user.setImageUrl("");
		}else {
			user.setImageUrl(up.path+imgPhone+".jpg");
		}
		user.setItem(item);
		user.setRoom(room);
		user.setAddTxt(addTxt);
		user.setMyPhone(myPhone);
		if (team == null) {
			team = "0";
		}
		// 将获取的队伍下标转为中文队伍表示
		switch (team) {
		case "0":
			user.setTeam("A4队");
			teamEmail = teamA4;
			break;
		case "1":
			user.setTeam("A7队");
			teamEmail = teamA7;
			break;
		case "2":
			user.setTeam("B1队");
			teamEmail = teamB1;
			break;
		default:
			break;
		}
		user.setMyEmail(myEmail);
		user.setDcmy(dcmy);
		user.setPageNo(pageNo);

		System.out.println(user.toString());
		// 3、调用ServiceDAO中的添加业务
		if (userSD.insert(user)) {
			System.out.println("{\"success\":true,\"msg\":\"添加数据库成功\"}");
		} else {
			System.out.println("{\"success\":false,\"msg\":\"添加数据库失败\"}");
		}

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from user");
			toExcelUser xls = new toExcelUser();

			try {
				xls.toxls(rs);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			TestEmail te = new TestEmail();

			System.out.println("初值n:" + n);
			try {
				System.out.println("判断时n:" + n);
				if (n % 3 == 0) {
					te.sendMail3(KevinEmail);
					System.out.println("发送邮件给Kevin成功");
//					te.sendMail3(oyqEmail);
//					System.out.println("发送邮件给oyq成功");
//					te.sendMail3(yjEmail);
//					System.out.println("发送邮件给yj成功");
					te.sendMail3(teamEmail);
					System.out.println("发送邮件给队伍成功");
					if (myEmail != "") {
						te.sendMail3(myEmail);
						System.out.println("发送邮件给收件人成功");
					}
					n = n + 1;
					System.out.println("发送了邮件终值n:" + n);
				} else {
					n = n + 1;
					System.out.println("不发送邮件终值n:" + n);
					return;
				}

				if (conn != null) {
					try {// 关闭连接
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
