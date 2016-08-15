package gov.gdgs.zs.service;

import gov.gdgs.zs.dao.GzApiDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GzApiService {
	
	@Resource
	private GzApiDao gzApiDao;

	public Map<String, Object> getSws(String year, String month, String day,
			String hour) {
		HashMap<String,Object> obj = new HashMap<String,Object>();
		List<Map<String,Object>> ls = gzApiDao.getSws();
		obj.put("code", 200);
		obj.put("total", ls.size());
		obj.put("data", ls);	
		
		return obj;
	}

	public Map<String, Object> getSwsjg(String year, String month, String day,
			String hour) {
		HashMap<String,Object> obj = new HashMap<String,Object>();
		List<Map<String,Object>> ls = gzApiDao.getSwsjg();
		obj.put("code", 200);
		obj.put("total", ls.size());
		obj.put("data", ls);	
		
		return obj;
	}

	public Map<String, Object> getYwba(String year, String month, String day,
			String hour) {
		HashMap<String,Object> obj = new HashMap<String,Object>();
		List<Map<String,Object>> ls = gzApiDao.getYwba();
		obj.put("code", 200);
		obj.put("total", ls.size());
		obj.put("data", ls);	
		
		return obj;
	}

	public Map<String, Object> getZsxy(String year, String month, String day,
			String hour) {
		HashMap<String,Object> obj = new HashMap<String,Object>();
		List<Map<String,Object>> ls = gzApiDao.getZsxy();
		obj.put("code", 200);
		obj.put("total", ls.size());
		obj.put("data", ls);	
		
		return obj;
	}

}
