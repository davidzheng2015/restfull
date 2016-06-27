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
	
	/**
	 * 判断是否存在重复身份证号
	 * @param sfzh
	 * @return false--身份证号重复
	 */
	public boolean checkHadSFZH(String sfzh){
		if(this.jdbcTemplate.queryForList("select * from zs_ryjbxx where SFZH = ? and ybxz =1",new Object[]{sfzh}).size()!=0){
			return false;
		}
		return true;
	}
}
