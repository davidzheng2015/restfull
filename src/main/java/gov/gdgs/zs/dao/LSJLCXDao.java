package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
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
	/**
	 * 
	 * @return 事务所历史记录——已合并事务所查询
	 * @throws Exception 
	 */
	public Map<String,Object> yhbswscx(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("b.XSWSMC", Condition.FUZZY, qury.get("dwmc"));
		condition.add("b.HBZT", Condition.EQUAL, qury.get("spzt"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',DATE_FORMAT(b.SBSJ,'%Y-%m-%d') AS SBSJ1 ,DATE_FORMAT(b.HBSJ,'%Y-%m-%d') AS HBSJ1 ,b.* ");
		sb.append("		FROM zs_jghb b,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   order by b.SBSJ desc");
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
	/**
	 * 
	 * @return 事务所历史记录——已注销事务所查询
	 * @throws Exception 
	 */
	public Map<String,Object> yzxswscx(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("b.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("b.zxrq", Condition.GREATER_EQUAL, qury.get("zxrq"));
		condition.add("b.zxrq", Condition.LESS_EQUAL, qury.get("zxrq2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',DATE_FORMAT(a.zxrq,'%Y-%m-%d') AS zxrq ,b.dwmc,b.fddbr,b.zsbh ");
		sb.append("		FROM zs_jgzx a,zs_jg_new b,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   and a.JG_ID = b.yid order by a.zxrq desc");
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
	/**
	 * 
	 * @return 税务师历史记录——省内变更记录
	 * @throws Exception 
	 */
	public Map<String,Object> swssnbgjl(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("e.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("d.xm", Condition.FUZZY, qury.get("xm"));
		condition.add("b.spzt_dm", Condition.EQUAL, qury.get("spzt"));
		condition.add("b.BGRQ", Condition.GREATER_EQUAL, qury.get("sbrq"));
		condition.add("b.BGRQ", Condition.LESS_EQUAL, qury.get("sbrq2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',b.id,b.spzt_dm,d.xm,e.dwmc,f.mc as xb,g.mc as xl,h.mc as zw,DATE_FORMAT(b.BGRQ,'%Y-%m-%d') AS sbrq,DATE_FORMAT(b.SPRQ,'%Y-%m-%d') AS sprq,i.MC as spzt ");
		sb.append("		FROM zs_zyswsbgsp b, zs_ry_zykz c,zs_ry d,zs_jg_new e, dm_xb f,dm_xl g,dm_zw h,dm_spzt i,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   and c.yid=b.ZYSWS_ID and d.ID=c.RY_ID and c.JG_ID=e.ID and f.ID=d.XB_DM and g.ID=d.XL_DM and h.ID=c.ZW_DM and i.ID=b.SPZT_DM order by b.SPRQ desc");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
		List<Map<String,Object>> ls = this.jdbcTemplate.query(sb.toString(),params.toArray(),
				new RowMapper<Map<String,Object>>(){
			public Map<String,Object> mapRow(ResultSet rs, int arg1) throws SQLException{
				Map<String,Object> map = new HashMap<String,Object>();
				List<Map<String,Object>> link = jdbcTemplate.queryForList("select e.ID AS 'key',e.* from zs_zyswsbgxxb e  where e.BGSPB_ID =?",rs.getObject("id"));
				map.put("key", rs.getObject("key"));
				map.put("xm", rs.getObject("xm"));
				map.put("bgxm", link);
				map.put("dwmc", rs.getObject("dwmc"));
				map.put("xb", rs.getObject("xb"));
				map.put("xl", rs.getObject("xl"));
				map.put("zw", rs.getObject("zw"));
				map.put("sbrq", rs.getObject("sbrq"));
				if(rs.getObject("spzt_dm").toString().equals("1")){
					map.put("sprq", null);
				}else{
					map.put("sprq", rs.getObject("sprq"));
				}
				map.put("spzt", rs.getObject("spzt"));
				return map;
				}
			});
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
	/**
	 * 
	 * @return 税务师历史记录——省内转所记录
	 * @throws Exception 
	 */
	public Map<String,Object> swssnzsjl(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("f.xm", Condition.FUZZY, qury.get("xm"));
		condition.add("a.spzt_dm", Condition.EQUAL, qury.get("spzt"));
		condition.add("a.tbrq", Condition.GREATER_EQUAL, qury.get("sbrq"));
		condition.add("a.tbrq", Condition.LESS_EQUAL, qury.get("sbrq2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',f.xm,j.DWMC as yjg,k.DWMC as xjg, g.MC as xb, h.mc as xl, i.MC as zw, c.mc as spzt,");
		sb.append("	DATE_FORMAT( a.tbrq,'%Y-%m-%d') AS sbrq,  DATE_FORMAT(e.spsj,'%Y-%m-%d') AS sprq ");
		sb.append("		FROM zs_zyswssndz a ,(select sjid,id from zs_spzx where lcbzid='402881831be2e6af011be3b60a58001f') d,dm_zw i,dm_xl h,dm_xb g,(select id,xm,xb_dm,xl_dm from zs_ry where rysf_dm='1') f,zs_ry_zykz b,dm_spzt c,zs_spxx e, zs_jg j,zs_jg_new k,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   and a.RY_ID = b.YID ");
		sb.append("	and a.spzt_dm = c.ID");
		sb.append("	and e.spid = d.id");
		sb.append("	and f.ID=b.RY_ID");
		sb.append("	and g.ID =f.XB_DM");
		sb.append("	and h.ID=f.XL_DM");
		sb.append("	and i.ID =b.ZW_DM");
		sb.append("	and j.ID=a.YJG_ID");
		sb.append("	and k.YID=a.XJG_ID");
		sb.append("	and d.sjid = a.id");
		sb.append("	order by a.tbrq desc");
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
	/**
	 * 
	 * @return 税务师历史记录——省内执转非记录
	 * @throws Exception 
	 */
	public Map<String,Object> swssnzzfjl(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("f.xming", Condition.FUZZY, qury.get("xm"));
		condition.add("a.spzt_dm", Condition.EQUAL, qury.get("spzt"));
		condition.add("a.tbrq", Condition.GREATER_EQUAL, qury.get("sbrq"));
		condition.add("a.tbrq", Condition.LESS_EQUAL, qury.get("sbrq2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',f.xming,k.dwmc, g.MC as xb, h.mc as xl, i.MC as zw, c.mc as spzt,");
		sb.append("	DATE_FORMAT( a.tbrq,'%Y-%m-%d') AS sbrq,  DATE_FORMAT(e.spsj,'%Y-%m-%d') AS sprq ");
		sb.append("		FROM zs_zyswszfzy a ,zs_spzx d,dm_zw i,dm_xl h,dm_xb g,zs_ryjbxx f,zs_zysws b,dm_spzt c,zs_spxx e,zs_jg_new k,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   and a.ZYSWS_ID = b.ID ");
		sb.append("	and a.spzt_dm = c.ID");
		sb.append("	and e.spid = d.id");
		sb.append("	and f.ID=b.RY_ID");
		sb.append("	and g.ID =f.XB_DM");
		sb.append("	and h.ID=f.XL_DM");
		sb.append("	and i.ID =b.ZW_DM");
		sb.append("	and k.yID=b.JG_ID");
		sb.append("	and d.sjid = a.id");
		sb.append("	order by a.tbrq desc");
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
	/**
	 * 
	 * @return 税务师历史记录——省内转籍记录
	 * @throws Exception 
	 */
	public Map<String,Object> swssnzjjl(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("f.xming", Condition.FUZZY, qury.get("xm"));
		condition.add("k.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("a.spzt_dm", Condition.EQUAL, qury.get("spzt"));
		condition.add("a.tbrq", Condition.GREATER_EQUAL, qury.get("sbrq"));
		condition.add("a.tbrq", Condition.LESS_EQUAL, qury.get("sbrq2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',f.xming,k.dwmc, g.MC as xb, h.mc as xl, i.MC as zw, c.mc as spzt,");
		sb.append("	DATE_FORMAT( a.tbrq,'%Y-%m-%d') AS sbrq,  DATE_FORMAT(e.spsj,'%Y-%m-%d') AS sprq ");
		sb.append("		FROM zs_zyswszj a ,zs_spzx d,dm_zw i,dm_xl h,dm_xb g,zs_ryjbxx f,zs_zysws b,dm_spzt c,(select max(spsj) as spsj,spid from zs_spxx,zs_spzx where zs_spxx.spid = zs_spzx.id group by zs_spxx.spid)as e,zs_jg_new k,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   and a.ZYSWS_ID = b.ID ");
		sb.append("	and a.spzt_dm = c.ID");
		sb.append("	and e.spid = d.id");
		sb.append("	and f.ID=b.RY_ID");
		sb.append("	and g.ID =f.XB_DM");
		sb.append("	and h.ID=f.XL_DM");
		sb.append("	and i.ID =b.ZW_DM");
		sb.append("	and k.yID=b.JG_ID");
		sb.append("	and d.sjid = a.id");
		sb.append("	order by a.tbrq desc");
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
	/**
	 * 
	 * @return 税务师历史记录——省内注销记录
	 * @throws Exception 
	 */
	public Map<String,Object> swssnzxjl(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("c.xming", Condition.FUZZY, qury.get("xm"));
		condition.add("h.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("a.spzt_dm", Condition.EQUAL, qury.get("spzt"));
		condition.add("a.zxrq", Condition.GREATER_EQUAL, qury.get("sbrq"));
		condition.add("a.zxrq", Condition.LESS_EQUAL, qury.get("sbrq2"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',c.xming,h.dwmc,i.mc as xb,j.mc as xl,k.mc as zw,e.mc as yy,d.mc as spzt,");
		sb.append("	DATE_FORMAT( a.zxrq,'%Y-%m-%d') AS zxrq,  DATE_FORMAT(g.spsj,'%Y-%m-%d') AS sprq ");
		sb.append("		FROM zs_zyswszx a,zs_zysws b,zs_ryjbxx c,dm_spzt d,dm_zyswszxyy e,zs_spzx f,zs_spxx g,zs_jg h,dm_xb i,dm_xl j,dm_zw k,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   and a.ZYSWS_ID=b.ID ");
		sb.append("		AND b.RY_ID=c.ID ");
		sb.append("		AND a.SPZT_DM=d.ID ");
		sb.append("		AND a.ZYSWSZXYY_DM=e.ID ");
		sb.append("		AND f.SJID=a.ID ");
		sb.append("		AND g.SPID=f.ID ");
		sb.append("		AND b.JG_ID=h.ID ");
		sb.append("		AND c.XB_DM=i.ID ");
		sb.append("		AND c.XL_DM=j.ID ");
		sb.append("		AND h.id<>-2 ");
		sb.append("		AND b.ZW_DM=k.ID");
		sb.append("		order by a.ZXRQ desc");
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
	/**
	 * 
	 * @return 税务师历史记录——省内变动记录
	 * @throws Exception 
	 */
	public Map<String,Object> swssnbdjl(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("b.xming", Condition.FUZZY, qury.get("xm"));
		condition.add("c.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("a.ZYZT_DM", Condition.EQUAL, qury.get("spzt"));
		condition.add("a.zyzgzsbh", Condition.EQUAL, qury.get("zyzgzsbh"));
		condition.add("a.zyzsbh", Condition.EQUAL, qury.get("zyzsbh"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS  @rownum:=@rownum+1 AS 'key',b.xming,c.dwmc,a.zyzgzsbh,a.zyzsbh,b.sfzh,d.mc as zt");
		sb.append("		FROM zs_zysws a,zs_ryjbxx b,zs_jg c,dm_ryzt d,(SELECT @rownum:=?) zs_jg ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		   and a.RY_ID=b.ID AND c.ID=a.JG_ID and d.ID=a.ZYZT_DM and a.ZYZT_DM<>1 and c.ID<>-2  order by a.ID desc ");
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
