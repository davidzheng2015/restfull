package gov.gdgs.zs.service;

import gov.gdgs.zs.dao.CustomerDao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class CustomerService {
	
	@Resource
	private CustomerDao customerDao;

	public Map<String, Object> getCustomers(int page, int pageSize, String where) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (where != null) {
			try {
				where = java.net.URLDecoder.decode(where, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(where,
						new TypeReference<Map<String, Object>>() {
						});
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		return customerDao.getCustomers(page,pageSize,map);
	}
	public Map<String, Object> getCustomer(int page, int pageSize, String where) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("jid", where);
				return customerDao.getCustomers(page,pageSize,map);
	}

	public Map<String, Object> AddCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> updateCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> delCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

}
