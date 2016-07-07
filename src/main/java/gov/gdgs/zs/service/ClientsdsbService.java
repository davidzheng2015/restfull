package gov.gdgs.zs.service;

import java.util.HashMap;
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
	
	public Map<String, Object> getHyryqktjb(int page, int pageSize, String where,int JgId,int uId) {
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
		Map<String, Object> rs = clientsdsbDao.getHyryqktjb(page, pageSize, map);
		return rs;
	}
	public Map<String, Object> getHyryqktjbById(String id) {
		Map<String,Object> obj = clientsdsbDao.getHyryqktjbById(id);
		return obj;
	}
}
