package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.gdgs.zs.dao.SPDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(rollbackFor=Exception.class)
public class SPservice {

	@Resource
	private SPDao spDao;
	
	public Map<String,Object> wspcx(int uid){
		return spDao.wspcx(uid);
	}
	
	public List<Map<String,Object>> cklc(int lid){
		return spDao.cklc(lid);
	}
	/**
	 * 审批明细查询
	 * @param pn ps uid lcid cxlx where
	 * @return
	 */
	public Map<String,Object> wspmxcx(int pn,int ps,int uid,int lcid,String cxlx,String where){
		HashMap<String, Object> qury = new HashMap<String, Object>();
		if (where != null) {
			try {
				where = java.net.URLDecoder.decode(where, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				qury = mapper.readValue(where,
						new TypeReference<Map<String, Object>>() {
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		switch(cxlx){
		case "jgsl":
			return spDao.jgslspcx(pn,ps,uid,lcid,qury);
		case "jg":
			return spDao.jgspcx(pn,ps,uid,lcid,qury);
		case "ry":
			return spDao.ryspcx(pn,ps,uid,lcid,qury);
		};
		return null;
	}
	/**
	 * 审批项目详细信息
	 * @param lcid sjid
	 * @return
	 */
	public Object spmxxx(String lcid,String sjid){
		return spDao.spmxxx(lcid,sjid);
	}
	/**
	 * 上级驳回意见
	 * @param spid
	 * @param lcbz
	 * @return
	 */
	public Map<String, Object> sjbhyj(String spid,int lcbz){
		return spDao.sjbhyj(spid,lcbz);
	}
	/**
	 * 审批提交
	 * @param spsq
	 * @param spid
	 * @param uid
	 * @param uname
	 * @return
	 * @throws Exception
	 */
	public boolean sptj(Map<String,Object> spsq,String spid,int uid,String uname)throws Exception{
		spsq.put("spid", spid);
		spsq.put("uid", uid);
		spsq.put("uname", uname);
		return spDao.sptj(spsq);
	}
	/**
	 * 审批申请
	 * @param sqxm
	 * @param splx
	 * @throws Exception
	 */
	public void spsq(Map<String, Object> sqxm,String splx) throws Exception{
		switch (splx) {
		case "jgbgsq"://机构变更申请
			this.spDao.swsbgsq(sqxm);break;
		case"jgzxsq"://机构注销申请
			this.spDao.swszxsq(sqxm);break;
		case"jghbsq"://机构合并申请
			this.spDao.swshbsq(sqxm);break;
		case "zyswsbgsq"://执业变更申请
			this.spDao.zyswsbgsq(sqxm);break;
		case"zyzjsq"://执业转籍申请
			this.spDao.zyzjsq(sqxm);break;
		case"fzyswsbasq"://非执业备案申请
			this.spDao.fzyswsba(sqxm);break;
		case"fzyswszjsq"://非执业转籍申请
			this.spDao.fzyzjsq(sqxm);break;
		}
	}
	/**
	 * 非审批申请
	 * @param ptxm
	 * @param splx
	 * @throws Exception
	 */
	public void fspsq(Map<String,Object> ptxm,String splx)throws Exception {
		 switch (splx) {
			case "jgbgsq":
		 this.spDao.updatePTXM(ptxm);
		 }
	}

	/**
	 * 按身份证号查询个人非执业备案情况
	 * @para
	 *
	 */
	public Map<String, Object> getFzyswsBa(String sfzh) {
		List<Map<String,Object>> ls = spDao.getFzyswsBa(sfzh);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj = ls.get(0);
		HashMap<String,Object> rm = new HashMap<String,Object>();
		int dm = (int) obj.get("RYZT_DM");
		String spyj = (String)obj.get("SPYJ");
		spyj = (spyj != null && !spyj.isEmpty()) ?  spyj : "";
		
		if(dm==3){
			rm.put("dm", "3");
			rm.put("zt", "审批中");
			rm.put("spyj", spyj);
			rm.put("spr", obj.get("NAMES"));
			rm.put("spsj",obj.get("SPSJ"));
		}else if (dm == 1){
			rm.put("dm", "1");
			rm.put("zt", "审批通过");
			rm.put("spyj", spyj);
			rm.put("spr", obj.get("NAMES"));
			rm.put("spsj",obj.get("SPSJ"));
		}else if (dm == 2){
			rm.put("dm", "2");
			rm.put("zt", "申请驳回");
			rm.put("spyj", spyj);
			rm.put("spr", obj.get("NAMES"));
			rm.put("spsj",obj.get("SPSJ"));
		}else{
			rm.put("dm", "0");
			rm.put("zt", "没有记录");
			rm.put("spyj", "");
			rm.put("spr", obj.get("NAMES"));
			rm.put("spsj",obj.get("SPSJ"));
		}
		return rm;
	}
}
