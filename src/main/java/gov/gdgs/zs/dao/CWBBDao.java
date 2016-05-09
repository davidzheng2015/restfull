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
		condition.add("t.nd", Condition.EQUAL, where.get("nd"));
		condition.add("j.dwmc",Condition.FUZZY,where.get("swsmc"));
		condition.add("j.cs_dm",Condition.EQUAL,where.get("cs"));
		condition.add(" AND t.ztbj = 1 ");
		condition.add(" AND t.jg_id = j.id ");

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
		sb.append("(" + condition.getSelectSql("zs_cwbb_lrgd t, zs_jg j", "t.id"));
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
		String countSql = condition.getCountSql("t.id", "zs_cwbb_lrgd t, zs_jg j");
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
		String sql = "select j.DWMC,t.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_lrgd t, zs_jg j where t.jg_id = j.id and t.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}

	public Map<String, Object> getZcfzb(int page, int pageSize,
			Map<String, Object> where) {
		Condition condition = new Condition();
		condition.add("t.nd", Condition.EQUAL, where.get("nd"));
		condition.add("j.dwmc",Condition.FUZZY,where.get("swsmc"));
		condition.add("j.cs_dm",Condition.EQUAL,where.get("cs"));
		condition.add(" AND t.ztbj = 1 ");
		condition.add(" AND t.jg_id = j.id ");

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
		sb.append("            t.zczj, ");
		sb.append("            t.fzsyzqy_hj ");
		sb.append("    FROM ");
		sb.append("        zs_cwbb_zcfzgd t, zs_jg j,dm_cs ds, ");
		sb.append("(" + condition.getSelectSql("zs_cwbb_zcfzgd t, zs_jg j", "t.id"));
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
		String countSql = condition.getCountSql("t.id", "zs_cwbb_zcfzgd t, zs_jg j");
		int total = jdbcTemplate.queryForObject(countSql, condition.getParams()
				.toArray(), Integer.class);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);

		return obj;
	}

	public Map<String, Object> getZcfzbById(String id) {
		String sql = "select j.DWMC,t.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_zcfzgd t, zs_jg j where t.jg_id = j.id and t.id = ?";
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
			sb.append("	 SELECT a.ID,b.DWMC,c.MC AS CS,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS TJSJ,a.*");
			sb.append("	FROM "+Config.PROJECT_SCHEMA+"zs_cwbb_xjll a, zs_jg b,dm_cs c");
			sb.append("	WHERE a.JG_ID=b.ID AND a.ZTBJ=1 AND b.CS_DM=c.ID AND a.id=?");
			 Map<String,Object> rs = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
			 Map<String, Object> ob = new HashMap<>();
				ob.put("data", rs);
			 
			 return ob;
		}
		//鉴证业务统计表
      public Map<String, Object> getJzywtjb(int page, int pageSize, Map<String,Object> where) {
			
			Condition condition = new Condition();
			condition.add("a.nd", Condition.EQUAL, where.get("nd"));
			condition.add("b.dwmc",Condition.FUZZY,where.get("dwmc"));
			condition.add("a.ZTBJ", Condition.EQUAL, where.get("ZTBJ"));


			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.* ");
			sb.append(" from   ( select a.id,a.nd,b.dwmc,a.HSQJJE_HS,a.HSQJJE_JE,");
			sb.append(" a.TZYNSDSE_HS,a.TZYNSDSE_JE,");
			sb.append(" a.TJYNSDSE_HS,a.TJYNSDSE_JE,");
			sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' WHEN 2 THEN '通过' WHEN 3 THEN '退回' ELSE NULL END AS ZTBJ");
			sb.append(" from "+Config.PROJECT_SCHEMA+"zs_sdsb_jzywqktjb a,zs_jg b,(SELECT @rownum:=?) temp");
			sb.append(condition.getSql());//相当元 where x.xx like '%%'
			sb.append(" and a.JG_ID=b.ID ) as t");
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
      
      public Map<String,Object> getJzywtjbById(String id){
			StringBuffer sb = new StringBuffer();
			sb.append("	 SELECT a.ID,b.dwmc,a.nd,DATE_FORMAT(a.SBRQ,'%Y年%m月%d日') AS SBRQ,");
			sb.append("	a.HSQJJE_HS0,a.HSQJJE_JE0,a.HSQJJE_HS,a.HSQJJE_JE,");
			sb.append("	(a.HSQJJE_HS-a.HSQJJE_HS0) AS HSZJE2,(a.HSQJJE_JE-a.HSQJJE_JE0) AS JEZJE2,");
			sb.append(" CONCAT((a.HSQJJE_HS-a.HSQJJE_HS0)/a.HSQJJE_HS0*100,'%') HSZZB2,CONCAT((a.HSQJJE_JE-a.HSQJJE_JE0)/a.HSQJJE_JE0*100,'%') JEZZB2,");
			sb.append(" a.TZYNSDSE_HS0,a.TZYNSDSE_JE0,a.TZYNSDSE_HS,a.TZYNSDSE_JE,");
			sb.append(" (a.TZYNSDSE_HS-a.TZYNSDSE_HS0) AS HSZJE3,(a.TZYNSDSE_JE-a.TZYNSDSE_JE0) AS JEZJE3,");
			sb.append(" CONCAT((a.TZYNSDSE_HS-a.TZYNSDSE_HS0)/a.TZYNSDSE_HS0*100,'%') HSZZB3,CONCAT((a.TZYNSDSE_JE-a.TZYNSDSE_JE0)/a.TZYNSDSE_JE0*100,'%') JEZZB3,");
			sb.append(" a.TJYNSDSE_HS0,a.TJYNSDSE_JE0,a.TJYNSDSE_HS,a.TJYNSDSE_JE,");
			sb.append(" (a.TJYNSDSE_HS-a.TJYNSDSE_HS0) AS HSZJE4,(a.TJYNSDSE_JE-a.TJYNSDSE_JE0) AS JEZJE4,");
			sb.append(" CONCAT((a.TJYNSDSE_HS-a.TJYNSDSE_HS0)/a.TJYNSDSE_HS0*100,'%') HSZZB4,CONCAT((a.TJYNSDSE_JE-a.TJYNSDSE_JE0)/a.TJYNSDSE_JE0*100,'%') JEZZB4,");
			sb.append(" a.MBKSJE_HS0,a.MBKSJE_JE0,a.MBKSJE_HS,a.MBKSJE_JE,");
			sb.append(" (a.MBKSJE_HS-a.MBKSJE_HS0) AS HSZJE5,(a.MBKSJE_JE-a.MBKSJE_JE0) AS JEZJE5,");
			sb.append(" CONCAT((a.MBKSJE_HS-a.MBKSJE_HS0)/a.MBKSJE_HS0*100,'%') HSZZB5,CONCAT((a.MBKSJE_JE-a.MBKSJE_JE0)/a.MBKSJE_JE0*100,'%') JEZZB5,");
			sb.append(" a.CCSSKC_HS0,a.CCSSKC_JE0,a.CCSSKC_HS,a.CCSSKC_JE,");
			sb.append(" (a.CCSSKC_HS-a.CCSSKC_HS0) AS HSZJE6,(a.CCSSKC_JE-a.CCSSKC_JE0) AS JEZJE6,");
			sb.append(" CONCAT((a.CCSSKC_HS-a.CCSSKC_HS0)/a.CCSSKC_HS0*100,'%') HSZZB6,CONCAT((a.CCSSKC_JE-a.CCSSKC_JE0)/a.CCSSKC_JE0*100,'%') JEZZB6,");
			sb.append(" a.TDZZSQSJZ_HS0,a.TDZZSQSJZ_JE0,a.TDZZSQSJZ_HS,a.TDZZSQSJZ_JE,");
			sb.append(" (a.TDZZSQSJZ_HS-a.TDZZSQSJZ_HS0) AS HSZJE7,(a.TDZZSQSJZ_JE-a.TDZZSQSJZ_JE0) AS JEZJE7,");
			sb.append(" CONCAT((a.TDZZSQSJZ_HS-a.TDZZSQSJZ_HS0)/a.TDZZSQSJZ_HS0*100,'%') HSZZB7,CONCAT((a.TDZZSQSJZ_JE-a.TDZZSQSJZ_JE0)/a.TDZZSQSJZ_JE0*100,'%') JEZZB7,");
			sb.append(" a.QTJZ_HS0,a.QTJZ_JE0,a.QTJZ_HS,a.QTJZ_JE,");
			sb.append(" (a.QTJZ_HS-a.QTJZ_HS0) AS HSZJE8,(a.QTJZ_JE-a.QTJZ_JE0) AS JEZJE8,");
			sb.append(" CONCAT((a.QTJZ_HS-a.QTJZ_HS0)/a.QTJZ_HS0*100,'%') HSZZB8,CONCAT((a.QTJZ_JE-a.QTJZ_JE0)/a.QTJZ_JE0*100,'%') JEZZB8,");
			sb.append(" a.GXJSQYRDQZYW_HS0,a.GXJSQYRDQZYW_JE0,a.GXJSQYRDQZYW_HS,a.GXJSQYRDQZYW_JE,");
			sb.append(" (a.GXJSQYRDQZYW_HS-a.GXJSQYRDQZYW_HS0) AS HSZJE9,(a.GXJSQYRDQZYW_JE-a.GXJSQYRDQZYW_JE0) AS JEZJE9,");
			sb.append(" CONCAT((a.GXJSQYRDQZYW_HS-a.GXJSQYRDQZYW_HS0)/a.GXJSQYRDQZYW_HS0*100,'%') HSZZB9,CONCAT((a.GXJSQYRDQZYW_JE-a.GXJSQYRDQZYW_JE0)/a.GXJSQYRDQZYW_JE0*100,'%') JEZZB9,");
			sb.append(" a.QYZXSWDESKJSJZYW_HS0,a.QYZXSWDESKJSJZYW_JE0,a.QYZXSWDESKJSJZYW_HS,a.QYZXSWDESKJSJZYW_JE,");
			sb.append(" (a.QYZXSWDESKJSJZYW_HS-a.QYZXSWDESKJSJZYW_HS0) AS HSZJE10,(a.QYZXSWDESKJSJZYW_JE-a.QYZXSWDESKJSJZYW_JE0) AS JEZJE10,");
			sb.append(" CONCAT((a.QYZXSWDESKJSJZYW_HS-a.QYZXSWDESKJSJZYW_HS0)/a.QYZXSWDESKJSJZYW_HS0*100,'%') HSZZB10,CONCAT((a.QYZXSWDESKJSJZYW_JE-a.QYZXSWDESKJSJZYW_JE0)/a.QYZXSWDESKJSJZYW_JE0*100,'%') JEZZB10,");
			sb.append(" a.YFFJJKCJZYW_HS0,a.YFFJJKCJZYW_JE0,a.YFFJJKCJZYW_HS,a.YFFJJKCJZYW_JE,");
			sb.append(" (a.YFFJJKCJZYW_HS-a.YFFJJKCJZYW_HS0) AS HSZJE11,(a.YFFJJKCJZYW_JE-a.YFFJJKCJZYW_JE0) AS JEZJE11,");
			sb.append(" CONCAT((a.YFFJJKCJZYW_HS-a.YFFJJKCJZYW_HS0)/a.YFFJJKCJZYW_HS0*100,'%') HSZZB11,CONCAT((a.YFFJJKCJZYW_JE-a.YFFJJKCJZYW_JE0)/a.YFFJJKCJZYW_JE0*100,'%') JEZZB11,");
			sb.append(" a.QT_HS0,a.QT_JE0,a.QT_HS,a.QT_JE,");
			sb.append(" (a.QT_HS-a.QT_HS0) AS HSZJE12,(a.QT_JE-a.QT_JE0) AS JEZJE12,");
			sb.append(" CONCAT((a.QT_HS-a.QT_HS0)/a.QT_HS0*100,'%') HSZZB12,CONCAT((a.QT_JE-a.QT_JE0)/a.QT_JE0*100,'%') JEZZB12,");
			sb.append(" a.TIANBIAOREN,a.SUOZHANG");
			sb.append("  FROM zs_sdsb_jzywqktjb a,zs_jg b WHERE a.JG_ID=b.ID and a.ID=?");
			 Map<String,Object> rs = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
			 Map<String, Object> ob = new HashMap<>();
				ob.put("data", rs);
			 
			 return ob;
		}
      
    //经营规模统计表
      public Map<String, Object> getJygmtjb(int page, int pageSize, Map<String,Object> where) {
			
			Condition condition = new Condition();
			condition.add("a.nd", Condition.EQUAL, where.get("nd"));
			condition.add("b.dwmc",Condition.FUZZY,where.get("dwmc"));
			condition.add("a.ZTBJ", Condition.EQUAL, where.get("ZTBJ"));


			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
			sb.append(" from	(select a.id,b.dwmc,a.nd,a.BNSRZE_HJ,a.BNSRZE_SSFW,");
			sb.append(" a.BNSRZE_SSJZ,a.BNSRZE_QTYW,a.SNSRZE,");
			sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' WHEN 2 THEN '通过' WHEN 3 THEN '退回' ELSE NULL END AS ZTBJ");
			sb.append(" from "+Config.PROJECT_SCHEMA+"zs_sdsb_jygmtjb a, zs_jg b,(SELECT @rownum:=?) temp");
			sb.append(condition.getSql());//相当元 where x.xx like '%%'
			sb.append(" and a.JG_ID=b.ID ) as t");
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
      
      public Map<String,Object> getJygmtjbById(String id){
			StringBuffer sb = new StringBuffer();
			sb.append("	select a.id,a.nd,b.dwmc,DATE_FORMAT(a.SBRQ,'%Y年%m月%d日') AS SBRQ,");
			sb.append("	a.BNSRZE_HJ,a.BNSRZE_SSFW,a.BNSRZE_SSJZ,a.BNSRZE_QTYW,a.SNSRZE,");
			sb.append("	CONCAT((a.BNSRZE_HJ-a.SNSRZE)/a.SNSRZE*100,'%') AS ZZB,a.TBR,a.SZ");
			sb.append("	from zs_sdsb_jygmtjb a, zs_jg b");
			sb.append("	where a.JG_ID=b.ID and a.id=?");
			 Map<String,Object> rs = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
			 Map<String, Object> ob = new HashMap<>();
				ob.put("data", rs);
			 
			 return ob;
		}
      

}
