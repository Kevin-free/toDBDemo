package Controller.UserAction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import POJO.User;
import ServiceDAO.UserServiceDAOimpl;
import ToExcel.TestEmail;
import ToExcel.toExcelUser;

public class Insert extends HttpServlet{
  private static final long serialVersionUID = 1L;
  UserServiceDAOimpl userSD = new UserServiceDAOimpl();
  User user = new User();
  int n = 0;//定义成员（全局）变量n记录输入数据数

//ToDo ??获取时间后但不会更新 解决：date需要放方法里局部变量！才会重新获取
 
  String KevinEmail = "1215894562@qq.com";
  String oyqEmail = "1404055432@qq.com";
  String teamEmail = null;
  String teamA4 = "xwt1215894562@163.com";
  String teamA7 = "15727546130@163.com";
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    //1、接收参数
    String userName = request.getParameter("userName");
    String userPhone = request.getParameter("userPhone");
    String userPassword = request.getParameter("userPassword");
    String item = request.getParameter("item");
    String room = request.getParameter("room");
    String addTxt = request.getParameter("addTxt");
    String myPhone = request.getParameter("myPhone");
    String team = request.getParameter("team");
    String myEmail = request.getParameter("myEmail");
    String dcmy = request.getParameter("dcmy");
    String pageNo = request.getParameter("pageNo");
    //2、封装到对象user中

    /*Date date = new Date();//获取系统时间
    String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.
    Timestamp sqlTime = Timestamp.valueOf(nowTime);//把时间转换*/
    //user.setDate(sqlTime.toString());
//System.out.println("nowTime:"+sqlTime);    
    user.setUserName(userName);
    user.setUserPhone(userPhone);
    user.setUserPassword(userPassword);
    user.setItem(item);
    user.setRoom(room);
    user.setAddTxt(addTxt);
    user.setMyPhone(myPhone);
    if(team == null) {
    	team = "0";
    }
    //将获取的队伍下标转为中文队伍表示
    switch (team) {
	case "0":
		user.setTeam("A4队");
		teamEmail = teamA4;
		break;
	case "1":
		user.setTeam("A7队");
		teamEmail = teamA7;
		break;

	default:
		break;
	}
    user.setMyEmail(myEmail);
    user.setDcmy(dcmy);
    user.setPageNo(pageNo);
       
    System.out.println(user.toString());
    //3、调用ServiceDAO中的添加业务
    if(userSD.insert(user)){
    	System.out.println("{\"success\":true,\"msg\":\"添加数据库成功\"}");
    }else{
    	System.out.println("{\"success\":false,\"msg\":\"添加数据库失败\"}");
    }
    
    try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String url = "jdbc:mysql://localhost:3306/yidong?useUnicode=true&characterEncoding=UTF8";
    String user1 = "root";
    String password = "1258";
    Connection conn;
	try {
		conn = DriverManager.getConnection(url,user1,password);
		Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery("select * from user");
	    toExcelUser xls = new toExcelUser();
	   	  
	    xls.toxls(rs);
	    TestEmail te = new TestEmail();
	    
System.out.println("初值n:"+n);
	    try {
System.out.println("判断时n:"+n);	    
	    	if(n%3 == 0) {
	    	te.sendMail3(KevinEmail);
System.out.println("发送邮件给Kevin成功");	 
			te.sendMail3(oyqEmail);
System.out.println("发送邮件给oyq成功");	   	
	    	te.sendMail3(teamEmail);
System.out.println("发送邮件给队伍成功");
			if(myEmail != "") {
				te.sendMail3(myEmail);
System.out.println("发送邮件给收件人成功");			
			}
			n = n+1;
System.out.println("发送了邮件终值n:"+n);	   
	    	}
	    	else {
	    		n = n+1;
System.out.println("不发送邮件终值n:"+n);		    		
	    		return ;
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
