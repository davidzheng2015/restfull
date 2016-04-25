package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
@Repository
public class CWBBDao extends BaseJdbcDao{
	public Map<String, Object> lrfp(int page, int pageSize, Map<String,Object> where) {
		StringBuffer condition = new StringBuffer(); 
		 		if (where.size() != 0) { 
		 			Iterator<Map.Entry<String, Object>> entries = where.entrySet() 
		 					.iterator(); 
		 			while (entries.hasNext()) { 
		 				Map.Entry<String, Object> entry = entries.next(); 
		 				condition.append(" and "); 
		 				condition.append(entry.getKey() + " like :" + entry.getKey() + " "); 
		 			} 
		 		} 

		
		int startIndex = pageSize * (page-1) ;
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT SQL_CALC_FOUND_ROWS b.ID AS 'key',b.id,b.DWMC,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS TJSJ,a.DWFZR,");
		sb.append(" a.CKFZR,CASE a.ZTBJ WHEN 0 THEN \"保存\"  WHEN 1 THEN \"提交\" ELSE NULL END AS ZTBJ");
		sb.append(" FROM zs_lrfp a,zs_jg b");
		sb.append(" WHERE a.JG_ID=b.ID AND a.ZTBJ=1  ORDER BY a.JSSJ");
		sb.append("		    LIMIT ?, ? ");
		int total = jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class);
		List<Map<String,Object>> ls = jdbcTemplate.queryForList(sb.toString(),new Object[]{startIndex,pageSize});
		
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("data", ls);
		obj.put("total", total);
		return obj;
	}
	public Map<String,Object> lrfp1(int pn,int ps){
	  	   StringBuffer sb= new StringBuffer();
	  	 sb.append("	SELECT  b.ID AS 'key',a.id,a.JG_ID,b.DWMC,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS TJSJ,a.DWFZR,");
			sb.append(" a.CKFZR,CASE a.ZTBJ WHEN 0 THEN \"保存\"  WHEN 1 THEN \"提交\" ELSE NULL END AS ZTBJ");
			sb.append(" FROM zs_lrfp a,zs_jg b");
			sb.append(" WHERE a.JG_ID=b.ID AND a.ZTBJ=1  ORDER BY a.JSSJ");	 
	  	   List<Map<String,Object>> ls= this.jdbcTemplate.queryForList(sb.toString());
	  	   Pager<Map<String,Object>> pager =Pager.create(ls,ps);
	         Map<String,Object> ob =new HashMap<>();
	         ob.put("data", pager.getPagedList(pn));
	         ob.put("total_number1", ls.size());
	         ob.put("pagetotal" , (ls.size()+ps-1)/ ps);
	         return ob;      

	    }
	 public Map<String,Object> lrfp(){
	   		StringBuffer sb = new StringBuffer();
	   		sb.append("	SELECT  b.ID AS 'key',a.id,a.JG_ID,b.DWMC,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS TJSJ,a.DWFZR,");
			sb.append(" a.CKFZR,CASE a.ZTBJ WHEN 0 THEN \"保存\"  WHEN 1 THEN \"提交\" ELSE NULL END AS ZTBJ");
			sb.append(" FROM zs_lrfp a,zs_jg b");
			sb.append(" WHERE a.JG_ID=b.ID AND a.ZTBJ=1  ORDER BY a.JSSJ");
	  	List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : ls){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_xx", "http://localhost:8080/api/lrfp/xx/"+rec.get("id").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		   Map<String,Object> ob = new HashMap<>();
	   		ob.put("data", ls);
	   		return ob;
	 }
	 public Map<String,Object> xx(String id){
	   StringBuffer sb = new StringBuffer();
	 sb.append(" SELECT b.id AS 'key',a.id,a.JG_ID,b.DWMC,DATE_FORMAT(a.JSSJ,'%Y-%m-%d')AS SJ,a.JLR,a.JLRUPYEAR,a.NCWFPLR,a.NCWFPLRUPYEAR,a.QTZR,a.QTZRUPYEAR,");
	  sb.append("  a.KFPLR,a.KFPLRUPYEAR,a.YYGJ,a.YYGJUPYEAR,a.JLFLJJ,a.JLFLJJUPYEAR,a.CBJJ,a.CBJJUPYEAR,a.QYFZJJ,a.QYFZJJUPYEAR,");	 
	  sb.append(" a.LRGHTZ,a.LRGHTZUPYEAR,a.TZZFPLR,a.TZZFPLRUPYEAR,a.YXGL,a.YXGLUPYEAR,a.PTGL,a.PTGLUPYEAR,a.ZHPTGL,a.ZHPTGLUPYEAR,");
	  sb.append(" a.WFPLR,a.WFPLRUPYEAR,a.DWFZR,a.CKFZR,a.FHR,a.ZBR");	
	  sb.append(" FROM zs_lrfp a,zs_jg b");	
	  sb.append(" WHERE a.JG_ID=b.ID AND a.ZTBJ=1 AND a.id=? ORDER BY a.JSSJ");	
	 Map<String,Object> bg = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
	   Map<String,Object> ob = new HashMap<>();
		    ob.put("data", bg);
		return ob;
		 
  }

}


