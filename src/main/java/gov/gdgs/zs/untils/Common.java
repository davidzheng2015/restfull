package gov.gdgs.zs.untils;

import java.util.UUID;

public class Common {
	//生成UUID
	public static String newUUID (){
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		return uuid;
	}
	//获取目前时间的Mysql DateTime格式
	public static String getCurrentTime2MysqlDateTime(){
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		return currentTime;
	}
}
