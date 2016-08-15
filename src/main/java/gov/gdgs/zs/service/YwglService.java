package gov.gdgs.zs.service;

import gov.gdgs.zs.dao.SWSDao;
import gov.gdgs.zs.dao.YwglDao;
import gov.gdgs.zs.untils.Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdky.restfull.exception.YwbbException;
import com.gdky.restfull.utils.HashIdUtil;

@Service
@Transactional
public class YwglService {

	@Resource
	private YwglDao ywglDao;
	
	@Resource
	private SWSDao swsDao;

	public Map<String, Object> getYwbb(int page, int pageSize, String where) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (where != null) {
			try {
				where = java.net.URLDecoder.decode(where, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(where,
						new TypeReference<Map<String, Object>>() {
						});
			} catch (Exception e) {
			}
		}		
		Map<String,Object> rs = ywglDao.getYwbb(page, pageSize, map);
/*		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		ArrayList<Map<String,Object>> ls = (ArrayList<Map<String,Object>>) rs.get("data");
		for (Map<String,Object> item : ls){
			String id = hashids.encode((Long)item.get("id")) ;
			item.put("id", id);
		}*/

		return rs;
	}

	public Map<String, Object> getYwbbById(String hash) {
		Long id = HashIdUtil.decode(hash);
		Map<String,Object> obj = ywglDao.getYwbbById(id);
		return obj;
	}

	public Map<String, Object> getYwbbByJg(String hashId, int page,
			int pageSize, String where) {
		Long id = HashIdUtil.decode(hashId);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (where != null) {
			try {
				where = java.net.URLDecoder.decode(where, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(where,
						new TypeReference<Map<String, Object>>() {
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String,Object> obj = ywglDao.getYwbbByJg(id,page,pageSize,map);
		return obj;
	}

	public Map<String, Object> getYwbbMiscByJg(String hashId) {
		Long id = HashIdUtil.decode(hashId);
		List<Map<String,Object>> zysws = ywglDao.getYwbbMiscByJg(id);
		Map<String,Object> jgxx = swsDao.swsxx(id.intValue());
		
		HashMap<String,Object> obj = new HashMap<String,Object>();
		obj.put("zysws",zysws);
		obj.put("jgxx", jgxx);
		return obj;
	}

	public Map<String, Object> addYwbb(Map<String, Object> values)  {
		Map<String,Object> xy = (Map<String,Object>)values.get("dataXY");
		Map<String,Object> yw = (Map<String,Object>)values.get("dataYW");
		Map<String,Object> jg = (Map<String,Object>)values.get("dataJG");
		Map<String,Object> customer = (Map<String,Object>)values.get("customer");
		String type = (String) values.get("type");
		
		//整理业务记录
		HashMap<String,Object> o = new HashMap<String,Object>();
		Calendar cal = Calendar.getInstance();  
		int now_y = cal.get(Calendar.YEAR);//得到年份
		int now_m = cal.get(Calendar.MONTH)+1;//得到月份
		int now_d = cal.get(Calendar.DATE);//得到月份中今天的号数
		int now_h = cal.get(Calendar.HOUR_OF_DAY);//得到一天中现在的时间，24小时制
		int now_mm = cal.get(Calendar.MINUTE);//得到分钟数
		int now_s = cal.get(Calendar.SECOND);//得到秒数
		
		String currentTime = Common.getCurrentTime2MysqlDateTime();
		String ND = String.valueOf(now_y);
		o.put("ND",ND);
		o.put("BBRQ",currentTime);
		o.put("BGWH", yw.get("BGWH"));
		o.put("BGRQ", Common.getTime2MysqlDateTime((String)yw.get("BGRQ")));
		o.put("SFJE", yw.get("SFJE"));
		o.put("JG_ID", customer.get("JG_ID"));
		o.put("SWSMC", jg.get("dwmc"));
		o.put("SWSSWDJZH", jg.get("swdjhm"));
		o.put("WTDW", customer.get("DWMC"));
		o.put("WTDWNSRSBH", customer.get("NSRSBH"));
		o.put("XYH", xy.get("XYH"));
		o.put("YJFH", yw.get("YJFH"));
		o.put("RJFH", yw.get("RJFH"));
		o.put("SJFH", yw.get("SJFH"));
		List<Map<String,Object>> qmswsList = (List<Map<String,Object>>) yw.get("QMSWS");
		String QMSWSID = (String)qmswsList.get(0).get("key") +","+(String)qmswsList.get(1).get("key") ;
		String QZSWS = (String)qmswsList.get(0).get("label") +","+(String)qmswsList.get(1).get("label") ;
		o.put("QZSWS", QZSWS);
		o.put("QMSWSID", QMSWSID);
		o.put("TXDZ", jg.get("dzhi"));
		o.put("SWSDZYJ", jg.get("dzyj"));
		o.put("SWSWZ", jg.get("wangzhi"));
		o.put("YWLX_DM", xy.get("YWLX_DM"));
		o.put("JTXM", yw.get("JTXM"));
		o.put("ZBRQ", currentTime);
		List<String> sssq = (List<String>)xy.get("SSSQ");
		o.put("SENDTIME", Common.getTime2MysqlDateTime(sssq.get(1)));
		o.put("SSTARTTIME", Common.getTime2MysqlDateTime(sssq.get(0)));
		o.put("NSRXZ", yw.get("NSRXZ"));
		o.put("HY_ID", yw.get("HY_ID"));
		o.put("ZSFS_DM", yw.get("ZSFS_DM"));
		o.put("ISWS", yw.get("ISWS"));
		o.put("SB_DM", yw.get("SB_DM"));
		o.put("CS_DM", yw.get("CS_DM"));
		o.put("QX_DM", yw.get("QX_DM"));
		o.put("WTDWXZ_DM", yw.get("WTDWXZ_DM"));
		o.put("WTDWNSRSBHDF", customer.get("NSRSBH"));
		o.put("WTDWLXR", customer.get("LXR"));
		o.put("WTDWLXDH", customer.get("LXDH"));
		o.put("WTDXLXDZ", customer.get("LXDZ"));
		o.put("XYJE", xy.get("XYJE"));
		o.put("CUSTOMER_ID", customer.get("ID"));
		if(yw.get("TZVALUE1")!=null){
			o.put("TZVALUE1", yw.get("TZVALUE1"));
		}else{
			o.put("TZVALUE1", null);
		}
		if(yw.get("TJVALUE2")!=null){
			o.put("TJVALUE2", yw.get("TJVALUE2"));
		}else{
			o.put("TJVALUE2", null);
		}


		/*判断是否有报备上报资质 */
		/*判断协议号是否唯一*/
		int xyhNum = ywglDao.getXyhNum((String)o.get("XYH"));
		if (xyhNum > 0){
			throw new YwbbException("协议文号已存在");
		}
		/*判断直接提交还是保存*/
		if (type.equals("save")){ //保存
			
			o.put("ZT", 0);
			ywglDao.addYwbb(o);
		}else if (type.equals("commit")){ //直接报备
			//生成报备号码
			String bbhm = String.valueOf(now_y)+Common.addZero(now_m, 2)+String.valueOf(cal.getTimeInMillis());
			System.out.println(bbhm);
				o.put("ZT", 3);
				ywglDao.addYwbb(o);
		}
		return null;
	}



}
