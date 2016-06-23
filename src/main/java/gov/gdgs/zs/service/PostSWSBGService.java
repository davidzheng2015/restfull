package gov.gdgs.zs.service;

import java.util.Map;

import gov.gdgs.zs.dao.PostSWSDao;
import gov.gdgs.zs.dao.SWSDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostSWSBGService {

	@Resource
	private SWSDao swsDao;
	
	@Resource
	private PostSWSDao postSWSDao;
	
	public Map<String, Object> swsbgPost(int jgid) {
		return this.swsDao.swsxx(jgid);
	}
	
	@Transactional
	public void updatePTXM(Map<String, Object> ptxm,int jgid) {
		 ptxm.put("jgid", jgid);
		 this.postSWSDao.updatePTXM(ptxm);
	}
	
	@Transactional
	public boolean updateSPXM(Map<String, Object> ptxm,int id,int jgid) throws Exception{
		if(postSWSDao.checkBGing(jgid)){//判断是否审批中
			this.postSWSDao.updateSPXM(ptxm,id,jgid);
			return true;
		}
		return false;
	}
}
