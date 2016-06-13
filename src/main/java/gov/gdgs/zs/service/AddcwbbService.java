package gov.gdgs.zs.service;


import gov.gdgs.zs.dao.AddcwbbDao;
import gov.gdgs.zs.dao.IAddcwbbDao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AddcwbbService implements IAddcwbbService {
	
	@Resource
	private IAddcwbbDao iaddcwbbDao;
	
	@Resource
	private AddcwbbDao addcwbbDao;
	
	
	
	@Override
	public Map<String, Object> AddXjllb (Map<String, Object> obj) {	
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String rs= iaddcwbbDao.AddXjllb(obj);
		map.put("id", rs);
		return map;

	}
	
	@Override
	public void UpdateXjllb(Map<String, Object> obj) {
		iaddcwbbDao.UpdateXjllb(obj);
        
	}
	
	 //现金流量表
	public Map<String, Object> getXjllb(int page, int pageSize, String where) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (where != null) {
			try {
				where = java.net.URLDecoder.decode(where, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(where,
						new TypeReference<Map<String, Object>>() {
						});
			} catch (Exception e) {
			}
		}		
		Map<String, Object> rs = addcwbbDao.getXjllb(page, pageSize, map);
		return rs;
	}
	public Map<String, Object> getXjllbById(String id) {
		Map<String,Object> obj = addcwbbDao.getXjllbById(id);
		return obj;
	}
	
	

}
