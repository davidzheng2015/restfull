package gov.gdgs.zs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class PostSWSDao extends BaseDao{
	/**
	 * 审批项目更新
	 * @param spxm
	 */
	@Resource
	private SPUntils spUntils;
	
	
	public void updateSPXM(Map<String, Object> spxm,int id,int jgid) throws Exception{
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
		spsq.put("lclx", SPUntils.JGBGSP);
		spsq.put("jgid", jgid);
		spUntils.swsSPqq(spsq);//生成审批表记录
		for(Map<String, Object> rec:forupdate){//插入变更项目信息
			this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),rs,});
		}
	}
	
	/**
	 * 普通项目更新
	 * @param ptxm
	 */
	public void updatePTXM(Map<String, Object> ptxm){
		List<Map<String, Object>> forupdate = (List<Map<String, Object>>) ptxm.remove("bgjl");//获取并移除bgjl属性
		List<Object> listValue = new ArrayList<Object>();  //Map转List
		Iterator<String> it = ptxm.keySet().iterator();  
		while (it.hasNext()) {  
			String key = it.next().toString();  
			listValue.add(ptxm.get(key));  
		};
		try {
			String sql ="update zs_jg set DHUA=?,CZHEN=?,jyfw=?,yzbm=?,SZPHONE=?,JGZCH=?,SWDJHM=?,jgdmzh=?,GSYHMCBH=?,KHH=?,KHHZH=?,TXYXMING=?,XTYPHONE=?,XTYYX=?,SZYX=?,wangzhi=?,dzyj=?,yhdw=?,yhsj=?,gzbh=?,gzdw=?,gzry=?,gzsj=?,yzbh=?,yzdw=?,yzry=?,yzsj=?,TTHYBH=?,rhsj=?,JBQK=?,GLZD=?,GDDH=?,BGCSZCZM=? where id =?";
			String sql2 ="insert into zs_jglsbgxxb (MC,JZHI,XZHI,GXSJ,JGB_ID) values(?,?,?,sysdate(),?)";
			this.jdbcTemplate.update(sql,listValue.toArray());//更新数据库
			for(Map<String, Object> rec:forupdate){//插入变更项目信息
				this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),ptxm.get("jgid")});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 判断审批中
	 * @param jgid
	 * @return
	 */
	public boolean checkBGing(int jgid){
		if(this.jdbcTemplate.queryForList("select * from zs_jgbgspb where spzt_dm = 1 and jg_id =?",new Object[]{jgid}).size()!=0){
			return false;
		}
		return true;
	}
}
