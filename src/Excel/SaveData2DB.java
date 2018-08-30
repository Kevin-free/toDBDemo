package Excel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import POJO.Cr;

public class SaveData2DB {
	
	@SuppressWarnings("rawtypes")
	public void save() throws IOException, SQLException {
		ReadExcel xlsMain = new ReadExcel();
		Cr cr = null;
		List<Cr> list = xlsMain.readXls();

		for (int i = 0; i < list.size(); i++) {
			cr = list.get(i);
			List l = DBdaoUtil.selectOne("select * from a4cr where phone = " + cr.getPhone(), cr);
			if (!l.contains(1)) {
				DBdaoUtil.insert("insert into a4cr(phone, name) values(?, ?)", cr);
			} else {
				System.out.println("The Record was Exist : phone:" + cr.getPhone() + " , name:" + cr.getName() + ", and has been throw away!");
			}
		}
	}
}
