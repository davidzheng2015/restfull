package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Condition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class HfglDao extends BaseDao{
	
	public Map<String,Object> hyhfjnqk(int pn,int ps,Map<String, Object> qury) throws Exception{
		 Calendar ca = Calendar.getInstance();
	     Object lyear =new Object();
	     DateFormat df = new SimpleDateFormat("MM-dd");
	     if(qury.containsKey("nd")){
	    	 lyear=qury.get("nd");
	     }else if(df.parse(ca.get(Calendar.MONTH)+1+"-"+ca.get(Calendar.DATE)).after(df.parse("07-15"))){
	    	 lyear=ca.get(Calendar.YEAR);
	     }else{
	    	 lyear=ca.get(Calendar.YEAR)-1;
	     }
	     Condition condition = new Condition();
		 condition.add("b.dwmc", Condition.FUZZY, qury.get("dwmc"));
		 ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
		StringBuffer sb = new StringBuffer();
		sb.append("		select SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 as 'key',b.dwmc,'"+lyear+"' as nd,");
		sb.append("		(select a.ZGYWSR from zs_cwbb_lrgd a where a.jg_id=b.id and a.nd='"+lyear+"' and a.ztbj=1 order by a.TIMEVALUE desc limit 1) as jyzsr,");
		sb.append("		f_yjtt(b.id, '"+lyear+"')+");
		sb.append("		(select count(c.RY_ID)*800 as yjgr from zs_zysws c where c.JG_ID=b.id  and c.YXBZ=1  and c.ry_id not in (select d.RY_ID from zs_hyhfjfryls d where d.nd='"+lyear+"') ) as yjz,");
		sb.append("		f_yjtt(b.id, '"+lyear+"') as yjtt,");
		sb.append("		(select e.YJTTHF from zs_hyhfjnqk e where e.JG_ID=b.id and e.ND='"+lyear+"' order by e.SX desc limit 1) as yftt,");
		sb.append("		f_qjtt(f_yjtt(b.id, '"+lyear+"'),b.id,'"+lyear+"') as qjtt,");
		sb.append("		(select count(c.RY_ID)*800 as yjgr from zs_zysws c where c.JG_ID=b.id  and c.YXBZ=1  and c.ry_id not in (select d.RY_ID from zs_hyhfjfryls d where d.nd='"+lyear+"') )as yjgr,");
		sb.append("		(select e.YJGRHF from zs_hyhfjnqk e where e.JG_ID=b.id and e.ND='"+lyear+"' order by e.SX desc limit 1) as yfgr,");
		sb.append("		f_qjgr((select count(c.RY_ID)*800 as yjgr from zs_zysws c where c.JG_ID=b.id  and c.YXBZ=1  and c.ry_id not in (select d.RY_ID from zs_hyhfjfryls d where d.nd='"+lyear+"') ),b.id,'"+lyear+"') as qjgr");
		sb.append("		from zs_jg b,(select @rownum:=?) zs_ry ");
		sb.append("	"+condition.getSql()+"	and b.yxbz=1 LIMIT ?, ?");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
	     int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
			Map<String,Object> ob = new HashMap<>();
			ob.put("data", ls);
			Map<String, Object> meta = new HashMap<>();
			meta.put("pageNum", pn);
			meta.put("pageSize", ps);
			meta.put("pageTotal",total);
			meta.put("pageAll",(total + ps - 1) / ps);
			ob.put("page", meta);
			return ob;
	}
	public Map<String,Object> fpdy(int pn,int ps,Map<String, Object> qury) throws Exception{
		Condition condition = new Condition();
		condition.add("a.dwmc", Condition.FUZZY, qury.get("dwmc"));
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
		StringBuffer sb = new StringBuffer();
		sb.append("		select SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 as 'key',a.ND,a.DWMC,a.JFZE,a.YJTTHF,a.YJGRHF,a.JFRQ,case a.SX when 0 then '上半年' when 1 then '全年' else null end as SX,a.DYCS"); 
		sb.append("		from zs_hyhfjnqk a,(select @rownum:=?) zs_ry");
		sb.append("		"+condition.getSql()+" and a.yxbz=1 order by a.JFRQ desc LIMIT ?, ?");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
		int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		return ob;
	}
}
