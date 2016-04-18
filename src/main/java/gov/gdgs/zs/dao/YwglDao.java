package gov.gdgs.zs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class YwglDao extends BaseJdbcDao {

	public Map<String, Object> getYwxy(int page, int pageSize, Map<String,Object> where) {
		int startIndex = pageSize * (page-1) ;
		String sql = "select t.nd,t.swsmc, t.CS_DM as cs ,t.YWLX_ID as ywlx,t.bgwh,t.SFJE as xysfje,t.bbhm,t.bbrq,t.yzm"
				+ " from zs_ywba_jl t limit ?,?";
		String countSql = "select count(t.id) from zs_ywba_jl t ";
		int total = jdbcTemplate.queryForObject(countSql, Integer.class);
		List<Map<String,Object>> ls = jdbcTemplate.queryForList(sql,new Object[]{startIndex,pageSize});
		
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("data", ls);
		obj.put("total", total);
		return obj;
	}

}
