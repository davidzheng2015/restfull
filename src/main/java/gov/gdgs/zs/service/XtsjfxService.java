package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.Map;

import gov.gdgs.zs.dao.XtsjfxDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class XtsjfxService {
	
	
	@Resource
	private XtsjfxDao xtsjfxDao;
	
	/*
	 * 机构年检数据分析
	 */
	public Map<String, Object> getJgnjsjfxb(int page, int pageSize, String where) {
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
		Map<String, Object> rs = xtsjfxDao.getJgnjsjfxb(page, pageSize, map);
		return rs;
	}

	public Map<String, Object> getRynjsjfxb(int page, int pageSize, String where) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(where != null){
			try{
				where = java.net.URLDecoder.decode(where, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(where,
						new TypeReference<Map<String, Object>>() {
						});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return xtsjfxDao.getRynjsjfxb(page, pageSize, map);
	}
}
