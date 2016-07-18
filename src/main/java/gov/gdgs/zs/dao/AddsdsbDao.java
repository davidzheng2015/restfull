package gov.gdgs.zs.dao;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hashids.Hashids;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

@Repository
public class AddsdsbDao extends BaseJdbcDao  implements IAddsdsbDao{
	
	
	
	@Override
	public String AddSwsjbqkb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");		
		obj.put("id", uuid);
		
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_swsjbqk ");	
		sb.append("  ( id,jg_id,use_id,nd,jgxz_dm,frdbxm,czrs,hhrs,ryzs,zyzcswsrs,zczj,yysr,zcze,srze,lrze,cs_dm,");
		sb.append(" wths,sbrq,ztbj,tianbiaoren,suozhang)");
		sb.append("values ( :id,:jg_id,:use_id,:nd,:jgxz_dm,:frdbxm,:czrs,:hhrs,:ryzs,:zyzcswsrs,:zczj,:yysr,:zcze,");	
		sb.append("  :srze,:lrze,:cs_dm,:wths,sysdate(),:ztbj,:tianbiaoren,:suozhang)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	@Override
	public void UpdateSwsjbqkb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_swsjbqk ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,nd=:nd,jgxz_dm=:jgxz_dm,frdbxm=:frdbxm,czrs=:czrs,hhrs=:hhrs,ryzs=:ryzs,");	
		sb.append(" zyzcswsrs=:zyzcswsrs,zczj=:zczj,yysr=:yysr,zcze=:zcze,srze=:srze,lrze=:lrze,cs_dm=:cs_dm,");
		sb.append(" wths=:wths,sbrq=sysdate(),ztbj=:ztbj,tianbiaoren=:tianbiaoren,suozhang=:suozhang where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
		
	
	}
	
	public Map<String, Object> getSwsjbqkb(int page, int pageSize,int Jgid,int Id,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM   ( select a.id,b.id as jgid,b.DWMC,a.nd,a.FRDBXM,a.CZRS,a.HHRS,a.ZYZCSWSRS,a.RYZS,a.ZCZJ,a.YYSR,");	
		sb.append(" case a.JGXZ_DM when 1 then '合伙事务所' when 2 then '有限公司' when 3 then '无'  else null end as JGXZ,");	
		sb.append(" case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when 3 then '退回' else null end as ZTBJ");		
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_sdsb_swsjbqk a,zs_jg b,(SELECT @rownum:=?) temp");
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
		obj.put("jg_id", Jgid);
		obj.put("Id", Id);
		
		return obj;
	}
	
	public Map<String, Object> getSwsjbqkbById(String id) {
		String sql = "select b.DWMC,c.MC as cs,a.JGXZ_DM as jgxzdm,b.CS_DM as csdm,case a.JGXZ_DM when 1 then '合伙事务所' when 2 then '有限公司' when 3 then '无'  else null end as JGXZ,"
				+ "a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_swsjbqk a, zs_jg b,dm_cs c where a.jg_id = b.id and b.CS_DM=c.ID and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	
	public Map<String, Object> getLrze(String jgid) {
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		int gid = (int)hashids.decode(jgid)[0];
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_lrgd a,zs_jg b where jg_id=?  and timevalue='1' and a.JG_ID=b.ID and nd=( select max(nd) as mnd from zs_cwbb_lrgd where  timevalue='1')";
		List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql,gid);
		Map<String,Object> ob = new HashMap<>();
		ob.put("upyear", rs);
			return ob;
	}
	
	
	@Override
	public String AddJygmtjb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		obj.put("id", uuid);
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_jygmtjb ");	
		sb.append("  ( id,jg_id,use_id,snsrze,bnsrze_hj,bnsrze_ssfw,bnsrze_ssjz,bnsrze_qtyw,nd,sbrq,ztbj,tbr,sz)");
		sb.append("values ( :id,:jg_id,:use_id,:snsrze,:bnsrze_hj,:bnsrze_ssfw,:bnsrze_ssjz,:bnsrze_qtyw,:nd,sysdate(),:ztbj,:tbr,:sz)");	
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	@Override
	public void UpdateJygmtjb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_jygmtjb ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,snsrze=:snsrze,bnsrze_hj=:bnsrze_hj,bnsrze_ssfw=:bnsrze_ssfw,bnsrze_ssjz=:bnsrze_ssjz,");	
		sb.append(" bnsrze_qtyw=:bnsrze_qtyw,nd=:nd,sbrq=sysdate(),ztbj=:ztbj,tbr=:tbr,sz=:sz where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
	}
	
	public Map<String, Object> getJygmtjb(int page, int pageSize,int Id,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM   ( select a.id,a.SNSRZE,a.nd,a.BNSRZE_HJ,a.BNSRZE_SSFW,a.BNSRZE_SSJZ,a.BNSRZE_QTYW,");	
		sb.append(" case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when 3 then '退回' else null end as ZTBJ");		
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_sdsb_jygmtjb a,zs_jg b,(SELECT @rownum:=?) temp");
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
		obj.put("jgid", Id);
		return obj;
	}
	
	public Map<String, Object> getJygmtjbById(String id) {
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_jygmtjb a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
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
	
	@Override
	public String AddJzywqktjb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		obj.put("id", uuid);
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_jzywqktjb ");	
		sb.append(" ( id,jg_id,use_id,nd,sbrq,ztbj,hsqjje_hs0,hsqjje_je0,hsqjje_hs,hsqjje_je,tzynsdse_hs0,tzynsdse_je0,");
		sb.append(" tzynsdse_hs,tzynsdse_je,tjynsdse_hs0,tjynsdse_je0,tjynsdse_hs,tjynsdse_je,mbksje_hs0,mbksje_je0,mbksje_hs,mbksje_je,");
		sb.append("  ccsskc_hs0,ccsskc_je0,ccsskc_hs,ccsskc_je,tdzzsqsjz_hs0,tdzzsqsjz_je0,tdzzsqsjz_hs,tdzzsqsjz_je,");
		sb.append(" qtjz_hs0,qtjz_je0,qtjz_hs,qtjz_je,gxjsqyrdqzyw_hs0,gxjsqyrdqzyw_je0,gxjsqyrdqzyw_hs,gxjsqyrdqzyw_je,qyzxswdeskjsjzyw_hs0,qyzxswdeskjsjzyw_je0,");
		sb.append(" qyzxswdeskjsjzyw_hs,qyzxswdeskjsjzyw_je,yffjjkcjzyw_hs0,yffjjkcjzyw_je0,yffjjkcjzyw_hs,yffjjkcjzyw_je,qt_hs0,qt_je0,qt_hs,qt_je,tianbiaoren,suozhang)");
		sb.append("values ( :id,:jg_id,:use_id,:nd,sysdate(),:ztbj,:hsqjje_hs0,:hsqjje_je0,:hsqjje_hs,:hsqjje_je,:tzynsdse_hs0,:tzynsdse_je0,");
		sb.append(" :tzynsdse_hs,:tzynsdse_je,:tjynsdse_hs0,:tjynsdse_je0,:tjynsdse_hs,:tjynsdse_je,:mbksje_hs0,:mbksje_je0,:mbksje_hs,:mbksje_je,");
		sb.append(" :ccsskc_hs0,:ccsskc_je0,:ccsskc_hs,:ccsskc_je,:tdzzsqsjz_hs0,:tdzzsqsjz_je0,:tdzzsqsjz_hs,:tdzzsqsjz_je,:qtjz_hs0,:qtjz_je0,");
		sb.append(" :qtjz_hs,:qtjz_je,:gxjsqyrdqzyw_hs0,:gxjsqyrdqzyw_je0,:gxjsqyrdqzyw_hs,:gxjsqyrdqzyw_je,:qyzxswdeskjsjzyw_hs0,:qyzxswdeskjsjzyw_je0,");
		sb.append(" :qyzxswdeskjsjzyw_hs,:qyzxswdeskjsjzyw_je,:yffjjkcjzyw_hs0,:yffjjkcjzyw_je0,:yffjjkcjzyw_hs,:yffjjkcjzyw_je,:qt_hs0,:qt_je0,:qt_hs,:qt_je,:tianbiaoren,:suozhang)");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		int count=named.update(sb.toString(), obj);
		if(count==0){
		return null;
	 }else {
		return uuid;
	 }
	}
	
	@Override
	public void UpdateJzywqktjb(Map <String,Object> obj) {
		 StringBuffer sb = new StringBuffer("update "
				+ Config.PROJECT_SCHEMA + "zs_sdsb_jzywqktjb ");
		sb.append(" set jg_id=:jg_id,use_id=:use_id,nd=:nd,sbrq=sysdate(),ztbj=:ztbj,hsqjje_hs0=:hsqjje_hs0,hsqjje_je0=:hsqjje_je0,hsqjje_hs=hsqjje_hs,");
		sb.append(" hsqjje_je=:hsqjje_je,tzynsdse_hs0=:tzynsdse_hs0,tzynsdse_je0=:tzynsdse_je0,tzynsdse_hs=:tzynsdse_hs,tzynsdse_je=:tzynsdse_je,");
		sb.append(" tjynsdse_hs0=:tjynsdse_hs0,tjynsdse_je0=:tjynsdse_je0,tjynsdse_hs=:tjynsdse_hs,tjynsdse_je=:tjynsdse_je,mbksje_hs0=:mbksje_hs0,");
		sb.append(" mbksje_je0=:mbksje_je0,mbksje_hs=:mbksje_hs,mbksje_je=:mbksje_je,ccsskc_hs0=:ccsskc_hs0,ccsskc_je0=:ccsskc_je0,");
		sb.append(" ccsskc_hs=:ccsskc_hs,ccsskc_je=:ccsskc_je,tdzzsqsjz_hs0=:tdzzsqsjz_hs0,tdzzsqsjz_je0=:tdzzsqsjz_je0,tdzzsqsjz_hs=:tdzzsqsjz_hs,");
		sb.append(" tdzzsqsjz_je=:tdzzsqsjz_je,qtjz_hs0=:qtjz_hs0,qtjz_je0=:qtjz_je0,qtjz_hs=:qtjz_hs,qtjz_je=:qtjz_je,gxjsqyrdqzyw_hs0=:gxjsqyrdqzyw_hs0,");
		sb.append(" gxjsqyrdqzyw_je0=:gxjsqyrdqzyw_je0,gxjsqyrdqzyw_hs=:gxjsqyrdqzyw_hs,gxjsqyrdqzyw_je=:gxjsqyrdqzyw_je,");
		sb.append(" qyzxswdeskjsjzyw_hs0=:qyzxswdeskjsjzyw_hs0,qyzxswdeskjsjzyw_je0=:qyzxswdeskjsjzyw_je0,qyzxswdeskjsjzyw_hs=:qyzxswdeskjsjzyw_hs,");
		sb.append(" qyzxswdeskjsjzyw_je=:qyzxswdeskjsjzyw_je,yffjjkcjzyw_hs0=:yffjjkcjzyw_hs0,yffjjkcjzyw_je0=:yffjjkcjzyw_je0,yffjjkcjzyw_hs=:yffjjkcjzyw_hs,");
		sb.append(" yffjjkcjzyw_je=:yffjjkcjzyw_je,qt_hs0=:qt_hs0,qt_je0=:qt_je0,qt_hs=:qt_hs,qt_je=:qt_je,tianbiaoren=:tianbiaoren,suozhang=:suozhang where id=:id");
		NamedParameterJdbcTemplate named=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		named.update(sb.toString(), obj);
	}
	
	public Map<String, Object> getJzywqktjb(int page, int pageSize,int Id,int Jgid,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));		

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM   ( select a.id,a.nd,b.DWMC,a.HSQJJE_HS,a.HSQJJE_JE,a.TZYNSDSE_HS,a.TZYNSDSE_JE,");	
		sb.append("  a.TJYNSDSE_JE,a.TJYNSDSE_HS,");
		sb.append(" case a.ZTBJ when 1 then '提交' when 2 then '通过' when 0 then '保存' when 3 then '退回' else null end as ZTBJ");		
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_sdsb_jzywqktjb a,zs_jg b,(SELECT @rownum:=?) temp");
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
		obj.put("jgid", Id);
		return obj;
	}
	
	public Map<String, Object> getJzywqktjbById(String id) {
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_jzywqktjb a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	
	public Map<String, Object> getUpyear(String jgid) {
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		int gid = (int)hashids.decode(jgid)[0];
		String sql = "select a.nd,b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_sdsb_jzywqktjb a,zs_jg b where jg_id=? and a.JG_ID=b.ID and a.ND=( date_format(sysdate(),'%Y')-2)";
		List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql,gid);
		Map<String,Object> ob = new HashMap<>();
		ob.put("upyear", rs);
//		if(ob.size()>0){
			return ob;
//		}else{
//			return null;
//		}
	}

}
