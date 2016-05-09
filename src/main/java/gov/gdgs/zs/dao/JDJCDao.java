package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class JDJCDao extends BaseDao{
	/**
	 * 
	 * @return 监督检查→→事物所年检分页查询
	 * @throws Exception 
	 */
	public Map<String,Object> swsnjcx(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("b.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("b.cs_dm", Condition.EQUAL, qury.get("cs"));
		condition.add("a.nd", Condition.EQUAL, qury.get("nd"));
		condition.add("a.ZTBJ", Condition.EQUAL, qury.get("bbzt"));
		condition.add("a.sbrq", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("a.sbrq", Condition.LESS_EQUAL, qury.get("sbsj2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',v.* from ( SELECT ");
		sb.append("		b.dwmc,b.zsbh,d.mc as jgxz,c.yzbm,c.bgdz,c.dhhm,a.*,");
		sb.append("		case a.ztdm when 3 then '已年检' when 2 then '已自检'  "
				+ " else null end as njzt, CASE a.WGCL_DM WHEN 1 THEN '年检予以通过' WHEN 2 THEN '年检不予通过，"
				+ "责令2个月整改' WHEN 6 THEN '年检不予以通过' WHEN 7 THEN '资料填写有误，请重新填写' ELSE NULL END AS njcl,"
				+ "DATE_FORMAT(a.zjsj,'%Y-%m-%d') AS zjrq,DATE_FORMAT(b.CLRQ ,'%Y-%m-%d') AS clsj,"
				+ "DATE_FORMAT(a.fzrsj,'%Y-%m-%d') AS qzrq");
		sb.append("	 FROM  zs_jg_njb a,zs_jg_new b,zs_jg_kzxx c,dm_jgxz d");
		sb.append("		"+condition.getSql()+" ");
		sb.append("	and a.JG_ID = b.ID and a.ztdm in (2,3) and c.JG_ID= b.ID and d.ID = b.JGXZ_DM");
		sb.append("	group by a.zsjg_id,nd order by a.ND desc");
		sb.append("	 ) as v ,(SELECT @rownum:=?) zs_jg");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
		int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		
		return ob;
	}
}
