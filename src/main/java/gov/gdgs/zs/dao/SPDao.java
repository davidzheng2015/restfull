package gov.gdgs.zs.dao;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Common;
import gov.gdgs.zs.untils.Condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hashids.Hashids;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SPDao extends BaseDao{
	/*-------------------------------中心端-------------------------------------*/
	/**
	 * 未审批查询
	 * @param uid
	 * @return List
	 */
	public Map<String,Object> wspcx(int uid){
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT ");
		sb.append("		d.PERANT_ID as lx,a.LCLXID as lid, d.MC as wsxm, COUNT(c.id) wss ");
		sb.append("		FROM zs_splc a,fw_user_role e,dm_lclx d,zs_splcbz b");
		sb.append("		LEFT JOIN zs_spzx c ON c.LCBZID=b.id AND c.ztbj='Y'");
		sb.append("		WHERE a.ID=b.LCID AND b.ROLEID=e.role_id AND d.ID=a.LCLXID AND a.ZTBJ=2 AND a.LCLXID<>'29' and e.USER_ID = ?");
		sb.append("		GROUP BY a.LCLXID");
		sb.append("		order by d.PERANT_ID,a.LCLXID");
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),new Object[]{uid});
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
	 * 机构设立未审批明细查询
	 * @param pn,ps,uid,lcid,qury
	 * @return
	 */
	public Map<String,Object> jgslspcx(int pn,int ps,int uid,int lclxid,Map<String,Object> qury){
		Condition condition = new Condition();
		StringBuffer sb = new StringBuffer();
			condition.add("e.dwmc", Condition.FUZZY, qury.get("dwmc"));
			condition.add("c.tjsj", Condition.GREATER_EQUAL, qury.get("sbsj"));
			condition.add("c.tjsj", Condition.LESS_EQUAL, qury.get("sbsj2"));
			sb.append("	SELECT 	SQL_CALC_FOUND_ROWS	@rownum:=@rownum+1 AS 'key',");
			sb.append("		e.dwmc, d.MC as wsxm,c.id,c.sjid,DATE_FORMAT(c.tjsj,'%Y-%m-%d') AS tjsj,e.id as jgid,a.id as lcid,b.lcbz,");
			sb.append("		group_concat(concat(b.LCBZ,'.',h.DESCRIPTION)) as dqlcbz");
			sb.append("		FROM zs_splc a,dm_lclx d,zs_splcbz b,zs_spzx c,zs_jg e,fw_user_role g,fw_role h,(SELECT @rownum:=?) zs_jg");
			sb.append("		"+condition.getSql()+" ");
			sb.append("		and a.ID=b.LCID AND b.ROLEID=g.role_id and g.USER_ID=? AND d.ID=a.LCLXID AND a.ZTBJ=2 and b.ROLEID=h.ID AND a.LCLXID<>'29' and a.LCLXID=? ");
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
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		if(ls.size()!=0){
			String lcbzmx = this.jdbcTemplate.queryForObject("select group_concat(concat(a.LCBZ,'.',b.DESCRIPTION)) as lcbzmx from zs_splcbz a,fw_role b where a.LCID=? and a.ROLEID=b.ID order by a.LCBZ",
					new Object[]{ls.get(0).get("lcid")}, String.class);
			ob.put("lcbzmx", lcbzmx);
			ob.put("dqlcbz", ls.get(0).get("dqlcbz"));
		}
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		return ob;
	}
	/**
	 * 机构未审批明细查询
	 * @param pn,ps,uid,lcid,qury
	 * @return
	 */
	public Map<String,Object> jgspcx(int pn,int ps,int uid,int lclxid,Map<String,Object> qury){
		Condition condition = new Condition();
		StringBuffer sb = new StringBuffer();
		condition.add("e.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("c.tjsj", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("c.tjsj", Condition.LESS_EQUAL, qury.get("sbsj2"));
		sb.append("	SELECT 	SQL_CALC_FOUND_ROWS	@rownum:=@rownum+1 AS 'key',");
		sb.append("		e.dwmc, d.MC as wsxm,c.id,c.sjid,DATE_FORMAT(c.tjsj,'%Y-%m-%d') AS tjsj,e.id as jgid,a.id as lcid,b.lcbz,");
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
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		if(ls.size()!=0){
			String lcbzmx = this.jdbcTemplate.queryForObject("select group_concat(concat(a.LCBZ,'.',b.DESCRIPTION)) as lcbzmx from zs_splcbz a,fw_role b where a.LCID=? and a.ROLEID=b.ID order by a.LCBZ",
					new Object[]{ls.get(0).get("lcid")}, String.class);
			ob.put("lcbzmx", lcbzmx);
			ob.put("dqlcbz", ls.get(0).get("dqlcbz"));
		}
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		return ob;
	}
	/**
	 * 人员未审批明细查询
	 * @param pn,ps,uid,lcid,qury
	 * @return
	 */
	public Map<String,Object> ryspcx(int pn,int ps,int uid,int lclxid,Map<String,Object> qury){
		Condition condition = new Condition();
		StringBuffer sb = new StringBuffer();
		String zdm="FZY_ID";
		String bm2="zs_fzysws";
		String bm="";
		String zd="";
		String where="";
		switch(lclxid){
		case 13:bm="zs_fzyzzy";break;
		case 14:bm="zs_fzyswszj";break;
		case 15:bm="zs_fzyzx";break;
		case 20:bm="zs_fzybasp";zdm="FZYSWS_ID";break;
		case 43:bm="zs_cyryzx";bm2="zs_cyry";zdm="CYRY_ID";break;
		case 44:bm="zs_cyryzzy";bm2="zs_cyry";zdm="CYRY_ID";break;
		
		case 46:bm="zs_fzyzzy";bm2="zs_jg l,zs_fzysws";zdm="FZY_ID";zd="l.dwmc,";
			where=" and c.ZSJG_ID = l.id ";break;
		case 5:bm="zs_zyswsbasp";bm2="zs_jg l,zs_zysws";zdm="ZYSWS_ID";zd="l.dwmc,";
			where=" and c.ZSJG_ID = l.id ";break;
		case 6:bm="zs_zyswsbgsp";bm2="zs_jg l,zs_zysws";zdm="ZYSWS_ID";zd="l.dwmc,";
			where=" and c.ZSJG_ID = l.id ";break;
		case 7:bm="zs_zyswszfzy";bm2="zs_jg l,zs_zysws";zdm="ZYSWS_ID";zd="l.dwmc,";
			where=" and c.ZSJG_ID = l.id ";break;
		case 8:bm="zs_zyswszj";bm2="zs_jg l,zs_zysws";zdm="ZYSWS_ID";zd="l.dwmc,";
		 	where=" and c.ZSJG_ID = l.id ";break;
		case 9:bm="zs_zyswssndz";bm2="zs_jg l,zs_zysws";zdm="RY_ID";zd="l.dwmc,";
			where=" and c.ZSJG_ID = l.id ";break;
		case 10:bm="zs_zyswszx";bm2="zs_jg l,zs_zysws";zdm="ZYSWS_ID";zd="l.dwmc,";
			where=" and c.ZSJG_ID = l.id ";break;
		case 12:bm="zs_zcswsnj";bm2="zs_jg l,zs_zysws";zdm="SWS_ID";zd="l.dwmc,";
			where=" and c.ZSJG_ID = l.id ";break;
		}
		condition.add("f.xming", Condition.FUZZY, qury.get("xming"));
		condition.add("c.tjsj", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("c.tjsj", Condition.LESS_EQUAL, qury.get("sbsj2"));
		sb.append("	SELECT 	SQL_CALC_FOUND_ROWS	@rownum:=@rownum+1 AS 'key',");
		sb.append("		f.xming,f.sfzh,k.MC as xb, "+zd+" ");
		sb.append("		d.MC as wsxm,c.id,c.sjid,DATE_FORMAT(c.tjsj,'%Y-%m-%d') AS tjsj,a.id as lcid,b.lcbz,");
		sb.append("		group_concat(concat(b.LCBZ,'.',h.DESCRIPTION)) as dqlcbz");
		sb.append("		FROM zs_splc a,dm_lclx d,zs_splcbz b,zs_spzx c,fw_user_role g,zs_ryjbxx f,"+bm+" i,"+bm2+" j,");
		sb.append("		fw_role h,dm_xb k,(SELECT @rownum:=?) zs_jg");
		sb.append("		"+condition.getSql()+where+"  ");
		sb.append("		and a.ID=b.LCID AND b.ROLEID=g.role_id ");
		sb.append("		 AND d.ID=a.LCLXID AND a.ZTBJ=2 and g.USER_ID=?");
		sb.append("		and b.ROLEID=h.ID  AND a.LCLXID<>'29' and a.LCLXID=?");
		sb.append("		 and c.SJID=i.ID and j.ID=i."+zdm+" and f.ID=j.RY_ID and k.ID=f.XB_DM");
		sb.append("		and c.LCBZID=b.id AND c.ztbj='Y' ");
		sb.append("		  group by c.sjid order by c.TJSJ desc");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add(uid);
		params.add(lclxid);
		params.add((pn-1)*ps);
		params.add(ps);
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
		int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		if(ls.size()!=0){
			String lcbzmx = this.jdbcTemplate.queryForObject("select group_concat(concat(a.LCBZ,'.',b.DESCRIPTION)) as lcbzmx from zs_splcbz a,fw_role b where a.LCID=? and a.ROLEID=b.ID order by a.LCBZ",
					new Object[]{ls.get(0).get("lcid")}, String.class);
			ob.put("lcbzmx", lcbzmx);
			ob.put("dqlcbz", ls.get(0).get("dqlcbz"));
		}
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		return ob;
	}
	
	/**
	 * 审批详细信息查看
	 * @param sjid
	 * @return List
	 */
	public Object spmxxx (String lcid,String sjid){
		StringBuffer sb = new StringBuffer();
		switch (lcid) {
		case "jgbgsp"://机构变更审批详细信息
			return this.jdbcTemplate.queryForList("select MC,XZHI,JZHI from zs_jgbgxxb where jgbgspb_id = ?",new Object[]{sjid});
		case "jgzxsp"://机构注销审批详细信息
			return this.jdbcTemplate.queryForMap("select b.MC as zxyy,a.BZ as zxsm from zs_jgzx a,dm_jgzxyy b where a.id=? and b.ID=a.zxyy_id ",new Object[]{sjid});
		case "jghbsp"://机构合并审批详细信息
			return this.jdbcTemplate.queryForMap("select * from zs_jghb a where a.id=? ",new Object[]{sjid});
		case "zybgsp"://执业变更审批详细信息
			return this.jdbcTemplate.queryForList("select MC,XZHI,JZHI from zs_zyswsbgxxb where bgspb_id = ?",new Object[]{sjid});
		case "fzyba"://非执业备案审批详细信息
			sb.append("select b.ID,c.XMING,d.MC as XB,c.SFZH,c.TXDZ,c.SRI,c.YZBM,c.DHHM,c.BYYX,c.YDDH,e.MC as CS,g.MC as ZZMM,c.BYSJ,c.XPIAN,");
			sb.append("		b.ZYZGZSBH,DATE_FORMAT(b.ZGZSQFRQ,'%Y-%m-%d') as ZGZSQFRQ,b.FZYHYBH, h.MC as ZW,b.ZZDW,DATE_FORMAT(b.RHSJ,'%Y-%m-%d') AS RHSJ,i.mc as XL");
			sb.append("		from zs_fzybasp a,zs_fzysws b,zs_ryjbxx c,dm_xb d,dm_cs e,dm_mz f,dm_zzmm g,dm_zw h,dm_xl i");
			sb.append("		where a.FZYSWS_ID=b.ID and b.RY_ID=c.id and d.ID=c.XB_DM and e.ID=c.CS_DM and f.ID=c.MZ_DM");
			sb.append("		and g.ID=c.ZZMM_DM and h.ID=b.ZW_DM and a.id=? and i.id=c.xl_dm");
			Map<String, Object> fzba = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{sjid});
			fzba.put("fzjl", this.jdbcTemplate.queryForList("select id,qzny,xxxx,zmr from zs_fzyjl where FZY_ID=? and QZNY is not null and qzny<>'' order by qzny",new Object[]{fzba.get("ID")}));
			return fzba;
		case "zyba"://执业备案审批详细信息
			sb.append("		select c.ID,c.XMING,d.MC as XB,c.SFZH,c.TXDZ,c.SRI,c.YZBM,c.DHHM,c.BYYX,c.YDDH,e.MC as CS,g.MC as ZZMM,c.BYSJ,c.XPIAN,");
			sb.append("		b.ZYZGZSBH,DATE_FORMAT(b.ZGZSQFRQ,'%Y-%m-%d') as ZGZSQFRQ, h.MC as ZW,DATE_FORMAT(b.RHSJ,'%Y-%m-%d') AS RHSJ,i.mc as XL,");
			sb.append("			DATE_FORMAT(b.SWDLYWKSSJ,'%Y-%m-%d') as SWDLYWKSSJ,b.ZYZSBH,DATE_FORMAT(b.ZYZCRQ,'%Y-%m-%d') as ZYZCRQ,b.GRHYBH,");
			sb.append("		case b.czr_dm when 1 then \"是\"  when 2 then \"否\" else null end as CZR,");
			sb.append("		case b.fqr_dm when 1 then \"是\"  when 2 then \"否\" else null end as FQR,");
			sb.append("		b.CZE,c.RYDAZT");
			sb.append("		from zs_zyswsbasp a,zs_zysws b,zs_ryjbxx c,dm_xb d,dm_cs e,dm_mz f,dm_zzmm g,dm_zw h,dm_xl i");
			sb.append("		where a.ZYSWS_ID=b.ID and b.RY_ID=c.id and d.ID=c.XB_DM and e.ID=c.CS_DM and f.ID=c.MZ_DM");
			sb.append("		and g.ID=c.ZZMM_DM and h.ID=b.ZW_DM  and i.id=c.xl_dm and a.id=?");
			Map<String, Object> zyba = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{sjid});
			zyba.put("zyjl", this.jdbcTemplate.queryForList("select id,qzny,xxxx,zmr from zs_jl where ry_ID=? and QZNY is not null and qzny<>'' order by qzny",new Object[]{zyba.get("ID")}));
			return zyba;
		}
		return null;
	}
	
	 /**
	  * 中心端审批审核处理方法
	  * @param Map:spid,uid,uname,spyj,ispass
	  * @return boolean
	  * @throws Exception
	  */
		public boolean sptj(Map<String,Object> spsq) throws Exception{
		 StringBuffer sb = new StringBuffer();
		 sb.append("	 select a.ID,b.LCBZ,b.SPBZLX,b.BHBZLX,b.LCID,c.LCLXID,b.ROLEID,e.SJID from zs_spxx a,zs_splcbz b,zs_splc c,zs_spzx e,fw_user_role f ");
		 sb.append("	 where a.LCBZID=b.ID and b.LCID=c.ID and a.SPID=e.ID and f.ROLE_ID=b.ROLEID and c.ZTBJ=2");
		 sb.append("	 and a.spid = ? ");
		 sb.append("	 and f.USER_ID=?");
		 Map<String, Object> mp = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{spsq.get("spid"),spsq.get("uid")});
		 String sql ="update zs_spxx set SPYJ=?,ISPASS=?,USERID=?,SPRNAME=?,SPSJ=sysdate() where id =?";
		 this.jdbcTemplate.update(sql,new Object[]{spsq.get("spyj"),spsq.get("ispass"),spsq.get("uid"),spsq.get("uname"),mp.get("ID")});
		 if(spsq.get("ispass").equals("Y")){
			 int c = (int)mp.get("SPBZLX");
			 if(c==1||c==2){
				 this.jdbcTemplate.update("update zs_spzx set ZTBJ='N', QRBJ=null where id =?",new Object[]{spsq.get("spid")});
				 switch((int)mp.get("LCLXID")){
				 case 2:
					 this.jdbcTemplate.update("update zs_jgbgspb set SPZT_DM='8',YJIAN=?,SPRQ=sysdate(),SPR_ID=? where id =?",
							 new Object[]{spsq.get("spyj"),spsq.get("uid"),mp.get("SJID")});
					 this.jdbcTemplate.update("update zs_jg a,zs_jgbglsb b,zs_jgbgspb c set a.DWMC=b.DWMC,"
					 		+ "a.CS_DM=b.CS_DM,a.JGXZ_DM=b.JGXZ_DM,a.DZHI=b.DZHI,"
					 		+ "a.ZCZJ=b.ZCZJ,a.ZCDZ=b.ZCDZ,a.YYZZHM=b.YYZZHM,"
					 		+ "a.SWSZSCLSJ=b.SWSZSCLSJ where c.id =? and c.JGBGLSB_ID = b.id "
					 		+ "and c.jg_id = a.id",new Object[]{mp.get("SJID")});
					 for(Map<String, Object> rec:this.jdbcTemplate.queryForList("select a.*,b.jg_id as jgid from"
					 		+ " zs_jgbgxxb a,zs_jgbgspb b where a.JGBGSPB_ID=b.id and b.id=?",mp.get("SJID"))){//插入变更项目历史信息
							this.jdbcTemplate.update("insert into zs_jglsbgxxb (MC,JZHI,XZHI,GXSJ,JGB_ID,ID) values(?,?,?,sysdate(),?,?)",
									new Object[]{rec.get("MC"),rec.get("JZHI"),rec.get("XZHI"),rec.get("jgid"),new Common().newUUID()});
						}
					 break;
				 case 4:
					 this.jdbcTemplate.update("update zs_jgzx set SPZT='2',ZXRQ=sysdate() where id =?",
							 new Object[]{mp.get("SJID")});
					 this.jdbcTemplate.update("update zs_jg a,zs_jgzx b set a.JGZT_DM='9',a.yxbz='0' where b.id =? and a.id=b.jg_id",
							 new Object[]{mp.get("SJID")});
					 break;
				 case 5:
					 this.jdbcTemplate.update("update zs_zyswsbasp a,zs_zysws b,zs_ryjbxx c set a.SPZT_DM='2',a.YJIAN=?,a.SPRQ=sysdate(),a.SPR=?,c.RYZT_DM='1',c.YXBZ='1', "
					 		+ "b.RHSJ=sysdate(),b.ZYZT_DM='1',b.RYSPGCZT_DM='1',b.YXBZ='1',b.ZYZCRQ=sysdate() "
					 		+ " where a.id =? and a.ZYSWS_ID=b.id and b.ry_id=c.id",
							 new Object[]{spsq.get("spyj"),spsq.get("uname"),mp.get("SJID")});
					 break;
				 case 6:
					 this.jdbcTemplate.update("update zs_fzybasp set SPZT_DM='8',YJIAN=?,SPRQ=sysdate(),SPR_ID=? where id =?",
							 new Object[]{spsq.get("spyj"),spsq.get("uid"),mp.get("SJID")});
					 this.jdbcTemplate.update("update zs_zysws a,zs_ryjbxx b,zs_zyswsbgls c,zs_zyswsbgsp d set a.ZYZGZSBH=c.ZYZGZSBH,"
					 		+ "a.ZGZSQFRQ=c.ZGZSQFRQ,a.ZYZSBH=c.ZYZSBH,"
							 + "a.ZYZCRQ=c.ZYZCRQ,a.SWDLYWKSSJ=c.SWDLYWKSSJ,a.HNHYJHS=c.HNHYJHS,"
							 + "a.XZDWJZWZC=c.XZDWJZWZC,a.JSQSZDWJZWZC=c.JSQSZDWJZWZC,"
							 + "a.CZE=c.CZE,a.SZ_DM=c.SZ_DM,a.FQR_DM=c.FQR_DM,a.CZR_DM=c.CZR_DM,"
							 + "b.XMING=c.XMING,b.SFZH=c.SFZH,a.ZW_DM=c.ZW_DM,"
							 + "a.ZYHYBH=c.ZYHYBH,a.RHSJ=c.RHSJ,b.CS_DM=c.CS_DM "
							 + "where a.RY_ID=b.ID and c.ID=d.SWSBGLSB_ID and d.ZYSWS_ID=a.ID and d.ID=?"
					 		,new Object[]{mp.get("SJID")});
					 for(Map<String, Object> rec:this.jdbcTemplate.queryForList("select a.*,b.ZYSWS_ID as zyid from"
						 		+ " zs_zyswsbgxxb a,zs_zyswsbgsp b where a.BGSPB_ID=b.id and b.id=?",mp.get("SJID"))){//插入变更项目历史信息
								this.jdbcTemplate.update("insert into zs_zyswslsbgxxb (MC,JZHI,XZHI,GXSJ,JGB_ID,ID) values(?,?,?,sysdate(),?,?)",
										new Object[]{rec.get("MC"),rec.get("JZHI"),rec.get("XZHI"),rec.get("zyid"),new Common().newUUID()});
							}
					 break;
				 case 20://select * from zs_splcbz where lcid='402881831be2e6af011be3c184d2003a' 审批步骤需改为2
					 this.jdbcTemplate.update("update zs_fzybasp a,zs_fzysws b,zs_ryjbxx c set a.SPZT_DM='2',a.SPR=?,c.RYZT_DM='1',c.YXBZ='1', "
					 		+ "b.RHSJ=sysdate(),b.FZYZT_DM='1',b.RYSPGCZT_DM='1',b.YXBZ='1',b.FZYZCRQ=sysdate() "
					 		+ " where a.id =? and a.FZYSWS_ID=b.id and b.ry_id=c.id",
							 new Object[]{spsq.get("uid"),mp.get("SJID")});
					 break;
				 };
			 }else{
				 this.jdbcTemplate.update("update zs_spzx set LCBZID=?, QRBJ=null where id =?",
						 new Object[]{this.jdbcTemplate.queryForObject("select id from zs_splcbz where lcid=? and lcbz=?",
								 new Object[]{mp.get("LCID"),(int)mp.get("LCBZ")+1}, Object.class),spsq.get("spid")});
			 };
		 }else if(spsq.get("ispass").equals("N")){
			 int c = (int)mp.get("BHBZLX");
			 if(c==1||c==2){
				 this.jdbcTemplate.update("update zs_spzx set ZTBJ='N', QRBJ=null where id =?",new Object[]{spsq.get("spid")});
				 switch((int)mp.get("LCLXID")){
				 case 2:
					 this.jdbcTemplate.update("update zs_jgbgspb set SPZT_DM='3',YJIAN=?,SPRQ=sysdate(),SPR_ID=? where id =?",
							 new Object[]{spsq.get("spyj"),spsq.get("uid"),mp.get("SJID")});
					 break;
				 case 4:
					 this.jdbcTemplate.update("update zs_jgzx set SPZT='3',ZXRQ=sysdate() where id =?",
							 new Object[]{mp.get("SJID")});
					 break;
				 case 5:
					 this.jdbcTemplate.update("update zs_zyswsbasp a,zs_zysws b,zs_ryjbxx c set a.SPZT_DM='3',a.YJIAN=?,a.SPRQ=sysdate(),a.SPR=?,c.RYZT_DM='2',c.YXBZ='0', "
					 		+ "b.ZYZT_DM='2',b.RYSPGCZT_DM='3',b.YXBZ='0' "
					 		+ " where a.id =? and a.ZYSWS_ID=b.id and b.ry_id=c.id",
							 new Object[]{spsq.get("spyj"),spsq.get("uname"),mp.get("SJID")});
					 break;
				 case 6:
					 this.jdbcTemplate.update("update zs_zyswsbgsp set SPZT_DM='3',YJIAN=?,SPRQ=sysdate(),SPR=? where id =?",
							 new Object[]{spsq.get("spyj"),spsq.get("uid"),mp.get("SJID")});
					 break;
				 case 20:
					 this.jdbcTemplate.update("update zs_fzybasp a,zs_fzysws b,zs_ryjbxx c set a.SPZT_DM='3',a.SPR=?,c.RYZT_DM='2',c.YXBZ='0', "
					 		+ "b.FZYZT_DM='2',b.RYSPGCZT_DM='3',b.YXBZ='0' "
					 		+ " where a.id =? and a.FZYSWS_ID=b.id and b.ry_id=c.id",
							 new Object[]{spsq.get("uid"),mp.get("SJID")});
					 break;
				 }
			 }else{
				 this.jdbcTemplate.update("update zs_spzx set LCBZID=?, QRBJ='Y' where id =?",
						 new Object[]{this.jdbcTemplate.queryForObject("select id from zs_splcbz where lcid=? and lcbz=?",
								 new Object[]{mp.get("LCID"),(int)mp.get("LCBZ")-1}, Object.class),spsq.get("spid")});
			 }
		 }
		 return true;
	 }
	 /**
	  * 上级驳回意见
	  * @param spid,lcbz
	  * @return
	  */
	 public Map<String, Object> sjbhyj(String spid,int lcbz){
		 return this.jdbcTemplate.queryForMap("select a.spyj from zs_spxx a,zs_splcbz b where a.spid=? and b.ID=a.lcbzid and b.LCBZ=?",
				 new Object[]{spid,lcbz});
	 }
	 
	/*-------------------------------事务所端-------------------------------------*/
	 /**
		 * 事务所变更审批项目申请
		 * @param spxm
		 */
	public void swsbgsq(Map<String, Object> spxm) throws Exception{
		List<Map<String, Object>> forupdate = (List<Map<String, Object>>) spxm.remove("bgjl");//获取变更项目
		int id=(int)spxm.remove("uid");
		int jgid=(int)spxm.remove("jgid");
		List<Object> listValue = new ArrayList<Object>();  //Map转List
		Iterator<String> it = spxm.keySet().iterator();  
		while (it.hasNext()) {  
			String key = it.next().toString();  
			listValue.add(spxm.get(key));  
		};
		String uuid = new Common().newUUID();
		String sql ="insert into zs_jgbgspb (ID,JG_ID,SPZT_DM,BGRQ,TXR_ID,JGBGLSB_ID) values(?,?,'1',sysdate(),?,?)";
		String sql2 ="insert into zs_jgbgxxb (MC,JZHI,XZHI,GXSJ,JGBGSPB_ID) values(?,?,?,sysdate(),?)";
		String sql3 ="insert into zs_jgbglsb (DWMC,CS_DM,JGXZ_DM,DZHI,ZCZJ,ZCDZ,YYZZHM,SWSZSCLSJ) values(?,?,?,?,?,?,?,?)";
		Number rs1 = this.insertAndGetKeyByJdbc(sql3,listValue.toArray(),new String[] {"ID"});//插入临时表，获取自动生成id
		this.jdbcTemplate.update(sql, new Object[]{uuid,jgid,id,rs1});//插入业务表
		Map<String,Object> spsq=new HashMap<>();//设置生成审批表方法参数
		spsq.put("sid", uuid);
		//判断是否分所
		if(this.jdbcTemplate.queryForObject("select JGXZ_DM from zs_jg where id =?",new Object[]{jgid},int.class)!=3){
			spsq.put("lclx", "402881831be2e6af011be3ab8b84000c");
		}else{
			spsq.put("lclx", "40288087228378910122838ecac50022");
		}
		spsq.put("jgid", jgid);
		swsSPqq(spsq);//生成审批表记录
		for(Map<String, Object> rec:forupdate){//插入变更项目信息
			this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),uuid});
		}
	}
	 
	 /**
	  * 非执业税务师备案申请
	  * @param sqxx
	  * @throws Exception
	  */
	public void fzyswsba(Map<String, Object> sqxx) throws Exception {
		String sql ="insert into zs_ryjbxx (XMING,XB_DM,SRI,SFZH,TXDZ,YZBM,DHHM,YDDH,CS_DM,MZ_DM,XL_DM,ZZMM_DM,BYYX,BYSJ,XPIAN,RYZT_DM,RYSF_DM,LRRQ,YXBZ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'3','2',sysdate(),'0')";
		String sql2 ="insert into zs_fzysws (RY_ID,ZYZGZSBH,ZGZSQFRQ,FZYHYBH,ZW_DM,ZZDW,RHSJ,FZYZT_DM,RYSPGCZT_DM,YXBZ) values(?,?,?,?,?,?,?,'3','2','0')";
		String sql3 ="insert into zs_fzyjl (FZY_ID,QZNY,XXXX,ZMR) values(?,?,?,?)";
		Number rs = this.insertAndGetKeyByJdbc(sql, new Object[]{sqxx.get("XMING"),sqxx.get("XB_DM"),
				sqxx.get("SRI"),sqxx.get("SFZH"),sqxx.get("TXDZ"),sqxx.get("YZBM"),
				sqxx.get("DHHM"),sqxx.get("YDDH"),sqxx.get("CS_DM"),sqxx.get("MZ_DM"),
				sqxx.get("XL_DM"),sqxx.get("ZZMM_DM"),sqxx.get("BYYX"),sqxx.get("BYSJ"),
				sqxx.get("XPIAN")},new String[] {"ID"});//插入ry表，获取自动生成id
		Number rs2 = this.insertAndGetKeyByJdbc(sql2, new Object[]{rs,sqxx.get("ZYZGZSBH"),
				sqxx.get("ZGZSQFRQ"),sqxx.get("FZYHYBH"),sqxx.get("ZW_DM"),sqxx.get("ZZDW"),
				sqxx.get("RHSJ")},new String[] {"ID"});
		for(Map<String, Object> rec:(List<Map<String, Object>>)sqxx.get("FZYJL")){
			if(rec.get("QZNY").toString().equals(""))continue;
			this.jdbcTemplate.update(sql3,new Object[]{rs2,rec.get("QZNY"),rec.get("XXXX"),rec.get("ZMR")});
		}
		String suid = new Common().newUUID();
		this.jdbcTemplate.update("insert into zs_fzybasp (ID,FZYSWS_ID,SPZT_DM,SPSJ) values(?,?,'1',sysdate())",new Object[]{suid,rs2});
		Map<String,Object> spsq=new HashMap<>();//设置生成审批表方法参数
		spsq.put("sid", suid);
		spsq.put("lclx", "402881831be2e6af011be3c184d2003a");
		spsq.put("csdm", sqxx.get("CS_DM"));
		swsSPqq(spsq);
	}
	 /**
	  * 非执业转籍申请
	  * @param sqxm
	  * @throws Exception
	  */
	public void fzyzjsq(Map<String, Object> sqxm) throws Exception{
		String sql ="insert into zs_fzyswszj (ID,FZY_ID,ZJYY,ZJYYRQ,TBRQ,RYSPZT) values(?,?,?,?,sysdate(),'0')";
		String uuid = new Common().newUUID();
		this.jdbcTemplate.update(sql, new Object[]{uuid,sqxm.get("FID"),sqxm.get("ZJYY"),sqxm.get("ZJYYRQ")});
		this.jdbcTemplate.update("update zs_fzysws a set a.RYSPGCZT_DM='6' where a.id=?",sqxm.get("FID"));
		Map<String,Object> spsq=new HashMap<>();//设置生成审批表方法参数
		spsq.put("sid", uuid);
		spsq.put("lclx", "402881831be2e6af011be3c6d5f70040");
		spsq.put("csdm", sqxm.get("CS"));
		swsSPqq(spsq);
	}
	
	/**
	 * 事务所注销审批申请
	 * @param sqxm
	 * @throws Exception
	 */
	public void swszxsq(Map<String, Object> sqxm) throws Exception{
		String sql ="insert into zs_jgzx (ZXYY_ID,JG_ID,BZ,SPZT) values(?,?,?,'1')";
		Number rs = this.insertAndGetKeyByJdbc(sql, new Object[]{sqxm.get("zxyy"),sqxm.get("jgid"),sqxm.get("zxsm")},new String[] {"ID"});
		Map<String,Object> spsq=new HashMap<>();//设置生成审批表方法参数
		spsq.put("sid", rs);
		spsq.put("lclx", "402881831be2e6af011be3adc72c0011");
		spsq.put("jgid", sqxm.get("jgid"));
		swsSPqq(spsq);
	}
	/**
	 * 事务所合并审批申请
	 * @param sqxm
	 * @throws Exception
	 */
	public void swshbsq(Map<String, Object> sqxm) throws Exception{
		String sql ="insert into zs_jghb (JG_ID,SFMC,XSWSMC,GSMCYHBH,SQR,HBSJ,SBSJ,HBZT) values(?,?,?,?,?,?,sysdate(),'1')";
		Number rs = this.insertAndGetKeyByJdbc(sql, new Object[]{sqxm.get("jgid"),
				sqxm.get("SFMC"),sqxm.get("XSWSMC"),sqxm.get("GSMCYHBH"),
				sqxm.get("SQR"),sqxm.get("HBSJ")},new String[] {"ID"});
		Map<String,Object> spsq=new HashMap<>();//设置生成审批表方法参数
		spsq.put("sid", rs);
		spsq.put("lclx", "402881831be2e6af011be3aceac6000e");
		spsq.put("jgid", sqxm.get("jgid"));
		swsSPqq(spsq);
	}
	
	
	/**
	  * 审批申请处理方法
	  * @param Map:sid,lclx,(选填：jgid,csdm)
	  * @return boolean
	  * @throws Exception
	  */
	 boolean swsSPqq(Map<String,Object> spsq) throws Exception{
		String sql ="select b.id,b.lcbz,b.roleid from zs_splcbz b where  b.LCID =? order by b.LCBZ";
		 List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sql,new Object[]{spsq.get("lclx")});//查询该流程需要步骤
		List<Object> listValue = new ArrayList<Object>();//设置插入单据表参数
		String suid = new Common().newUUID();
		listValue.add(suid);
		listValue.add(spsq.get("sid"));
		listValue.add(ls.get(0).get("id"));//获取第一步步骤id
		String insterspzx = "";
		String insterspzx2 = "";
		if(spsq.containsKey("jgid")){//是否填写机构id
			listValue.add(spsq.get("jgid"));
			insterspzx+="ZSJG_ID,";
			insterspzx2+="?,";
		}
		if(spsq.containsKey("csdm")){//是否填写城市代码
			listValue.add(spsq.get("csdm"));
			insterspzx+="CS_ID,";
			insterspzx2+="?,";
		}
		this.jdbcTemplate.update("insert into zs_spzx (ID,SJID,LCBZID,ZTBJ,"+insterspzx+"TJSJ) values(?,?,?,'Y',"+insterspzx2+"sysdate())",listValue.toArray());
		for(Map<String, Object> rec:ls){//根据步骤生成对应审批节点数记录
			this.jdbcTemplate.update("insert into zs_spxx (ID,SPID,LCBZID) values(?,?,?)",new Object[]{new Common().newUUID(),suid,rec.get("id")});
		}
		return true;
	}
	
	 /*-------------------------------非审批申请-------------------------------------*/
	 /**
		 * 普通项目更新
		 * @param ptxm
		 */
		public void updatePTXM(Map<String, Object> ptxm)throws Exception{
			List<Map<String, Object>> forupdate = (List<Map<String, Object>>) ptxm.remove("bgjl");//获取并移除bgjl属性
			Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
			int jgid = (int)hashids.decode((String)ptxm.get("jgid"))[0];
			ptxm.put("jgid", jgid);
			List<Object> listValue = new ArrayList<Object>();  //Map转List
			Iterator<String> it = ptxm.keySet().iterator();  
			while (it.hasNext()) {  
				String key = it.next().toString();  
				listValue.add(ptxm.get(key));  
			};
			String sql ="update zs_jg set DHUA=?,CZHEN=?,jyfw=?,yzbm=?,SZPHONE=?,JGZCH=?,SWDJHM=?,jgdmzh=?,GSYHMCBH=?,KHH=?,KHHZH=?,TXYXMING=?,XTYPHONE=?,XTYYX=?,SZYX=?,wangzhi=?,dzyj=?,yhdw=?,yhsj=?,gzbh=?,gzdw=?,gzry=?,gzsj=?,yzbh=?,yzdw=?,yzry=?,yzsj=?,TTHYBH=?,rhsj=?,JBQK=?,GLZD=?,GDDH=?,BGCSZCZM=? where id =?";
			String sql2 ="insert into zs_jglsbgxxb (MC,JZHI,XZHI,GXSJ,JGB_ID,ID) values(?,?,?,sysdate(),?,?)";
			this.jdbcTemplate.update(sql,listValue.toArray());//更新数据库
			for(Map<String, Object> rec:forupdate){//插入变更项目信息
				this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),ptxm.get("jgid"),new Common().newUUID()});
			}
				
		}


	public List<Map<String, Object>> getFzyswsBa(String sfzh) {
		StringBuffer sb = new StringBuffer();
		sb.append("  select d.ZTBJ,e.spyj,a.RYZT_DM,a.ID ,e.SPSJ,a.XMING,u.`NAMES` ");
		sb.append("  from zs_ryjbxx a,zs_fzysws b,zs_fzybasp c,zs_spzx d,zs_spxx e ");
		sb.append("  left join fw_users u  ");
		sb.append("  on u.ID = e.USERID ");
		sb.append("  where a.ID=b.RY_ID   ");
		sb.append("  and b.ID=c.FZYSWS_ID  ");
		sb.append("  and d.SJID=c.ID  ");
		sb.append("  and d.ID=e.SPID ");
		sb.append("  and a.SFZH= ? ");
		sb.append("  order by a.id desc  ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(), new Object[]{sfzh});
		return ls;
	}
}

