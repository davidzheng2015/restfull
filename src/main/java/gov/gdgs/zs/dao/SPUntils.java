package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gdky.restfull.dao.BaseJdbcDao;

 class SPUntils extends BaseJdbcDao {
	/**
	 * 事务所变更审批
	 */
	 static final String JGBGSP = "402881831be2e6af011be3ab8b84000c"; 
	 
	 
	 /**
	  * 事务所端审批申请方法
	  * @param spsq
	  * @return
	  * @throws Exception
	  */
	public String swsSPqq(Map<String,Object> spsq) throws Exception{
		String sql ="select b.id,b.lcbz,b.roleid from zs_splc a,zs_splcbz b where a.id = b.LCID and a.id =? order by b.LCBZ";
		List<Map<String,Object>> ls =this.jdbcTemplate.queryForList(sql,new Object[]{spsq.get("lclx")});
		List listValue = new ArrayList();
		String suid = new Common().newUUID();
		listValue.add(suid);
		listValue.add(spsq.get("sid"));
		listValue.add(ls.get(0).get("id"));
		String insterspzx = "";
		String insterspzx2 = "";
		if(spsq.containsKey("jgid")){
			listValue.add(spsq.get("jgid"));
			insterspzx+="ZSJG_ID,";
			insterspzx2+="?,";
		}
		if(spsq.containsKey("csdm")){
			listValue.add(spsq.get("csdm"));
			insterspzx+="CS_ID,";
			insterspzx2+="?,";
		}
		this.jdbcTemplate.update("insert into zs_spzx (ID,SJID,LCBZID,ZTBJ,"+insterspzx+"TJSJ) values(?,?,?,'Y',"+insterspzx2+"sysdate())",new Object[]{listValue.toArray()});
		for(Map<String, Object> rec:ls){
			this.jdbcTemplate.update("insert into zs_spxx (ID,SPID,LCBZID) values(?,?,?)",new Object[]{new Common().newUUID(),suid,rec.get("id")});
		}
		return "ok";
	}
}
