package ToExcel;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;

/**
 * JavaMail 版本: 1.6.0 JDK 版本: JDK 1.7 以上（必须）
 */
public class TestEmail implements Job {
	public String path = "/home/yidongdata/userXls/";// 设置Linux系统文件夹路径
	// public String path = "E:/yidongdata/userXls/";// 设置文件夹路径
	public String destPath = null;// 设置图片根路径
	/*
	 * SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,需要改为对应邮箱的 SMTP 服务器的端口,
	 * 具体可查看对应邮箱服务的帮助, QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
	 */
	public final String senderEmail = "1215894562@qq.com";
	public final String senderName = "Kevin";
	public final String fwq = "smtp.qq.com";
	public final String sqm = "qouebuengldujagi";// 授权码，在自己的QQ邮箱中设置
	public final String smtpPort = "465";

	public void sendMail3(String reciverEmail, String team) throws Exception {
		// 1.创建邮件对象
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.port", smtpPort);
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.socketFactory.port", smtpPort);

		properties.put("mail.smtp.host", fwq); // 指定SMTP服务器
		properties.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
		System.out.println("带有附件的邮件-----start3");

		Session session = Session.getInstance(properties);
		MimeMessage message = new MimeMessage(session);

		/*
		 * 2.设置发件人 其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
		 */
		message.setFrom(new InternetAddress(senderEmail, senderName, "UTF-8"));
		/*
		 * 3.设置收件人 To收件人 CC 抄送 BCC密送
		 */
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(reciverEmail, "team", "UTF-8"));// setRecipient写最前

		/* 4.设置标题 */
		message.setSubject("橙人交单表邮件", "UTF-8");
		// message.setContent("你好~请注意查收！","text/html;charset=UTF-8");

		/* 5.设置邮件正文 */

		// 一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
		MimeMultipart multipart = new MimeMultipart();

		// 读取本地图片,将图片数据添加到"节点"
		/*
		 * MimeBodyPart image = new MimeBodyPart(); DataHandler dataHandler1 = new
		 * DataHandler(new FileDataSource("E:\\img.png"));
		 * image.setDataHandler(dataHandler1); image.setContentID("image");
		 */

		// 创建文本节点
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("你好~请注意查收！", "text/html;charset=UTF-8");

		// 创建附件节点 读取本地文件,并读取附件名称
		destPath = path + team + ".xls";
		MimeBodyPart file2 = new MimeBodyPart();
		DataHandler dataHandler3 = new DataHandler(new FileDataSource(destPath));
		file2.setDataHandler(dataHandler3);
		file2.setFileName(MimeUtility.encodeText(dataHandler3.getName()));

		// 将文本和图片添加到multipart
		multipart.addBodyPart(text);
		/* multipart.addBodyPart(image); */
		multipart.addBodyPart(file2);
		multipart.setSubType("mixed");// 混合关系

		message.setContent(multipart);

		message.setSentDate(new Date());
		message.saveChanges();
		Transport transport = session.getTransport("smtp");
		transport.connect(fwq, senderEmail, sqm);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		// Boolean isFlag = true;
		// logger.info(Calendar.getInstance().getTime()+" : # Send mail to "+" success
		// #");

		System.out.println("sendMailServlet-----end3");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("-----do quartz execute-----");
		// 设置属性
		Properties props = new Properties();
		// QQ邮箱发件的服务器和端口
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");

		// 接收值
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String teamXls = dataMap.getString("teamXls");
		String KevinEmail = dataMap.getString("KevinEmail");
		String oyqEmail = dataMap.getString("oyqEmail");
		String yjEmail = dataMap.getString("yjEmail");
		String ctEmail = dataMap.getString("ctEmail");
		String teamEmail = dataMap.getString("teamEmail");
		System.out.println("recived team:" + teamXls);
		System.out.println(
				"recived Email:" + KevinEmail + "," + oyqEmail + "," + yjEmail + "," + ctEmail + "," + teamEmail);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// 填写你的qq邮箱用户名和密码
				return new PasswordAuthentication(senderEmail, sqm);
			}
		});
		MimeMessage message = new MimeMessage(session);
		// 这里用flag来标记是否发件成功（有时候会连不上服务器)，
		// 如果没有，继续发送，直到发送成功为止。
		int flag = 0;
		{
			try {
				// 设置发件人，收件人，主题和文本内容，并发送
				message.setFrom(new InternetAddress(senderEmail, senderName, "UTF-8"));
				message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(KevinEmail, "summary", "UTF-8"));// setRecipient写最前
				System.out.println("add1");
				message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(oyqEmail, "summary", "UTF-8"));
				System.out.println("add2");
				message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(yjEmail, "summary", "UTF-8"));
				System.out.println("add3");
				message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(ctEmail, "summary", "UTF-8"));
				System.out.println("add4");
				if(teamEmail != null) {
					message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(teamEmail, "summary", "UTF-8"));
					System.out.println("add5");
				}
				message.setSubject("今日橙人交单汇总表", "UTF-8");
				System.out.println("Preparing sending mail...");
				// 一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
				MimeMultipart multipart = new MimeMultipart();
				// 创建文本节点
				MimeBodyPart text = new MimeBodyPart();
				text.setContent("今日汇总表！", "text/html;charset=UTF-8");

				// 创建附件节点 读取本地文件,并读取附件名称
				destPath = path + teamXls + ".xls";
				MimeBodyPart file2 = new MimeBodyPart();
				DataHandler dataHandler3 = new DataHandler(new FileDataSource(destPath));
				file2.setDataHandler(dataHandler3);
				file2.setFileName(MimeUtility.encodeText(dataHandler3.getName()));

				// 将文本和图片添加到multipart
				multipart.addBodyPart(text);
				/* multipart.addBodyPart(image); */
				multipart.addBodyPart(file2);
				multipart.setSubType("mixed");// 混合关系

				message.setContent(multipart);

				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(fwq, senderEmail, sqm);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				flag = 1;
				System.out.println("message sent successfully");
			} catch (Exception e) {
				flag = 0;
			}
		}
		while (flag == 0)
			;

	}

}