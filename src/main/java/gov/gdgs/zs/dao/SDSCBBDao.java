package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class SDSCBBDao  extends BaseDao{
	/**
	 * 
	 * @return 事务所基本情况统计表分页查询
	 * @throws Exception 
	 */
	public Map<String,Object> swsjbqktjbcx(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("b.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("a.nd", Condition.EQUAL, qury.get("nd"));
		condition.add("a.cs_dm", Condition.EQUAL, qury.get("cs"));
		condition.add("a.ZTBJ", Condition.EQUAL, qury.get("bbzt"));
		condition.add("a.sbrq", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("a.sbrq", Condition.LESS_EQUAL, qury.get("sbsj2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT 	SQL_CALC_FOUND_ROWS	 ");
		sb.append("		@rownum:=@rownum+1 AS 'key',a.yysr,a.zcze,a.srze,a.lrze,");
		sb.append("	 b.dwmc,a.nd,a.dwxz,ifnull(a.czrs,0) as czrs,a.hhrs,a.ryzs,a.zyzcswsrs,a.zczj,a.jgszd,a.wths,c.mc as cs, d.mc as jgxz, ");
		sb.append("		case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when "
				+ "3 then '退回' else null end as bbzt,a.frdbxm,DATE_FORMAT(a.sbrq,'%Y年%m月%d日') AS sbsj,a.tianbiaoren,a.suozhang");
		sb.append("	 FROM zs_sdsb_swsjbqk a,zs_jg b,(SELECT @rownum:=?) zs_jg,dm_cs c,dm_jgxz d");
		sb.append("		"+condition.getSql()+" ");
		sb.append("	and a.JG_ID=b.ID and a.cs_dm = c.id and d.id = a. jgxz_dm");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
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
	/**
	 * 
	 * @return 注册税务师行业人员情况统计表分页查询
	 * @throws Exception 
	 */
	public Map<String,Object> hyryqktjcx(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("b.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("b.cs_dm", Condition.EQUAL, qury.get("cs"));
		condition.add("a.nd", Condition.EQUAL, qury.get("nd"));
		condition.add("a.ZTBJ", Condition.EQUAL, qury.get("bbzt"));
		condition.add("a.sbrq", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("a.sbrq", Condition.LESS_EQUAL, qury.get("sbsj2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT 	SQL_CALC_FOUND_ROWS	 ");
		sb.append("		@rownum:=@rownum+1 AS 'key',b.dwmc,a.*,c.mc as cs,");
		sb.append("		case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when "
				+ "3 then '退回' else null end as bbzt,DATE_FORMAT(a.sbrq,'%Y年%m月%d日') AS sbsj");
		sb.append("	 FROM zs_sdsb_hyryqktj a,zs_jg b,(SELECT @rownum:=?) zs_jg,dm_cs c");
		sb.append("		"+condition.getSql()+" ");
		sb.append("	and a.JG_ID=b.ID and c.id = b.cs_dm");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
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
	/**
	 * 
	 * @return 注册税务师行业经营收入情况统计表分页查询
	 * @throws Exception 
	 */
	public Map<String,Object> jysrqktjcx(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("b.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("b.cs_dm", Condition.EQUAL, qury.get("cs"));
		condition.add("a.nd", Condition.EQUAL, qury.get("nd"));
		condition.add("a.ZTBJ", Condition.EQUAL, qury.get("bbzt"));
		condition.add("a.sbrq", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("a.sbrq", Condition.LESS_EQUAL, qury.get("sbsj2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT 	SQL_CALC_FOUND_ROWS	 ");
		sb.append("		@rownum:=@rownum+1 AS 'key',b.dwmc,a.*,c.mc as cs,");
		sb.append("		case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when "
				+ "3 then '退回' else null end as bbzt,DATE_FORMAT(a.sbrq,'%Y年%m月%d日') AS sbsj");
		sb.append("	 FROM zs_sdsb_jysrqk a,zs_jg b,(SELECT @rownum:=?) zs_jg,dm_cs c");
		sb.append("		"+condition.getSql()+" ");
		sb.append("	and a.JG_ID=b.ID  and c.id = b.cs_dm");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
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
	/**
	 * 
	 * @return 未上交手动报表事务所分页查询
	 * @throws Exception 
	 */
	public Map<String,Object> wsbbbcx(int pn,int ps,Map<String,Object> qury) {
		List<String> arr = new ArrayList<String>();
		arr.add("zs_sdsb_swsjbqk");
		arr.add("zs_sdsb_hyryqktj");
		arr.add("zs_sdsb_jysrqk");
		arr.add("zs_sdsb_jygmtjb");
		arr.add("zs_sdsb_jzywqktjb");
		Condition condition = new Condition();
		condition.add("d.dwmc", Condition.FUZZY, qury.get("dwmc"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select 	sql_calc_found_rows	 @rownum:=@rownum+1 as 'key','"+qury.get("nd")+"' as nd,'未上报' as sbzt,d.dwmc,d.JGZCH as zsbh,c.mc as cs, d.DHUA as dhhm,d.TXYXMING as txyxm,d.XTYPHONE as txyyddh");
		sb.append("		FROM zs_jg d,dm_cs c,(SELECT @rownum:=?) zs_jg");
		sb.append("		 "+condition.getSql()+" ");
		sb.append("		and d.ID NOT IN (");
		sb.append("		SELECT d.id");
		sb.append("		FROM "+arr.get(Integer.parseInt(qury.get("bblx").toString()))+" b");
		sb.append("		where d.ID =b.jg_id and b.nd = '"+qury.get("nd")+"' ) ");
		sb.append("		AND d.YXBZ = '1'");
		sb.append("		and d.CS_DM = c.ID");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
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
