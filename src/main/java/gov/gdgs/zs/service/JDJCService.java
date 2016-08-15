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
	
	//执业税务师年检表
	public Map<String, Object> getZyswsnjb(int page, int pageSize,
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
		Map<String, Object> rs = jdjcDao.getZyswsnjb(page, pageSize, map);
		return rs;
	}
    //经营规模统计表信息
	public Map<String, Object> getZyswsnjbById(long id) {
		Map<String,Object> obj = jdjcDao.getZyswsnjbById(id);
		return obj;
	}
	
	//执业税务师年检表
		public Map<String, Object> getWsbbb(int page, int pageSize,
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
			Map<String, Object> rs = jdjcDao.getWsbbb(page, pageSize, map);
			return rs;
		}
}
