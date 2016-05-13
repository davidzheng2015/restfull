package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class LSJLCXDao extends BaseDao{
	
	/**
	 * 
	 * @return 事务所历史记录——变更记录查询
	 * @throws Exception 
	 */
	public Map<String,Object> swsbglsjlcx(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("e.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("b.SPZT_DM", Condition.EQUAL, qury.get("spzt"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',DATE_FORMAT(b.bgrq,'%Y-%m-%d') AS sqsj ,e.dwmc,e.fddbr,DATE_FORMAT(b.sprq,'%Y-%m-%d') AS spsj,d.mc as spzt,c.mc as bgmc,c.jzhi,c.xzhi ");
		sb.append("		FROM zs_jgbgspb b,zs_jgbgxxb c,dm_spzt d,zs_jg e,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		 AND b.ID=c.JGBGSPB_ID AND d.ID=b.SPZT_DM and b.JG_ID=e.id order by b.bgrq desc");
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
