package ToExcel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

public class toExcelUser {
	private static Logger logger = Logger.getLogger(toExcelUser.class);

	public boolean toxls(ResultSet rs) throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();// 新建工作薄对象
		HSSFSheet sheet = wb.createSheet("交单表");// 在工作薄中创建工作表
		HSSFRow row = null;
		HSSFCell cell = null;
		sheet.setDefaultRowHeightInPoints(30);// 设置缺省列高

		// HSSFRow row = sheet.createRow(0);//在sheet中添加表头第0行

		// ----------------标题样式---------------------
		HSSFCellStyle titleStyle = wb.createCellStyle(); // 标题样式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font ztFont = wb.createFont();
		ztFont.setItalic(false);
		ztFont.setColor(Font.COLOR_NORMAL); // 将字体设置为“红色”
		ztFont.setFontHeightInPoints((short) 12); // 将字体大小设置为18px
		ztFont.setFontName("宋体"); // 将“宋体”字体应用到当前单元格上
		ztFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
		titleStyle.setFont(ztFont);

		// ----------------二级标题格样式-------------------------
		HSSFCellStyle titleStyle2 = wb.createCellStyle();
		titleStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font ztFont2 = wb.createFont();
		ztFont2.setFontHeightInPoints((short) 11);
		ztFont2.setFontName("宋体");
		ztFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle2.setFont(ztFont2);

		// ----------------单元格样式------------------------------
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) 11);
		cellFont.setFontName("宋体");
		cellStyle.setFont(cellFont);

		// ----------------------创建第一行---------------
		row = sheet.createRow(0);
		row.setHeightInPoints(72);
		cell = row.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		cell.setCellValue("业务项目中：0代表存送话费或预约办卡，1代表10元20G流量\n" + 
								"2代表低消38宽带，3代表低消48宽带\n" +
								"4代表低消38WiFi，5代表3元WiFi，6代表6元WiFi\n" + 
								"7代表变更为28套餐，8代表变更为48套餐\n" + 
								"9代表两城一号，10代表其他");
		cell.setCellStyle(titleStyle);

		// 定义各列
		row = sheet.createRow(1); // 创建第二行
		cell = row.createCell(0);
		cell.setCellValue("提交ID");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(1);
		cell.setCellValue("提交时间");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(2);
		cell.setCellValue("用户姓名");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(3);
		cell.setCellValue("用户电话");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(4);
		cell.setCellValue("服务密码");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(5);
		cell.setCellValue("图片链接");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(6);
		cell.setCellValue("业务项目");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(7);
		cell.setCellValue("楼栋号");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(8);
		cell.setCellValue("其他说明");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(9);
		cell.setCellValue("橙人电话");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(10);
		cell.setCellValue("办理队伍");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(11);
		cell.setCellValue("橙人邮箱");
		cell.setCellStyle(titleStyle2);
		cell = row.createCell(12);
		cell.setCellValue("满意度");
		cell.setCellStyle(titleStyle2);
		// 写入实体数据,实际应用中这些数据从数据库或记录结果集中得到
		int i = 2;
		while (rs.next()) {
			row = sheet.createRow(i);
			for (int j = 1; j <= 13; j++) {
				cell = row.createCell(j - 1);
				cell.setCellValue(rs.getString(j));
				cell.setCellStyle(cellStyle);
				sheet.setColumnWidth(j - 1, 3700); // 设置列的宽度
			}
			i++;
		}
		// 将文件存到指定位置
		FileOutputStream fout = null;
		File file;
		try {
			file = new File("/home/yidongdata/user.xls");
//			file = new File("E:/user.xls");
			fout = new FileOutputStream(file);
			wb.write(fout);
			// fout.write("xiongwentao会如何如何".getBytes());
			// fout.flush();
			System.out.println("导出Excel成功！");
			return true;
		} catch (Exception e) {
			logger.info("导出Excel失败");
			e.printStackTrace();
			return false;
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
