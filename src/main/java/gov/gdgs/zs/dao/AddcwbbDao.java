package gov.gdgs.zs.dao;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class AddcwbbDao extends BaseJdbcDao implements IAddcwbbDao{
	
	@Override
	public String AddXjllb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		obj.put("id", uuid);
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_xjll ");	
		sb.append("  ( id,jg_id,kssj,jssj,nd,jyhd_xjlr_xslw,jyhd_xjlr_skfh,jyhd_xjlr_qtjy,jyhd_xjlr_xj,jyhd_xjlc_gmlw,jyhd_xjlc_zfzg,jyhd_xjlc_sf,");
		sb.append(" jyhd_xjlc_qtjy,jyhd_xjlc_xj,jyhd_je,tzhd_xjlr_shtz,tzhd_xjlr_tzsy,tzhd_xjlr_czzc,tzhd_xjlr_qttz,tzhd_xjlr_xj,");
		sb.append(" tzhd_xjlc_gjzc,tzhd_xjlc_tz,tzhd_xjlc_qttz,tzhd_xjlc_xj,tzhd_je,czhd_xjlr_xstz,czhd_xjlr_jk,czhd_xjlr_qtcz,");
		sb.append(" czhd_xjlr_xj,czhd_xjlc_chzw,czhd_xjlc_fplr,czhd_xjlc_qtcz,czhd_xjlc_xj,czhd_je,hlbdyx,xjjzzj,bczl_jlr,bczl_jtzcjz,");
		sb.append(" bczl_gdzczj,bczl_wxzctx,bczl_cqdtfy,bczl_dtfyjs,bczl_ytfyjs,bczl_zcss,bczl_gdzcbf,bczl_cwfy,bczl_tzss,bczl_dysddx,");
		sb.append(" bczl_chjs,bczl_ysxmjs,bczl_yfxmzj,bczl_qt,bczl_xjllje,bczl_zwzwzb,bczl_yndqzj,bczl_rzzrzc,bczl_xjqmye,");
		sb.append(" bczl_xjdjwqmye,bczl_xjqcye,bczl_xjdjwqcye,bczl_xjdjwjezj,ztbj)");
		sb.append("values ( :id,:jg_id,:kssj,:jssj,:nd,:jyhd_xjlr_xslw,:jyhd_xjlr_skfh,:jyhd_xjlr_qtjy,:jyhd_xjlr_xj,:jyhd_xjlc_gmlw,:jyhd_xjlc_zfzg,:jyhd_xjlc_sf,");
		sb.append(" :jyhd_xjlc_qtjy,:jyhd_xjlc_xj,:jyhd_je,:tzhd_xjlr_shtz,:tzhd_xjlr_tzsy,:tzhd_xjlr_czzc,:tzhd_xjlr_qttz,:tzhd_xjlr_xj,");
		sb.append(" :tzhd_xjlc_gjzc,:tzhd_xjlc_tz,:tzhd_xjlc_qttz,:tzhd_xjlc_xj,:tzhd_je,:czhd_xjlr_xstz,:czhd_xjlr_jk,:czhd_xjlr_qtcz,");
		sb.append(" :czhd_xjlr_xj,:czhd_xjlc_chzw,:czhd_xjlc_fplr,:czhd_xjlc_qtcz,:czhd_xjlc_xj,:czhd_je,:hlbdyx,:xjjzzj,:bczl_jlr,:bczl_jtzcjz,");
		sb.append(" :bczl_gdzczj,:bczl_wxzctx,:bczl_cqdtfy,:bczl_dtfyjs,:bczl_ytfyjs,:bczl_zcss,:bczl_gdzcbf,:bczl_cwfy,:bczl_tzss,:bczl_dysddx,");
		sb.append(" :bczl_chjs,:bczl_ysxmjs,:bczl_yfxmzj,:bczl_qt,:bczl_xjllje,:bczl_zwzwzb,:bczl_yndqzj,:bczl_rzzrzc,:bczl_xjqmye,");
		sb.append(" :bczl_xjdjwqmye,:bczl_xjqcye,:bczl_xjdjwqcye,:bczl_xjdjwjezj,:ztbj)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	@Override
	public void UpdateXjllb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_xjll ");
		sb.append(" set jg_id=:jg_id,kssj=:kssj,jssj=:jssj,nd=:nd,jyhd_xjlr_xslw=:jyhd_xjlr_xslw,jyhd_xjlr_skfh=:jyhd_xjlr_skfh,jyhd_xjlr_qtjy=:jyhd_xjlr_qtjy,");	
		sb.append(" jyhd_xjlr_xj=:jyhd_xjlr_xj,jyhd_xjlc_gmlw=:jyhd_xjlc_gmlw,jyhd_xjlc_zfzg=:jyhd_xjlc_zfzg,jyhd_xjlc_sf=:jyhd_xjlc_sf,");
		sb.append(" jyhd_xjlc_qtjy=:jyhd_xjlc_qtjy,jyhd_xjlc_xj=:jyhd_xjlc_xj,jyhd_je=:jyhd_je,tzhd_xjlr_shtz=:tzhd_xjlr_shtz,");
		sb.append(" tzhd_xjlr_tzsy=:tzhd_xjlr_tzsy,tzhd_xjlr_czzc=:tzhd_xjlr_czzc,tzhd_xjlr_qttz=:tzhd_xjlr_qttz,tzhd_xjlr_xj=:tzhd_xjlr_xj,");
		sb.append(" tzhd_xjlc_gjzc=:tzhd_xjlc_gjzc,tzhd_xjlc_tz=:tzhd_xjlc_tz,tzhd_xjlc_qttz=:tzhd_xjlc_qttz,tzhd_xjlc_xj=:tzhd_xjlc_xj,");
		sb.append(" tzhd_je=:tzhd_je,czhd_xjlr_xstz=:czhd_xjlr_xstz,czhd_xjlr_jk=:czhd_xjlr_jk,czhd_xjlr_qtcz=:czhd_xjlr_qtcz,czhd_xjlr_xj=:czhd_xjlr_xj,");
		sb.append(" czhd_xjlc_chzw=:czhd_xjlc_chzw,czhd_xjlc_fplr=:czhd_xjlc_fplr,czhd_xjlc_qtcz=:czhd_xjlc_qtcz,czhd_xjlc_xj=:czhd_xjlc_xj,");
		sb.append(" czhd_je=:czhd_je,hlbdyx=:hlbdyx,xjjzzj=:xjjzzj,bczl_jlr=:bczl_jlr,bczl_jtzcjz=:bczl_jtzcjz,bczl_gdzczj=:bczl_gdzczj,");
		sb.append(" bczl_wxzctx=:bczl_wxzctx,bczl_cqdtfy=:bczl_cqdtfy,bczl_dtfyjs=:bczl_dtfyjs,bczl_ytfyjs=:bczl_ytfyjs,bczl_zcss=:bczl_zcss,");
		sb.append(" bczl_gdzcbf=:bczl_gdzcbf,bczl_cwfy=:bczl_cwfy,bczl_tzss=:bczl_tzss,bczl_dysddx=:bczl_dysddx,bczl_chjs=:bczl_chjs,");
		sb.append(" bczl_ysxmjs=:bczl_ysxmjs,bczl_yfxmzj=:bczl_yfxmzj,bczl_qt=:bczl_qt,bczl_xjllje=:bczl_xjllje,bczl_zwzwzb=:bczl_zwzwzb,");
		sb.append(" bczl_yndqzj=:bczl_yndqzj,bczl_rzzrzc=:bczl_rzzrzc,bczl_xjqmye=:bczl_xjqmye,bczl_xjdjwqmye=:bczl_xjdjwqmye,bczl_xjqcye=:bczl_xjqcye,");
		sb.append(" bczl_xjdjwqcye=:bczl_xjdjwqcye,bczl_xjdjwjezj=:bczl_xjdjwjezj,ztbj=:ztbj where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}
	
	public Map<String, Object> getXjllb(int page, int pageSize,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));
		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM    ( SELECT a.id,b.DWMC,a.nd,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS JSSJ,");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ");		
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_cwbb_xjll a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" AND a.JG_ID=b.ID  and a.JG_ID=68 ORDER BY a.nd DESC ) AS t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
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
	
	public Map<String, Object> getXjllbById(String id) {
		String sql = "select b.DWMC,DATE_FORMAT(a.KSSJ,'%Y-%m-%d') AS A,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS B,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_xjll a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}

}
