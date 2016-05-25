package gov.gdgs.zs.service;

import java.util.Map;

import gov.gdgs.zs.dao.PostSWSDao;
import gov.gdgs.zs.dao.SWSDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class PostSWSBGService {

	@Resource
	private SWSDao swsDao;
	
	@Resource
	private PostSWSDao postSWSDao;
	
	public Map<String, Object> swsbgPost(int jgid) {
		return this.swsDao.swsxx(jgid);
	}
	public void updatePTXM(Map<String, Object> ptxm) {
		 this.postSWSDao.updatePTXM(ptxm);
	}
}
