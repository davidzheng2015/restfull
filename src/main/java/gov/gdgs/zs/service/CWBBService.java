package gov.gdgs.zs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.dao.CWBBDao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.gdky.restfull.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.hashids.Hashids;

@Service
public class CWBBService {

	@Resource
	private CWBBDao cwbbDao;
   //支出明细表
	public Map<String, Object> getZcmxb(int page, int pageSize, String where) {
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
		Map<String, Object> rs = cwbbDao.getZcmxb(page, pageSize, map);
		return rs;
	}
	//支出明细表id
	public Map<String, Object> getZcmxbById(String id) {
		Map<String,Object> obj = cwbbDao.getZcmxbById(id);
		return obj;
	}
	 //利润分配表
		public Map<String, Object> getLrfpb(int page, int pageSize, String where) {
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
			Map<String, Object> rs = cwbbDao.getLrfpb(page, pageSize, map);
			return rs;
		}
		//利润分配表id
		public Map<String, Object> getLrfpbById(String id) {
			Map<String,Object> obj = cwbbDao.getLrfpbById(id);
			return obj;
		}
   //利润表
	public Map<String, Object> getLrb(int page, int pageSize,
			String where) {
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
		Map<String, Object> rs = cwbbDao.getLrb(page, pageSize, map);
		return rs;
	}
    //利润表id
	public Map<String, Object> getLrbById(String id) {
		Map<String,Object> obj = cwbbDao.getLrbById(id);
		return obj;
	}
	
	
	//现金流量表
		public Map<String, Object> getXjllb(int page, int pageSize,
				String where) {
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
			Map<String, Object> rs = cwbbDao.getXjllb(page, pageSize, map);
			return rs;
		}
	    //现金流量表信息
		public Map<String, Object> getXjllbById(String id) {
			Map<String,Object> obj = cwbbDao.getXjllbById(id);
			return obj;
		}
       
	

}
