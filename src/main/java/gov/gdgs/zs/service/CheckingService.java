package gov.gdgs.zs.service;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.dao.CheckingDao;

import javax.annotation.Resource;

import org.hashids.Hashids;
import org.springframework.stereotype.Service;

@Service
public class CheckingService {

	@Resource
	private CheckingDao chDao;
	
	public boolean checkSPing(String splx,String jgid){
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		switch (splx) {
		case "jgbg":
			return chDao.checkBGing((int)hashids.decode(jgid)[0]);
		case "jgzx":
			return chDao.checkZXing((int)hashids.decode(jgid)[0]);
		case "jghb":
			return chDao.checkHBing((int)hashids.decode(jgid)[0]);
		case "zysp":
			return chDao.checkZYSPing((int)hashids.decode(jgid)[0]);
		}
		return true;
	}
	
	public boolean checkIsBH(String spid){
		return this.chDao.checkIsBH(spid);
	}
	
	//检查身份证身份证是否已存在
	public boolean checkSFZH(String sfzh){
		return this.chDao.checkHadSFZH(sfzh);
	}
}
