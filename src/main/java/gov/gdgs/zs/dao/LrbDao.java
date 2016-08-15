package gov.gdgs.zs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import gov.gdgs.zs.configuration.Config;







import gov.gdgs.zs.untils.Condition;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;



@Repository
public class LrbDao extends BaseJdbcDao implements ILrbDao{
	 
	@Override
	public String addLrb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		obj.put("id", uuid);
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_lrgd ");
		sb.append("(id,jg_id,use_id,nd,timevalue,zgywsr,zgywsr1,zgywcb,zgywcb1,zgywsj,zgywsj1,zgwylr,zgwylr1,qtywlr,qtywlr1,yyfy,yyfy1,glfy,glfy1,");
		sb.append(" cwfy,cwfy1,yylr,yylr1,tzsy,tzsy1,btsr,btsr1,yywsr,yywsr1,yywzc,yywzc1,lrze,lrze1,sds,sds1,jlr,jlr1,");
		sb.append("  csczsy,csczsy1,zhss,zhss1,zcbglr,zcbglr1,gjbglr,gjbglr1,zwczss,zwczss1,qt,qt1,dlswdjhs,dlswdjsr,");
		sb.append("  dlswdjsr1,dlnssbhs,dlnssbsr,dlnssbsr1,dlnsschs,dlnsscsr,dlnsscsr1,dljzjzhs,dljzjzsr,dljzjzsr1,spgwzxhs,");
		sb.append("  spgwzxsr,spgwzxsr1,dlsqswfyhs,dlsqswfysr,dlsqswfysr1,pxhs,pxsr,pxsr1,qtzyywsrhs,qtzyywsr,qtzyywsr1,sz,zgkj,zbr,ztbj)");
		sb.append("values (:id,:jg_id,:use_id,:nd,:timevalue,:zgywsr,:zgywsr1,:zgywcb,:zgywcb1,:zgywsj,:zgywsj1,:zgwylr,:zgwylr1,:qtywlr,:qtywlr1,:yyfy,:yyfy1,:glfy,:glfy1,");
		sb.append("  :cwfy,:cwfy1,:yylr,:yylr1,:tzsy,:tzsy1,:btsr,:btsr1,:yywsr,:yywsr1,:yywzc,:yywzc1,:lrze,:lrze1,:sds,:sds1,:jlr,:jlr1,");
		sb.append("  :csczsy,:csczsy1,:zhss,:zhss1,:zcbglr,:zcbglr1,:gjbglr,:gjbglr1,:zwczss,:zwczss1,:qt,:qt1,:dlswdjhs,:dlswdjsr,");
		sb.append("  :dlswdjsr1,:dlnssbhs,:dlnssbsr,:dlnssbsr1,:dlnsschs,:dlnsscsr,:dlnsscsr1,:dljzjzhs,:dljzjzsr,:dljzjzsr1,:spgwzxhs,");
		sb.append("  :spgwzxsr,:spgwzxsr1,:dlsqswfyhs,:dlsqswfysr,:dlsqswfysr1,:pxhs,:pxsr,:pxsr1,:qtzyywsrhs,:qtzyywsr,:qtzyywsr1,:sz,:zgkj,:zbr,:ztbj)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	}else {
		return uuid;
	}
	}


	public Map<String, Object> getlrb(int page, int pageSize,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));
		condition.add("a.TIMEVALUE", "FUZZY", where.get("TIMEVALUE"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM    ( SELECT a.id,b.DWMC,a.nd,");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ,");
		sb.append(" CASE a.TIMEVALUE WHEN 0 THEN '半年' WHEN 1 THEN '全年' ELSE NULL END AS TIMEVALUE ");
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_cwbb_lrgd a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" AND a.JG_ID=b.ID  and a.JG_ID=? ORDER BY a.nd DESC ) AS t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
		params.add(Jgid);	
		params.add(startIndex);
		params.add(pageSize);

		// 获取符合条件的记录
		List<Map<String, Object>> ls = jdbcTemplate.queryForList(sb.toString(),
				params.toArray());

		// 获取符合条件的记录数

		int total = jdbcTemplate.queryForObject("SELECT FOUND_ROWS()",
				Integer.class);

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);
		obj.put("jg_id", Jgid);
		return obj;
	}
	public Map<String, Object> getLrbById(String id) {
		String sql = "select b.DWMC,CASE a.TIMEVALUE WHEN 0 THEN '半年' WHEN 1 THEN '全年' ELSE NULL END AS TIMEVALUE,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_lrgd a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	@Override
	public void updateLrb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_lrgd ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,nd=:nd,timevalue=:timevalue,zgywsr=:zgywsr,zgywsr1=:zgywsr1,zgywcb=:zgywcb,zgywcb1=:zgywcb1,zgywsj=:zgywsj,zgywsj1=:zgywsj1,");
		sb.append(" zgwylr=:zgwylr,zgwylr1=:zgwylr1,qtywlr=:qtywlr,qtywlr1=:qtywlr1,yyfy=:yyfy,yyfy1=:yyfy1,glfy=:glfy,glfy1=:glfy1,");
		sb.append(" cwfy=:cwfy,cwfy1=:cwfy1,yylr=:yylr,yylr1=:yylr1,tzsy=:tzsy,tzsy1=:tzsy1,btsr=:btsr,btsr1=:btsr1,yywsr=:yywsr,yywsr1=:yywsr1,");
		sb.append(" yywzc=:yywzc,yywzc1=:yywzc1,lrze=:lrze,lrze1=:lrze1,sds=:sds,sds1=:sds1,jlr=:jlr,jlr1=:jlr1,");
		sb.append(" csczsy=:csczsy,csczsy1=:csczsy1,zhss=:zhss,zhss1=:zhss1,zcbglr=:zcbglr,zcbglr1=:zcbglr1,gjbglr=:gjbglr,gjbglr1=:gjbglr1,");
		sb.append(" zwczss=:zwczss,zwczss1=:zwczss1,qt=:qt,qt1=:qt1,dlswdjhs=:dlswdjhs,dlswdjsr=:dlswdjsr,");
		sb.append(" dlswdjsr1=:dlswdjsr1,dlnssbhs=:dlnssbhs,dlnssbsr=:dlnssbsr,dlnssbsr1=:dlnssbsr1,dlnsschs=:dlnsschs,dlnsscsr=:dlnsscsr,");
		sb.append(" dlnsscsr1=:dlnsscsr1,dljzjzhs=:dljzjzhs,dljzjzsr=:dljzjzsr,dljzjzsr1=:dljzjzsr1,spgwzxhs=:spgwzxhs,");
		sb.append(" spgwzxsr=:spgwzxsr,spgwzxsr1=:spgwzxsr1,dlsqswfyhs=:dlsqswfyhs,dlsqswfysr=:dlsqswfysr,dlsqswfysr1=:dlsqswfysr1,pxhs=:pxhs,pxsr=:pxsr,");
		sb.append(" pxsr1=:pxsr1,qtzyywsrhs=:qtzyywsrhs,qtzyywsr=:qtzyywsr,qtzyywsr1=:qtzyywsr1,sz=:sz,zgkj=:zgkj,zbr=:zbr,ztbj=:ztbj where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}
	
	public Map<String, Object> getLrfpb(int page, int pageSize,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM    ( SELECT a.id,b.DWMC,a.nd,");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ ");
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_cwbb_lrfp a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" AND a.JG_ID=b.ID  and a.JG_ID=? ORDER BY a.nd DESC ) AS t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
		params.add(Jgid);
		params.add(startIndex);
		params.add(pageSize);

		// 获取符合条件的记录
		List<Map<String, Object>> ls = jdbcTemplate.queryForList(sb.toString(),
				params.toArray());

		// 获取符合条件的记录数

		int total = jdbcTemplate.queryForObject("SELECT FOUND_ROWS()",
				Integer.class);

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);
		obj.put("jg_id", Jgid);
		return obj;
	}
	
	public Map<String, Object> getLrfpbById(String id) {
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_lrfp a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
  
	@Override
	public String addLrfpb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		obj.put("id", uuid);
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_lrfp ");
		sb.append("(id,jg_id,use_id,nd,dwfzr,ckfzr,fhr,zbr,jlr,jlrupyear,ncwfplr,ncwfplrupyear,qtzr,qtzrupyear,");	
		sb.append(" kfplr,kfplrupyear,yygj,yygjupyear,jlfljj,jlfljjupyear,cbjj,cbjjupyear,qyfzjj,qyfzjjupyear,");
		sb.append(" lrghtz,lrghtzupyear,tzzfplr,tzzfplrupyear,yxgl,yxglupyear,ptgl,ptglupyear,zhptgl,zhptglupyear,wfplr,wfplrupyear,ztbj)");
		sb.append("values (:id,:jg_id,:use_id,:nd,:dwfzr,:ckfzr,:fhr,:zbr,:jlr,:jlrupyear,:ncwfplr,:ncwfplrupyear,:qtzr,:qtzrupyear,");
		sb.append(" :kfplr,:kfplrupyear,:yygj,:yygjupyear,:jlfljj,:jlfljjupyear,:cbjj,:cbjjupyear,:qyfzjj,:qyfzjjupyear,");
		sb.append(" :lrghtz,:lrghtzupyear,:tzzfplr,:tzzfplrupyear,:yxgl,:yxglupyear,:ptgl,:ptglupyear,");
		sb.append(" :zhptgl,:zhptglupyear,:wfplr,:wfplrupyear,:ztbj)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	}else {
		return uuid;
	}
	}
	
	@Override
	public void updateLrfpb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_lrfp ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,nd=:nd,dwfzr=:dwfzr,ckfzr=:ckfzr,fhr=:fhr,zbr=:zbr,jlr=:jlr,jlrupyear=:jlrupyear,");
		sb.append(" ncwfplr=:ncwfplr,ncwfplrupyear=:ncwfplrupyear,qtzr=:qtzr,qtzrupyear=:qtzrupyear,kfplr=:kfplr,");
		sb.append(" kfplrupyear=:kfplrupyear,yygj=:yygj,yygjupyear=:yygjupyear,jlfljj=:jlfljj,jlfljjupyear=:jlfljjupyear,cbjj=:cbjj,");
		sb.append(" cbjjupyear=:cbjjupyear,qyfzjj=:qyfzjj,qyfzjjupyear=:qyfzjjupyear,lrghtz=:lrghtz,lrghtzupyear=:lrghtzupyear,tzzfplr=:tzzfplr,");
		sb.append(" tzzfplrupyear=:tzzfplrupyear,yxgl=:yxgl,yxglupyear=:yxglupyear,ptgl=:ptgl,ptglupyear=:ptglupyear,zhptgl=:zhptgl,");
		sb.append(" zhptglupyear=:zhptglupyear,wfplr=:wfplr,wfplrupyear=:wfplrupyear,ztbj=:ztbj where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}

}