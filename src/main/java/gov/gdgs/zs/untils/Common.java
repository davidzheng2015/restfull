package gov.gdgs.zs.untils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class Common {
	// 生成UUID
	public static String newUUID() {
		String uuid = UUID.randomUUID().toString().replace("-", "")
				.toUpperCase();
		return uuid;
	}

	// 获取目前时间的Mysql DateTime格式
	public static String getCurrentTime2MysqlDateTime() {
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		return currentTime;
	}

	public static String getTime2MysqlDateTime(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat utcFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
		
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String time = "";
		try {
			Date d = utcFormat.parse(date);
			time = sdf.format(d);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return time;
	}
	// 数字补零兼转换字符串 
    public static String addZero(int num,int len){  
        StringBuffer s = new StringBuffer() ;  
        s.append(num) ;  
        while(s.length()<len){   // 如果长度不足，则继续补0  
            s.insert(0,"0") ;   // 在第一个位置处补0  
        }  
        return s.toString() ;  
    }  
}
