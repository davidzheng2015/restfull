package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.dao.BaseJdbcDao;
@Repository
 class SPUntils extends BaseJdbcDao {
	/**
	 * 事务所变更审批
	 */
	 static final String JGBGSP = "402881831be2e6af011be3ab8b84000c"; 
	 
	 
	 /**
	  * 事务所端审批申请方法
	  * @param Map:sid,lclx,(可填：jgid,csdm)
	  * @return boolean
	  * @throws Exception
	  */
	 @Transactional
	public boolean swsSPqq(Map<String,Object> spsq) throws Exception{
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
}
