package gov.gdgs.zs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class GzApiDao extends BaseJdbcDao {

	public List<Map<String, Object>> getSws() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ID,XM,XB,SFZH,SWSSWSXLH,CS,MZ,XL,ZZMM,ZYZGZH,ZYZCHM,YZBM,DH,YDDH,ZW,SFSC,SFCZR,SFFQR,SJZT, ");
		sb.append(" DATE_FORMAT(CSNY,'%Y-%m-%d') AS CSNY, ");
		sb.append(" DATE_FORMAT(ZYZGZQFRQ,'%Y-%m-%d') AS ZYZGZQFRQ, ");
		sb.append(" DATE_FORMAT(ZYZCRQ,'%Y-%m-%d') AS ZYZCRQ ");
		sb.append(" FROM gzapi_data_sws ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		return ls;
	}

	
}
