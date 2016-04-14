package gov.gdgs.zs.service;

import gov.gdgs.zs.dao.YwglDao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class YwglService {

	@Resource
	private YwglDao ywglDao;

	public Map<String, Object> getYwxy() {
		Map<String,Object> obj = new LinkedHashMap<String,Object>();
		List<Map<String, Object>> ls = ywglDao.getYwxy();
		obj.put("data", ls) ;
		return obj;
	}

}
