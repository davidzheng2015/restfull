package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import gov.gdgs.zs.dao.ClientsdsbDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClientsdsbService {

	
	@Resource
	private ClientsdsbDao clientsdsbDao;
	/*
	 * 行业人员统计表
	 */
	public Map<String, Object> getHyryqktjb(int page, int pageSize, int Jgid,String where) {
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
		Map<String, Object> rs = clientsdsbDao.getHyryqktjb(page, pageSize,Jgid, map);
		return rs;
	}
	public Map<String, Object> getHyryqktjbById(String id) {
		Map<String,Object> obj = clientsdsbDao.getHyryqktjbById(id);
		return obj;
	}
	public Map<String, Object> AddHyryqktjb (Map<String, Object> obj) {	
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String rs= clientsdsbDao.AddHyryqktjb(obj);
		map.put("id", rs);
		return map;
    }
	
	public void UpdateHyryqktjb(Map<String, Object> obj) {
		clientsdsbDao.UpdateHyryqktjb(obj);
	}
	
	public Map<String, Object> getOK(String jgid) {
		Map<String,Object> obj = clientsdsbDao.getOk(jgid);
		return obj;
	}
	/*
	 * 经营收入统计表
	 */
	public Map<String, Object> getJysrqkb(int page, int pageSize,int Jgid,String where) {
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
		Map<String, Object> rs = clientsdsbDao.getJysrqkb(page, pageSize,Jgid, map);
		return rs;
	}
	public Map<String, Object> getJysrqkbById(String id) {
		Map<String,Object> obj = clientsdsbDao.getJysrqkbById(id);
		return obj;
	}
	
	public Map<String, Object> AddJysrqkb (Map<String, Object> obj) {	
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String rs= clientsdsbDao.AddJysrqkb(obj);
		map.put("id", rs);
		return map;
    }
	
	public void UpdateJysrqkb(Map<String, Object> obj) {
		clientsdsbDao.UpdateJysrqkb(obj);
	}
	
	public Map<String, Object> getUpyear(String jgid){
		Map<String, Object >obj=clientsdsbDao.getUpyear(jgid);				
		return obj;
	}
 }