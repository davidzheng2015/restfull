package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Common;
import gov.gdgs.zs.untils.Condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SPDao extends BaseDao{
	/*-------------------------------中心端-------------------------------------*/
	/**
	 * 未审批查询
	 * @param uid
	 * @return List
	 */
	public Map<String,Object> wspcx(int uid){
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT ");
		sb.append("		d.PERANT_ID as lx,a.LCLXID as lid, d.MC as wsxm, COUNT(c.id) wss ");
		sb.append("		FROM zs_splc a,fw_user_role e,dm_lclx d,zs_splcbz b");
		sb.append("		LEFT JOIN zs_spzx c ON c.LCBZID=b.id AND c.ztbj='Y'");
		sb.append("		WHERE a.ID=b.LCID AND b.ROLEID=e.role_id AND d.ID=a.LCLXID AND a.ZTBJ=2 AND a.LCLXID<>'29' and e.USER_ID = ?");
		sb.append("		GROUP BY a.LCLXID");
		sb.append("		order by d.PERANT_ID,a.LCLXID");
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),new Object[]{uid});
		List<String> dl = new ArrayList<String>();
		dl.add("事务所审批类型");
		dl.add("执业税务师审批类型");
		dl.add("非执业注册税务师审批类型");
		dl.add("年检审批类型");
		dl.add("其他从业人员中心审批");
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("ls", ls);
		mp.put("dl", dl);
		return mp;
	}
	
	/**
	 * 查看流程
	 * @param lid
	 * @return
	 */
	public List<Map<String,Object>> cklc(int lid){
		String sql = "select a.ID,a.LCMC,a.LCMS from zs_splc a where a.LCLXID =? and a.ZTBJ=2 order by a.ID desc";
		return this.jdbcTemplate.query(sql,new Object[]{lid},
				new RowMapper<Map<String,Object>>(){
			public Map<String,Object> mapRow(ResultSet rs, int arg1) throws SQLException{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("lcmc", rs.getObject("LCMC"));
				map.put("lcms", rs.getObject("LCMS"));
				map.put("xxlc", jdbcTemplate.queryForList("SELECT b.lcbz,c.DESCRIPTION as js"
						+ ",case b.spbz WHEN 'Y' THEN '是' WHEN 'N' THEN '否' ELSE NULL END AS tjbz"
						+ ",CASE b.bhbz WHEN 'Y' THEN '是' WHEN 'N' THEN '否' ELSE NULL END AS bhbz"
						+ ",CASE b.sfhq WHEN 'Y' THEN '是' WHEN 'N' THEN '否' ELSE NULL END AS sfhq"
						+ " FROM zs_splcbz b,fw_role c"
						+ " WHERE b.lcid = ? and c.ID=b.roleid"
						+ " ORDER BY lcbz",new Object[]{rs.getObject("ID")}));
				return map;
				}
			});
	}
	
	/**
	 * 未审批明细查询
	 * @param pn,ps,uid,lcid,qury
	 * @return
	 */
	public Map<String,Object> swsbgsp(int pn,int ps,int uid,int lclxid,Map<String,Object> qury){
		Condition condition = new Condition();
		condition.add("e.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("c.tjsj", Condition.GREATER_EQUAL, qury.get("sbsj"));
		condition.add("c.tjsj", Condition.LESS_EQUAL, qury.get("sbsj2"));
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT 	SQL_CALC_FOUND_ROWS	@rownum:=@rownum+1 AS 'key',");
		sb.append("		e.dwmc, d.MC as wsxm,c.id,c.sjid,DATE_FORMAT(c.tjsj,'%Y-%m-%d') AS tjsj,e.id as jgid,a.id as lcid,");
		sb.append("		case f.yjxx when f.yjxx then f.yjxx else '无' end as yjxx,group_concat(concat(b.LCBZ,'.',h.DESCRIPTION)) as dqlcbz");
		sb.append("		FROM zs_splc a,dm_lclx d,zs_splcbz b,zs_spzx c,zs_jg e,zs_jgyjxxb f,fw_user_role g,fw_role h,(SELECT @rownum:=?) zs_jg");
		sb.append("		"+condition.getSql()+" ");
		sb.append("		and a.ID=b.LCID AND b.ROLEID=g.role_id and g.USER_ID=? AND d.ID=a.LCLXID AND a.ZTBJ=2 and b.ROLEID=h.ID AND a.LCLXID<>'29' and a.LCLXID=? and e.id=f.id");
		sb.append("		and c.LCBZID=b.id AND c.ztbj='Y' and e.ID=c.ZSJG_ID group by e.dwmc order by c.TJSJ desc");
		sb.append("		    LIMIT ?, ? ");
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add(uid);
		params.add(lclxid);
		params.add((pn-1)*ps);
		params.add(ps);
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
		int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
		String lcbzmx = this.jdbcTemplate.queryForObject("select group_concat(concat(a.LCBZ,'.',b.DESCRIPTION)) as lcbzmx from zs_splcbz a,fw_role b where a.LCID=? and a.ROLEID=b.ID order by a.LCBZ",new Object[]{ls.get(0).get("lcid")}, String.class);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		ob.put("dqlcbz", ls.get(0).get("dqlcbz"));
		ob.put("lcbzmx", lcbzmx);
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		return ob;
	}
	
	/**
	 * 事务所变更详细信息查看
	 * @param sjid
	 * @return
	 */
	public List<Map<String, Object>> swsbgspxx (int sjid){
		return this.jdbcTemplate.queryForList("select MC,XZHI,JZHI from zs_jgbgxxb where jgbgspb_id = ?",new Object[]{sjid});
	}
	
	 /**
	  * 中心端审批审核处理方法
	  * @param Map:spid,uid,uname,spyj,ispass
	  * @return boolean
	  * @throws Exception
	  */
	 @Transactional
		public boolean sptj(Map<String,Object> spsq) throws Exception{
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
			 int c = (int)mp.get("BHBZLX");
			 if(c==1||c==2){
				 this.jdbcTemplate.update("update zs_spzx set ZTBJ='N' where id =?",new Object[]{spsq.get("spid")});
				 switch((int)mp.get("LCLXID")){
				 case 2:
					 this.jdbcTemplate.update("update zs_jgbgspb set SPZT_DM='3',SPRQ=sysdate(),SPR_ID=? where id =?",
							 new Object[]{spsq.get("uid"),mp.get("SJID")});
					 break;
				 }
			 }else{
				 this.jdbcTemplate.update("update zs_spzx set LCBZID=? where id =?",
						 new Object[]{this.jdbcTemplate.queryForObject("select id from zs_splcbz where lcid=? and lcbz=?",
								 new Object[]{mp.get("LCID"),(int)mp.get("LCBZ")-1}, String.class),spsq.get("spid")});
			 }
		 }
		 
		 return true;
	 }
	/*-------------------------------事务所端-------------------------------------*/
	 /**
		 * 事务所变更审批项目更新
		 * @param spxm
		 */
		public void swsbgsq(Map<String, Object> spxm,int id,int jgid) throws Exception{
			List<Map<String, Object>> forupdate = (List<Map<String, Object>>) spxm.remove("bgjl");//获取变更项目
			List<Object> listValue = new ArrayList<Object>();  //Map转List
			Iterator<String> it = spxm.keySet().iterator();  
			while (it.hasNext()) {  
				String key = it.next().toString();  
				listValue.add(spxm.get(key));  
			};
			String sql ="insert into zs_jgbgspb (JG_ID,SPZT_DM,BGRQ,TXR_ID,JGBGLSB_ID) values(?,'1',sysdate(),?,?)";
			String sql2 ="insert into zs_jgbgxxb (MC,JZHI,XZHI,GXSJ,JGBGSPB_ID) values(?,?,?,sysdate(),?)";
			String sql3 ="insert into zs_jgbglsb (DWMC,CS_DM,JGXZ_DM,DZHI,ZCZJ,ZCDZ,YYZZHM,SWSZSCLSJ) values(?,?,?,?,?,?,?,?)";
			Number rs1 = this.insertAndGetKeyByJdbc(sql3,listValue.toArray(),new String[] {"ID"});//插入临时表，获取自动生成id
			Number rs = this.insertAndGetKeyByJdbc(sql, new Object[]{jgid,id,rs1},new String[] {"ID"});//插入业务表，获取自动生成id
			Map<String,Object> spsq=new HashMap<>();//设置生成审批表方法参数
			spsq.put("sid", rs);
			//判断是否分所
			if(this.jdbcTemplate.queryForObject("select JGXZ_DM from zs_jg where id =?",new Object[]{jgid},int.class)!=3){
				spsq.put("lclx", "402881831be2e6af011be3ab8b84000c");
			}else{
				spsq.put("lclx", "40288087228378910122838ecac50022");
			}
			spsq.put("jgid", jgid);
			swsSPqq(spsq);//生成审批表记录
			for(Map<String, Object> rec:forupdate){//插入变更项目信息
				this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),rs,});
			}
		}
		
		/**
		  * 审批申请处理方法
		  * @param Map:sid,lclx,(选填：jgid,csdm)
		  * @return boolean
		  * @throws Exception
		  */
		 @Transactional
		 boolean swsSPqq(Map<String,Object> spsq) throws Exception{
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
	
		 /*-------------------------------非审批申请-------------------------------------*/
		 /**
			 * 普通项目更新
			 * @param ptxm
			 */
			public void updatePTXM(Map<String, Object> ptxm)throws Exception{
				List<Map<String, Object>> forupdate = (List<Map<String, Object>>) ptxm.remove("bgjl");//获取并移除bgjl属性
				List<Object> listValue = new ArrayList<Object>();  //Map转List
				Iterator<String> it = ptxm.keySet().iterator();  
				while (it.hasNext()) {  
					String key = it.next().toString();  
					listValue.add(ptxm.get(key));  
				};
				String sql ="update zs_jg set DHUA=?,CZHEN=?,jyfw=?,yzbm=?,SZPHONE=?,JGZCH=?,SWDJHM=?,jgdmzh=?,GSYHMCBH=?,KHH=?,KHHZH=?,TXYXMING=?,XTYPHONE=?,XTYYX=?,SZYX=?,wangzhi=?,dzyj=?,yhdw=?,yhsj=?,gzbh=?,gzdw=?,gzry=?,gzsj=?,yzbh=?,yzdw=?,yzry=?,yzsj=?,TTHYBH=?,rhsj=?,JBQK=?,GLZD=?,GDDH=?,BGCSZCZM=? where id =?";
				String sql2 ="insert into zs_jglsbgxxb (MC,JZHI,XZHI,GXSJ,JGB_ID) values(?,?,?,sysdate(),?)";
				this.jdbcTemplate.update(sql,listValue.toArray());//更新数据库
				for(Map<String, Object> rec:forupdate){//插入变更项目信息
					this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),ptxm.get("jgid")});
				}
					
			}
}

