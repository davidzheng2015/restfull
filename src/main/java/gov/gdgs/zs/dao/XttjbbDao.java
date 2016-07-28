package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class XttjbbDao extends BaseJdbcDao {

	public Map<String, Object> getXttjbb(int page, int pageSize, HashMap<String, Object> where) {
		// TODO Auto-generated method stub
		Condition condition = new Condition();
		condition.add(" and g.ID = s.JG_ID ");
		Object year = where.get("year");
		Object jgmc = where.get("dwmc");
		if(year == null){
			condition.add(" and s.ND = if((date_format(now(),'%m')>3),(date_format(now(),'%Y')-1),(date_format(now(),'%Y')-2)) ");
		}else{
			//condition.add("s.ND", Condition.EQUAL, year);
			condition.add(" and s.ND ="+year+" ");
		}
		if(jgmc != null){
			//condition.add("g.DWMC", Condition.FUZZY, jgmc);
			condition.add(" and g.DWMC like '%"+jgmc+"%' ");
		}
		//获取count查询语句
		String sqlCount = condition.getCountSql("s.id",
				" zs_sdsb_swsjbqk s,zs_jg g,(SELECT @rownum:=0) temp ");
		condition.add(" limit ?,? ");
		//获取查询语句
		String sql = condition.getSelectSql(" zs_sdsb_swsjbqk s,zs_jg g ",
				new String[] { "SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 as 'KEY'","g.DWMC", "s.DWXZ", "s.FRDBXM", "s.CZRS",
						"s.HHRS", "s.RYZS", "s.ZYZCSWSRS",
						"(s.RYZS - s.ZYZCSWSRS) as CYRS", "s.ZCZJ", "s.YYSR",
						"s.ZCZE", "s.SRZE", "s.LRZE", "s.JGSZD" });
		int total = this.jdbcTemplate.queryForObject(sqlCount, Integer.class);
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sql , new Object[] {
				pageSize * (page - 1), pageSize });
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);
		
		return obj;
	}

}
