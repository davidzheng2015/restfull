package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.gdgs.zs.dao.SPDao;
import gov.gdgs.zs.dao.SPUntils;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SPservice {

	@Resource
	private SPDao spDao;
	
	@Resource
	private SPUntils spUntils;
	
	public Map<String,Object> wspcx(int uid){
		return spDao.wspcx(uid);
	}
	
	public List<Map<String,Object>> cklc(int lid){
		return spDao.cklc(lid);
	}
	
	public Map<String,Object> swsbgsp(int pn,int ps,int uid,int lcid){
		return spDao.swsbgsp(pn,ps,uid,lcid);
	}
	public List<Map<String, Object>> swsbgspxx(int uid){
		return spDao.swsbgspxx(uid);
	}
	
	@Transactional
	public boolean sptj(Map<String,Object> spsq,int uid,String uname)throws Exception{
		spsq.put("uid", uid);
		spsq.put("uname", uname);
		return spUntils.glzxSPtj(spsq);
	}
}
