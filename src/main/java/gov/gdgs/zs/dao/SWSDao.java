package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SWSDao {
	@Resource(name ="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
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
	public Map<String,Object> swscx(int pn,int ps){
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT ");
		sb.append("		    a.ID,");
		sb.append("	    a.dwmc,");
		sb.append("		    a.ZCZJ,");
		sb.append("		    a.fddbr,");
		sb.append("		    a.jgzch as zsbh,");
		sb.append("		    b.mc as swsxz,");
		sb.append("		    c.mc as cs,");
		sb.append("		    d.zrs,");
		sb.append("		    d.zyrs,");
		sb.append("		    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		    	FROM");
		sb.append("		    zs_jg a,");
		sb.append("		    dm_jgxz b,");
		sb.append("	    dm_cs c,");
		sb.append("	    zs_jgnj d,");
		sb.append("	    (select max(nd) as nd,ZSJG_ID from zs_jgnj group by ZSJG_ID) as v");
		sb.append("		WHERE");
		sb.append("		    a.JGXZ_DM = b.ID");
		sb.append("		    AND a.CS_DM = c.ID");
		sb.append("			AND a.ID = d.ZSJG_ID");
		sb.append("			AND v.ZSJG_ID = d.ZSJG_ID");
		sb.append("		    and d.nd = v.nd ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		Pager<Map<String, Object>> pager = Pager.create(ls, ps);
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : pager.getPagedList(pn)){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_sws", "http://localhost:8080/restfull/api/swsxx/"+rec.get("ID").toString());
			link.put("herf_zyry", "http://localhost:8080/restfull/api/zyryxx/"+rec.get("ID").toString());
			link.put("herf_cyry", "http://localhost:8080/restfull/api/cyryxx/"+rec.get("ID").toString());
			link.put("herf_czrylb", "http://localhost:8080/restfull/api/czrylb/"+rec.get("ID").toString());
			link.put("herf_swsbgxx", "http://localhost:8080/restfull/api/swsbgxx/"+rec.get("ID").toString());
			link.put("herf_njjl", "http://localhost:8080/restfull/api/njjl/"+rec.get("ID").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", fl);
		ob.put("totalsize", ls.size());
		ob.put("pagesize", (ls.size() + ps - 1) / ps);
		return ob;
	}
	/**
	 * 
	 * @return 事务所查询
	 */
	public Map<String,Object> swscx(){
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT ");
		sb.append("		    a.ID,");
		sb.append("	    a.dwmc,");
		sb.append("		    a.ZCZJ,");
		sb.append("		    a.fddbr,");
		sb.append("		    a.jgzch as zsbh,");
		sb.append("		    b.mc as swsxz,");
		sb.append("		    c.mc as cs,");
		sb.append("		    d.zrs,");
		sb.append("		    d.zyrs,");
		sb.append("		    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		    	FROM");
		sb.append("		    zs_jg a,");
		sb.append("		    dm_jgxz b,");
		sb.append("	    dm_cs c,");
		sb.append("	    zs_jgnj d,");
		sb.append("	    (select max(nd) as nd,ZSJG_ID from zs_jgnj group by ZSJG_ID) as v");
		sb.append("		WHERE");
		sb.append("		    a.JGXZ_DM = b.ID");
		sb.append("		    AND a.CS_DM = c.ID");
		sb.append("			AND a.ID = d.ZSJG_ID");
		sb.append("			AND v.ZSJG_ID = d.ZSJG_ID");
		sb.append("		    and d.nd = v.nd ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : ls){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_sws", "http://localhost:8080/restfull/api/swsxx/"+rec.get("ID").toString());
			link.put("herf_zyry", "http://localhost:8080/restfull/api/zyryxx/"+rec.get("ID").toString());
			link.put("herf_cyry", "http://localhost:8080/restfull/api/cyryxx/"+rec.get("ID").toString());
			link.put("herf_czrylb", "http://localhost:8080/restfull/api/czrylb/"+rec.get("ID").toString());
			link.put("herf_swsbgxx", "http://localhost:8080/restfull/api/swsbgxx/"+rec.get("ID").toString());
			link.put("herf_njjl", "http://localhost:8080/restfull/api/njjl/"+rec.get("ID").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", fl);
		return ob;
	}
	/**
	 * 
	 * @param id
	 * @return 事务所详细信息
	 */
	public Map<String,Object> swsxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT 	");	 
		sb.append("		a.ID,	 ");
		sb.append("		a.dwmc,");
		sb.append("		c.mc AS cs,	");
		sb.append("		a.fddbr,	");
		sb.append("		a.DZHI,");
		sb.append("		a.SJLZXSBWH,");
		sb.append("		a.ZCDZ,");
		sb.append("		a.SGLZXSBSJ,");
		sb.append("		a.ZJPZSJ,");
		sb.append("		a.YZBM,");
		sb.append("		a.ZJPZWH,");
		sb.append("		a.CZHEN,");
		sb.append("		a.DHUA,");
		sb.append("		a.SZYX,");
		sb.append("		a.TXYXMING,");
		sb.append("		a.XTYYX,");
		sb.append("		a.XTYPHONE,");
		sb.append("		a.jgzch AS zsbh,	");
		sb.append("		a.ZCZJ,");
		sb.append("		a.JYFW,");
		sb.append("		d.zrs,");
		sb.append("		b.mc AS swsxz,	");	 
		sb.append("		a.SZPHONE,");
		sb.append("		a.GSYHMCBH,");
		sb.append("		a.DZYJ,");
		sb.append("		a.YHDW,");
		sb.append("		a.YHSJ,");
		sb.append("		a.GZBH,");
		sb.append("		a.GZDW,");
		sb.append("		a.GZRY,");
		sb.append("		a.GZSJ,");
		sb.append("		a.YZBH,");
		sb.append("		a.YZDW,");
		sb.append("		a.YZRY,");
		sb.append("		a.YZSJ,");
		sb.append("		a.TTHYBH,");
		sb.append("		a.RHSJ,");
		sb.append("		a.KHH,");
		sb.append("		a.KHHZH,");
		sb.append("		a.FJ,");
		sb.append("		a.SWDJHM,");
		sb.append("		a.JBQK,");
		sb.append("		a.GLZD,");
		sb.append("		a.GDDH,");
		sb.append("		a.BGCSZCZM		"); 
		sb.append("		FROM		");
		sb.append("		 zs_jg a,	");	 
		sb.append("		dm_jgxz b,	 ");
		sb.append("		dm_cs c,	 zs_jgnj d,	 ");
		sb.append("		(SELECT MAX(nd) AS nd,ZSJG_ID FROM zs_jgnj GROUP BY ZSJG_ID) AS v");
		sb.append("		WHERE		 ");
		sb.append("		a.JGXZ_DM = b.ID ");
		sb.append("		AND a.CS_DM = c.ID ");
		sb.append("		AND a.ID = d.ZSJG_ID ");
		sb.append("		AND v.ZSJG_ID = d.ZSJG_ID ");
		sb.append("		AND d.nd = v.nd");
		sb.append("		and a.ID = ?");
		String sql = "SELECT b.* FROM zs_jg a,zs_nbjgsz b where a.ID = b.jg_id and a.id = ?";
		Map<String,Object> tl = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
		tl.put("NBJGSZ", this.jdbcTemplate.queryForList(sql,new Object[]{id}));
		return tl;
	}
	/**
	 * 
	 * @param id
	 * @return 执业人员信息
	 */
	public List<Map<String,Object>> zyryxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select c.xming, ");
		sb.append("		case a.CZR_DM when 1 then \"是\"  when 2 then \"否\" ELSE null end as CZR,");
		sb.append("		case a.FQR_DM when 1 then \"是\"  when 2 then \"否\" ELSE null end as FQR,");
		sb.append("	 case a.SZ_DM when 1 then \"是\"  when 2 then \"否\" ELSE null end as SZ ");
		sb.append("		from ");
		sb.append("	zs_zysws a,zs_jg b ,");
		sb.append("		zs_ryjbxx c ");
		sb.append("		where");
		sb.append("		 b.id =?");
		sb.append("		and b.id=a.jg_id");
		sb.append("		 and a.ry_id = c.ID");
		sb.append("		 and a.ZYZT_DM = '1'");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * @param id
	 * @return 从业人员信息
	 */
	public List<Map<String,Object>> cyryxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT c.xming,");
		sb.append("		 d.mc, ");
		sb.append("		c.SFZH,");
		sb.append("		e.MC,");
		sb.append("		a.ID");
		sb.append("		FROM zs_cyry a,");
		sb.append("		zs_jg b");
		sb.append("		,zs_ryjbxx c,");
		sb.append("		dm_xl d,");
		sb.append("		dm_zw e");
		sb.append("		WHERE b.id =?");
		sb.append("		AND b.id=a.jg_id ");
		sb.append("		AND a.ry_id = c.ID ");
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
		sb.append("		select c.xming,");
		sb.append("		a.CZE,");
		sb.append("		b.DWMC,");
		sb.append("		c.SFZH");
		sb.append("		from ");
		sb.append("		zs_zysws a,zs_jg b ,");
		sb.append("			zs_ryjbxx c ");
		sb.append("			where");
		sb.append("			 b.id =? ");
		sb.append("		and b.id=a.jg_id");
		sb.append("		and a.CZR_DM = 1");
		sb.append("		 and a.ry_id = c.ID");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}
	/**
	 * 
	 * @param id
	 * @return 事务所变更信息
	 */
	public List<Map<String,Object>> swsbgxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from (select b.* from zs_jg a,zs_jglsbgxxb b where a.id = ? and b.JGB_ID = a.ID ");
		sb.append("				union ");
		sb.append("				select b.* from zs_jg a,zs_jgbgxxb b,zs_jgbgspb c where a.id = ? and b.JGBGSPB_ID = c.ID and c.JG_ID = a.ID) as g where g.jgb_id = ? ");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id,id,id});
	}
	/**
	 * 
	 * @param id
	 * @return 年检记录
	 */
	public List<Map<String,Object>> njjl(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT 		 ");
		sb.append("		a.ID,");
		sb.append("		f.ND,	 ");
		sb.append("		a.dwmc,");
		sb.append("		c.SPYJ,");
		sb.append("		case f.ZTDM when 1 then \"保存\"  when 2 then \"自检\" when 0 then \"退回\" when 3 then \"年检\" ELSE null end as njzt,	");	 
		sb.append("		DATE_FORMAT( c.SPSJ,'%Y-%m-%d') AS spsj ");
		sb.append("		FROM		");
		sb.append("		 zs_jg a,");
		sb.append("		 zs_spzx b,");
		sb.append("		 zs_spxx c,");
		sb.append("		 zs_splcbz d,");
		sb.append("		 zs_splc e,");
		sb.append("		zs_jgnj f");
		sb.append("		WHERE		 ");
		sb.append("		a.ID = ?");
		sb.append("			and b.zsjg_id = a.ID ");
		sb.append("		and c.SPID = b.ID ");
		sb.append("		and c.ISPASS = 'Y' ");
		sb.append("		 and d.ID = c.LCBZID ");
		sb.append("		 and e.ID = d.LCID");
		sb.append("		  and e.LCLXID ='11'");
		sb.append("		and f.ZTDM = 3");
		sb.append("		and f.ID = b.SJID");
		sb.append("		AND a.ID = f.ZSJG_ID");
		sb.append("		order by f.ND ");
		return this.jdbcTemplate.queryForList(sb.toString(),new Object[]{id});
	}

}
