package gov.gdgs.zs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class PostSWSDao extends BaseDao{
	/**
	 * 审批项目更新
	 * @param ptxm
	 */
	public void updateSPXM(Map<String, Object> ptxm){
		List<Map<String, Object>> forupdate = (List<Map<String, Object>>) ptxm.remove("bgjl");
		List listValue = new ArrayList();  
	       Iterator it = ptxm.keySet().iterator();  
	       while (it.hasNext()) {  
	           String key = it.next().toString();  
	           listValue.add(ptxm.get(key));  
	       };
		try {
			String sql ="update zs_jg_kzxx set dhhm=?,czhm=?,jyfw=?,yzbm=?,szyddh=?,swdjhm=?,jgdmzh=?,gsyhbh=?,khyh=?,khyhzh=?,txyxm=?,txyyddh=?,txyyx=?,szyx=?,wangzhi=?,dzyj=?,yhdw=?,yhsj=?,gzbh=?,gzdw=?,gzry=?,gzsj=?,yzbh=?,yzdw=?,yzry=?,yzsj=?,tthyzcbh=?,rhsj=?,qkjj=?,swsnbglzd=?,dycgddh=?,bgcscqzm=? where id =?";
//			String sql2 ="insert into zs_jg_ptbgxmjl (dhhm,czhm,jyfw,yzbm,szyddh,swdjhm,jgdmzh,gsyhbh,khyh,khyhzh,txyxm,txyyddh,txyyx,szyx,wangzhi,dzyj,yhdw,yhsj,gzbh,gzdw,gzry,gzsj,yzbh,yzdw,yzry,yzsj,tthyzcbh,rhsj,qkjj,swsnbglzd,dycgddh,bgcscqzm,lrrq,jg_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate()) ";
			String sql2 ="insert into zs_jglsbgxxb (MC,JZHI,XZHI,GXSJ,JGB_ID) values(?,?,?,sysdate(),?)";
			this.jdbcTemplate.update(sql,listValue.toArray());
			for(Map<String, Object> rec:forupdate){
				this.jdbcTemplate.update(sql2,new Object[]{rec.get("mc"),rec.get("jzhi"),rec.get("xzhi"),ptxm.get("jgid"),});
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
		String zsbh = (String) ptxm.remove("zsbh");
		List listValue = new ArrayList();  
		Iterator it = ptxm.keySet().iterator();  
		while (it.hasNext()) {  
			String key = it.next().toString();  
			listValue.add(ptxm.get(key));  
		};
		try {
			String sql ="update zs_jg_kzxx set dhhm=?,czhm=?,jyfw=?,yzbm=?,szyddh=?,swdjhm=?,jgdmzh=?,gsyhbh=?,khyh=?,khyhzh=?,txyxm=?,txyyddh=?,txyyx=?,szyx=?,wangzhi=?,dzyj=?,yhdw=?,yhsj=?,gzbh=?,gzdw=?,gzry=?,gzsj=?,yzbh=?,yzdw=?,yzry=?,yzsj=?,tthyzcbh=?,rhsj=?,qkjj=?,swsnbglzd=?,dycgddh=?,bgcscqzm=? where id =?";
//			String sql2 ="insert into zs_jg_ptbgxmjl (dhhm,czhm,jyfw,yzbm,szyddh,swdjhm,jgdmzh,gsyhbh,khyh,khyhzh,txyxm,txyyddh,txyyx,szyx,wangzhi,dzyj,yhdw,yhsj,gzbh,gzdw,gzry,gzsj,yzbh,yzdw,yzry,yzsj,tthyzcbh,rhsj,qkjj,swsnbglzd,dycgddh,bgcscqzm,lrrq,jg_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate()) ";
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
