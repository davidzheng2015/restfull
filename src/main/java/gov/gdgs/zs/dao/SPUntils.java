package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.dao.BaseJdbcDao;
@Repository
public class SPUntils extends BaseJdbcDao {
	/**
	 * 事务所变更审批
	 */
	public static final String JGBGSP = "402881831be2e6af011be3ab8b84000c"; 
	 
	 
	 /**
	  * 事务所端审批申请处理方法
	  * @param Map:sid,lclx,(选填：jgid,csdm)
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
	 
	 /**
	  * 中心端审批审核处理方法
	  * @param Map:spid,uid,uname,spyj,ispass
	  * @return boolean
	  * @throws Exception
	  */
	 @Transactional
		public boolean glzxSPtj(Map<String,Object> spsq) throws Exception{
		 StringBuffer sb = new StringBuffer();
		 sb.append("	 select a.ID,b.LCBZ,b.SPBZLX,b.BHBZLX,b.LCID,c.LCLXID,b.ROLEID,e.SJID from zs_spxx a,zs_splcbz b,zs_splc c,zs_spzx e,fw_user_role f ");
		 sb.append("	 where a.LCBZID=b.ID and b.LCID=c.ID and a.SPID=e.ID and f.ROLE_ID=b.ROLEID and c.ZTBJ=2");
		 sb.append("	 and a.spid = ? ");
		 sb.append("	 and f.USER_ID=?");
		 Map<String, Object> mp = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{spsq.get("spid"),spsq.get("uid")});
		 String sql ="update zs_spxx set SPYJ=?,ISPASS=?,USERID=?,SPRNAME=?,SPSJ=sysdate() where id =?";
		 this.jdbcTemplate.update(sql,new Object[]{spsq.get("spyj"),spsq.get("ispass"),spsq.get("uid"),spsq.get("uname"),mp.get("ID")});
		 if(spsq.get("ispass").equals("Y")){
			 int c = (int)mp.get("SPBZLX");
			 if(c==1||c==2){
				 this.jdbcTemplate.update("update zs_spzx set ZTBJ='N' where id =?",new Object[]{spsq.get("spid")});
				 switch((int)mp.get("LCLXID")){
				 case 2:
					 this.jdbcTemplate.update("update zs_jgbgspb set SPZT_DM='8',SPRQ=sysdate(),SPR_ID=? where id =?",
							 new Object[]{spsq.get("uid"),mp.get("SJID")});
					 this.jdbcTemplate.update("update zs_jg a,zs_jgbglsb b,zs_jgbgspb c set a.DWMC=b.DWMC,"
					 		+ "a.CS_DM=b.CS_DM,a.JGXZ_DM=b.JGXZ_DM,a.DZHI=b.DZHI,"
					 		+ "a.ZCZJ=b.ZCZJ,a.ZCDZ=b.ZCDZ,a.YYZZHM=b.YYZZHM,"
					 		+ "a.SWSZSCLSJ=b.SWSZSCLSJ where c.id =? and c.JGBGLSB_ID = b.id "
					 		+ "and c.jg_id = a.id",new Object[]{mp.get("SJID")});
					 break;
				 };
			 }else{
				 this.jdbcTemplate.update("update zs_spzx set LCBZID=? where id =?",
						 new Object[]{this.jdbcTemplate.queryForObject("select id from zs_splcbz where lcid=? and lcbz=?",
								 new Object[]{mp.get("LCID"),(int)mp.get("LCBZ")+1}, String.class),spsq.get("spid")});
			 };
		 }else if(spsq.get("ispass").equals("N")){
			 this.jdbcTemplate.update("update zs_spzx set ZTBJ='N' where id =?",new Object[]{spsq.get("spid")});
			 switch((int)mp.get("LCLXID")){
			 case 2:
				 this.jdbcTemplate.update("update zs_jgbgspb set SPZT_DM='3',SPRQ=sysdate(),SPR_ID=? where id =?",
						 new Object[]{spsq.get("uid"),mp.get("SJID")});
				 break;
				 }
			 }
		 
		 return true;
	 }
}
