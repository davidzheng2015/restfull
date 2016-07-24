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
public class XtsjfxDao extends BaseJdbcDao{
	/*
	 * 机构年检数据分析
	 */
	
	public Map<String, Object> getJgnjsjfxb(int page, int pageSize,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("nd", "EQUAL", where.get("nd"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select SQL_CALC_FOUND_ROWS	@rownum:=@rownum+1 AS 'key',t.id,t.parent_id,t.mc,ifnull(t.zjgs,0) zjgs,ifnull(t.cjnj_jgs,0)cjnj_jgs,");
		sb.append(" round(t.cjnj_jgs*100/t.zjgs,2) cjnj_bl,ifnull((t.zjgs-t.cjnj_jgs),0) wcjnj_jgs,100-round(t.cjnj_jgs*100/t.zjgs,2) wcjnj_bl,");	
		sb.append(" ifnull(t.tgnj_jgs,0) tgnj_jgs,round(t.tgnj_jgs*100/t.cjnj_jgs,2) tgnj_bl,ifnull(t.njz_jgs,0) njz_jgs,round(t.njz_jgs*100/t.cjnj_jgs,2) njz_bl,");	
		sb.append(" ifnull(t.wtgnj_jgs,0) wtgnj_gjs,round(t.wtgnj_jgs*100/t.cjnj_jgs,2)wtgnj_bl ");		
		sb.append(" FROM (select cs.ID,cs.PARENT_ID,cs.mc,(select count(id) from zs_jg where yxbz = '1') zjgs,(select count(nj.id)");
		sb.append("    from zs_jg jg, zs_jg_njb nj where jg.yxbz = '1'   and nj.yxbz = '1' and jg.id = nj.zsjg_id and nj.nd = ?) cjnj_jgs,");
		sb.append("  (select count(nj.id) from zs_jg jg, zs_jg_njb nj where jg.yxbz = '1' and nj.yxbz = '1' and nj.ztdm = '3' ");
		sb.append("  and jg.id = nj.zsjg_id and nj.nd = ?) tgnj_jgs,(select count(nj.id) from zs_jg jg, zs_jg_njb nj");
		sb.append("  where jg.yxbz = '1'  and nj.yxbz = '1'  and nj.ztdm in ('1', '2') and jg.id = nj.zsjg_id and nj.nd = ?) njz_jgs,");
		sb.append("  (select count(nj.id) from zs_jg jg, zs_jg_njb nj where jg.yxbz = '1' and nj.yxbz = '1' and nj.ztdm = '0' ");
		sb.append("  and jg.id = nj.zsjg_id and nj.nd = ?) wtgnj_jgs from dm_cs cs,(SELECT @rownum:=?) temp  where cs.parent_id is null");
		sb.append("  union all select b.id, b.parent_id, b.mc, a.zrs, b.zrs, b.tgnj_jgs, b.njz_jgs, b.btgnj_jgs  from (select cs.ID,");
		sb.append("  cs.PARENT_ID, cs.mc, sum(case when parent_id = '0'  then 1 when parent_id <> '0'   and parent_id is not null then");
		sb.append("   1 else 0 end )zrs from zs_jg jb right join dm_cs cs  on jb.CS_DM = cs.id where cs.parent_id is not null  and jb.yxbz = '1' ");
		sb.append("  group by cs.ID, cs.PARENT_ID, cs.mc) a,(select cs.ID, cs.PARENT_ID, cs.mc, sum( case when parent_id = '0' and id_2 is not null then ");
		sb.append("  1 when parent_id <> '0'  and id_2 is not null and parent_id is not null then 1 else 0 end ) zrs, sum(case ");
		sb.append("  when parent_id = '0' and ztdm = '3'  and id_2 is not null then 1 when parent_id <> '0' and ztdm = '3' and id_2 is not null  and ");
		sb.append("  parent_id is not null then 1 else 0 end ) tgnj_jgs, sum( case when parent_id = '0' and ztdm = '0'  and id_2 is not null then ");
		sb.append("  1  when parent_id <> '0' and ztdm = '0'  and id_2 is not null and  parent_id is not null then 1 else 0 end  )btgnj_jgs,");
		sb.append("  sum( case when parent_id = '0' and ztdm in ('1', '2')  and id_2 is not null then 1 when parent_id <> '0' and ztdm in ('1', '2')  and id_2 is not null and ");
		sb.append("  parent_id is not null then 1 else 0 end  )njz_jgs from (select jb.id    id_1, zy.id    id_2, zy.nd, zy.ztdm, ");
		sb.append("  jb.cs_dm from zs_jg jb left join zs_jg_njb zy on jb.id = zy.zsjg_id where jb.yxbz = '1' and zy.yxbz = '1' and (zy.nd = ? or zy.nd is null)) jg ");
		sb.append("  right join dm_cs cs on jg.CS_DM = cs.id where cs.parent_id is not null group by cs.ID, cs.PARENT_ID, cs.mc) b where a.id = b.id)t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = (condition.getParams());
		params.add(condition.getParams().get(0));
		params.add(condition.getParams().get(0));
		params.add(condition.getParams().get(0));
		params.add(pageSize * (page - 1));
		params.add(condition.getParams().get(0));
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
