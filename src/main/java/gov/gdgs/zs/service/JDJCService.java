package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.Map;

import gov.gdgs.zs.dao.JDJCDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JDJCService {
	@Resource
	private JDJCDao jdjcDao;
	
	public Map<String, Object> swsnjcx(int pn, int ps, String where) {
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
		return jdjcDao.swsnjcx(pn, ps, map);
	}
}
