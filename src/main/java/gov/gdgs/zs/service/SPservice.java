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
		return spDao.wspmxcx(pn,ps,uid,lcid,cxlx,qury);
	}
	
	public Object spmxxx(String lcid,String sjid){
		return spDao.spmxxx(lcid,sjid);
	}
	
	public Map<String, Object> sjbhyj(String spid,int lcbz){
		return spDao.sjbhyj(spid,lcbz);
	}
	
	public boolean sptj(Map<String,Object> spsq,String spid,int uid,String uname)throws Exception{
		spsq.put("spid", spid);
		spsq.put("uid", uid);
		spsq.put("uname", uname);
		return spDao.sptj(spsq);
	}
	
	public void spsq(Map<String, Object> sqxm,String splx) throws Exception{
		switch (splx) {
		case "jgbgsq":
			this.spDao.swsbgsq(sqxm,(int)sqxm.get("uid"),(int)sqxm.get("jgid"));break;
		//非执业备案
		case"fzyswsbasq":
			this.spDao.fzyswsba(sqxm);break;
		case"jgzxsq":
			this.spDao.swszxsq(sqxm);break;
		case"jghbsq":
			this.spDao.swshbsq(sqxm);break;
		}
	}
	
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
