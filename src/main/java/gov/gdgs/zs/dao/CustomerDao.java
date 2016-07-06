package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
import com.gdky.restfull.dao.AuthDao.UserRowMapper;

@Repository
public class CustomerDao extends BaseJdbcDao{

	public Map<String, Object> getCustomers(int page, int pageSize,
			Long jid, HashMap<String, Object> where) {
		Condition condition = new Condition();
		condition.add("c.jg_id",Condition.EQUAL,jid);
		condition.add("c.lxr",Condition.FUZZY,where.get("lxr"));
		condition.add("c.nsrsbh", Condition.FUZZY, where.get("nsrsbh"));
		condition.add("c.nsrsbhdf", Condition.FUZZY, where.get("nsrsbhdf"));
		condition.add("c.dwmc", Condition.FUZZY, where.get("dwmc"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  ");
		sb.append("     @rownum:=@rownum + 1 AS 'key', v.* ");
		sb.append(" FROM ");
		sb.append("     (SELECT  ");
		sb.append("         t.* ");
		sb.append("     FROM ");
		sb.append("         zs_customer t, ");
		sb.append(" ( ");
		sb.append(condition.getSelectSql("zs_customer c", "c.id"));
		sb.append("     ORDER BY c.ADDDATE DESC ");
		sb.append("     LIMIT ? , ?) sub ");
		sb.append("     WHERE ");
		sb.append("         sub.id = t.id) v, ");
		sb.append("     (SELECT @rownum:=?) tmp ");

		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(startIndex);
		params.add(pageSize);
		params.add(startIndex);
		

		// 获取符合条件的记录
		List<Map<String,Object>> ls = jdbcTemplate.queryForList(sb.toString(),
				params.toArray());
		String countSql = condition.getCountSql("c.id",
				"zs_customer c");
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
