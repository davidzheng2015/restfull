package gov.gdgs.zs.dao;


import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;










import org.hashids.Hashids;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RyglDao extends BaseDao{
	/**
	 * 人员查询
	 * @param pn ps
	 * @param qury
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> rycx(int pn,int ps,Map<String, Object> qury) {
		final String url=Config.URL_PROJECT;
		Condition condition = new Condition();
		condition.add("a.xming", Condition.FUZZY, qury.get("xm"));
		condition.add("a.rysf_dm", Condition.EQUAL, qury.get("rysfdm"));
		condition.add("a.sfzh", Condition.FUZZY_LEFT, qury.get("sfzh"));
		condition.add("a.CS_DM", Condition.EQUAL, qury.get("cs"));
		condition.add("a.xb_DM", Condition.EQUAL, qury.get("xb"));
		condition.add("a.xl_dm", Condition.EQUAL, qury.get("xl"));
		StringBuffer sb = new StringBuffer();
		sb.append("	select SQL_CALC_FOUND_ROWS ");
		sb.append("		@rownum:=@rownum+1 as 'key',");
		sb.append("				a.id,");
		sb.append("				a.xming,");
		sb.append("				d.mc as xb,");
		sb.append("				date_format(a.SRI,'%Y-%m-%d') as srrq,");
		sb.append("				a.sfzh,");
		sb.append("				b.mc as cs,");
		sb.append("				c.mc as mz,");
		sb.append("				f.mc as xl,");
		sb.append("				e.mc as rysf,a.rysf_dm as rysfdm");
		sb.append("				from zs_ryjbxx a,dm_cs b,dm_mz c,dm_xb d,dm_rysf e,dm_xl f,(select @rownum:=?) zs_ry");
		sb.append("		"+condition.getSql()+" ");
		sb.append("				and a.xb_dm= d.id");
		sb.append("				and a.cs_dm=b.id");
		sb.append("				and a.mz_dm=c.id");
		sb.append("				and a.xl_dm=f.id");
		sb.append("				and a.rysf_dm=e.id");
		sb.append("				and a.yxbz='1'");
		if(qury.containsKey("sorder")){
			Boolean asc = qury.get("sorder").toString().equals("ascend");
			switch (qury.get("sfield").toString()) {
			case "xm":
				if(asc){
					sb.append("		    order by convert( a.xming USING gbk) COLLATE gbk_chinese_ci ");
				}else{
					sb.append("		    order by convert( a.xming USING gbk) COLLATE gbk_chinese_ci desc");
				}
				break;
			case "xb":
				if(asc){
					sb.append("		    order by a.xb_dm ");
				}else{
					sb.append("		    order by a.xb_dm desc");
				}
				break;
			case "cs":
				if(asc){
					sb.append("		    order by convert( b.mc USING gbk) COLLATE gbk_chinese_ci ");
				}else{
					sb.append("		    order by convert( b.mc USING gbk) COLLATE gbk_chinese_ci desc");
				}
				break;
			case "srrq":
				if(asc){
					sb.append("		    order by a.sri ");
				}else{
					sb.append("		    order by a.sri desc");
				}
				break;
			}
		}
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
		List<Map<String,Object>> ls = this.jdbcTemplate.query(sb.toString(),params.toArray(),
				new RowMapper<Map<String,Object>>(){
			public Map<String,Object> mapRow(ResultSet rs, int arg1) throws SQLException{
				Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String,Object> link = new HashMap<>();
				String id = hashids.encode(rs.getLong("id"));
				switch (rs.getObject("rysfdm").toString()) {
				case "1":
					link.put("herf_xxzl", url+"/ryxx/zyryxx/"+id);
					link.put("herf_bgjl", url+"/ryxx/zyrybgjl/"+id);
					link.put("herf_zsjl", url+"/ryxx/zyryzsjl/"+id);
					link.put("herf_zjjl", url+"/ryxx/zyryzjjl/"+id);
					link.put("herf_zzjl", url+"/ryxx/zyryzzjl/"+id);
					link.put("herf_spzt", url+"/ryxx/zyryspzt/"+id);
					link.put("herf_njjl", url+"/ryxx/zyrynjjl/"+id);
					break;
				case "2":
					link.put("herf_xxzl", url+"/ryxx/fzyryxx/"+id);
					link.put("herf_bgjl", url+"/ryxx/fzyrybgjl/"+id);
					link.put("herf_zsjl", url+"/ryxx/fzyryzxjl/"+id);
					link.put("herf_zjjl", url+"/ryxx/fzyryzjjl/"+id);
					link.put("herf_zzjl", url+"/ryxx/fzyryzfjl/"+id);
					break;
				case "3":
					link.put("herf_xxzl", url+"/ryxx/cyryxx/"+id);
					link.put("herf_bgjl", url+"/ryxx/cyrybgjl/"+id);
					break;
				
				}
				map.put("key", rs.getObject("key"));
				map.put("xh", rs.getObject("key"));
				map.put("_links", link);
				map.put("xm", rs.getObject("xming"));
				map.put("xb", rs.getObject("xb"));
				map.put("cs", rs.getObject("cs"));
				map.put("srrq", rs.getObject("srrq"));
				map.put("sfzh", rs.getObject("sfzh"));
				map.put("rysfdm", rs.getObject("rysfdm"));
				map.put("rysf", rs.getObject("rysf"));
				map.put("mz", rs.getObject("mz"));
				map.put("xl", rs.getObject("xl"));
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
	 * 
	 * @param id
	 * @return 执业人员详细信息
	 */
	public Map<String,Object> zyryxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("	select @rownum:=@rownum+1 as 'key',b.dwmc,");	
		sb.append("	c.xming as xm,");	
		sb.append("	f.mc as cs,");	
		sb.append("	d.mc as xb, ");	
		sb.append("	g.mc as mz,");	
		sb.append("	date_format(c.sri,'%Y-%m-%d') as csny,");	
		sb.append("		e.mc as xl,");	
		sb.append("	c.sfzh,");	
		sb.append("	h.mc as zzmm,");	
		sb.append("	c.txdz,");	
		sb.append("	c.yddh,");	
		sb.append("		c.yzbm,");	
		sb.append("	i.mc as zw,");	
		sb.append("	c.dhhm, ");	
		sb.append("	c.byyx,");	
		sb.append("	a.zyzgzsbh,");	
		sb.append("	date_format(c.bysj,'%Y-%m-%d') as bysj,");	
		sb.append("		date_format(a.zgzsqfrq,'%Y-%m-%d') as qfrq,");	
		sb.append("	date_format(a.swdlywkssj,'%Y-%m-%d') as ywkssj,");	
		sb.append("	 a.zyzsbh,");	
		sb.append("	date_format(a.zyzcrq,'%Y-%m-%d') as zyzcrq, ");	
		sb.append("	a.grhybh,");	
		sb.append("	date_format(a.rhsj,'%Y-%m-%d') as rhsj,");	
		sb.append("	case a.czr_dm when 1 then \"是\"  when 2 then \"否\" else null end as czr,");	
		sb.append("	a.cze,");	
		sb.append("	case a.fqr_dm when 1 then \"是\"  when 2 then \"否\" else null end as fqr,");	
		sb.append("	c.rydazt,");	
		sb.append("	c.xpian");	
		sb.append("	from ");	
		sb.append("	zs_zysws a,zs_jg b ,zs_ryjbxx c ,");	
		sb.append("	dm_xb d,dm_xl e,dm_cs f,dm_mz g,dm_zzmm h,dm_zw i,(select @rownum:=0) zs_sws");	
		sb.append("	where");	
		sb.append("		b.id=a.jg_id");	
		sb.append("		 and a.ry_id = c.ID ");	
		sb.append("		and d.ID = c.XB_DM");	
		sb.append("	and e.ID = c.XL_DM");	
		sb.append("		and f.ID = c.CS_DM");	
		sb.append("		and g.ID = c.MZ_DM");	
		sb.append("	and h.ID = c.ZZMM_DM");	
		sb.append("	and i.ID = a.ZW_DM");	
		sb.append("	and c.ID = ?");	
		String sql = "SELECT @rownum:=@rownum+1 as 'key',a.qzny,a.xxxx,a.zmr FROM zs_jl a,zs_ryjbxx c,(select @rownum:=0) zs_sws WHERE a.ry_id = c.id  and a.xxxx is not null and c.ID = ? order by a.ID";
		List<Map<String, Object>> tl = this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
		Map<String,Object> ll =new HashMap<String,Object>();
		if(tl.size()!=0){
			ll =tl.get(0);
		}
		ll.put("ryjl", this.jdbcTemplate.queryForList(sql,new Object[]{id}));
		return ll;
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 执业人员变更记录
	 */
	public List<Map<String,Object>> zyrybgjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key',a.mc as bgmc,a.jzhi,a.xzhi,date_format(a.gxsj,'%Y-%m-%d') as gxsj ");
		sb.append("		from zs_zyswslsbgxxb a,zs_zysws b ,(select @rownum:=0) zs_sws");
		sb.append("		where a.ZYSWS_ID = b.ID ");
		sb.append("		and b.ry_id = ?");
		sb.append("		order by a.gxsj");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 执业人员转所记录
	 */
	public List<Map<String,Object>> zyryzsjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key', ");
		sb.append("		c.mc as spztmc,a.zyswsyj as bryj,a.yswsyj as ydwyj,");
		sb.append("		DATE_FORMAT( a.tbrq,'%Y-%m-%d') AS tbrq  ");
		sb.append("		 from zs_zyswssndz a ,zs_zysws b,dm_spzt c,(select @rownum:=0) zs_sws ");
		sb.append("		where a.RY_ID = b.ID ");
		sb.append("		and a.spzt_dm = c.ID ");
		sb.append("		and b.ry_id =? ");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 执业人员转籍记录
	 */
	public List<Map<String,Object>> zyryzjjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key', ");
		sb.append("		a.zysws_id ,c.mc as spztmc,a.zjyy as zjyj,a.yswsyj as dwyj,");
		sb.append("		DATE_FORMAT( a.tbrq,'%Y-%m-%d') AS tbrq   ");
		sb.append("		from zs_zyswszj a ,dm_spzt c,zs_zysws d,(select @rownum:=0) zs_sws");
		sb.append("		 where d.ry_id = ? ");
		sb.append("		 and a.spzt_dm = c.ID and d.id= a.zysws_id");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 执业人员转执记录
	 */
	public List<Map<String,Object>> zyryzzjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key', ");
		sb.append("		a.zysq as zzsq,a.xdwyj as dwyj,DATE_FORMAT( a.tbrq,'%Y-%m-%d') AS tbrq,DATE_FORMAT( c.SPSJ,'%Y-%m-%d') AS spsj ");
		sb.append("	 from (select * from zs_fzyzzy union");
		sb.append("		 select * from zs_cyryzzy )as a,");
		sb.append("		 zs_spzx b,");
		sb.append("		 zs_spxx c,");
		sb.append("		 zs_zysws f,(select @rownum:=0) zs_sws ");
		sb.append("		 where a.zysws_id = f.id ");
		sb.append("		 and a.ID = b.sjid");
		sb.append("		and c.SPID = b.ID ");
		sb.append("		and c.ISPASS = 'Y' ");
		sb.append("		   and f.ry_ID = ?");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 执业人员审批状态
	 */
	public String zyryspzt(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select ");
		sb.append("		distinct  a.mc ");
		sb.append("		 from dm_ryspgczt a, zs_zysws b ");
		sb.append("		 where a.ID = b.RYSPGCZT_DM ");
		sb.append("		 and b.ry_ID = ?");
		return this.jdbcTemplate.queryForObject(sb.toString(),new Object[]{id},String.class);
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 执业人员年检记录
	 */
	public List<Map<String,Object>> zyrynjjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key', ");
		sb.append("	 c.ID,a.nd,b.dwmc,a.swsfzryj,");
		sb.append("		CASE a.ZTDM WHEN 1 THEN '保存'  WHEN 2 THEN '自检' WHEN 0 THEN '退回' WHEN 3 THEN '已年检' ELSE NULL END AS njzt,");
		sb.append("		 DATE_FORMAT( f.SPSJ,'%Y-%m-%d') AS spsj");
		sb.append("		 FROM  zs_zcswsnj a,zs_jg b,zs_ryjbxx c,zs_zysws d,zs_spzx e,zs_spxx f,zs_splcbz g,zs_splc h,(select @rownum:=0) zs_sws ");
		sb.append("		 WHERE a.ZSJG_ID=b.ID  ");
		sb.append("		 AND c.id=d.RY_ID   ");
		sb.append("		  AND d.id = a.sws_id ");
		sb.append("		  AND f.SPID=e.ID ");
		sb.append("		  AND f.ISPASS='Y'");
		sb.append("		 AND g.ID=f.LCBZID ");
		sb.append("		 AND h.ID=g.LCID ");
		sb.append("		 AND h.LCLXID='12' ");
		sb.append("		 AND a.ZTDM= 3 ");
		sb.append("		 AND a.ID=e.SJID ");
		sb.append("		  AND d.ry_ID=? ");
		sb.append("		  ORDER BY a.ND");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 非执业人员详细信息
	 */
	public Map<String,Object> fzyryxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("	select @rownum:=@rownum+1 as 'key',");	
		sb.append("		a.zzdw,");
		sb.append("		h.XMING as xm,");
		sb.append("		d.MC AS cs,");
		sb.append("		b.MC as xb,");
		sb.append("		e.MC AS mz,");
		sb.append("		DATE_FORMAT(h.sri,'%Y-%m-%d') AS csrq, ");
		sb.append("		c.MC as xl,");
		sb.append("		h.sfzh,");
		sb.append("		f.MC AS zzmm,");
		sb.append("		h.txdz,");
		sb.append("		h.yddh,");
		sb.append("		h.yzbm,");
		sb.append("		g.mc as zw,");
		sb.append("		h.dhhm,");
		sb.append("		h.byyx,");
		sb.append("		a.zyzgzsbh, ");
		sb.append("		DATE_FORMAT(h.BYSJ,'%Y-%m-%d') AS bysj, ");
		sb.append("		a.fzyhybh,");
		sb.append("		DATE_FORMAT(a.RHSJ,'%Y-%m-%d') AS rhsj,");
		sb.append("		DATE_FORMAT(a.ZGZSQFRQ,'%Y-%m-%d') AS zgzsqfrq,");
		sb.append("		a.fzyzczsbh,");
		sb.append("		DATE_FORMAT(a.FZYZCRQ,'%Y-%m-%d') AS fzyzcrq");
		sb.append("		from zs_fzysws a,zs_ryjbxx h,dm_xb b,dm_xl c,dm_cs d,dm_mz e,dm_zzmm f,dm_zw g,(select @rownum:=0) zs_sws");
		sb.append("		where a.fzyzt_dm = 1");
		sb.append("		and h.XB_DM =b.ID");
		sb.append("		and h.XL_DM = c.ID");
		sb.append("		and h.cs_dm = d.ID");
		sb.append("		and h.mz_dm = e.ID");
		sb.append("		and h.zzmm_dm = f.ID");
		sb.append("		and a.zw_dm = g.ID");
		sb.append("		and a.RY_ID = h.ID");
		sb.append("		and a.RY_ID=?");
		sb.append("		order by a.ID");
		String sql = "SELECT @rownum:=@rownum+1 as 'key',a.qzny,a.xxxx,a.zmr FROM zs_fzyjl a,(select @rownum:=0) zs_sws,zs_fzysws b WHERE a.xxxx is not null and a.xxxx<>'' and a.FZY_ID = b.ID and b.RY_ID=? order by a.ID";
		List<Map<String, Object>> tl = this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
		Map<String,Object> ll =tl.get(0);
		ll.put("ryjl", this.jdbcTemplate.queryForList(sql,new Object[]{id}));
		return ll;
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 非执业人员变更记录
	 */
	public List<Map<String,Object>> fzyrybgjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key',a.mc as bgmc,a.jzhi,a.xzhi,date_format(a.gxsj,'%Y-%m-%d') as gxsj ");
		sb.append("		from zs_fzylsbgxxb a,zs_fzysws b,(select @rownum:=0) zs_sws");
		sb.append("		where b.RY_ID = ?");
		sb.append("		and a.FZY_ID = b.id");
		sb.append("		order by a.gxsj");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 非执业人员注销记录
	 */
	public List<Map<String,Object>> fzyryzxjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key', ");
		sb.append("		a.fzy_id,a.zxyy,date_format(a.bdrq,'%Y-%m-%d') as bdrq,a.lrr");
		sb.append("		FROM  zs_fzyzx a,zs_fzysws b,(select @rownum:=0) zs_sws");
		sb.append("		where b.RY_ID = ?");
		sb.append("		and a.FZY_ID = b.id");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 非执业人员转籍记录
	 */
	public List<Map<String,Object>> fzyryzjjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key', ");
		sb.append("		 a.fzy_id,a.zjyy,a.zjyyrq,date_format( a.tbrq,'%Y-%m-%d') AS tbrq ");
		sb.append("		 FROM zs_fzyswszj a ,zs_fzysws b,(select @rownum:=0) zs_sws");
		sb.append("		where b.RY_ID = ?");
		sb.append("		and a.FZY_ID = b.id");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 非执业人员转非记录
	 */
	public List<Map<String,Object>> fzyryzfjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("			select  @rownum:=@rownum+1 as 'key',a.fzysq ,a.xdwyj,DATE_FORMAT( a.tbrq,'%Y-%m-%d') AS bdrq,");
		sb.append("			DATE_FORMAT( a.SGLZXYJRQ,'%Y-%m-%d') as spsj,g.dwmc as ydwmc ");
		sb.append("		 from zs_zyswszfzy a,");
		sb.append("			 zs_fzysws f,zs_zysws b,");
		sb.append("			 zs_jg g,(select @rownum:=0) zs_sws");
		sb.append("			 where a.fzysws_id = f.id");
		sb.append("			 and a.zysws_id  = b.id");
		sb.append("			 and b.jg_id  = g.id");
		sb.append("			 and a.spzt_dm  = 2");
		sb.append("			 and f.RY_ID = ?");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 从业人员详细信息
	 */
	public Map<String,Object> cyryxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("	select @rownum:=@rownum+1 as 'key',c.dwmc,");	
		sb.append("		b.XMING as xm,");
		sb.append("		f.MC as cs,");
		sb.append("		d.MC as xb,");
		sb.append("		g.MC as mz,");
		sb.append("		date_format(b.SRI,'%Y-%m-%d') as sri,");
		sb.append("		e.MC as xl,");
		sb.append("		b.sfzh,");
		sb.append("		h.MC as zzmm,");
		sb.append("		b.txdz,");
		sb.append("		b.yddh,");
		sb.append("		b.yzbm,");
		sb.append("		i.mc as zw,");
		sb.append("		b.dhhm,");
		sb.append("		b.byyx,");
		sb.append("		a.xzsngzgw,");
		sb.append("		date_format(b.BYSJ,'%Y-%m-%d') as bysj,");
		sb.append("		date_format(a.LRSJ,'%Y-%m-%d') as lrsj,");
		sb.append("		date_format(a.SWDLYWKSSJ,'%Y-%m-%d') as swdlywkssj,");
		sb.append("		a.zgxlzymc,");
		sb.append("		date_format(a.ZGXLFZJGJSJ,'%Y-%m-%d') as zgxlfzjgjsj,");
		sb.append("		b.rydazt");
		sb.append("		from zs_cyry a,zs_ryjbxx b,zs_jg c,dm_xb d,dm_xl e,dm_cs f,dm_mz g,dm_zzmm h,dm_zw i");
		sb.append("		where a.RY_ID = b.ID");
		sb.append("		and a.JG_ID = c.ID");
		sb.append("		and b.xb_dm = d.ID");
		sb.append("		and b.XL_DM = e.ID");
		sb.append("		and b.CS_DM = f.ID");
		sb.append("		and b.MZ_DM = g.ID");
		sb.append("		and b.ZZMM_DM = h.ID");
		sb.append("		and a.ZW_DM = i.ID");
		sb.append("		and c.id <> '-2'");
		sb.append("		and b.ID = ?");
		String sql = "SELECT @rownum:=@rownum+1 as 'key',a.qzny,a.xxxx,a.zmr FROM zs_jl a,zs_ryjbxx c,(select @rownum:=0) zs_sws WHERE a.ry_id = c.id  and a.xxxx is not null and c.ID = ? order by a.ID";
		List<Map<String, Object>> tl = this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
		Map<String,Object> ll =tl.get(0);
		ll.put("ryjl", this.jdbcTemplate.queryForList(sql,new Object[]{id}));
		return ll;
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 从业人员变更记录
	 */
	public List<Map<String,Object>> cyrybgjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key',a.mc as bgmc,a.jzhi,a.xzhi,date_format(a.gxsj,'%Y-%m-%d') as gxsj ");
		sb.append("		from zs_cyrylsbgxxb a,zs_cyry b ,(select @rownum:=0) zs_sws");
		sb.append("		where a.cyry_id  = b.ID ");
		sb.append("		and b.ry_id = ?");
		sb.append("		order by a.gxsj");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * 事务所端执业查询
	 * @param jgid
	 * @return 
	 */
	public Map<String, Object> swszycx(int pn,int ps,int jgid,Map<String, Object> qury){
		final String url=Config.URL_PROJECT;
		Condition condition = new Condition();
		condition.add("a.xming", Condition.FUZZY, qury.get("xm"));
		condition.add("a.RYSPGCZT_DM", Condition.EQUAL, qury.get("ryzt"));
		condition.add("a.sfzh", Condition.FUZZY_LEFT, qury.get("sfzh"));
		condition.add("a.CS_DM", Condition.EQUAL, qury.get("cs"));
		condition.add("a.xb_DM", Condition.EQUAL, qury.get("xb"));
		condition.add("a.xl_dm", Condition.EQUAL, qury.get("xl"));
		StringBuffer sb = new StringBuffer();
		sb.append("		select SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 as 'key', b.id,b.xming,d.mc as xb,b.sfzh,a.zyzsbh,e.mc as cs,f.mc as xl,g.mc as zw,c.mc as ryzt, ");
		sb.append("		a.ryspgczt_dm from zs_zysws a,zs_ryjbxx b,dm_ryspgczt c,dm_xb d,dm_cs e,dm_xl f,dm_zw g,(select @rownum:=?) zs_ry  ");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		and  a.JG_ID=? and b.ID=a.ry_id and c.ID=a.RYSPGCZT_DM and ZYZT_DM in (1,2,3)");
		sb.append("		and b.XB_DM=d.ID and b.CS_DM=e.ID and f.ID=b.XL_DM and a.ZW_DM=g.ID");
		if(qury.containsKey("sorder")){
			Boolean asc = qury.get("sorder").toString().equals("ascend");
			switch (qury.get("sfield").toString()) {
			case "xm":
				if(asc){
					sb.append("		    order by convert( b.xming USING gbk) COLLATE gbk_chinese_ci ");
				}else{
					sb.append("		    order by convert( b.xming USING gbk) COLLATE gbk_chinese_ci desc");
				}
				break;
			case "xl":
				if(asc){
					sb.append("		    order by b.xl_dm ");
				}else{
					sb.append("		    order by b.xl_dm desc");
				}
				break;
			case "zw":
				if(asc){
					sb.append("		    order by a.ZW_DM");
				}else{
					sb.append("		    order by a.ZW_DM desc");
				}
				break;
			case "ryzt":
				if(asc){
					sb.append("		    order by a.RYSPGCZT_DM ");
				}else{
					sb.append("		    order by a.RYSPGCZT_DM desc");
				}
				break;
			}
		}
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add(jgid);
		params.add((pn-1)*ps);
		params.add(ps);
		List<Map<String,Object>> ls = this.jdbcTemplate.query(sb.toString(),params.toArray(),
				new RowMapper<Map<String,Object>>(){
			public Map<String,Object> mapRow(ResultSet rs, int arg1) throws SQLException{
				Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String,Object> link = new HashMap<>();
				String id = hashids.encode(rs.getLong("id"));
				link.put("herf_xxzl", url+"/ryxx/zyryxx/"+id);
				link.put("herf_bgjl", url+"/ryxx/zyrybgjl/"+id);
				link.put("herf_njjl", url+"/ryxx/zyrynjjl/"+id);
				map.put("key", rs.getObject("key"));
				map.put("xh", rs.getObject("key"));
				map.put("_links", link);
				map.put("xm", rs.getObject("xming"));
				map.put("xb", rs.getObject("xb"));
				map.put("cs", rs.getObject("cs"));
				map.put("sfzh", rs.getObject("sfzh"));
				map.put("zyzsbh", rs.getObject("zyzsbh"));
				map.put("zw", rs.getObject("zw"));
				map.put("ryzt", rs.getObject("ryzt"));
				map.put("xl", rs.getObject("xl"));
				map.put("ryztdm", rs.getObject("ryspgczt_dm"));
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
	//非执业备案通过列表
	public Map<String,Object> fzybatg(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("c.XMING", Condition.FUZZY, qury.get("XMING"));
		condition.add("b.ZYZGZSBH", Condition.EQUAL, qury.get("ZYZGZSBH"));
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT sql_calc_found_rows	 @rownum:=@rownum+1 'key', c.XMING,f.mc as XB,b.ZYZGZSBH,b.ZZDW,b.FZYZCZSBH,");
		sb.append(" DATE_FORMAT(d.TJSJ,'%Y-%m-%d') as TJSJ, ");
		sb.append(" DATE_FORMAT(e.SPSJ,'%Y-%m-%d') as SPSJ ");
		sb.append("		FROM zs_fzybasp a,zs_fzysws b,zs_ryjbxx c,zs_spzx d,zs_spxx e,dm_xb f,(SELECT @rownum:=?) tmp");
		sb.append("		 "+condition.getSql()+" ");
		sb.append("		and a.FZYSWS_ID=b.ID AND b.RY_ID=c.ID AND a.ID=d.SJID AND d.ID=e.SPID AND a.SPZT_DM=2 and e.ISPASS='Y' and b.FZYZT_DM=1");
		sb.append("		and f.ID=c.XB_DM order by e.SPSJ desc");
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
	//执业转非执业通过列表
	public Map<String,Object> fzyzzytg(int pn,int ps,Map<String,Object> qury) {
		Condition condition = new Condition();
		condition.add("c.XMING", Condition.FUZZY, qury.get("XMING"));
		condition.add("b.ZYZGZSBH", Condition.EQUAL, qury.get("ZYZGZSBH"));
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT sql_calc_found_rows @rownum:=@rownum+1 as 'key', c.XMING,d.MC AS XB,");
		sb.append("	b.ZYZGZSBH,b.FZYZCZSBH,b.ZZDW,date_format(a.TBRQ,'%Y-%m-%d') as TBRQ,date_format(a.SGLZXYJRQ,'%Y-%m-%d') as SGLZXYJRQ");
		sb.append("		FROM zs_zyswszfzy a,zs_fzysws b,zs_ryjbxx c,dm_xb d,(select @rownum:=?) zs_ry");
		sb.append("		 "+condition.getSql()+" ");
		sb.append("		and a.FZYSWS_ID=b.ID AND b.RY_ID=c.ID AND a.SPZT_DM='2' and d.ID=c.XB_DM");
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
	//非执业税务师转籍
	public Object fzyzjcx(String sfzh) {
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT c.XMING,'跨省转籍审批中' AS SPZT,'0' as JGLX ");
		sb.append("	FROM zs_fzyswszj a,zs_fzysws b,zs_ryjbxx c,dm_xb d ");
		sb.append("	WHERE a.FZY_ID=b.ID AND b.RY_ID=c.ID AND d.ID=c.XB_DM AND a.RYSPZT='0' and b.FZYZT_DM='1' and c.SFZH=? ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),sfzh);
		if(ls.size()!=0){
			return ls.get(0);
		}else{
			StringBuffer sb2 = new StringBuffer();
			sb2.append("		SELECT b.XMING,c.MC AS XB,a.ID as FID,b.CS_DM as CS,'1' as JGLX");
			sb2.append("			FROM zs_fzysws a,zs_ryjbxx b,dm_xb c");
			sb2.append("			WHERE a.RY_ID=b.ID AND b.XB_DM=c.ID AND a.FZYZT_DM='1' AND a.RYSPGCZT_DM='1'");
			sb2.append("			and b.SFZH=?");
			List<Map<String, Object>> ls2 = this.jdbcTemplate.queryForList(sb2.toString(),sfzh);
			if(ls2.size()!=0){
				return ls2.get(0);
			}
		}
		return false;
	}
}

