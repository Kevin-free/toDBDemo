package Controller.UserAction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.google.gson.Gson;

import DBUtil.DBUtil;
import POJO.User;
import ServiceDAO.UserServiceDAOimpl;
import ToExcel.TestEmail;
import ToExcel.toExcelUser;

/**
 * 插入数据至数据库，并调用导表发邮件
 * 
 * @author Kevin
 *
 */

@WebServlet(name = "UserActionInsert", urlPatterns = "/UserAction/Insert")

public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private PreparedStatement pst = null;
	UserServiceDAOimpl userSD = new UserServiceDAOimpl();
	User user = new User();
	UploadPic up = new UploadPic();
	int n = 0;// 定义成员（全局）变量n记录输入数据数

	// ToDo ??获取时间后但不会更新 解决：date需要放方法里局部变量！才会重新获取
	String dbuser = null;// 设置变量用于根据不同队伍插入不同数据表
	String dbcr = null;//查哪个队的橙人
	String touserXls = null; //存储的Excel名
	String teamXls = null;// 设置变量获取本地Excel文件
	String teamText = null; //设置写入的队伍文本
	String teamEmail = null; // 设置变量发送给哪个队伍
	String teamAdmin = null; //设置管理员队伍变量
	String KevinEmail = "1215894562@qq.com";// 设置始终接收者接收所有队Email
	String oyqEmail = "1404055432@qq.com";
	String yjEmail = "787766819@qq.com";
	String ctEmail = "S_love_en@163.com";

	// 定义业务item文字
	String item0 = "——预约办卡——";
	String item1 = "免费送校内40G";
	String item2 = "38元低消宽带";
	String item3 = "48元低消宽带";
	String item4 = "38元低消WiFi";
	String item5 = "3元WiFi";
	String item6 = "6元WiFi";
	String item7 = "转校园28卡(4年)";
	String item8 = "转校园48卡(4年)";
	String item9 = "两城一号";
	String item10 = "校园3000分钟免费V网";
	String item11 = "30元钻石会员权益包";
	String item12 = "存送话费";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	//增，删，改 数据建议用Post(安全性高，效率低)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置编码格式，防止中文乱码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//连接数据库
		conn = DBUtil.getConnection();
		// 1、接收参数
		String userName = request.getParameter("userName");
		String userPhone = request.getParameter("userPhone");
		String userPassword = request.getParameter("userPassword");
		String imgPhone = request.getParameter("imgPhone");
		String item = request.getParameter("item");
		System.out.println("item:"+item);
		String room = request.getParameter("room");
		String addTxt = request.getParameter("addTxt");
		String myPhone = request.getParameter("myPhone");
		String team = request.getParameter("team");
		String dcmy = request.getParameter("dcmy");
		String pageNo = request.getParameter("pageNo");
		// 2、封装到对象user中
		user.setUserName(userName);
		user.setUserPhone(userPhone);
		user.setUserPassword(userPassword);
		if (imgPhone == null || imgPhone.equals("null")) {
			user.setImageUrl("");
		} else {
			user.setImageUrl(imgPhone);
		}

		// user.setItem(item);
		user.setRoom(room);
		user.setAddTxt(addTxt);

		// TODO 队伍更改此处更新
		// 将获取的队伍下标转为中文队伍表示
		switch (team) {
		case "0":
			teamText = "九院本部A4队";//设置存入的文本
			user.setTeam(teamText);
			dbuser = "usera4";// 从哪数据表获取数据
			dbcr = "a4cr";//验证哪个队的橙人
			touserXls = "userA4";//以什么名命名Excel
			teamXls = "userA4";// 用哪个Excel发送邮件
			teamAdmin = "a4";//以admin字段中取发送邮件给哪个队
			break;
		case "1":
			teamText = "九院本部A7队";
			user.setTeam(teamText);
			dbuser = "usera7";
			dbcr = "a7cr";
			touserXls = "userA7";
			teamXls = "userA7";
			teamAdmin = "a7";
			break;
		case "2":
			teamText = "九院本部B9队";
			user.setTeam(teamText);
			dbuser = "userb9";
			dbcr = "b9cr";
			touserXls = "userB9";
			teamXls = "userB9";
			teamAdmin = "b9";
			break;
		default:
			break;
		}
		
		//根据队伍获取管理员邮箱
		String sqladmin = "select * from admin where team = '"+teamAdmin+"' ";//若是字符型需要加单引号
		try {
			pst = conn.prepareStatement(sqladmin);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) { 
				for (int i = 0; i <= rs.getRow(); i++) {
					if(rs.getString("email") != null) {						
						teamEmail = rs.getString("email");
					}
					rs.next();
				}
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		
		user.setMyPhone(myPhone);
		//根据用户电话获取用户姓名
		String sql = "select * from "+ dbcr +" where phone = "+ myPhone;//整型数不需要加不会有问题
		System.out.println(sql);
		try {
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				for (int i = 0; i <= rs.getRow(); i++) {
					user.setMyName(rs.getString("name"));
					rs.next();
				}
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		user.setDcmy(dcmy);
		user.setPageNo(pageNo);
		System.out.println("received item:" + item);
		// 处理item业务
		if (item.contains("00")) {
			user.setItem(item0);
		}else {
			user.setItem("——办理业务如下——");
		}
		System.out.println(user.toString());
		Boolean isInsert = false;
		//判断是否插入成功
		isInsert = userSD.insert(user);
		System.out.println("isInsert:"+isInsert);
		Map<String,String> map=new HashMap<String,String>();
		if(isInsert) {
			map.put("isInsert", "ok");
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			response.getWriter().write(json);
		}
		else {
			return;
		}

		if (item.contains("01")) {
			User user2 = new User();
			user2.setItem(item1);
			user2.setTeam(teamText);
			userSD.insert(user2);
		}
		if (item.contains("02")) {
			User user3 = new User();
			user3.setItem(item2);
			user3.setTeam(teamText);
			userSD.insert(user3);
		}
		if (item.contains("03")) {
			User user4 = new User();
			user4.setItem(item3);
			user4.setTeam(teamText);
			userSD.insert(user4);
		}
		if (item.contains("04")) {
			User user5 = new User();
			user5.setItem(item4);
			user5.setTeam(teamText);
			userSD.insert(user5);
		}
		if (item.contains("05")) {
			User user6 = new User();
			user6.setItem(item5);
			user6.setTeam(teamText);
			userSD.insert(user6);
		}
		if (item.contains("06")) {
			User user7 = new User();
			user7.setItem(item6);
			user7.setTeam(teamText);
			userSD.insert(user7);
		}
		if (item.contains("07")) {
			User user8 = new User();
			user8.setItem(item7);
			user8.setTeam(teamText);
			userSD.insert(user8);
		}
		if (item.contains("08")) {
			User user9 = new User();
			user9.setItem(item8);
			user9.setTeam(teamText);
			userSD.insert(user9);
		}
		if (item.contains("09")) {
			User user10 = new User();
			user10.setItem(item9);
			user10.setTeam(teamText);
			userSD.insert(user10);
		}
		if (item.contains("10")) {
			User user11 = new User();
			user11.setItem(item10);
			user11.setTeam(teamText);
			userSD.insert(user11);
		}
		if (item.contains("11")) {
			User user12 = new User();
			user12.setItem(item11);
			user12.setTeam(teamText);
			userSD.insert(user12);
		}
		if (item.contains("12")) {
			User user13 = new User();
			user13.setItem(item12);
			user13.setTeam(teamText);
			userSD.insert(user13);
		}
		//插入一条空数据隔开显示
		User userlast = new User();
		userlast.setItem("  ");
		userlast.setTeam(teamText);
		userSD.insert(userlast);
		
		//解决不同步bug，在commit一次
		try {
			conn.commit();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from " + dbuser);
			toExcelUser xls = new toExcelUser();
			// 导出xls：待解决bug 当前次rs为空，无法写入Excel。 
			try {
				xls.toxls(rs,touserXls);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			//发邮件
			TestEmail te = new TestEmail();

			System.out.println("初值n:" + n);
			try {
				System.out.println("判断时n:" + n);
				if (n % 5 == 0) {
//					te.sendMail3(KevinEmail, teamXls);
//					System.out.println("发送邮件给Kevin成功");
					te.sendMail3(oyqEmail, teamXls);
					System.out.println("发送邮件给oyq成功");
					te.sendMail3(ctEmail, teamXls);
					System.out.println("发送邮件给ct成功");
					te.sendMail3(teamEmail, teamXls);
					System.out.println("发送邮件给队伍" + team + "成功" + teamEmail);

					n = n + 1;
					System.out.println("发送了邮件终值n:" + n);
				} else {
					n = n + 1;
					System.out.println("不发送邮件终值n:" + n);
					return;
				}
				
				//定时发送邮件功能
				//创建工作对象
		        JobDetail job = JobBuilder.newJob(TestEmail.class)
		        .withIdentity("dummyJobName", "group1").build();
		        //传值
		        job.getJobDataMap().put("teamXls", teamXls);
		        job.getJobDataMap().put("KevinEmail", KevinEmail);
		        job.getJobDataMap().put("oyqEmail", oyqEmail);
		        job.getJobDataMap().put("ctEmail", ctEmail);
		        job.getJobDataMap().put("teamEmail", teamEmail);
		        
//		        //为了立即测试，可以使用下面的代码，每隔5秒钟执行一次
//		        int secDelta = 50;
//		         Trigger trigger = TriggerBuilder
//		         .newTrigger()
//		         .withIdentity("dummyTriggerName", "group1")
//		         .withSchedule(
//		             CronScheduleBuilder.cronSchedule("0/" + secDelta + " * * * * ?"))
//		         .build();
		        
		      //在每天的21点,22点执行
		        //创建触发器对象
		         Trigger trigger = TriggerBuilder
		        .newTrigger()
		        .withIdentity("dummyTriggerName", "group1")
		        .withSchedule(
		            CronScheduleBuilder.cronSchedule("0 0 21,22 * * ?"))
		        .build();
		         
		         Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		         scheduler.start();
		         //将触发器与工作关联起来
		         scheduler.scheduleJob(job, trigger);  

				if (conn != null) {
					try {// 关闭连接
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
