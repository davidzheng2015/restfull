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
public class SPDao extends BaseDao{
	/**
	 * 未审批查询
	 * @param uid
	 * @return List
	 */
	public Map<String,Object> wspcx(int uid){
		String sql="select role_id from fw_user_role where USER_ID = ?";
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT ");
		sb.append("		d.PERANT_ID as lx,a.LCLXID as lid, d.MC as wsxm, COUNT(c.id) wss ");
		sb.append("		FROM zs_splc a,dm_lclx d,zs_splcbz b");
		sb.append("		LEFT JOIN zs_spzx c ON c.LCBZID=b.id AND c.ztbj='Y'");
		sb.append("		WHERE a.ID=b.LCID AND b.ROLEID=? AND d.ID=a.LCLXID AND a.ZTBJ=2 AND a.LCLXID<>'29'");
		sb.append("		GROUP BY a.LCLXID");
		sb.append("		order by d.PERANT_ID,a.LCLXID");
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),new Object[]{this.jdbcTemplate.queryForObject(sql,new Object[]{uid}, int.class)});
		List<String> dl = new ArrayList<String>();
		dl.add("事务所审批类型");
		dl.add("执业税务师审批类型");
		dl.add("非执业注册税务师审批类型");
		dl.add("年检审批类型");
		dl.add("其他从业人员中心审批");
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("ls", ls);
		mp.put("dl", dl);
		return mp;
	}
	
	/**
	 * 查看流程
	 * @param lid
	 * @return
	 */
	public List<Map<String,Object>> cklc(int lid){
		String sql = "select a.ID,a.LCMC,a.LCMS from zs_splc a where a.LCLXID =? and a.ZTBJ=2 order by a.ID desc";
		return this.jdbcTemplate.query(sql,new Object[]{lid},
				new RowMapper<Map<String,Object>>(){
			public Map<String,Object> mapRow(ResultSet rs, int arg1) throws SQLException{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("lcmc", rs.getObject("LCMC"));
				map.put("lcms", rs.getObject("LCMS"));
				map.put("xxlc", jdbcTemplate.queryForList("SELECT b.lcbz,c.DESCRIPTION as js"
						+ ",case b.spbz WHEN 'Y' THEN '是' WHEN 'N' THEN '否' ELSE NULL END AS tjbz"
						+ ",CASE b.bhbz WHEN 'Y' THEN '是' WHEN 'N' THEN '否' ELSE NULL END AS bhbz"
						+ ",CASE b.sfhq WHEN 'Y' THEN '是' WHEN 'N' THEN '否' ELSE NULL END AS sfhq"
						+ " FROM zs_splcbz b,fw_role c"
						+ " WHERE b.lcid = ? and c.ID=b.roleid"
						+ " ORDER BY lcbz",new Object[]{rs.getObject("ID")}));
				return map;
				}
			});
	}
	
	/**
	 * 事务所变更未审批明细查询
	 * @param pn,ps,uid,lcid,qury
	 * @return
	 */
	public Map<String,Object> swsbgsp(int pn,int ps,int uid,int lclxid,Map<String,Object> qury){
		Condition condition = new Condition();
		condition.add("e.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("c.tjsj", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("c.tjsj", Condition.LESS_EQUAL, qury.get("sbsj2"));
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT 	SQL_CALC_FOUND_ROWS	@rownum:=@rownum+1 AS 'key',");
		sb.append("		e.dwmc, d.MC as wsxm,c.id,c.sjid,DATE_FORMAT(c.tjsj,'%Y-%m-%d') AS tjsj,e.id as jgid,a.id as lcid,");
		sb.append("		case f.yjxx when f.yjxx then f.yjxx else '无' end as yjxx,group_concat(concat(b.LCBZ,'.',h.DESCRIPTION)) as dqlcbz");
		sb.append("		FROM zs_splc a,dm_lclx d,zs_splcbz b,zs_spzx c,zs_jg e,zs_jgyjxxb f,fw_user_role g,fw_role h,(SELECT @rownum:=?) zs_jg");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		and a.ID=b.LCID AND b.ROLEID=g.role_id and g.USER_ID=? AND d.ID=a.LCLXID AND a.ZTBJ=2 and b.ROLEID=h.ID AND a.LCLXID<>'29' and a.LCLXID=? and e.id=f.id");
		sb.append("		and c.LCBZID=b.id AND c.ztbj='Y' and e.ID=c.ZSJG_ID group by e.dwmc order by c.TJSJ desc");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add(uid);
		params.add(lclxid);
		params.add((pn-1)*ps);
		params.add(ps);
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
		int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
		String lcbzmx = this.jdbcTemplate.queryForObject("select group_concat(concat(a.LCBZ,'.',b.DESCRIPTION)) as lcbzmx from zs_splcbz a,fw_role b where a.LCID=? and a.ROLEID=b.ID order by a.LCBZ",new Object[]{ls.get(0).get("lcid")}, String.class);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		ob.put("dqlcbz", ls.get(0).get("dqlcbz"));
		ob.put("lcbzmx", lcbzmx);
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		return ob;
	}
	/**
	 * 事务所变更详细信息查看
	 * @param sjid
	 * @return
	 */
	public List<Map<String, Object>> swsbgspxx (int sjid){
		return this.jdbcTemplate.queryForList("select MC,XZHI,JZHI from zs_jgbgxxb where jgbgspb_id = ?",new Object[]{sjid});
	}
	
}

