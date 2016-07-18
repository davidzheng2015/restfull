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
		sb.append("  ( id,jg_id,use_id,kssj,jssj,tjrq,nd,jyhd_xjlr_xslw,jyhd_xjlr_skfh,jyhd_xjlr_qtjy,jyhd_xjlr_xj,jyhd_xjlc_gmlw,jyhd_xjlc_zfzg,jyhd_xjlc_sf,");
		sb.append(" jyhd_xjlc_qtjy,jyhd_xjlc_xj,jyhd_je,tzhd_xjlr_shtz,tzhd_xjlr_tzsy,tzhd_xjlr_czzc,tzhd_xjlr_qttz,tzhd_xjlr_xj,");
		sb.append(" tzhd_xjlc_gjzc,tzhd_xjlc_tz,tzhd_xjlc_qttz,tzhd_xjlc_xj,tzhd_je,czhd_xjlr_xstz,czhd_xjlr_jk,czhd_xjlr_qtcz,");
		sb.append(" czhd_xjlr_xj,czhd_xjlc_chzw,czhd_xjlc_fplr,czhd_xjlc_qtcz,czhd_xjlc_xj,czhd_je,hlbdyx,xjjzzj,bczl_jlr,bczl_jtzcjz,");
		sb.append(" bczl_gdzczj,bczl_wxzctx,bczl_cqdtfy,bczl_dtfyjs,bczl_ytfyjs,bczl_zcss,bczl_gdzcbf,bczl_cwfy,bczl_tzss,bczl_dysddx,");
		sb.append(" bczl_chjs,bczl_ysxmjs,bczl_yfxmzj,bczl_qt,bczl_xjllje,bczl_zwzwzb,bczl_yndqzj,bczl_rzzrzc,bczl_xjqmye,");
		sb.append(" bczl_xjdjwqmye,bczl_xjqcye,bczl_xjdjwqcye,bczl_xjdjwjezj,ztbj)");
		sb.append("values ( :id,:jg_id,:use_id,:kssj,:jssj,sysdate(),:nd,:jyhd_xjlr_xslw,:jyhd_xjlr_skfh,:jyhd_xjlr_qtjy,:jyhd_xjlr_xj,:jyhd_xjlc_gmlw,:jyhd_xjlc_zfzg,:jyhd_xjlc_sf,");
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
		sb.append(" set jg_id=:jg_id,use_id=:use_id,kssj=:kssj,jssj=:jssj,tjrq=sysdate(),nd=:nd,jyhd_xjlr_xslw=:jyhd_xjlr_xslw,jyhd_xjlr_skfh=:jyhd_xjlr_skfh,jyhd_xjlr_qtjy=:jyhd_xjlr_qtjy,");	
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
	
	public Map<String, Object> getXjllb(int page, int pageSize,int Jgid,
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
	
	public Map<String, Object> getXjllbById(String id) {
		String sql = "select b.DWMC,DATE_FORMAT(a.KSSJ,'%Y-%m-%d') AS A,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS B,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_xjll a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	
	@Override
	public String AddZcfzb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		obj.put("id", uuid);
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_zcfzgd ");	
		sb.append("  ( id,jg_id,use_id,ztbj,kssj,jssj,tjsj,nd,timevalue,ldzc_hj,ldzc_hbzj,ldzc_dqtz,ldzc_yspj,ldzc_ysgl,ldzc_yslx,ldzc_yszk,ldzc_qtys,ldzc_yfzk,ldzc_ysbt,");	
		sb.append(" ldzc_ch,ldzc_dtfy,ldzc_dqzj,ldzc_qtldzc,cqtz_gq,cqtz_zq,cqtz_hj,gdzc_yj,gdzc_ljzj,gdzc_jz,gdzc_jzzb,gdzc_je,gdzc_gcwz,gdzc_zjgc,gdzc_ql,gdzc_hj,");
		sb.append(" wxqt_wxzc,wxqt_cqdt,wxqt_qtcq,wxqt_hj,ydsx_skjx,zczj,ldfz_dqjk,ldfz_yfpj,ldfz_yfzk,ldfz_yszk,ldfz_yfgz,ldfz_yffl,ldfz_yfgl,ldfz_yjsj,");
		sb.append(" ldfz_qtyj,ldfz_qtyf,ldfz_ytfy,ldfz_yjfz,ldfz_dqfz,ldfz_qtfz,ldfz_hj,cqfz_cqjk,cqfz_yfzq,cqfz_cqyf,cqfz_zxyf,cqfz_zyfxjj,cqfz_qtfz,cqfz_hj,");
		sb.append(" dysx_dyskdx,dysx_fzhj,syzqy_sszbje,syzqy_yhtz,syzqy_sszb,syzqy_zbgj,syzqy_yygj,syzqy_wfplr,syzqy_hj,fzsyzqy_hj,zgkj,sz,zbr,");
		sb.append(" ldzc_hj_nc,ldzc_hbzj_nc,ldzc_dqtz_nc,ldzc_yspj_nc,ldzc_ysgl_nc,ldzc_yslx_nc,ldzc_yszk_nc,ldzc_qtys_nc,ldzc_yfzk_nc,ldzc_ysbt_nc,");
		sb.append(" ldzc_ch_nc,ldzc_dtfy_nc,ldzc_dqzj_nc,ldzc_qtldzc_nc,cqtz_gq_nc,cqtz_zq_nc,cqtz_hj_nc,gdzc_yj_nc,gdzc_ljzj_nc,gdzc_jz_nc,gdzc_jzzb_nc,gdzc_je_nc,gdzc_gcwz_nc,gdzc_zjgc_nc,gdzc_ql_nc,gdzc_hj_nc,");
		sb.append(" wxqt_wxzc_nc,wxqt_cqdt_nc,wxqt_qtcq_nc,wxqt_hj_nc,ydsx_skjx_nc,zczj_nc,ldfz_dqjk_nc,ldfz_yfpj_nc,ldfz_yfzk_nc,ldfz_yszk_nc,ldfz_yfgz_nc,ldfz_yffl_nc,");
		sb.append(" ldfz_yfgl_nc,ldfz_yjsj_nc,ldfz_qtyj_nc,ldfz_qtyf_nc,ldfz_ytfy_nc,ldfz_yjfz_nc,ldfz_dqfz_nc,ldfz_qtfz_nc,ldfz_hj_nc,cqfz_cqjk_nc,cqfz_yfzq_nc,cqfz_cqyf_nc,cqfz_zxyf_nc,");
		sb.append(" cqfz_zyfxjj_nc,cqfz_qtfz_nc,cqfz_hj_nc,dysx_dyskdx_nc,dysx_fzhj_nc,syzqy_sszbje_nc,syzqy_yhtz_nc,syzqy_sszb_nc,syzqy_zbgj_nc,syzqy_yygj_nc,syzqy_wfplr_nc,syzqy_hj_nc,fzsyzqy_hj_nc)");
		sb.append("values ( :id,:jg_id,:use_id,:ztbj,:kssj,:jssj,sysdate(),:nd,:timevalue,:ldzc_hj,:ldzc_hbzj,:ldzc_dqtz,:ldzc_yspj,:ldzc_ysgl,:ldzc_yslx,:ldzc_yszk,:ldzc_qtys,:ldzc_yfzk,:ldzc_ysbt,");	
		sb.append(" :ldzc_ch,:ldzc_dtfy,:ldzc_dqzj,:ldzc_qtldzc,:cqtz_gq,:cqtz_zq,:cqtz_hj,:gdzc_yj,:gdzc_ljzj,:gdzc_jz,:gdzc_jzzb,:gdzc_je,:gdzc_gcwz,:gdzc_zjgc,:gdzc_ql,:gdzc_hj,");
		sb.append(" :wxqt_wxzc,:wxqt_cqdt,:wxqt_qtcq,:wxqt_hj,:ydsx_skjx,:zczj,:ldfz_dqjk,:ldfz_yfpj,:ldfz_yfzk,:ldfz_yszk,:ldfz_yfgz,:ldfz_yffl,:ldfz_yfgl,:ldfz_yjsj,");
		sb.append(" :ldfz_qtyj,:ldfz_qtyf,:ldfz_ytfy,:ldfz_yjfz,:ldfz_dqfz,:ldfz_qtfz,:ldfz_hj,:cqfz_cqjk,:cqfz_yfzq,:cqfz_cqyf,:cqfz_zxyf,:cqfz_zyfxjj,:cqfz_qtfz,:cqfz_hj,");
		sb.append(" :dysx_dyskdx,:dysx_fzhj,:syzqy_sszbje,:syzqy_yhtz,:syzqy_sszb,:syzqy_zbgj,:syzqy_yygj,:syzqy_wfplr,:syzqy_hj,:fzsyzqy_hj,:zgkj,:sz,:zbr,");
		sb.append(" :ldzc_hj_nc,:ldzc_hbzj_nc,:ldzc_dqtz_nc,:ldzc_yspj_nc,:ldzc_ysgl_nc,:ldzc_yslx_nc,:ldzc_yszk_nc,:ldzc_qtys_nc,:ldzc_yfzk_nc,:ldzc_ysbt_nc,");
		sb.append(" :ldzc_ch_nc,:ldzc_dtfy_nc,:ldzc_dqzj_nc,:ldzc_qtldzc_nc,:cqtz_gq_nc,:cqtz_zq_nc,:cqtz_hj_nc,:gdzc_yj_nc,:gdzc_ljzj_nc,:gdzc_jz_nc,:gdzc_jzzb_nc,:gdzc_je_nc,:gdzc_gcwz_nc,:gdzc_zjgc_nc,:gdzc_ql_nc,:gdzc_hj_nc,");
		sb.append(" :wxqt_wxzc_nc,:wxqt_cqdt_nc,:wxqt_qtcq_nc,:wxqt_hj_nc,:ydsx_skjx_nc,:zczj_nc,:ldfz_dqjk_nc,:ldfz_yfpj_nc,:ldfz_yfzk_nc,:ldfz_yszk_nc,:ldfz_yfgz_nc,:ldfz_yffl_nc,");
		sb.append(" :ldfz_yfgl_nc,:ldfz_yjsj_nc,:ldfz_qtyj_nc,:ldfz_qtyf_nc,:ldfz_ytfy_nc,:ldfz_yjfz_nc,:ldfz_dqfz_nc,:ldfz_qtfz_nc,:ldfz_hj_nc,:cqfz_cqjk_nc,:cqfz_yfzq_nc,:cqfz_cqyf_nc,:cqfz_zxyf_nc,");
		sb.append(" :cqfz_zyfxjj_nc,:cqfz_qtfz_nc,:cqfz_hj_nc,:dysx_dyskdx_nc,:dysx_fzhj_nc,:syzqy_sszbje_nc,:syzqy_yhtz_nc,:syzqy_sszb_nc,:syzqy_zbgj_nc,:syzqy_yygj_nc,:syzqy_wfplr_nc,:syzqy_hj_nc,:fzsyzqy_hj_nc)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	@Override
	public void UpdateZcfzb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_zcfzgd ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,ztbj=:ztbj,kssj=:kssj,jssj=:jssj,tjsj=sysdate(),nd=:nd,timevalue=:timevalue,ldzc_hj=:ldzc_hj,ldzc_hbzj=:ldzc_hbzj,ldzc_dqtz=:ldzc_dqtz,");		
		sb.append(" ldzc_yspj=:ldzc_yspj,ldzc_ysgl=:ldzc_ysgl,ldzc_yslx=:ldzc_yslx,ldzc_yszk=:ldzc_yszk,ldzc_qtys=:ldzc_qtys,ldzc_yfzk=:ldzc_yfzk,ldzc_ysbt=:ldzc_ysbt,");
		sb.append(" ldzc_ch=:ldzc_ch,ldzc_dtfy=:ldzc_dtfy,ldzc_dqzj=:ldzc_dqzj,ldzc_qtldzc=:ldzc_qtldzc,cqtz_gq=:cqtz_gq,cqtz_zq=:cqtz_zq,cqtz_hj=:cqtz_hj,");
		sb.append(" gdzc_yj=:gdzc_yj,gdzc_ljzj=:gdzc_ljzj,gdzc_jz=:gdzc_jz,gdzc_jzzb=:gdzc_jzzb,gdzc_je=:gdzc_je,gdzc_gcwz=:gdzc_gcwz,gdzc_zjgc=:gdzc_zjgc,gdzc_ql=:gdzc_ql,gdzc_hj=:gdzc_hj,");
		sb.append(" wxqt_wxzc=:wxqt_wxzc,wxqt_cqdt=:wxqt_cqdt,wxqt_qtcq=:wxqt_qtcq,wxqt_hj=:wxqt_hj,ydsx_skjx=:ydsx_skjx,zczj=:zczj,ldfz_dqjk=:ldfz_dqjk,");
		sb.append(" ldfz_yfpj=:ldfz_yfpj,ldfz_yfzk=:ldfz_yfzk,ldfz_yszk=:ldfz_yszk,ldfz_yfgz=:ldfz_yfgz,ldfz_yffl=:ldfz_yffl,ldfz_yfgl=:ldfz_yfgl,ldfz_yjsj=:ldfz_yjsj,");
		sb.append(" ldfz_qtyj=:ldfz_qtyj,ldfz_qtyf=:ldfz_qtyf,ldfz_ytfy=:ldfz_ytfy,ldfz_yjfz=:ldfz_yjfz,ldfz_dqfz=:ldfz_dqfz,ldfz_qtfz=:ldfz_qtfz,ldfz_hj=:ldfz_hj,");
		sb.append(" cqfz_cqjk=:cqfz_cqjk,cqfz_yfzq=:cqfz_yfzq,cqfz_cqyf=:cqfz_cqyf,cqfz_zxyf=:cqfz_zxyf,cqfz_zyfxjj=:cqfz_zyfxjj,cqfz_qtfz=:cqfz_qtfz,cqfz_hj=:cqfz_hj,");
		sb.append(" dysx_dyskdx=:dysx_dyskdx,dysx_fzhj=:dysx_fzhj,syzqy_sszbje=:syzqy_sszbje,syzqy_yhtz=:syzqy_yhtz,syzqy_sszb=:syzqy_sszb,syzqy_zbgj=:syzqy_zbgj,");
		sb.append(" syzqy_yygj=:syzqy_yygj,syzqy_wfplr=:syzqy_wfplr,syzqy_hj=:syzqy_hj,fzsyzqy_hj=:fzsyzqy_hj,zgkj=:zgkj,sz=:sz,zbr=:zbr,");
		sb.append(" ldzc_hj_nc=:ldzc_hj_nc,ldzc_hbzj_nc=:ldzc_hbzj_nc,ldzc_dqtz_nc=:ldzc_dqtz_nc,ldzc_yspj_nc=:ldzc_yspj_nc,ldzc_ysgl_nc=:ldzc_ysgl_nc,");
		sb.append(" ldzc_yslx_nc=:ldzc_yslx_nc,ldzc_yszk_nc=:ldzc_yszk_nc,ldzc_qtys_nc=:ldzc_qtys_nc,ldzc_yfzk_nc=:ldzc_yfzk_nc,ldzc_ysbt_nc=:ldzc_ysbt_nc,");
		sb.append(" ldzc_ch_nc=:ldzc_ch_nc,ldzc_dtfy_nc=:ldzc_dtfy_nc,ldzc_dqzj_nc=:ldzc_dqzj_nc,ldzc_qtldzc_nc=:ldzc_qtldzc_nc,cqtz_gq_nc=:cqtz_gq_nc,cqtz_zq_nc=:cqtz_zq_nc,cqtz_hj_nc=:cqtz_hj_nc,");
		sb.append(" gdzc_yj_nc=:gdzc_yj_nc,gdzc_ljzj_nc=:gdzc_ljzj_nc,gdzc_jz_nc=:gdzc_jz_nc,gdzc_jzzb_nc=:gdzc_jzzb_nc,gdzc_je_nc=:gdzc_je_nc,gdzc_gcwz_nc=:gdzc_gcwz_nc,gdzc_zjgc_nc=:gdzc_zjgc_nc,gdzc_ql_nc=:gdzc_ql_nc,gdzc_hj_nc=:gdzc_hj_nc,");
		sb.append(" wxqt_wxzc_nc=:wxqt_wxzc_nc,wxqt_cqdt_nc=:wxqt_cqdt_nc,wxqt_qtcq_nc=:wxqt_qtcq_nc,wxqt_hj_nc=:wxqt_hj_nc,ydsx_skjx_nc=:ydsx_skjx_nc,zczj_nc=:zczj_nc,");
		sb.append(" ldfz_dqjk_nc=:ldfz_dqjk_nc,ldfz_yfpj_nc=:ldfz_yfpj_nc,ldfz_yfzk_nc=:ldfz_yfzk_nc,ldfz_yszk_nc=:ldfz_yszk_nc,ldfz_yfgz_nc=:ldfz_yfgz_nc,ldfz_yffl_nc=:ldfz_yffl_nc,");
		sb.append(" ldfz_yfgl_nc=:ldfz_yfgl_nc,ldfz_yjsj_nc=:ldfz_yjsj_nc,ldfz_qtyj_nc=:ldfz_qtyj_nc,ldfz_qtyf_nc=:ldfz_qtyf_nc,ldfz_ytfy_nc=:ldfz_ytfy_nc,ldfz_yjfz_nc=:ldfz_yjfz_nc,");
		sb.append(" ldfz_dqfz_nc=:ldfz_dqfz_nc,ldfz_qtfz_nc=:ldfz_qtfz_nc,ldfz_hj_nc=:ldfz_hj_nc,cqfz_cqjk_nc=:cqfz_cqjk_nc,cqfz_yfzq_nc=:cqfz_yfzq_nc,cqfz_cqyf_nc=:cqfz_cqyf_nc,cqfz_zxyf_nc=:cqfz_zxyf_nc,");
		sb.append(" cqfz_zyfxjj_nc=:cqfz_zyfxjj_nc,cqfz_qtfz_nc=:cqfz_qtfz_nc,cqfz_hj_nc=:cqfz_hj_nc,dysx_dyskdx_nc=:dysx_dyskdx_nc,dysx_fzhj_nc=:dysx_fzhj_nc,syzqy_sszbje_nc=:syzqy_sszbje_nc,");
		sb.append(" syzqy_yhtz_nc=:syzqy_yhtz_nc,syzqy_sszb_nc=:syzqy_sszb_nc,syzqy_zbgj_nc=:syzqy_zbgj_nc,syzqy_yygj_nc=:syzqy_yygj_nc,syzqy_wfplr_nc=:syzqy_wfplr_nc,syzqy_hj_nc=:syzqy_hj_nc,fzsyzqy_hj_nc=:fzsyzqy_hj_nc where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}
	
	public Map<String, Object> getZcfzb(int page, int pageSize,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));
		condition.add("a.TIMEVALUE", "FUZZY", where.get("TIMEVALUE"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM    ( SELECT a.id,b.DWMC,a.nd,");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ,");
		sb.append(" CASE a.TIMEVALUE WHEN 0 THEN '半年' WHEN 1 THEN '全年' ELSE NULL END AS TIMEVALUE ");
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_cwbb_zcfzgd a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
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
	
	public Map<String, Object> getZcfzbById(String id) {
		String sql = "select b.DWMC,DATE_FORMAT(a.KSSJ,'%Y-%m-%d') AS A,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS B,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_zcfzgd a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	
	@Override
	public String AddZcmxb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		obj.put("id", uuid);
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_zcmx ");	
		sb.append("  ( id,jg_id,use_id,ztbj,kssj,jssj,tjrq,nd,zyywcb1,zyywcb,zyywsjfj1,zyywsjfj,gzfy1,gzfy,qtywzc1,qtywzc,flf1,flf,");
		sb.append(" glfy1,glfy,jyf1,jyf,glfy_gzfy1,glfy_gzfy,ghjf1,ghjf,glfy_flf1,glfy_flf,shtc1,shtc,glfy_ywzdf1,glfy_ywzdf,");
		sb.append(" bgf1,bgf,glfy_bgf1,glfy_bgf,clf1,clf,glfy_qtsj1,glfy_qtsj,hf1,hf,glfy_qcfy1,glfy_qcfy,pxzlf1,pxzlf,glfy_zyfxjj1,glfy_zyfxjj,");
		sb.append(" hwf1,hwf,glfy_zyzrbx1,glfy_zyzrbx,zpf1,zpf,glfy_clf1,glfy_clf,zj1,zj,glfy_qtfy1,glfy_qtfy,zfgjj1,zfgjj,cwfy1,cwfy,");
		sb.append(" gwzxf1,gwzxf,yywzc1,yywzc,qt1,qt,zczj1,zczj,sz,agkj,zb)");
		sb.append("values ( :id,:jg_id,:use_id,:ztbj,:kssj,:jssj,sysdate(),:nd,:zyywcb1,:zyywcb,:zyywsjfj1,:zyywsjfj,:gzfy1,:gzfy,:qtywzc1,:qtywzc,:flf1,:flf,");	
		sb.append(" :glfy1,:glfy,:jyf1,:jyf,:glfy_gzfy1,:glfy_gzfy,:ghjf1,:ghjf,:glfy_flf1,:glfy_flf,:shtc1,:shtc,:glfy_ywzdf1,:glfy_ywzdf,");
		sb.append(" :bgf1,:bgf,:glfy_bgf1,:glfy_bgf,:clf1,:clf,:glfy_qtsj1,:glfy_qtsj,:hf1,:hf,:glfy_qcfy1,:glfy_qcfy,:pxzlf1,:pxzlf,:glfy_zyfxjj1,:glfy_zyfxjj,");
		sb.append(" :hwf1,:hwf,:glfy_zyzrbx1,:glfy_zyzrbx,:zpf1,:zpf,:glfy_clf1,:glfy_clf,:zj1,:zj,:glfy_qtfy1,:glfy_qtfy,:zfgjj1,:zfgjj,:cwfy1,:cwfy,");
		sb.append(" :gwzxf1,:gwzxf,:yywzc1,:yywzc,:qt1,:qt,:zczj1,:zczj,:sz,:agkj,:zb)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	@Override
	public void UpdateZcmxb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_zcmx ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,ztbj=:ztbj,kssj=:kssj,jssj=:jssj,tjrq=sysdate(),nd=:nd,zyywcb1=:zyywcb1,zyywcb=:zyywcb,zyywsjfj1=:zyywsjfj1,zyywsjfj=:zyywsjfj,");		
		sb.append(" gzfy1=:gzfy1,gzfy=:gzfy,qtywzc1=:qtywzc1,qtywzc=:qtywzc,flf1=:flf1,flf=:flf,glfy1=:glfy1,glfy=:glfy,jyf1=:jyf1,jyf=:jyf,glfy_gzfy1=:glfy_gzfy1,");
		sb.append(" glfy_gzfy=:glfy_gzfy,ghjf1=:ghjf1,ghjf=:ghjf,glfy_flf1=:glfy_flf1,glfy_flf=:glfy_flf,shtc1=:shtc1,shtc=:shtc,glfy_ywzdf1=:glfy_ywzdf1,glfy_ywzdf=:glfy_ywzdf,");
		sb.append(" bgf1=:bgf1,bgf=:bgf,glfy_bgf1=:glfy_bgf1,glfy_bgf=:glfy_bgf,clf1=:clf1,clf=:clf,glfy_qtsj1=:glfy_qtsj1,glfy_qtsj=:glfy_qtsj,hf1=:hf1,hf=:hf,");
		sb.append(" glfy_qcfy1=:glfy_qcfy1,glfy_qcfy=:glfy_qcfy,pxzlf1=:pxzlf1,pxzlf=:pxzlf,glfy_zyfxjj1=:glfy_zyfxjj1,glfy_zyfxjj=:glfy_zyfxjj,");
		sb.append(" hwf1=:hwf1,hwf=:hwf,glfy_zyzrbx1=:glfy_zyzrbx1,glfy_zyzrbx=:glfy_zyzrbx,zpf1=:zpf1,zpf=:zpf,glfy_clf1=:glfy_clf1,glfy_clf=:glfy_clf,zj1=:zj1,zj=:zj,");
		sb.append(" glfy_qtfy1=:glfy_qtfy1,glfy_qtfy=:glfy_qtfy,zfgjj1=:zfgjj1,zfgjj=:zfgjj,cwfy1=:cwfy1,cwfy=:cwfy,gwzxf1=:gwzxf1,gwzxf=:gwzxf,");
		sb.append(" yywzc1=:yywzc1,yywzc=:yywzc,qt1=:qt1,qt=:qt,zczj1=:zczj1,zczj=:zczj,sz=:sz,agkj=:agkj,zb=:zb where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}
	
	public Map<String, Object> getZcmxb(int page, int pageSize,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM    ( SELECT a.id,b.DWMC,a.nd,ZYYWCB,");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ");
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_cwbb_zcmx a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
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
	
	public Map<String, Object> getZcmxbById(String id) {
		String sql = "select b.DWMC,DATE_FORMAT(a.KSSJ,'%Y-%m-%d') AS A,DATE_FORMAT(a.JSSJ,'%Y-%m-%d') AS B,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_zcmx a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}

}
