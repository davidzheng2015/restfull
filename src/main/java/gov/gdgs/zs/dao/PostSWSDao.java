package gov.gdgs.zs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PostSWSDao extends BaseDao{
	/**
	 * 审批项目更新
	 * @param spxm
	 */
	public void updateSPXM(Map<String, Object> spxm,int id){
		List<Map<String, Object>> forupdate = (List<Map<String, Object>>) spxm.get("bgjl");
		try {
			String sql ="insert into zs_jgbgspb (JG_ID,SPZT_DM,BGRQ,TXR_ID) values(?,'1',sysdate(),?)";
			String sql2 ="insert into zs_jgbgxxb (MC,JZHI,XZHI,GXSJ,JGBGSPB_ID) values(?,?,?,sysdate(),?)";
			Number rs = this.insertAndGetKeyByJdbc(sql, new Object[]{spxm.get("jgid"),id},
					new String[] { "id" });
			Map<String,Object> spsq=new HashMap<>();
			spsq.put("sid", rs);
			spsq.put("lclx", SPUntils.JGBGSP);
			spsq.put("jgid", spxm.get("jgid"));
			new SPUntils().swsSPqq(spsq);
			for(Map<String, Object> rec:forupdate){
				this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),rs,});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 普通项目更新
	 * @param ptxm
	 */
	public void updatePTXM(Map<String, Object> ptxm){
		List<Map<String, Object>> forupdate = (List<Map<String, Object>>) ptxm.remove("bgjl");
		List listValue = new ArrayList();  
		Iterator it = ptxm.keySet().iterator();  
		while (it.hasNext()) {  
			String key = it.next().toString();  
			listValue.add(ptxm.get(key));  
		};
		try {
			String sql ="update zs_jg set DHUA=?,CZHEN=?,jyfw=?,yzbm=?,SZPHONE=?,JGZCH=?,SWDJHM=?,jgdmzh=?,GSYHMCBH=?,KHH=?,KHHZH=?,TXYXMING=?,XTYPHONE=?,XTYYX=?,SZYX=?,wangzhi=?,dzyj=?,yhdw=?,yhsj=?,gzbh=?,gzdw=?,gzry=?,gzsj=?,yzbh=?,yzdw=?,yzry=?,yzsj=?,TTHYBH=?,rhsj=?,JBQK=?,GLZD=?,GDDH=?,BGCSZCZM=? where id =?";
			String sql2 ="insert into zs_jglsbgxxb (MC,JZHI,XZHI,GXSJ,JGB_ID) values(?,?,?,sysdate(),?)";
			this.jdbcTemplate.update(sql,listValue.toArray());
			for(Map<String, Object> rec:forupdate){
				this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),ptxm.get("jgid"),});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
