package Controller.UserAction;

import java.io.IOException;
import java.sql.SQLException;

import Excel.SaveData2DB;

/*
 * 代码操作导表入数据库
 */
public class ManaCrExClient {
	public static void main(String[] args) throws IOException, SQLException {
		SaveData2DB saveData2DB = new SaveData2DB();
		saveData2DB.save();
		System.out.println("end");
	}
}
