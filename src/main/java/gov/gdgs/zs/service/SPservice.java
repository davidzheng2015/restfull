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
public class SPservice {

	@Resource
	private SPDao spDao;
	
	public Map<String,Object> wspcx(int uid){
		return spDao.wspcx(uid);
	}
	
	public List<Map<String,Object>> cklc(int lid){
		return spDao.cklc(lid);
	}
	
	public Map<String,Object> swsbgsp(int pn,int ps,int uid,int lcid,String where){
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
		return spDao.swsbgsp(pn,ps,uid,lcid,qury);
	}
	public List<Map<String, Object>> spmxxx(String lcid,int sjid){
		switch (lcid) {
		case "jgbgsp":
			return spDao.swsbgspxx(sjid);
		}
		return null;
	}
	
	@Transactional
	public boolean sptj(Map<String,Object> spsq,String spid,int uid,String uname)throws Exception{
		spsq.put("spid", spid);
		spsq.put("uid", uid);
		spsq.put("uname", uname);
		return spDao.sptj(spsq);
	}
	
	@Transactional
	public void spsq(Map<String, Object> ptxm,String splx,int id,int jgid) throws Exception{
		switch (splx) {
		case "jgbgsp":
			this.spDao.swsbgsq(ptxm,id,jgid);
		}
	}
	
	@Transactional
	public void updatePTXM(Map<String,Object> ptxm,String splx,int jgid)throws Exception {
		 ptxm.put("jgid", jgid);
		 switch (splx) {
			case "jgbgsp":
		 this.spDao.updatePTXM(ptxm);
		 }
	}
}
