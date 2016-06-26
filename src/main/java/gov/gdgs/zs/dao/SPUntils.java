package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.dao.BaseJdbcDao;
@Repository
public class SPUntils extends BaseJdbcDao {
	/**
	 * 事务所变更审批
	 */
	public static final String JGBGSP = "402881831be2e6af011be3ab8b84000c"; 
	/**
	 * 事务所分所变更审批
	 */
	public static final String JGFSBGSP = "40288087228378910122838ecac50022"; 
	/**
	 * 事务所合并审批
	 */
	public static final String JGHBSP = "402881831be2e6af011be3aceac6000e"; 
	/**
	 * 事务所注销审批
	 */
	public static final String JGZXSP = "402881831be2e6af011be3adc72c0011"; 
	 
	 
	 
	 

}
