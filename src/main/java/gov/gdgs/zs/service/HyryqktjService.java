package gov.gdgs.zs.service;

import java.util.HashMap;
import java.util.Map;

import gov.gdgs.zs.dao.HyryqktDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class HyryqktjService {
	@Resource
	private HyryqktDao hyryqktDao;

	public Map<String, Object> getHyryqktj(int page, int pageSize, String where) {
		// TODO Auto-generated method stub
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
		return hyryqktDao.getHyryqktj(page,pageSize,map);
	}
	
	
}
