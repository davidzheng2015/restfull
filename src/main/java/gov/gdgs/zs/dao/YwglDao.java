package gov.gdgs.zs.dao;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class YwglDao extends BaseJdbcDao {

	public Map<String, Object> getYwbb(int page, int pageSize,
			Map<String, Object> where) {

		// 子查询，用于拼接查询条件和返回起止区间
		Condition condition = new Condition();
		condition.add("swsmc", "FUZZY", where.get("swsmc"));
		condition.add("cs_dm", "EQUAL", where.get("cs"));
		condition.add("bbhm", "FUZZY", where.get("bbhm"));
		condition.add("bbrq", "FUZZY", where.get("bbrq"));

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  ");
		sb.append("    @rownum:=@rownum + 1 AS 'key', v . * ");
		sb.append("FROM ");
		sb.append("    (SELECT  ");
		sb.append("        t.id, ");
		sb.append("        t.nd, ");
		sb.append("            t.swsmc, ");
		sb.append("            ds.MC AS cs, ");
		sb.append("            dl.MC AS ywlx, ");
		sb.append("            t.bgwh, ");
		sb.append("            t.xyje, ");
		sb.append("            t.sjsqje, ");
		sb.append("            t.bbhm, ");
		sb.append("            t.bbrq, ");
		sb.append("            t.yzm ");
		sb.append("    FROM ");
		sb.append("        zs_ywbb t, dm_cs ds, dm_ywlx dl, ");
		// <=== 查询条件集合
		sb.append(" ( "
				+ condition.getSelectSql(Config.PROJECT_SCHEMA
						+ "zs_ywbb", "id"));
		sb.append("    ORDER BY bbrq DESC ");
		sb.append("    LIMIT ? , ?) sub ");
		// ===> 插入查询条件集合结束
		sb.append("    WHERE ");
		sb.append("        t.CS_DM = ds.ID AND t.YWLX_DM = dl.ID ");
		sb.append("            AND t.id ");
		sb.append("            AND sub.id = t.id) v, ");
		sb.append("    (SELECT @rownum:=?) tmp ");
		sb.append(" ");

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
		String countSql = condition.getCountSql("id", "zs_ywbb");
		int total = jdbcTemplate.queryForObject(countSql, condition.getParams()
				.toArray(), Integer.class);

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);

		return obj;
	}

}
