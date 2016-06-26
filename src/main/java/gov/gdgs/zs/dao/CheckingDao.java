package gov.gdgs.zs.dao;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
@Repository
public class CheckingDao extends BaseJdbcDao{
	/**
	 * 判断事务所变更审批中
	 * @param jgid
	 * @return false--审批中
	 */
	public boolean checkBGing(int jgid){
		if(this.jdbcTemplate.queryForList("select * from zs_jgbgspb where spzt_dm = 1 and jg_id =?",new Object[]{jgid}).size()!=0){
			return false;
		}
		return true;
	}
}
