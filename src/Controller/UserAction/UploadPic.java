package Controller.UserAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.log4j.Logger;

/**
 * 上传图片存至本地
 * 
 * @author Kevin
 *
 */
@WebServlet(name="UploadPic", urlPatterns="/UserAction/UploadPic"
)

public class UploadPic extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UploadPic.class);
	public String path = "/home/yidongdata/userPic/";// 设置Linux系统文件夹路径
//	public String path = "E:/yidongdata/userPic/";// 设置Windows系统文件夹路径
	public String destPath = null;// 设置图片根路径

	String name = null;// 图片名

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//若不存在则新建一个文件夹
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		logger.debug("图片目录path=" + path);

		request.setCharacterEncoding("utf-8"); // 设置编码
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 如果没以下两行设置的话,上传大的文件会占用很多内存，
		// 设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
		/**
		 * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上， 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
		 * 然后再将其真正写到对应目录的硬盘上
		 */
		factory.setRepository(dir);
		// 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = upload.parseRequest(request);
			FileItem picture = null;
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串
					String value = item.getString();
					request.setAttribute(name, value);
					logger.debug("name=" + name + ",value=" + value);
				} else {
					picture = item;
				}
			}

			// 自定义上传图片的名字为userId.jpg
			String fileName = request.getAttribute("userPhone") + ".jpg";
			name = (String) request.getAttribute("userPhone");
			destPath = path + fileName;
			logger.debug("图片上传成功destPath=" + destPath);

			// 真正写到磁盘上
			File file = new File(destPath);
			OutputStream out = new FileOutputStream(file);
			InputStream in = picture.getInputStream();
			int length = 0;
			byte[] buf = new byte[1024];
			// in.read(buf) 每次读到的数据存放在buf 数组中
			while ((length = in.read(buf)) != -1) {
				// 在buf数组中取出数据写到（输出流）磁盘上
				out.write(buf, 0, length);
			}
			in.close();
			out.close();
		} catch (FileUploadException e1) {
			logger.debug("图片上传失败");
			logger.error("", e1);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}