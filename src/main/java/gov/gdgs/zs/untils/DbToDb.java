package gov.gdgs.zs.untils;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 简陋的JDBC连接
 * @author Administrator
 *
 */
@Repository
public class DbToDb {
	/**
	 * 事务所基本表(zs_jg)插入数据方法
	 */
	public  void insertnewDB(List<Map<String,Object>> ls) throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/gdzs?"
		+ "user=root&password=my123&useUnicode=true&characterEncoding=UTF8"; 
		try {
		Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
		conn = DriverManager.getConnection(url);
		Statement stmt = conn.createStatement();
		
//		for(Map<String, Object> rec : ls){
//				
//				StringBuffer sb = new StringBuffer();
//				sb.append("insert into zs_jg(ID,DWMC,CS_DM,FDDBR,JGXZ_DM,ZCZJ,JGZT_DM,YXBZ) values('"+rec.get("id").toString()+"',");
//				sb.append("'"+rec.get("dwmc").toString()+"',");
//				sb.append("'"+rec.get("cs_dm").toString()+"',");
//				sb.append("'"+rec.get("fddbr").toString()+"',");
//				sb.append("'"+rec.get("jgxz_dm").toString()+"',");
//				sb.append("'"+rec.get("zczj").toString()+"',");
//				sb.append("'"+rec.get("JGZT_DM").toString()+"',");
//				sb.append("'0')");
//				stmt.executeUpdate(sb.toString());
//		}
		/**
		 * 更新无效事务所
		 */
		for(Map<String, Object> rec : ls){
			
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE zs_ry set YXBZ='0' where YID = '"+rec.get("id").toString()+"'");
//			+rec.get("xm").toString()+"',XB_DM='"
//					+rec.get("XB_DM").toString()+
					

			stmt.executeUpdate(sb.toString());
		}
		System.out.println("更新了~~~~~");
		} catch (SQLException e) {
		System.out.println("MySQL操作错误");
		e.printStackTrace();
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		conn.close();
		}

		}
	/**
	 * 人员基本表(zs_ry)插入数据方法
	 */
	public  void insertnewRYDB(List<Map<String,Object>> ls, char q) throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/gdzs?"
				+ "user=root&password=my123&useUnicode=true&characterEncoding=UTF8"; 
		try {
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			
		for(Map<String, Object> rec : ls){
				
				StringBuffer sb = new StringBuffer();
//				Boolean qq =rec.get("RYZT_DM");
				sb.append("insert into zs_ry(YID,XM,XB_DM,BIR,SFZH,CS_DM,MZ_DM,XL_DM,RYZT_DM,RYSF_DM,YXBZ) values('"+rec.get("id").toString()+"',");
				if(rec.get("xm")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("xm").toString()+"',");
				}
				if(rec.get("XB_DM")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("XB_DM").toString()+"',");
				}
				if(rec.get("SRI")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("SRI").toString()+"',");
				}
				if(rec.get("SFZH")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("SFZH").toString()+"',");
				}
				if(rec.get("CS_DM")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("CS_DM").toString()+"',");
				}
				if(rec.get("MZ_DM")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("MZ_DM").toString()+"',");
				}
				if(rec.get("XL_DM")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("XL_DM").toString()+"',");
				}
				if(rec.get("RYZT_DM")==null){
					sb.append("null,");
				}else{
					
					sb.append("'"+rec.get("RYZT_DM").toString()+"',");
				}
				sb.append("'"+q+"',");
				sb.append("'1')");
				stmt.executeUpdate(sb.toString());
		}
		System.out.println("更新了~~~~~");
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
	}
	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	public  List<Map<String,Object>> querynewDB() throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/gdzs?"
				+ "user=root&password=my123&useUnicode=true&characterEncoding=UTF8"; 
		try {
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select* from zs_jg");
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();
			@SuppressWarnings("rawtypes")
			List listOfRows = new ArrayList();
			while (rs.next()) {
				@SuppressWarnings("rawtypes")
				Map mapOfColValues = new HashMap(num);
				for (int i = 1; i <= num; i++) {
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
				}
				listOfRows.add(mapOfColValues);
			}
			return listOfRows;
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
		
	}
	/**
	 * 删除重复身份证记录
	 * @return
	 * @throws Exception
	 */
	public  void dealwithRYDB() throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/gdzs?"
				+ "user=root&password=my123&useUnicode=true&characterEncoding=UTF8"; 
		try {
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			
			
			
			
			ResultSet rs = stmt.executeQuery("select * from (select count(sfzh) as q,xm,sfzh,rysf_dm from zs_ry group by sfzh) as v where q>1 ");
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();
			List<Map<String,Object>> listOfRows = new ArrayList<Map<String,Object>>();
			while (rs.next()) {
				Map mapOfColValues = new HashMap(num);
				for (int i = 1; i <= num; i++) {
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
				}
				listOfRows.add(mapOfColValues);
			}
			for(Map<String, Object> rec : listOfRows){
				Statement stmt2 = conn.createStatement();
				Statement stmt3 = conn.createStatement();
				String sql ="select min(rysf_dm) as min from zs_ry a where a.sfzh ='"+rec.get("sfzh").toString()+"'" ;
			ResultSet rs2 = stmt2.executeQuery(sql);
        	rs2.next();
        	String value = rs2.getString("min"); 
        	switch(value){
        	case "1":
        			stmt3.executeUpdate("delete from zs_ry where sfzh ='"+rec.get("sfzh").toString()+"' and rysf_dm = '2'");
        			stmt3.executeUpdate("delete from zs_ry where sfzh ='"+rec.get("sfzh").toString()+"' and rysf_dm = '3'");
				
        		break;
        	case "2":
        			stmt3.executeUpdate("delete from zs_ry where sfzh ='"+rec.get("sfzh").toString()+"' and rysf_dm = '3'");
//        			stmt3.executeUpdate("delete from zs_ry where id  in (select * from(select min(d.id) from zs_ry d where d.sfzh='"+rec.get("sfzh").toString()+"') v)");
        		
        	case "3":
        		stmt3.executeUpdate("delete from zs_ry where id  in (select * from(select min(d.id) from zs_ry d where d.sfzh='"+rec.get("sfzh").toString()+"') v)");
        	
        	}
        	stmt2.close();
        	stmt3.close();
			
	}
			System.out.println("删除了~~~~~");
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
