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
	 * 判断事务所注销审批中
	 * @param jgid
	 * @return false--审批中
	 */
	public boolean checkZXing(int jgid){
		if(this.jdbcTemplate.queryForList("select id from zs_jgzx where spzt = 1 and jg_id =?",new Object[]{jgid}).size()!=0){
			return false;
		}
		return true;
	}
	/**
	 * 判断事务所合并审批中
	 * @param jgid
	 * @return false--审批中
	 */
	public boolean checkHBing(int jgid){
		if(this.jdbcTemplate.queryForList("select id from zs_jghb where HBZT = 1 and jg_id =?",new Object[]{jgid}).size()!=0){
			return false;
		}
		return true;
	}
	/**
	 * 判断执业税务师审批中
	 * @param jgid
	 * @return false--审批中
	 */
	public boolean checkZYSPing(int zyid){
		if(this.jdbcTemplate.queryForList("select id from zs_zysws where zyzt_dm=1 and ryspgczt_dm not in (1,3) and id=?",zyid).size()!=0){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断审批是否为上级驳回
	 * @param sfzh
	 * @return false--上级驳回
	 */
	public boolean checkIsBH(String spid){
		if(this.jdbcTemplate.queryForList("select id from zs_spzx where id = ? and qrbj is not null",new Object[]{spid}).size()!=0){
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
		if(this.jdbcTemplate.queryForList("select id from zs_ryjbxx where SFZH = ? and ryzt_dm not in(2,5,6)",new Object[]{sfzh}).size()!=0){
			return false;
		}
		return true;
	}
}
