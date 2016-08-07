package gov.gdgs.zs.service;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.dao.YwglDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hashids.Hashids;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdky.restfull.exception.InvalidRequestException;
import com.gdky.restfull.exception.ResourceNotFoundException;
import com.gdky.restfull.utils.HashIdUtil;

@Service
public class YwglService {

	@Resource
	private YwglDao ywglDao;

	public Map<String, Object> getYwbb(int page, int pageSize, String where) {
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
		Map<String,Object> rs = ywglDao.getYwbb(page, pageSize, map);
/*		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		ArrayList<Map<String,Object>> ls = (ArrayList<Map<String,Object>>) rs.get("data");
		for (Map<String,Object> item : ls){
			String id = hashids.encode((Long)item.get("id")) ;
			item.put("id", id);
		}*/

		return rs;
	}

	public Map<String, Object> getYwbbById(String hash) {
		Long id = HashIdUtil.decode(hash);
		Map<String,Object> obj = ywglDao.getYwbbById(id);
		return obj;
	}

	public Map<String, Object> getYwbbByJg(String hashId, int page,
			int pageSize, String where) {
		Long id = HashIdUtil.decode(hashId);
		Map<String,Object> obj = ywglDao.getYwbbByJg(id,page,pageSize,where);
		return obj;
	}

}
