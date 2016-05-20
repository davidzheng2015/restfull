package gov.gdgs.zs.dao;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;


@Repository
   public class FzylsjlDao extends BaseJdbcDao{
	public Map<String, Object> getFzyzjjlb(int page, int pageSize,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("b.XMING", "FUZZY", where.get("XMING"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" from   ( select b.XMING,b.ZZDW,c.MC as xb,d.MC as xl,a.RYSPZT,e.MC as zw,");
		sb.append(" CASE a.RYSPZT WHEN 1 THEN '退回'  WHEN 2 THEN '通过' WHEN 0 THEN '审批中'  ELSE NULL END AS zt ,");
		sb.append(" DATE_FORMAT(a.ZJYYRQ,'%Y-%m-%d') AS tbrq,");
		sb.append(" DATE_FORMAT(a.TBRQ,'%Y-%m-%d') AS sprq");
		sb.append(" from "
				+ Config.PROJECT_SCHEMA
				+ "zs_fzyswszj a, zs_fzysws b,dm_xb c,dm_xl d,dm_zw e,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" and a.FZY_ID=b.ID and b.XB_DM=c.ID and b.XL_DM=d.ID");
		sb.append(" and b.ZW_DM=e.ID and b.RYZT_DM in (1,7) ) as t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
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

		return obj;
	}

	public Map<String, Object> getFzyzxjlb(int page, int pageSize,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("b.XMING", "FUZZY", where.get("XMING"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM   (select b.XMING,b.ZZDW,c.MC as xb,d.MC as xl,e.MC as zw,");
		sb.append(" DATE_FORMAT(a.BDRQ,'%Y-%m-%d') AS zxrq,f.MC as yy,a.ZXYY,");
		sb.append(" DATE_FORMAT(a.SPSJ,'%Y-%m-%d') AS sprq,");
		sb.append(" CASE a.RYSPZT WHEN 1 THEN '退回'  WHEN 2 THEN '通过' WHEN 0 THEN '审批中'  ELSE NULL END AS zt");
		sb.append(" from "
				+ Config.PROJECT_SCHEMA
				+ "zs_fzyzx a, zs_fzysws b,dm_xb c,dm_xl d,dm_zw e,dm_fzyzxlb f,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" and a.FZY_ID=b.ID and b.XB_DM=c.ID");
		sb.append(" and d.ID=b.XL_DM and e.ID=b.ZW_DM ");
		sb.append("  and f.ID=a.ZXLB_DM and a.RYSPZT=2 ) AS t ");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
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

		return obj;
	}  
	
	public Map<String, Object> getFzyzzyjlb(int page, int pageSize,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("b.XMING", "FUZZY", where.get("XMING"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" from ( select b.XMING,b.ZZDW,c.MC as xb,d.MC as xl,f.MC as zw,");
		sb.append(" DATE_FORMAT(a.TBRQ,'%Y-%m-%d') AS BDRQ,");
		sb.append(" DATE_FORMAT(a.BDRQ,'%Y-%m-%d') AS SPSJ,");
		sb.append(" CASE a.RYSPZT WHEN 1 THEN '退回'  WHEN 2 THEN '通过' WHEN 0 THEN '审批中'  ELSE NULL END AS zt");
		sb.append("  from "+Config.PROJECT_SCHEMA+"zs_fzyzzy a,zs_fzysws b,dm_xb c,dm_xl d,dm_zw f,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" and b.ID=a.FZY_ID and b.XB_DM=c.ID and b.XL_DM=d.ID ");
		sb.append(" and b.ZW_DM=f.ID ) as t ");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
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

		return obj;
	}


}
