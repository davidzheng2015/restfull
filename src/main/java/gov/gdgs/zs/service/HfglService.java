package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.Map;

import gov.gdgs.zs.dao.HfglDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(rollbackFor=Exception.class)
public class HfglService {
	@Resource
	private HfglDao hfglDao;
	
	public Map<String, Object> hyhfjnqk(int pn, int ps, String where) throws Exception {
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
		return hfglDao.hyhfjnqk(pn, ps, map);
	}
	public Map<String, Object> fpdy(int pn, int ps, String where) throws Exception {
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
		return hfglDao.fpdy(pn, ps, map);
	}

}
