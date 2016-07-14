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
	public Map<String, Object> getXjllb(int page, int pageSize, int Jgid,String where) {
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
		Map<String, Object> rs = addcwbbDao.getXjllb(page, pageSize,Jgid, map);
		return rs;
	}
	public Map<String, Object> getXjllbById(String id) {
		Map<String,Object> obj = addcwbbDao.getXjllbById(id);
		return obj;
	}
	
	@Override
	public Map<String, Object> AddZcfzb (Map<String, Object> obj) {	
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String rs= iaddcwbbDao.AddZcfzb(obj);
		map.put("id", rs);
		return map;

	}
	
	@Override
	public void UpdateZcfzb(Map<String, Object> obj) {
		iaddcwbbDao.UpdateZcfzb(obj);
        
	}
	
	 //资产负债表
		public Map<String, Object> getZcfzb(int page, int pageSize,int Jgid, String where) {
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
			Map<String, Object> rs = addcwbbDao.getZcfzb(page, pageSize,Jgid, map);
			return rs;
		}
		public Map<String, Object> getZcfzbById(String id) {
			Map<String,Object> obj = addcwbbDao.getZcfzbById(id);
			return obj;
		}
		
		@Override
		public Map<String, Object> AddZcmxb (Map<String, Object> obj) {	
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			String rs= iaddcwbbDao.AddZcmxb(obj);
			map.put("id", rs);
			return map;

		}
		
		@Override
		public void UpdateZcmxb(Map<String, Object> obj) {
			iaddcwbbDao.UpdateZcmxb(obj);
	        
		}
		
		 //资产负债表
		public Map<String, Object> getZcmxb(int page, int pageSize,int Jgid, String where) {
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
			Map<String, Object> rs = addcwbbDao.getZcmxb(page, pageSize,Jgid, map);
			return rs;
		}
		public Map<String, Object> getZcmxbById(String id) {
			Map<String,Object> obj = addcwbbDao.getZcmxbById(id);
			return obj;
		}
	
	

}
