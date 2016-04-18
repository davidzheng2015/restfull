package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.DbToDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hashids.Hashids;
import org.springframework.stereotype.Repository;

@Repository
public class RyglDao extends BaseDao{
	/**
	 * 人员基本表(zs_ry)插入数据sql
	 */
	public void ryqy() throws Exception{
//		char q = '2';
//		StringBuffer sb = new StringBuffer();
//		sb.append("		 select a.id,a.xming as xm ,DATE_FORMAT(a.sri,'%Y-%m-%d') AS csrq,");
//		sb.append("		 b.MC as xb, c.MC as xl, a.ZYZGZSBH, a.zzdw,");
//		sb.append("		 a.SRI,a.SFZH,a.XB_DM,a.CS_DM,a.MZ_DM,a.XL_DM,a.RYZT_DM,");
//		sb.append("		    a.ryzt_DM");
//		sb.append("		 from zs_fzysws a,dm_xb b,dm_xl c ");
//		sb.append("		 where a.ryzt_dm = 1");
//		sb.append("		 and a.XB_DM =b.ID");
//		sb.append("		 and a.XL_DM = c.ID");
//		sb.append("		 order by a.ID");
//		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		
		/**
		 * 从业人员查询
		 */
		char q = '3';
		StringBuffer sb = new StringBuffer();
		sb.append("		select b.id,b.xming as xm,b.XB_DM,b.SRI,b.SFZH,b.CS_DM,b.MZ_DM,b.XL_DM,b.RYZT_DM,d.MC as xb,e.MC as xl,a.XZSNGZGW,a.ZGXLZYMC,c.DWMC");
		sb.append("		from zs_cyry a,zs_ryjbxx b,zs_jg c,dm_xb d,dm_xl e ");
		sb.append("		where a.RY_ID = b.ID");
		sb.append("		and a.JG_ID = c.ID");
		sb.append("		and b.xb_dm = d.ID");
		sb.append("		and b.XL_DM = e.ID");
		sb.append("		and c.id <>'-2' and a.cyryzt_dm='1'");//从业人员状态代码
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
//		
		/**
		 * 执业人员查询
		 */
//		char q = '1';
//		StringBuffer sb = new StringBuffer();
//		sb.append("		select c.id ,c.xming as xm,d.MC as xb,c.XB_DM, c.SRI,c.DHHM, c.SFZH,c.CS_DM,c.MZ_DM,c.XL_DM,c.RYZT_DM,e.MC as xl, a.ZYZGZSBH,a.ZYZSBH,");
//		sb.append("		case a.CZR_DM when 1 then \"是\"  when 2 then \"否\" ELSE null end as CZR,");
//		sb.append("	case a.FQR_DM when 1 then \"是\"  when 2 then \"否\" ELSE null end as FQR,");
//		sb.append("		b.DWMC");
//		sb.append("		from ");
//		sb.append("		zs_zysws a,zs_jg b ,");
//		sb.append("		zs_ryjbxx c ,dm_xb d,dm_xl e");
//		sb.append("		where");
//		sb.append("		b.id=a.jg_id");
//		sb.append("		 and a.ry_id = c.ID ");
//		sb.append("		and a.ZYZT_DM = '1'");
//		sb.append("		and d.ID = c.XB_DM");
//		sb.append("		and e.ID = c.XL_DM");
//		sb.append("		and a.JG_ID <>'-2'");
//		sb.append("		order by a.id");
//		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		
		DbToDb bb = new DbToDb();
		bb.insertnewRYDB(ls,q);
	}
	
	public Map<String,Object> rycx(String z,Map<String,Object> qury) throws Exception{
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
		if(qury.get("dwmc")!=null){
			sb.append("		    and a.dwmc like '%"+qury.get("dwmc")+"%'");
		}
		if(qury.get("zsbh")!=null){
			sb.append("		    and a.jgzch =  "+qury.get("zsbh"));
		}
		if(qury.get("zczj")!=null){
			sb.append("		    and a.zczj>="+qury.get("zczj"));
		}
		if(qury.get("zczj2")!=null){
			sb.append("		    and a.zczj<="+qury.get("zczj2"));
		}
		if(qury.get("cs")!=null){
			sb.append("		   and a.CS_DM="+qury.get("cs"));
		}
		if(qury.get("swsxz")!=null){
			sb.append("		    and a.JGXZ_DM="+qury.get("swsxz"));
		}
		if(qury.get("zrs")!=null){
			sb.append("		    and d.zrs>="+qury.get("zrs"));
		}
		if(qury.get("zrs2")!=null){
			sb.append("		    and d.zrs<="+qury.get("zrs2"));
		}
		if(qury.get("clsj")!=null){
			sb.append("		    and DATE_FORMAT(a.swszsclsj,'%Y-%m-%d') >='"+qury.get("clsj")+"'");
		}
		if(qury.get("clsj2")!=null){
			sb.append("		    and DATE_FORMAT(a.swszsclsj,'%Y-%m-%d') <='"+qury.get("clsj2")+"'");
		}
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
			Hashids hashids = new Hashids("project-zs",6);
			String id = hashids.encode(Integer.parseInt(rec.get("id").toString()));
			link.put("herf_sws", z+"swsxx/"+id);
			link.put("herf_zyry", z+"zyryxx/"+id);
			link.put("herf_cyry", z+"cyryxx/"+id);
			link.put("herf_czrylb", z+"czrylb/"+id);
			link.put("herf_swsbgxx", z+"swsbgxx/"+id);
			link.put("herf_njjl", z+"njjl/"+id);
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
		
		return ob;}
}
