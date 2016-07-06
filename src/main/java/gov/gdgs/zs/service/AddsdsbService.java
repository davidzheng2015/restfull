package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import gov.gdgs.zs.dao.AddsdsbDao;
import gov.gdgs.zs.dao.IAddsdsbDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AddsdsbService implements IAddsdsbService{
	
	@Resource
	private IAddsdsbDao iaddsdsbDao;
	
	@Resource
	private AddsdsbDao addsdsbDao;
	
	@Override
	public Map<String, Object> AddSwsjbqkb (Map<String, Object> obj) {	
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String rs= iaddsdsbDao.AddSwsjbqkb(obj);
		map.put("id", rs);
		return map;

	}
	
	@Override
	public void UpdateSwsjbqkb(Map<String, Object> obj) {
		iaddsdsbDao.UpdateSwsjbqkb(obj);
	}
	
	 
		public Map<String, Object> getSwsjbqkb(int page, int pageSize,int Jgid,int Id, String where) {
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
			Map<String, Object> rs = addsdsbDao.getSwsjbqkb(page, pageSize, Jgid,Id, map);
			return rs;
		}
		public Map<String, Object> getSwsjbqkbById(String id) {
			Map<String,Object> obj = addsdsbDao.getSwsjbqkbById(id);
			return obj;
		}
		public Map<String, Object> getOK(String jgid) {
			Map<String,Object> obj = addsdsbDao.getLrze(jgid);
			return obj;
		}
		
		@Override
		public Map<String, Object> AddJygmtjb (Map<String, Object> obj) {	
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			String rs= iaddsdsbDao.AddJygmtjb(obj);
			map.put("id", rs);
			return map;

		}
		
		@Override
		public void UpdateJygmtjb(Map<String, Object> obj) {
			iaddsdsbDao.UpdateJygmtjb(obj);
		}
		
		
		//经营规模统计表
		public Map<String, Object> getJygmtjb(int page, int pageSize,int Id,int Jgid, String where) {
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
			Map<String, Object> rs = addsdsbDao.getJygmtjb(page, pageSize,Id,Jgid, map);
			return rs;
		}
		public Map<String, Object> getJygmtjbById(String id) {
			Map<String,Object> obj = addsdsbDao.getJygmtjbById(id);
			return obj;
		}
		
		public Map<String, Object> getOK1(String jgid) {
			Map<String,Object> obj = addsdsbDao.getOk(jgid);
			return obj;
		}
		
		//鉴证业务情况统计表
		
		@Override
		public Map<String, Object> AddJzywqktjb (Map<String, Object> obj) {	
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			String rs= iaddsdsbDao.AddJzywqktjb(obj);
			map.put("id", rs);
			return map;

		}
		
		@Override
		public void UpdateJzywqktjb(Map<String, Object> obj) {
			iaddsdsbDao.UpdateJzywqktjb(obj);
		}
		public Map<String, Object> getJzywqktjb(int page, int pageSize,int Id,int Jgid, String where) {
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
			Map<String, Object> rs = addsdsbDao.getJzywqktjb(page, pageSize, Id,Jgid,map);
			return rs;
		}
		public Map<String, Object> getJzywqktjbById(String id) {
			Map<String,Object> obj = addsdsbDao.getJzywqktjbById(id);
			return obj;
		}
		
		public Map<String, Object> getUpyear(String jgid){
			Map<String, Object >obj=addsdsbDao.getUpyear(jgid);
			return obj;
		}

}
