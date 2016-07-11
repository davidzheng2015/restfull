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
public class ClientsdsbDao extends BaseJdbcDao{

	
	public Map<String, Object> getHyryqktjb(int page, int pageSize,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM   ( select a.id,a.nd,a.RYZS_RY_ZJ,a.ZYSWS_RY_ZJ,a.QTCYRY_RY_ZJ,");	
		sb.append(" DATE_FORMAT(a.SBRQ,'%Y-%m-%d') as SBRQ,");	
		sb.append(" case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when 3 then '退回' else null end as ZTBJ");		
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_sdsb_hyryqktj a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" AND a.JG_ID=b.ID  and a.JG_ID=68 ORDER BY a.nd DESC ) AS t");
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
	
	public Map<String, Object> getHyryqktjbById(String id) {
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_hyryqktj a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
}
