package gov.gdgs.zs.service;

import gov.gdgs.zs.dao.XttjbbDao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdky.restfull.utils.HashIdUtil;

@Service
@Transactional
public class XttjbbService {
	
	@Resource
	private XttjbbDao xttjbbDao;

	public Map<String, Object> getXttjbb(int page, int pageSize,
			String where) {
		// TODO Auto-generated method stub
		//Long jid = HashIdUtil.decode(jgid);
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
		return xttjbbDao.getXttjbb(page,pageSize,map);
	}

}
