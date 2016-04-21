package gov.gdgs.zs.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class YwglDao extends BaseJdbcDao {

	public Map<String, Object> getYwxy(int page, int pageSize, Map<String,Object> where) {
		
		int startIndex = pageSize * (page-1) ;
		
		//子查询，用于设定查询条件和返回起止区间
		StringBuffer sq = new StringBuffer();
		sq.append("SELECT t.id FROM zs_ywbb t ");
		sq.append("WHERE ");
		sq.append("    t.swsmc LIKE '%广州%' AND t.nd = 2011 ");
		sq.append("ORDER BY t.bbrq DESC ");
		sq.append("LIMIT 21000 , 10 ");
		
		//拼接查询条件
		StringBuffer condition = new StringBuffer();
		if (where.size() != 0) {
			condition.append("WHERE ");
			Iterator<Map.Entry<String, Object>> entries = where.entrySet()
					.iterator();
			while (entries.hasNext()) {
				Map.Entry<String, Object> entry = entries.next();
				condition.append(" and ");
				condition.append(entry.getKey() + " like :" + entry.getKey() + " ");
			}
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  ");
		sb.append("    @rownum:=@rownum + 1 AS rownum, v . * ");
		sb.append("FROM ");
		sb.append("    (SELECT  ");
		sb.append("        t.nd, ");
		sb.append("            t.swsmc, ");
		sb.append("            ds.MC AS cs, ");
		sb.append("            dl.MC AS ywlx, ");
		sb.append("            t.bgwh, ");
		sb.append("            t.xyje, ");
		sb.append("            t.SJSQJE, ");
		sb.append("            t.bbhm, ");
		sb.append("            t.bbrq, ");
		sb.append("            t.yzm ");
		sb.append("    FROM ");
		sb.append("        zs_ywbb t, dm_cs ds, dm_ywlx dl, (SELECT  ");
		sb.append("        id ");
		sb.append("    FROM ");
		sb.append("        zs_ywbb ");
		//查询条件集合
		sb.append("    WHERE ");
		sb.append("        swsmc LIKE '%广州%' AND nd = 2011 ");
		//===插入查询条件集合结束
		sb.append("    ORDER BY bbrq DESC ");
		sb.append("    LIMIT "+startIndex+" , "+pageSize+") sub ");
		sb.append("    WHERE ");
		sb.append("        t.CS_DM = ds.ID AND t.YWLX_DM = dl.ID ");
		sb.append("            AND t.id ");
		sb.append("            AND sub.id = t.id) v, ");
		sb.append("    (SELECT @rownum:=0) tmp ");
		sb.append(" ");

		String countSql = "select count(t.id) from zs_ywba_jl t ";
		int total = jdbcTemplate.queryForObject(countSql, Integer.class);
		List<Map<String,Object>> ls = jdbcTemplate.queryForList(sb.toString(),new Object[]{startIndex,pageSize});
		
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("data", ls);
		obj.put("total", total);
		return obj;
	}

}
