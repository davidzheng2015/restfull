package gov.gdgs.zs.service;

import gov.gdgs.zs.dao.YwglDao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class YwglService {

	@Resource
	private YwglDao ywglDao;

	public List<Map<String, Object>> getYwxy() {
		return ywglDao.getYwxy();
	}

}
