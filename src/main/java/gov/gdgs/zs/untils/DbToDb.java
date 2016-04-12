package gov.gdgs.zs.untils;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
public class DbToDb {
	public  void insertnewDB() throws Exception {
		Connection conn = null;
		String sql;
		String url = "jdbc:mysql://localhost:3306/gdzs?"
		+ "user=root&password=my123&useUnicode=true&characterEncoding=UTF8"; 
		try {
		Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
		System.out.println("成功加载MySQL驱动程序");
		conn = DriverManager.getConnection(url);
		Statement stmt = conn.createStatement();
		sql = "select * from zs_jg";
//		int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
//		if (result != -1) {
//		System.out.println("创建数据表成功");
//		sql = "insert into student(NO,name) values('2012001','陶伟基')";
//		result = stmt.executeUpdate(sql);
//		sql = "insert into student(NO,name) values('2012002','周小俊')";
//		result = stmt.executeUpdate(sql);
//		sql = "select * from student";
//		ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
//		System.out.println("学号\t姓名");
//		while (rs.next()) {
//		System.out
//		.println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
		String bb = stmt.executeQuery(sql).getString(1);
		System.out.println(stmt.executeQuery(sql));
//		}
//		}
		} catch (SQLException e) {
		System.out.println("MySQL操作错误");
		e.printStackTrace();
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		conn.close();
		}

		}

}
