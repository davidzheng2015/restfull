package gov.gdgs.zs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class SWSDao extends BaseDao{
//	@Resource(name ="jdbcTemplate")
//	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> testJDBC (){
		String sql = "select * from zs_jg";
		return this.jdbcTemplate.queryForList(sql);
		
	}
	/**
	 * 
	 * @param pn
	 * @param ps
	 * @return 事务所分页查询
	 */
	public Map<String,Object> swscx(String z,Map<String,Object> qury){
		int pn = Integer.parseInt(qury.get("pn").toString());
		int ps = Integer.parseInt(qury.get("ps").toString());
		StringBuffer sb = new StringBuffer();
		sb.append("	select SQL_CALC_FOUND_ROWS ");
		sb.append("		     @rownum:=@rownum+1 as 'key',@rownum AS xh,a.id,");
		sb.append("	    a.dwmc,");
		sb.append("		    a.zczj,");
		sb.append("		    a.fddbr,");
		sb.append("		    a.jgzch as zsbh,");
		sb.append("		    b.mc as swsxz,");
		sb.append("		    c.mc as cs,");
		sb.append("		    d.zrs,");
		sb.append("		    d.zyrs,");
		sb.append("		    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		    	from");
		sb.append("		    zs_jg a,");
		sb.append("		    dm_jgxz b,");
		sb.append("	    dm_cs c,");
		sb.append("	    zs_jgnj d,");
		sb.append("	    (select max(nd) as nd,zsjg_id from zs_jgnj group by zsjg_id) as v,(select @rownum:=?) zs_jg");
		sb.append("		where");
		sb.append("		    a.jgxz_dm = b.id");
		sb.append("		    and a.cs_dm = c.id");
		sb.append("			and a.id = d.zsjg_id");
		sb.append("			and v.zsjg_id = d.zsjg_id");
		sb.append("		    and d.nd = v.nd ");
		Boolean asc = qury.get("sorder").toString().equals("ascend");
		switch (qury.get("sfield").toString()) {
		case "dwmc":
			if(asc){
				sb.append("		    order by convert( a.dwmc USING gbk) COLLATE gbk_chinese_ci ");
			}else{
				sb.append("		    order by convert( a.dwmc USING gbk) COLLATE gbk_chinese_ci desc");
			}
			break;
		case "zczj":
			if(asc){
				sb.append("		    order by a.zczj ");
			}else{
				sb.append("		    order by a.zczj desc");
			}
			break;
		case "fddbr":
			if(asc){
				sb.append("		    order by convert( a.fddbr USING gbk) COLLATE gbk_chinese_ci ");
			}else{
				sb.append("		    order by convert( a.fddbr USING gbk) COLLATE gbk_chinese_ci desc");
			}
			break;
		case "clsj":
			if(asc){
				sb.append("		    order by a.swszsclsj ");
			}else{
				sb.append("		    order by a.swszsclsj desc");
			}
			break;

		}
		sb.append("		    LIMIT ?, ? ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),new Object[]{(pn-1)*ps,(pn-1)*ps,ps});
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : ls){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_sws", z+"swsxx/"+rec.get("id").toString());
			link.put("herf_zyry", z+"zyryxx/"+rec.get("id").toString());
			link.put("herf_cyry", z+"cyryxx/"+rec.get("id").toString());
			link.put("herf_czrylb", z+"czrylb/"+rec.get("id").toString());
			link.put("herf_swsbgxx", z+"swsbgxx/"+rec.get("id").toString());
			link.put("herf_njjl", z+"njjl/"+rec.get("id").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", fl);
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
	 * @return 事务所查询
	 */
	public Map<String,Object> swscx(String z){
		StringBuffer sb = new StringBuffer();
		sb.append("	select ");
		sb.append("		     @rownum:=@rownum+1 as 'key',@rownum AS xh,a.id,");
		sb.append("	    a.dwmc,");
		sb.append("		    a.zczj,");
		sb.append("		    a.fddbr,");
		sb.append("		    a.jgzch as zsbh,");
		sb.append("		    b.mc as swsxz,");
		sb.append("		    c.mc as cs,");
		sb.append("		    d.zrs,");
		sb.append("		    d.zyrs,");
		sb.append("		    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		    	from");
		sb.append("		    zs_jg a,");
		sb.append("		    dm_jgxz b,");
		sb.append("	    dm_cs c,");
		sb.append("	    zs_jgnj d,");
		sb.append("	    (select max(nd) as nd,zsjg_id from zs_jgnj group by zsjg_id) as v,(select @rownum:=0) zs_jg");
		sb.append("		where");
		sb.append("		    a.jgxz_dm = b.id");
		sb.append("		    and a.cs_dm = c.id");
		sb.append("			and a.id = d.zsjg_id");
		sb.append("			and v.zsjg_id = d.zsjg_id");
		sb.append("		    and d.nd = v.nd ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : ls){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_sws", z+"swsxx/"+rec.get("id").toString());
			link.put("herf_zyry", z+"zyryxx/"+rec.get("id").toString());
			link.put("herf_cyry", z+"cyryxx/"+rec.get("id").toString());
			link.put("herf_czrylb", z+"czrylb/"+rec.get("id").toString());
			link.put("herf_swsbgxx", z+"swsbgxx/"+rec.get("id").toString());
			link.put("herf_njjl", z+"njjl/"+rec.get("id").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", fl);
		ob.put("totalsize", ls.size());
		System.out.println("拿数据了~~~~~~~~~~~~");
		return ob;
	}
	/**
	 * 
	 * 
	 * @param id
	 * @return 事务所详细信息
	 */
	public Map<String,Object> swsxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("select 	");	 
		sb.append("		@rownum:=@rownum+1 as 'key',a.id,	 ");
		sb.append("		a.dwmc,");
		sb.append("		c.mc as cs,	");
		sb.append("		a.fddbr,	");
		sb.append("		a.dzhi,");
		sb.append("		a.sjlzxsbwh,");
		sb.append("		a.zcdz,");
		sb.append("		date_format(a.sglzxsbsj,'%Y-%m-%d') as sglzxsbsj,");
		sb.append("		date_format(a.zjpzsj,'%Y-%m-%d') as zjpzsj,");
		sb.append("		a.yzbm,");
		sb.append("		a.zjpzwh,");
		sb.append("		a.czhen,");
		sb.append("		a.dhua,");
		sb.append("		a.szyx,");
		sb.append("		a.txyxming,");
		sb.append("		a.xtyyx,");
		sb.append("		a.xtyphone,");
		sb.append("		a.jgzch as zsbh,	");
		sb.append("		a.zczj,");
		sb.append("		a.jyfw,");
		sb.append("		d.zrs,");
		sb.append("		b.mc as swsxz,	");	 
		sb.append("		a.szphone,");
		sb.append("		a.gsyhmcbh,");
		sb.append("		a.dzyj,");
		sb.append("		a.yhdw,");
		sb.append("		date_format(a.yhsj,'%Y-%m-%d') as yhsj,");
		sb.append("		a.gzbh,");
		sb.append("		a.gzdw,");
		sb.append("		a.gzry,");
		sb.append("		date_format(a.gzsj,'%Y-%m-%d') as gzsj,");
		sb.append("		a.yzbh,");
		sb.append("		a.yzdw,");
		sb.append("		a.yzry,");
		sb.append("		date_format(a.yzsj,'%Y-%m-%d') as yzsj,");
		sb.append("		a.tthybh,");
		sb.append("		date_format(a.rhsj,'%Y-%m-%d') as rhsj,");
		sb.append("		a.khh,");
		sb.append("		a.khhzh,");
		sb.append("		a.fj,");
		sb.append("		a.swdjhm,");
		sb.append("		a.jbqk,");
		sb.append("		a.glzd,");
		sb.append("		a.gddh,");
		sb.append("		a.bgcszczm		"); 
		sb.append("		from		");
		sb.append("		 zs_jg a,	");	 
		sb.append("		dm_jgxz b,	 ");
		sb.append("		dm_cs c,	 zs_jgnj d,	 ");
		sb.append("		(select max(nd) as nd,zsjg_id from zs_jgnj group by zsjg_id) as v,(select @rownum:=0) zs_jg");
		sb.append("		where		 ");
		sb.append("		a.jgxz_dm = b.id ");
		sb.append("		and a.cs_dm = c.id ");
		sb.append("		and a.id = d.zsjg_id ");
		sb.append("		and v.zsjg_id = d.zsjg_id ");
		sb.append("		and d.nd = v.nd");
		sb.append("		and a.id = ?");
		String sql = "select @rownum:=@rownum+1 as 'key',b.* from zs_jg a,zs_nbjgsz b where a.id = b.jg_id and a.id = ?";
		Map<String,Object> tl = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
		tl.put("nbjgsz", this.jdbcTemplate.queryForList(sql,new Object[]{id}));
		return tl;
	}
	/**
	 * 
	 * @param id
	 * @return 执业人员信息
	 */
	public List<Map<String,Object>> zyryxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key',@rownum AS xh,c.xming, ");
		sb.append("		case a.czr_dm when 1 then \"是\"  when 2 then \"否\" else null end as czr,");
		sb.append("		case a.fqr_dm when 1 then \"是\"  when 2 then \"否\" else null end as fqr,");
		sb.append("	 case a.sz_dm when 1 then \"是\"  when 2 then \"否\" else null end as sz ");
		sb.append("		from ");
		sb.append("	zs_zysws a,zs_jg b ,");
		sb.append("		zs_ryjbxx c,(select @rownum:=0) zs_jg ");
		sb.append("		where");
		sb.append("		 b.id =?");
		sb.append("		and b.id=a.jg_id");
		sb.append("		 and a.ry_id = c.id");
		sb.append("		 and a.zyzt_dm = '1'");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * @param id
	 * @return 从业人员信息
	 */
	public List<Map<String,Object>> cyryxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key', c.xming, ");
		sb.append("		 d.mc as xl, ");
		sb.append("		c.sfzh,");
		sb.append("		e.mc as zc,");
		sb.append("		a.id");
		sb.append("		from zs_cyry a,");
		sb.append("		zs_jg b");
		sb.append("		,zs_ryjbxx c,");
		sb.append("		dm_xl d,");
		sb.append("		dm_zw e,(select @rownum:=0) zs_jg");
		sb.append("		where b.id =?");
		sb.append("		and b.id=a.jg_id ");
		sb.append("		and a.ry_id = c.id ");
		sb.append("		and c.xl_dm = d.id ");
		sb.append("			and a.zw_dm = e.id");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * @param id
	 * @return 出资人列表
	 */
	public List<Map<String,Object>> czrylb(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select @rownum:=@rownum+1 as 'key',c.xming,");
		sb.append("		a.cze,");
		sb.append("		b.dwmc,");
		sb.append("		c.sfzh");
		sb.append("		from ");
		sb.append("		zs_zysws a,zs_jg b ,");
		sb.append("			zs_ryjbxx c,(select @rownum:=0) zs_jg ");
		sb.append("			where");
		sb.append("			 b.id =? ");
		sb.append("		and b.id=a.jg_id");
		sb.append("		and a.czr_dm = 1");
		sb.append("		 and a.ry_id = c.id");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * @param id
	 * @return 事务所变更信息
	 */
	public List<Map<String,Object>> swsbgxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from (select @rownum:=@rownum+1 as 'key',date_format(b.gxsj,'%Y-%m-%d') as xgxsj,b.* from zs_jg a,zs_jglsbgxxb b,(select @rownum:=0) zs_jg where a.id = ? and b.jgb_id = a.id ");
		sb.append("				union ");
		sb.append("				select @rownum:=@rownum+1 as 'key',date_format(b.gxsj,'%Y-%m-%d') as xgxsj,b.* from zs_jg a,zs_jgbgxxb b,zs_jgbgspb c where a.id = ? and b.jgbgspb_id = c.id and c.jg_id = a.id) as g where g.jgb_id = ? ");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id,id,id});
	}
	/**
	 * 
	 * @param id
	 * @return 年检记录
	 */
	public List<Map<String,Object>> njjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("	select 		 ");
		sb.append("		@rownum:=@rownum+1 as 'key',a.id,");
		sb.append("		f.nd,	 ");
		sb.append("		a.dwmc,");
		sb.append("		c.spyj,");
		sb.append("		case f.ztdm when 1 then \"保存\"  when 2 then \"自检\" when 0 then \"退回\" when 3 then \"年检\" else null end as njzt,	");	 
		sb.append("		date_format( c.spsj,'%Y-%m-%d') as spsj ");
		sb.append("		from		");
		sb.append("		 zs_jg a,");
		sb.append("		 zs_spzx b,");
		sb.append("		 zs_spxx c,");
		sb.append("		 zs_splcbz d,");
		sb.append("		 zs_splc e,");
		sb.append("		zs_jgnj f,(select @rownum:=0) zs_jg");
		sb.append("		where		 ");
		sb.append("		a.id = ?");
		sb.append("			and b.zsjg_id = a.id ");
		sb.append("		and c.spid = b.id ");
		sb.append("		and c.ispass = 'y' ");
		sb.append("		 and d.id = c.lcbzid ");
		sb.append("		 and e.id = d.lcid");
		sb.append("		  and e.lclxid ='11'");
		sb.append("		and f.ztdm = 3");
		sb.append("		and f.id = b.sjid");
		sb.append("		and a.id = f.zsjg_id");
		sb.append("		order by f.nd ");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}

}
