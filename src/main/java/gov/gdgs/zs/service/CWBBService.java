package gov.gdgs.zs.service;


import java.util.HashMap;
import java.util.Map;

import gov.gdgs.zs.dao.CWBBDao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.annotation.Resource;
public class CWBBService {
	@Resource 
	 	private CWBBDao cwbbDao; 
	 
	 
	 	public Map<String, Object> lrfp(int page, int pageSize, String where) { 
	 		HashMap<String, Object> map = new HashMap<String, Object>(); 
	 		if (where != null) { 
	 			try { 
	 				where = java.net.URLDecoder.decode(where, "UTF-8"); 
	 				ObjectMapper mapper = new ObjectMapper(); 
	 				map = mapper.readValue(where, 
	 						new TypeReference<Map<String, Object>>() { 
	 						}); 
	 			} catch (Exception e) { 
	 				e.printStackTrace(); 
	 			} 
	 		} 
	 
	 
	 		return cwbbDao.lrfp(page, pageSize, map); 
	 	} 


}
