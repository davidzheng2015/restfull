package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.framework.dao.BaseJdbcDao;

@Repository
public class HYHFDao extends BaseJdbcDao{
//	@Resource(name="jdbcTemplate")
//	private JdbcTemplate jdbcTemplate;
	 public Map<String,Object> hyhf(int pn,int ps){
  	   StringBuffer sb= new StringBuffer();
  	 sb.append("	select  ");
  	 sb.append("  a.ID AS 'key',a.ID,a.ND,a.JGNAME,CONCAT(a.ZYYWSR,'元') AS ZYYWSR,CONCAT(a.ZJNE,'元') AS ZJNE ,");	 
  	  sb.append(" CONCAT(a.YJTTHF,'元') AS YJTTHF,CONCAT(a.YJTTHF1,'元') AS YJTTHF1,a.QJTTHF,CONCAT(a.YJGRHF,'元') AS YJGRHF,");
  	 sb.append(" CONCAT(a.YJGRHF2,'元') AS YJGRHF2,a.QJGRHF,a.JG_ID");
  	 sb.append(" FROM zs_hyhf a");
 
  	   List<Map<String,Object>> ls= this.jdbcTemplate.queryForList(sb.toString());
  	   Pager<Map<String,Object>> pager =Pager.create(ls,ps);
         Map<String,Object> ob =new HashMap<>();
         ob.put("data", pager.getPagedList(pn));
         ob.put("total_number", ls.size());
         ob.put("pagetotal" , (ls.size()+ps-1)/ ps);
         ob.put("alldata", ls);
         return ob;      

    }	
	
	 public Map<String,Object> hyhf1(){
	   		StringBuffer sb = new StringBuffer();
	   	   sb.append(" SELECT a.ID AS 'key',a.ID,a.ND,a.JGNAME,CONCAT(a.ZYYWSR,'元') AS ZYYWSR,CONCAT(a.ZJNE,'元') AS ZJNE ,");	 
	   	  sb.append(" CONCAT(a.YJTTHF,'元') AS YJTTHF,CONCAT(a.YJTTHF1,'元') AS YJTTHF1,a.QJTTHF,CONCAT(a.YJGRHF,'元') AS YJGRHF,");
	   	 sb.append(" CONCAT(a.YJGRHF2,'元') AS YJGRHF2,a.QJGRHF,a.JG_ID");
	   	 sb.append(" FROM zs_hyhf a");
		   List<Map<String,Object>> f1= this.jdbcTemplate.queryForList(sb.toString());
	   		Map<String,Object> ob = new HashMap<>();
	   		ob.put("data", f1);
	   		return ob;
	 }
	 
	 public Map<String,Object> grhf1(int pn,int ps){
	  	   StringBuffer sb= new StringBuffer();
	  	 sb.append(" SELECT a.JG_ID AS 'key',a.JG_ID AS xh,a.ID,a.ND,b.MC AS DQ,a.JGNAME,a.SEX,DATE_FORMAT(a.SQDATE,'%Y-%m-%d') AS JFSJ");
	  	 sb.append("  FROM zs_grhf a,dm_cs b");	 
	  	  sb.append(" WHERE a.DQ=b.ID AND a.TYPE=1  ORDER BY a.ND DESC,ID DESC");	 
	  	   List<Map<String,Object>> ls= this.jdbcTemplate.queryForList(sb.toString());
	  	   Pager<Map<String,Object>> pager =Pager.create(ls,ps);
	         Map<String,Object> ob =new HashMap<>();
	         ob.put("data", pager.getPagedList(pn));
	         ob.put("total_number1", ls.size());
	         ob.put("pagetotal" , (ls.size()+ps-1)/ ps);
	         return ob;      

	    }
	 public Map<String,Object> grhf(){
	   		StringBuffer sb = new StringBuffer();
	   	 sb.append(" SELECT a.JG_ID AS 'key',a.JG_ID AS xh,a.ID,a.ND,b.MC AS DQ,a.JGNAME,a.SEX,DATE_FORMAT(a.SQDATE,'%Y-%m-%d') AS JFSJ");
	  	 sb.append("  FROM zs_grhf a,dm_cs b");	 
	  	  sb.append(" WHERE a.DQ=b.ID AND a.TYPE=1  ORDER BY a.ND DESC,ID DESC");
	  	List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : ls){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_xx", "http://localhost:8080/api/grhf/xx/"+rec.get("ID").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		   Map<String,Object> ob = new HashMap<>();
	   		ob.put("data", ls);
	   		return ob;
	 }
	 public Map<String,Object> xx(int id){
  	   StringBuffer sb = new StringBuffer();
  	 sb.append(" SELECT a.ID,a.JG_ID,a.ND,b.MC AS DQ,a.JGNAME,a.NAME,a.MONEY,a.SEX,a.SQDATE");
 	  sb.append("  FROM zs_grhf a,dm_cs b");	 
 	  sb.append(" WHERE a.TYPE=1 AND a.DQ=b.ID  AND a.ID=? ORDER BY a.ND DESC,ID DESC");	 	    
 	 Map<String,Object> bg = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
	   Map<String,Object> ob = new HashMap<>();
		    ob.put("Data", bg);
		return ob;
		 
     }
	 
	
}