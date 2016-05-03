package gov.gdgs.zs.dao;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Pager;
import gov.gdgs.zs.untils.Condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hashids.Hashids;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class CWBBDao extends BaseJdbcDao{
	//支出明细表
	public Map<String, Object> getZcmxb(int page, int pageSize, Map<String,Object> where) {
		
		Condition condition = new Condition();
		condition.add("b.DWMC", "FUZZY", where.get("DWMC"));


		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM (select");
		sb.append(" a.id,");
		sb.append(" b.DWMC,");
		sb.append(" a.ZYYWCB,");
		sb.append(" a.ZYYWSJFJ,a.QTYWZC,a.GLFY,a.CWFY,");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ,");
		sb.append(" DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS TJSJ");
		sb.append(" FROM "+Config.PROJECT_SCHEMA+"zs_cwbb_zcmx a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());//相当元 where b.DWMC like '%%'
		sb.append(" AND a.JG_ID=b.ID AND a.ZTBJ=1 ORDER  BY TJSJ) as t");
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
    //获取支出明细信息
	public Map<String,Object> getZcmxbById(String id){
		StringBuffer sb = new StringBuffer();
		sb.append("	 SELECT a.id AS 'key',a.id,b.DWMC,DATE_FORMAT(a.KSSJ,'%Y-%m-%d') as KSSJ,");
		sb.append("	DATE_FORMAT(a.JSSJ,'%Y-%m-%d') as JSSJ,a.ZYYWCB1,a.ZYYWCB,a.ZYYWSJFJ1,a.ZYYWSJFJ,");
		sb.append("	a.GZFY1,a.GZFY,a.QTYWZC1,a.QTYWZC,");
		sb.append("	a.FLF1,a.FLF,a.GLFY1,a.GLFY,a.JYF1,a.JYF,a.GLFY_GZFY1,");
		sb.append("	a.GLFY_GZFY,a.GHJF1,a.GHJF,a.GLFY_FLF1,a.GLFY_FLF,");
		sb.append("	a.SHTC1,a.SHTC,a.GLFY_YWZDF1,a.GLFY_YWZDF,a.BGF1,");
		sb.append("	a.BGF,a.GLFY_BGF1,a.GLFY_BGF,a.CLF1,a.CLF,");
		sb.append("	a.GLFY_QTSJ1,a.GLFY_QTSJ,a.HF1,a.HF,a.GLFY_QCFY1,a.GLFY_QCFY,");
		sb.append("	a.PXZLF1,a.PXZLF,a.GLFY_ZYFXJJ1,a.GLFY_ZYFXJJ,");
		sb.append("	a.HWF1,a.HWF,a.GLFY_ZYZRBX1,a.GLFY_ZYZRBX,a.ZPF1,");
		sb.append("	a.ZPF,a.GLFY_CLF1,a.GLFY_CLF,a.ZJ1,a.ZJ,a.GLFY_QTFY1,a.GLFY_QTFY,");
		sb.append("	a.ZFGJJ1,a.ZFGJJ,a.CWFY1,a.CWFY,a.GWZXF1,a.GWZXF,");
		sb.append("	a.YYWZC1,a.YYWZC,a.QT1,a.QT,a.ZCZJ1,a.ZCZJ,a.SZ,a.AGKJ,a.ZB");
		sb.append("	FROM "+Config.PROJECT_SCHEMA+"zs_cwbb_zcmx a,zs_jg b");
		sb.append("	WHERE a.JG_ID=b.ID AND a.ZTBJ=1 and a.id=?");
		 Map<String,Object> rs = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
		 Map<String, Object> ob = new HashMap<>();
			ob.put("data", rs);
		 
		 return ob;
	}
	
    //利润分配表
     public Map<String, Object> getLrfpb(int page, int pageSize, Map<String,Object> where) {
		
		Condition condition = new Condition();
		condition.add("b.DWMC", "FUZZY", where.get("DWMC"));


		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM (SELECT a.id,b.DWMC,");
		sb.append(" DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS TJSJ,");
		sb.append(" a.DWFZR,a.CKFZR, ");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ");
		sb.append(" FROM "+Config.PROJECT_SCHEMA+"zs_cwbb_lrfp a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());//相当元 where b.DWMC like '%%'
		sb.append(" AND a.JG_ID=b.ID AND a.ZTBJ=1 ORDER  BY TJSJ) as t");
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
    //利润分配表信息
     public Map<String,Object> getLrfpbById(String id){
 		StringBuffer sb = new StringBuffer();
 		sb.append("	 SELECT a.id AS 'key',a.id,b.DWMC,DATE_FORMAT(a.JSSJ,'%Y-%m-%d')AS SJ,a.JLR,");
 		sb.append("	a.JLRUPYEAR,a.NCWFPLR,a.NCWFPLRUPYEAR,a.QTZR,a.QTZRUPYEAR,");
 		sb.append("	a.KFPLR,a.KFPLRUPYEAR,a.YYGJ,a.YYGJUPYEAR,a.JLFLJJ,");
 		sb.append("	a.JLFLJJUPYEAR,a.CBJJ,a.CBJJUPYEAR,a.QYFZJJ,a.QYFZJJUPYEAR,");
 		sb.append("	a.LRGHTZ,a.LRGHTZUPYEAR,a.TZZFPLR,a.TZZFPLRUPYEAR,a.YXGL,");
 		sb.append("	a.YXGLUPYEAR,a.PTGL,a.PTGLUPYEAR,a.ZHPTGL,a.ZHPTGLUPYEAR,");
 		sb.append("	a.WFPLR,a.WFPLRUPYEAR,a.DWFZR,a.CKFZR,a.FHR,a.ZBR");
 		sb.append("	FROM "+Config.PROJECT_SCHEMA+"zs_cwbb_lrfp a,zs_jg b");
 		sb.append("	WHERE a.JG_ID=b.ID AND a.ZTBJ=1 and a.id=?");
 		 Map<String,Object> rs = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
 		 Map<String, Object> ob = new HashMap<>();
 			ob.put("data", rs);
 		 
 		 return ob;
 	}
   //利润表
	public Map<String, Object> getLrb(int page, int pageSize,
			Map<String, Object> where) {
		// 子查询，用于拼接查询条件和返回起止区间
		Condition condition = new Condition();
		condition.add("nd", condition.EQUAL, where.get("nd"));
		condition.add(" AND ztbj = 1 ");

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  ");
		sb.append("    @rownum:=@rownum + 1 AS 'key', v.* ");
		sb.append("FROM ");
		sb.append("    (SELECT  ");
		sb.append("        t.id, ");
		sb.append("            j.dwmc, ");
		sb.append("            t.nd, ");
		sb.append("            if(t.TIMEVALUE = 0,'半年','全年') as tjsjd, ");
		sb.append("            ds.MC AS cs, ");
		sb.append("            t.zgywsr, ");
		sb.append("            t.zgwylr ");
		sb.append("    FROM ");
		sb.append("        zs_cwbb_lrgd t, zs_jg j,dm_cs ds, ");
		sb.append("(" + condition.getSelectSql("zs_cwbb_lrgd", "id"));
		sb.append("    ORDER BY nd DESC  ");
		sb.append("    LIMIT ? , ?) sub ");
		sb.append("    WHERE j.CS_DM = ds.ID  ");
		sb.append("   	  AND t.JG_ID=j.ID ");
		sb.append("		  AND sub.id = t.id) v, ");
		sb.append("    (SELECT @rownum:=?) tmp ");

		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(startIndex);
		params.add(pageSize);
		params.add(pageSize * (page - 1));

		// 获取符合条件的记录
		List<Map<String, Object>> ls = jdbcTemplate.queryForList(sb.toString(),
				params.toArray());

		// 获取符合条件的记录数
		String countSql = condition.getCountSql("id", "zs_cwbb_lrgd");
		int total = jdbcTemplate.queryForObject(countSql, condition.getParams()
				.toArray(), Integer.class);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);

		return obj;
	}

	public Map<String, Object> getLrbById(String id) {
		String sql = "select * from "+Config.PROJECT_SCHEMA+"zs_cwbb_lrgd where id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	
	//现金流量表
		public Map<String, Object> getXjllb(int page, int pageSize, Map<String,Object> where) {
			
			Condition condition = new Condition();
			condition.add("b.DWMC", "FUZZY", where.get("DWMC"));


			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
			sb.append(" FROM    ( SELECT c.MC as CS,a.id,b.DWMC,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS TJSJ,");
			sb.append(" a.JYHD_XJLR_XJ,a.JYHD_XJLC_XJ,a.TZHD_XJLR_XJ,a.TZHD_XJLC_XJ,");
			sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ");
			sb.append(" FROM "+Config.PROJECT_SCHEMA+"zs_cwbb_xjll a,zs_jg b,dm_cs c,(SELECT @rownum:=?) temp");
			sb.append(condition.getSql());//相当元 where b.DWMC like '%%'
			sb.append(" AND a.JG_ID=b.ID AND a.ZTBJ=1 and b.CS_DM=c.ID ORDER  BY TJSJ) as t");
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
	    //获取现金流量信息
		public Map<String,Object> getXjllbById(String id){
			StringBuffer sb = new StringBuffer();
			sb.append("	 SELECT a.ID,b.DWMC,c.MC AS CS,a.*");
			sb.append("	FROM "+Config.PROJECT_SCHEMA+"zs_cwbb_xjll a, zs_jg b,dm_cs c");
			sb.append("	WHERE a.JG_ID=b.ID AND a.ZTBJ=1 AND b.CS_DM=c.ID AND a.id=?");
			 Map<String,Object> rs = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
			 Map<String, Object> ob = new HashMap<>();
				ob.put("data", rs);
			 
			 return ob;
		}

}
