package gov.gdgs.zs.dao;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hashids.Hashids;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class ClientsdsbDao extends BaseJdbcDao{

	/*
	 * 行业人员统计表
	 */
	public Map<String, Object> getHyryqktjb(int page, int pageSize,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM   ( select a.id,a.nd,b.DWMC,a.RYZS_RY_ZJ,a.ZYSWS_RY_ZJ,a.QTCYRY_RY_ZJ,");	
		sb.append(" DATE_FORMAT(a.SBRQ,'%Y-%m-%d') as SBRQ,");	
		sb.append(" case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when 3 then '退回' else null end as ZTBJ");		
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_sdsb_hyryqktj a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where 1=1;
		sb.append(" AND a.JG_ID=b.ID  and a.JG_ID=? ORDER BY a.nd DESC ) AS t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
		params.add(Jgid);
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
	
	public Map<String, Object> getHyryqktjbById(String id) {
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_hyryqktj a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	
	public String AddHyryqktjb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");		
		obj.put("id", uuid);
		
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_hyryqktj ");	
		sb.append("  ( id,jg_id,use_id,nd,sbrq,ztbj,zbr,sz,ryzs_ry_zj,ryzs_ry_nv,ryzs_xl_yjs,ryzs_xl_bk,ryzs_xl_dz,ryzs_xl_zz,ryzs_nl_35,");
		sb.append(" ryzs_nl_50,ryzs_nl_60l,ryzs_nl_60u,ryzs_zzmm_gcd,ryzs_zzmm_mzp,bz1,bz2,bz3,bz4,bz5,bz6,bz7,bz8,zysws_ry_zj,zysws_ry_nv,");
		sb.append(" zysws_xl_yjs,zysws_xl_bk,zysws_xl_dz,zysws_xl_zz,zysws_nl_35,zysws_nl_50,zysws_nl_60l,zysws_nl_60u,zysws_zzmm_gcd,");
		sb.append(" zysws_zzmm_mzp,bz9,bz10,bz11,bz12,bz13,bz14,bz15,bz16,hhczr_ry_zj,hhczr_ry_nv,hhczr_xl_yjs,hhczr_xl_bk,hhczr_xl_dz,hhczr_xl_zz,");
		sb.append(" hhczr_nl_35,hhczr_nl_50,hhczr_nl_60l,hhczr_nl_60u,hhczr_zzmm_gcd,hhczr_zzmm_mzp,bz17,bz18,bz19,bz20,bz21,bz22,bz23,bz24,");
		sb.append(" qtcyry_ry_zj,qtcyry_ry_nv,qtcyry_xl_yjs,qtcyry_xl_bk,qtcyry_xl_dz,qtcyry_xl_zz,qtcyry_nl_35,qtcyry_nl_50,qtcyry_nl_60l,");
		sb.append(" qtcyry_nl_60u,qtcyry_zzmm_gcd,qtcyry_zzmm_mzp,bz25,bz26,bz27,bz28,bz29,bz30,bz31,bz32,fzyzss_ry_zj,fzyzss_ry_nv,fzyzss_xl_yjs,");
		sb.append(" fzyzss_xl_bk,fzyzss_xl_dz,fzyzss_xl_zz,fzyzss_nl_35,fzyzss_nl_50,fzyzss_nl_60l,fzyzss_nl_60u,fzyzss_zzmm_gcd,fzyzss_zzmm_mzp,");
		sb.append(" bz33,bz34,bz35,bz36,bz37,bz38,bz39,bz40,zckjs_ry_zj,zckjs_ry_nv,zckjs_xl_yjs,zckjs_xl_bk,zckjs_xl_dz,zckjs_xl_zz,zckjs_nl_35,");
		sb.append(" zckjs_nl_50,zckjs_nl_60l,zckjs_nl_60u,zckjs_zzmm_gcd,zckjs_zzmm_mzp,bz41,bz42,bz43,bz44,bz45,bz46,bz47,bz48,zcpgs_ry_zj,");
		sb.append(" zcpgs_ry_nv,zcpgs_xl_yjs,zcpgs_xl_bk,zcpgs_xl_dz,zcpgs_xl_zz,zcpgs_nl_35,zcpgs_nl_50,zcpgs_nl_60l,zcpgs_nl_60u,zcpgs_zzmm_gcd,");
		sb.append(" zcpgs_zzmm_mzp,bz49,bz50,bz51,bz52,bz53,bz54,bz55,bz56,ls_ry_zj,ls_ry_nv,ls_xl_yjs,ls_xl_bk,ls_xl_dz,ls_xl_zz,ls_nl_35,ls_nl_50,");
		sb.append(" ls_nl_60l,ls_nl_60u,ls_zzmm_gcd,ls_zzmm_mzp,bz57,bz58,bz59,bz60,bz61,bz62,bz63,bz64)");
		sb.append("values ( :id,:jg_id,:use_id,:nd,sysdate(),:ztbj,:zbr,:sz,:ryzs_ry_zj,:ryzs_ry_nv,:ryzs_xl_yjs,:ryzs_xl_bk,:ryzs_xl_dz,:ryzs_xl_zz,:ryzs_nl_35,");
		sb.append(" :ryzs_nl_50,:ryzs_nl_60l,:ryzs_nl_60u,:ryzs_zzmm_gcd,:ryzs_zzmm_mzp,:bz1,:bz2,:bz3,:bz4,:bz5,:bz6,:bz7,:bz8,:zysws_ry_zj,:zysws_ry_nv,");
		sb.append(" :zysws_xl_yjs,:zysws_xl_bk,:zysws_xl_dz,:zysws_xl_zz,:zysws_nl_35,:zysws_nl_50,:zysws_nl_60l,:zysws_nl_60u,:zysws_zzmm_gcd,");
		sb.append(" :zysws_zzmm_mzp,:bz9,:bz10,:bz11,:bz12,:bz13,:bz14,:bz15,:bz16,:hhczr_ry_zj,:hhczr_ry_nv,:hhczr_xl_yjs,:hhczr_xl_bk,:hhczr_xl_dz,:hhczr_xl_zz,");
		sb.append(" :hhczr_nl_35,:hhczr_nl_50,:hhczr_nl_60l,:hhczr_nl_60u,:hhczr_zzmm_gcd,:hhczr_zzmm_mzp,:bz17,:bz18,:bz19,:bz20,:bz21,:bz22,:bz23,:bz24,");
		sb.append(" :qtcyry_ry_zj,:qtcyry_ry_nv,:qtcyry_xl_yjs,:qtcyry_xl_bk,:qtcyry_xl_dz,:qtcyry_xl_zz,:qtcyry_nl_35,:qtcyry_nl_50,:qtcyry_nl_60l,");
		sb.append(" :qtcyry_nl_60u,:qtcyry_zzmm_gcd,:qtcyry_zzmm_mzp,:bz25,:bz26,:bz27,:bz28,:bz29,:bz30,:bz31,:bz32,:fzyzss_ry_zj,:fzyzss_ry_nv,:fzyzss_xl_yjs,");
		sb.append(" :fzyzss_xl_bk,:fzyzss_xl_dz,:fzyzss_xl_zz,:fzyzss_nl_35,:fzyzss_nl_50,:fzyzss_nl_60l,:fzyzss_nl_60u,:fzyzss_zzmm_gcd,:fzyzss_zzmm_mzp,");
		sb.append(" :bz33,:bz34,:bz35,:bz36,:bz37,:bz38,:bz39,:bz40,:zckjs_ry_zj,:zckjs_ry_nv,:zckjs_xl_yjs,:zckjs_xl_bk,:zckjs_xl_dz,:zckjs_xl_zz,:zckjs_nl_35,");
		sb.append(" :zckjs_nl_50,:zckjs_nl_60l,:zckjs_nl_60u,:zckjs_zzmm_gcd,:zckjs_zzmm_mzp,:bz41,:bz42,:bz43,:bz44,:bz45,:bz46,:bz47,:bz48,:zcpgs_ry_zj,");
		sb.append(" :zcpgs_ry_nv,:zcpgs_xl_yjs,:zcpgs_xl_bk,:zcpgs_xl_dz,:zcpgs_xl_zz,:zcpgs_nl_35,:zcpgs_nl_50,:zcpgs_nl_60l,:zcpgs_nl_60u,:zcpgs_zzmm_gcd,");
		sb.append(" :zcpgs_zzmm_mzp,:bz49,:bz50,:bz51,:bz52,:bz53,:bz54,:bz55,:bz56,:ls_ry_zj,:ls_ry_nv,:ls_xl_yjs,:ls_xl_bk,:ls_xl_dz,:ls_xl_zz,:ls_nl_35,:ls_nl_50,");
		sb.append(" :ls_nl_60l,:ls_nl_60u,:ls_zzmm_gcd,:ls_zzmm_mzp,:bz57,:bz58,:bz59,:bz60,:bz61,:bz62,:bz63,:bz64)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	public void UpdateHyryqktjb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_hyryqktj ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,nd=:nd,sbrq=sysdate(),ztbj=:ztbj,zbr=:zbr,sz=:sz,ryzs_ry_zj=:ryzs_ry_zj,ryzs_ry_nv=:ryzs_ry_nv,");
		sb.append(" ryzs_xl_yjs=:ryzs_xl_yjs,ryzs_xl_bk=:ryzs_xl_bk,ryzs_xl_dz=:ryzs_xl_dz,ryzs_xl_zz=:ryzs_xl_zz,ryzs_nl_35=:ryzs_nl_35,ryzs_nl_50=:ryzs_nl_50,");
		sb.append(" ryzs_nl_60l=:ryzs_nl_60l,ryzs_nl_60u=:ryzs_nl_60u,ryzs_zzmm_gcd=:ryzs_zzmm_gcd,ryzs_zzmm_mzp=:ryzs_zzmm_mzp,bz1=:bz1,bz2=:bz2,bz3=:bz3,bz4=:bz4,");
		sb.append(" bz5=:bz5,bz6=:bz6,bz7=:bz7,bz8=:bz8,zysws_ry_zj=:zysws_ry_zj,zysws_ry_nv=:zysws_ry_nv,zysws_xl_yjs=:zysws_xl_yjs,zysws_xl_bk=:zysws_xl_bk,");
		sb.append(" zysws_xl_dz=:zysws_xl_dz,zysws_xl_zz=:zysws_xl_zz,zysws_nl_35=:zysws_nl_35,zysws_nl_50=:zysws_nl_50,zysws_nl_60l=:zysws_nl_60l,");
		sb.append(" zysws_nl_60u=:zysws_nl_60u,zysws_zzmm_gcd=:zysws_zzmm_gcd,zysws_zzmm_mzp=:zysws_zzmm_mzp,bz9=:bz9,bz10=:bz10,bz11=:bz11,bz12=:bz12,bz13=:bz13,");
		sb.append(" bz14=:bz14,bz15=:bz15,bz16=:bz16,hhczr_ry_zj=:hhczr_ry_zj,hhczr_ry_nv=:hhczr_ry_nv,hhczr_xl_yjs=:hhczr_xl_yjs,hhczr_xl_bk=:hhczr_xl_bk,");
		sb.append(" hhczr_xl_dz=:hhczr_xl_dz,hhczr_xl_zz=:hhczr_xl_zz,hhczr_nl_35=:hhczr_nl_35,hhczr_nl_50=:hhczr_nl_50,hhczr_nl_60l=:hhczr_nl_60l,hhczr_nl_60u=:hhczr_nl_60u,");
		sb.append(" hhczr_zzmm_gcd=:hhczr_zzmm_gcd,hhczr_zzmm_mzp=:hhczr_zzmm_mzp,bz17=:bz17,bz18=:bz18,bz19=:bz19,bz20=:bz20,bz21=:bz21,bz22=:bz22,bz23=:bz23,bz24=:bz24,");
		sb.append(" qtcyry_ry_zj=:qtcyry_ry_zj,qtcyry_ry_nv=:qtcyry_ry_nv,qtcyry_xl_yjs=:qtcyry_xl_yjs,qtcyry_xl_bk=:qtcyry_xl_bk,qtcyry_xl_dz=:qtcyry_xl_dz,");
		sb.append(" qtcyry_xl_zz=:qtcyry_xl_zz,qtcyry_nl_35=:qtcyry_nl_35,qtcyry_nl_50=:qtcyry_nl_50,qtcyry_nl_60l=:qtcyry_nl_60l,qtcyry_nl_60u=:qtcyry_nl_60u,");
		sb.append(" qtcyry_zzmm_gcd=:qtcyry_zzmm_gcd,qtcyry_zzmm_mzp=:qtcyry_zzmm_mzp,bz25=:bz25,bz26=:bz26,bz27=:bz27,bz28=:bz28,bz29=:bz29,bz30=:bz30,bz31=:bz31,");
		sb.append(" bz32=:bz32,fzyzss_ry_zj=:fzyzss_ry_zj,fzyzss_ry_nv=:fzyzss_ry_nv,fzyzss_xl_yjs=:fzyzss_xl_yjs,fzyzss_xl_bk=:fzyzss_xl_bk,fzyzss_xl_dz=:fzyzss_xl_dz,");
		sb.append(" fzyzss_xl_zz=:fzyzss_xl_zz,fzyzss_nl_35=:fzyzss_nl_35,fzyzss_nl_50=:fzyzss_nl_50,fzyzss_nl_60l=:fzyzss_nl_60l,fzyzss_nl_60u=:fzyzss_nl_60u,");
		sb.append(" fzyzss_zzmm_gcd=:fzyzss_zzmm_gcd,fzyzss_zzmm_mzp=:fzyzss_zzmm_mzp,bz33=:bz33,bz34=:bz34,bz35=:bz35,bz36=:bz36,bz37=:bz37,bz38=:bz38,bz39=:bz39,bz40=:bz40,");
		sb.append(" zckjs_ry_zj=:zckjs_ry_zj,zckjs_ry_nv=:zckjs_ry_nv,zckjs_xl_yjs=:zckjs_xl_yjs,zckjs_xl_bk=:zckjs_xl_bk,zckjs_xl_dz=:zckjs_xl_dz,zckjs_xl_zz=:zckjs_xl_zz,");
		sb.append(" zckjs_nl_35=:zckjs_nl_35,zckjs_nl_50=:zckjs_nl_50,zckjs_nl_60l=:zckjs_nl_60l,zckjs_nl_60u=:zckjs_nl_60u,zckjs_zzmm_gcd=:zckjs_zzmm_gcd,");
		sb.append(" zckjs_zzmm_mzp=:zckjs_zzmm_mzp,bz41=:bz41,bz42=:bz42,bz43=:bz43,bz44=:bz44,bz45=:bz45,bz46=:bz46,bz47=:bz47,bz48=:bz48,zcpgs_ry_zj=:zcpgs_ry_zj,");
		sb.append(" zcpgs_ry_nv=:zcpgs_ry_nv,zcpgs_xl_yjs=:zcpgs_xl_yjs,zcpgs_xl_bk=:zcpgs_xl_bk,zcpgs_xl_dz=:zcpgs_xl_dz,zcpgs_xl_zz=:zcpgs_xl_zz,zcpgs_nl_35=:zcpgs_nl_35,");
		sb.append(" zcpgs_nl_50=:zcpgs_nl_50,zcpgs_nl_60l=:zcpgs_nl_60l,zcpgs_nl_60u=:zcpgs_nl_60u,zcpgs_zzmm_gcd=:zcpgs_zzmm_gcd,zcpgs_zzmm_mzp=:zcpgs_zzmm_mzp,");
		sb.append(" bz49=:bz49,bz50=:bz50,bz51=:bz51,bz52=:bz52,bz53=:bz53,bz54=:bz54,bz55=:bz55,bz56=:bz56,ls_ry_zj=:ls_ry_zj,ls_ry_nv=:ls_ry_nv,ls_xl_yjs=:ls_xl_yjs,");
		sb.append(" ls_xl_bk=:ls_xl_bk,ls_xl_dz=:ls_xl_dz,ls_xl_zz=:ls_xl_zz,ls_nl_35=:ls_nl_35,ls_nl_50=:ls_nl_50,ls_nl_60l=:ls_nl_60l,ls_nl_60u=:ls_nl_60u,");
		sb.append(" ls_zzmm_gcd=:ls_zzmm_gcd,ls_zzmm_mzp=:ls_zzmm_mzp,bz57=:bz57,bz58=:bz58,bz59=:bz59,bz60=:bz60,bz61=:bz61,bz62=:bz62,bz63=:bz63,bz64=:bz64 where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}
	
	public Map<String, Object> getOk(String jgid) {
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		int gid = (int)hashids.decode(jgid)[0];
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_swsjbqk a,zs_jg b where jg_id=? and a.JG_ID=b.ID and nd=(select max(nd) from zs_sdsb_swsjbqk )";
		List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql,gid);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", rs);
		if(ob.size()>0){
			return ob;
		}else{
			return null;
		}
	}
	
	public Map<String, Object> getJysrqkb(int page, int pageSize,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM   ( select a.id,a.nd,b.DWMC,a.SRZE,a.ZCZE,a.LRZE,");	
		sb.append(" case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when 3 then '退回' else null end as ZTBJ");		
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_sdsb_jysrqk a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where 1=1;
		sb.append(" AND a.JG_ID=b.ID  and a.JG_ID=? ORDER BY a.nd DESC ) AS t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
		params.add(Jgid);
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
	
	public Map<String, Object> getJysrqkbById(String id) {
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_jysrqk a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	
	public String AddJysrqkb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");		
		obj.put("id", uuid);
		
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_jysrqk ");	
		sb.append("  ( id,jg_id,use_id,nd,sbrq,ztbj,tbr,sz,srze0,srze,zyywsrhj_hs0,zyywsrhj_je0,zyywsrhj_hs,zyywsrhj_je,ssfwyw_hs0,");
		sb.append("  ssfwyw_je0,ssfwyw_hs,ssfwyw_je,dlswdj_hs0,dlswdj_je0,dlswdj_hs,dlswdj_je,dlnssb_hs0,dlnssb_je0,dlnssb_hs,");
		sb.append("  dlnssb_je,dljzjz_hs0,dljzjz_je0,dljzjz_hs,dljzjz_je,dlsqjmts_hs0,dlsqjmts_je0,dlsqjmts_hs,dlsqjmts_je,");
		sb.append("  dlzgrd_hs0,dlzgrd_je0,dlzgrd_hs,dlzgrd_je,dlzzssws_hs0,dlzzssws_je0,dlzzssws_hs,dlzzssws_je,dlyjdk_hs0,");
		sb.append("  dlyjdk_je0,dlyjdk_hs,dlyjdk_je,spswgwzx_hs0,spswgwzx_je0,spswgwzx_hs,spswgwzx_je,dlssch_hs0,dlssch_je0,");
		sb.append("  dlssch_hs,dlssch_je,sspx_hs0,sspx_je0,sspx_hs,sspx_je,qtssfwywxj_hs0,qtssfwywxj_je0,qtssfwywxj_hs,qtssfwywxj_je,");
		sb.append("  ssjzyw_hs0,ssjzyw_je0,ssjzyw_hs,ssjzyw_je,sdshsqj_hs0,sdshsqj_je0,sdshsqj_hs,sdshsqj_je,mbks_hs0,mbks_je0,");
		sb.append("  mbks_hs,mbks_je,ccsssqkc_hs0,ccsssqkc_je0,ccsssqkc_hs,ccsssqkc_je,tt_hs0,tt_je0,tt_hs,tt_je,qtssjz_hs0,qtssjz_je0,");
		sb.append("  qtssjz_hs,qtssjz_je,qtssywsr_hs10,qtssywsr_je10,qtssywsr_hs1,qtssywsr_je1,qtssywsr_hs20,qtssywsr_je20,qtssywsr_hs2,");
		sb.append("  qtssywsr_je2,qtssywsr_hs30,qtssywsr_je30,qtssywsr_hs3,qtssywsr_je3,qtywsrhj0,qtywsrhj,zcze0,zcze,zyywcb0,zyywcb,");
		sb.append("  zyywsjfj0,zyywsjfj,yyfy0,yyfy,glfy0,glfy,cwfy0,cwfy,yywzc0,yywzc,qtzc0,qtzc,lrze0,lrze)");
		sb.append("values ( :id,:jg_id,:use_id,:nd,sysdate(),:ztbj,:tbr,:sz,:srze0,:srze,:zyywsrhj_hs0,:zyywsrhj_je0,:zyywsrhj_hs,:zyywsrhj_je,:ssfwyw_hs0,");
		sb.append(" :ssfwyw_je0,:ssfwyw_hs,:ssfwyw_je,:dlswdj_hs0,:dlswdj_je0,:dlswdj_hs,:dlswdj_je,:dlnssb_hs0,:dlnssb_je0,:dlnssb_hs,");
		sb.append(" :dlnssb_je,:dljzjz_hs0,:dljzjz_je0,:dljzjz_hs,:dljzjz_je,:dlsqjmts_hs0,:dlsqjmts_je0,:dlsqjmts_hs,:dlsqjmts_je,");
		sb.append(" :dlzgrd_hs0,:dlzgrd_je0,:dlzgrd_hs,:dlzgrd_je,:dlzzssws_hs0,:dlzzssws_je0,:dlzzssws_hs,:dlzzssws_je,:dlyjdk_hs0,");
		sb.append(" :dlyjdk_je0,:dlyjdk_hs,:dlyjdk_je,:spswgwzx_hs0,:spswgwzx_je0,:spswgwzx_hs,:spswgwzx_je,:dlssch_hs0,:dlssch_je0,");
		sb.append(" :dlssch_hs,:dlssch_je,:sspx_hs0,:sspx_je0,:sspx_hs,:sspx_je,:qtssfwywxj_hs0,:qtssfwywxj_je0,:qtssfwywxj_hs,:qtssfwywxj_je,");
		sb.append(" :ssjzyw_hs0,:ssjzyw_je0,:ssjzyw_hs,:ssjzyw_je,:sdshsqj_hs0,:sdshsqj_je0,:sdshsqj_hs,:sdshsqj_je,:mbks_hs0,:mbks_je0,");
		sb.append(" :mbks_hs,:mbks_je,:ccsssqkc_hs0,:ccsssqkc_je0,:ccsssqkc_hs,:ccsssqkc_je,:tt_hs0,:tt_je0,:tt_hs,:tt_je,:qtssjz_hs0,:qtssjz_je0,");
		sb.append(" :qtssjz_hs,:qtssjz_je,:qtssywsr_hs10,:qtssywsr_je10,:qtssywsr_hs1,:qtssywsr_je1,:qtssywsr_hs20,:qtssywsr_je20,:qtssywsr_hs2,");
		sb.append(" :qtssywsr_je2,:qtssywsr_hs30,:qtssywsr_je30,:qtssywsr_hs3,:qtssywsr_je3,:qtywsrhj0,:qtywsrhj,:zcze0,:zcze,:zyywcb0,:zyywcb,");
		sb.append(" :zyywsjfj0,:zyywsjfj,:yyfy0,:yyfy,:glfy0,:glfy,:cwfy0,:cwfy,:yywzc0,:yywzc,:qtzc0,:qtzc,:lrze0,:lrze)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	public void UpdateJysrqkb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_jysrqk ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,nd=:nd,sbrq=sysdate(),ztbj=:ztbj,tbr=:tbr,sz=:sz,srze0=:srze0,srze=:srze,zyywsrhj_hs0=:zyywsrhj_hs0,");
		sb.append(" zyywsrhj_je0=:zyywsrhj_je0,zyywsrhj_hs=:zyywsrhj_hs,zyywsrhj_je=:zyywsrhj_je,ssfwyw_hs0=:ssfwyw_hs0,ssfwyw_je0=:ssfwyw_je0,");
		sb.append(" ssfwyw_hs=:ssfwyw_hs,ssfwyw_je=:ssfwyw_je,dlswdj_hs0=:dlswdj_hs0,dlswdj_je0=:dlswdj_je0,dlswdj_hs=:dlswdj_hs,dlswdj_je=:dlswdj_je,");
		sb.append(" dlnssb_hs0=:dlnssb_hs0,dlnssb_je0=:dlnssb_je0,dlnssb_hs=:dlnssb_hs,dlnssb_je=:dlnssb_je,dljzjz_hs0=:dljzjz_hs0,dljzjz_je0=:dljzjz_je0,");
		sb.append(" dljzjz_hs=dljzjz_hs,dljzjz_je=:dljzjz_je,dlsqjmts_hs0=:dlsqjmts_hs0,dlsqjmts_je0=:dlsqjmts_je0,dlsqjmts_hs=:dlsqjmts_hs,dlsqjmts_je=:dlsqjmts_je,");
		sb.append(" dlzgrd_hs0=:dlzgrd_hs0,dlzgrd_je0=:dlzgrd_je0,dlzgrd_hs=:dlzgrd_hs,dlzgrd_je=:dlzgrd_je,dlzzssws_hs0=:dlzzssws_hs0,dlzzssws_je0=:dlzzssws_je0,");
		sb.append(" dlzzssws_hs=:dlzzssws_hs,dlzzssws_je=:dlzzssws_je,dlyjdk_hs0=:dlyjdk_hs0, dlyjdk_je0=:dlyjdk_je0,dlyjdk_hs=:dlyjdk_hs,dlyjdk_je=:dlyjdk_je,");
		sb.append(" spswgwzx_hs0=:spswgwzx_hs0,spswgwzx_je0=:spswgwzx_je0,spswgwzx_hs=:spswgwzx_hs,spswgwzx_je=:spswgwzx_je,dlssch_hs0=:dlssch_hs0,dlssch_je0=:dlssch_je0,");
		sb.append(" dlssch_hs=:dlssch_hs,dlssch_je=:dlssch_je,sspx_hs0=:sspx_hs0,sspx_je0=:sspx_je0,sspx_hs=:sspx_hs,sspx_je=:sspx_je,qtssfwywxj_hs0=:qtssfwywxj_hs0,");
		sb.append(" qtssfwywxj_je0=:qtssfwywxj_je0,qtssfwywxj_hs=:qtssfwywxj_hs,qtssfwywxj_je=:qtssfwywxj_je,ssjzyw_hs0=:ssjzyw_hs0,ssjzyw_je0=:ssjzyw_je0,");
		sb.append(" ssjzyw_hs=:ssjzyw_hs,ssjzyw_je=:ssjzyw_je,sdshsqj_hs0=:sdshsqj_hs0,sdshsqj_je0=:sdshsqj_je0,sdshsqj_hs=:sdshsqj_hs,sdshsqj_je=:sdshsqj_je,");
		sb.append(" mbks_hs0=:mbks_hs0,mbks_je0=:mbks_je0, mbks_hs=:mbks_hs,mbks_je=:mbks_je,ccsssqkc_hs0=:ccsssqkc_hs0,ccsssqkc_je0=:ccsssqkc_je0,");
		sb.append(" ccsssqkc_hs=:ccsssqkc_hs,ccsssqkc_je=:ccsssqkc_je,tt_hs0=:tt_hs0,tt_je0=:tt_je0,tt_hs=:tt_hs,tt_je=:tt_je,qtssjz_hs0=:qtssjz_hs0,qtssjz_je0=:qtssjz_je0,");
		sb.append(" qtssjz_hs=:qtssjz_hs,qtssjz_je=:qtssjz_je,qtssywsr_hs10=:qtssywsr_hs10,qtssywsr_je10=:qtssywsr_je10,qtssywsr_hs1=:qtssywsr_hs1,qtssywsr_je1=:qtssywsr_je1,");
		sb.append(" qtssywsr_hs20=:qtssywsr_hs20,qtssywsr_je20=:qtssywsr_je20,qtssywsr_hs2=:qtssywsr_hs2,qtssywsr_je2=:qtssywsr_je2,qtssywsr_hs30=:qtssywsr_hs30,");
		sb.append(" qtssywsr_je30=:qtssywsr_je30,qtssywsr_hs3=:qtssywsr_hs3,qtssywsr_je3=:qtssywsr_je3,qtywsrhj0=:qtywsrhj0,qtywsrhj=:qtywsrhj,zcze0=:zcze0,");
		sb.append(" zcze=:zcze,zyywcb0=:zyywcb0,zyywcb=:zyywcb,zyywsjfj0=:zyywsjfj0,zyywsjfj=:zyywsjfj,yyfy0=:yyfy0,yyfy=:yyfy,glfy0=:glfy0,glfy=:glfy,cwfy0=:cwfy0,");
		sb.append(" cwfy=:cwfy,yywzc0=:yywzc0,yywzc=:yywzc,qtzc0=:qtzc0,qtzc=:qtzc,lrze0=:lrze0,lrze=:lrze where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}
	public Map<String, Object> getUpyear(String jgid) {
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		int gid = (int)hashids.decode(jgid)[0];
		String sql = "select a.nd,b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_jysrqk a,zs_jg b where a.jg_id=b.id and jg_id=? and a.ND=( date_format(sysdate(),'%Y')-2)";
		List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql,gid);
		Map<String,Object> ob = new HashMap<>();
		ob.put("upyear", rs);
			return ob;
	}

}
