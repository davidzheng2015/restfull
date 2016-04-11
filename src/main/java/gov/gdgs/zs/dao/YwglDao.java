package gov.gdgs.zs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class YwglDao extends BaseJdbcDao {

	public List<Map<String, Object>> getYwxy() {
		String sql = "select t.nd,t.swsmc, t.CS_DM as ywfsd ,t.YWLX_ID as ywlx,t.bgwh,t.SFJE as xysfje,t.bbhm,t.bbrq,t.yzm"
				+ " from zs_ywba_jl t limit 100";
		List<Map<String,Object>> ls = jdbcTemplate.queryForList(sql);
		return ls;
	}

}
