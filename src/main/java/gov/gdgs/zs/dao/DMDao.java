package gov.gdgs.zs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class DMDao extends BaseJdbcDao {

	public List<Map<String, Object>> getDM_cs() {
		String sql = "select * from "+Constants.PROJECT_SCHEMA+"dm_cs order by"
		return null;
	}

}
