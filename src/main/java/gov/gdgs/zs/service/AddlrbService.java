package gov.gdgs.zs.service;

import gov.gdgs.zs.dao.ILrbDao;
import gov.gdgs.zs.dao.LrbDao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdky.restfull.entity.AsideMenu;




@Service
public class AddlrbService implements IAddlrbService{
	
	@Resource
	private ILrbDao lrbDao;
	@Resource
	private LrbDao lrDao;
	@Override
	public Map<String, Object> addLrb (Map<String, Object> obj) {
		
		
		
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		
		String rs =  lrbDao.addLrb(obj);
		map.put("id", rs);
		return map;

	}
	@Override
	public void updateLrb(Map<String, Object> obj) {
		lrbDao.updateLrb(obj);
        
	}
	 //利润表
		public Map<String, Object> getlrb(int page, int pageSize,int Jgid, String where) {
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
			Map<String, Object> rs = lrDao.getlrb(page, pageSize,Jgid, map);
			return rs;
		}
		public Map<String, Object> getlrbById(String id) {
			Map<String,Object> obj = lrDao.getLrbById(id);
			return obj;
		}
		
		@Override
		public Map<String, Object> addLrfpb (Map<String, Object> obj) {
			
			
			
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			
			String rs =  lrbDao.addLrfpb(obj);
			map.put("id", rs);
			return map;

		}
		@Override
		public void updateLrfpb(Map<String, Object> obj) {
			lrbDao.updateLrfpb(obj);
	        
		}
		
		 //利润分配表
		public Map<String, Object> getlrfpb(int page, int pageSize,int Jgid, String where) {
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
			Map<String, Object> rs = lrDao.getLrfpb(page, pageSize,Jgid, map);
			return rs;
		}
		public Map<String, Object> getlrfpbById(String id) {
			Map<String,Object> obj = lrDao.getLrfpbById(id);
			return obj;
		}

}
