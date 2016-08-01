package gov.gdgs.zs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
@Repository
public class HyryqktDao extends BaseJdbcDao {

	public Map<String, Object> getHyryqktj(int page, int pageSize,
			HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" select '1:执业税务师' as xmmc, count(distinct zy.id) zrs, ")
			.append(" sum(case when jb.xb_dm = '1' then 1 else 0 end) n_zrs, ")
			.append(" sum(case when jb.xb_dm = '2' then 1 else 0 end) v_zrs, ")
			.append(" sum(case when jb.xl_dm = '5' then 1 else 0 end) bs_zrs, ")
			.append(" sum(case when jb.xl_dm = '4' then 1 else 0 end) yjs_zrs, ")
			.append(" sum(case when jb.xl_dm = '1' then 1 else 0 end) bk_zrs, ")
			.append(" sum(case when jb.xl_dm = '2' then 1 else 0 end) dz_zrs, ")
			.append(" sum(case when jb.xl_dm = '3' then 1 else 0 end) gz_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) <= 35 then 1 else 0 end) 35_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 36 and (year(now()) - year(sri) - 1) <= 50 then 1 else 0 end) 50_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 51 and (year(now()) - year(sri) - 1) <= 60 then 1 else 0 end) 60_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) > 60 then 1 else 0 end) 61_zrs ")
			.append(" from zs_zysws zy left join zs_ryjbxx jb on zy.ry_id = jb.id ")
			.append(" where zy.yxbz = '1' and jb.yxbz = '1' ")
			.append(" union all ")
			.append(" select '其中：合伙人', count(distinct zy.id) zrs, ")
			.append(" sum(case when jb.xb_dm = '1' then 1 else 0 end) n_zrs, ")
			.append(" sum(case when jb.xb_dm = '2' then 1 else 0 end) v_zrs, ")
			.append(" sum(case when jb.xl_dm = '5' then 1 else 0 end) bs_zrs, ")
			.append(" sum(case when jb.xl_dm = '4' then 1 else 0 end) yjs_zrs, ")
			.append(" sum(case when jb.xl_dm = '1' then 1 else 0 end) bk_zrs, ")
			.append(" sum(case when jb.xl_dm = '2' then 1 else 0 end) dz_zrs, ")
			.append(" sum(case when jb.xl_dm = '3' then 1 else 0 end) gz_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) <= 35 then 1 else 0 end) 35_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 36 and (year(now()) - year(sri) - 1) <= 50 then 1 else 0 end) 50_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 51 and (year(now()) - year(sri) - 1) <= 60 then 1 else 0 end) 60_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) > 60 then 1 else 0 end) 61_zrs ")
			.append(" from zs_zysws zy left join zs_ryjbxx jb on zy.ry_id = jb.id ")
			.append(" where zy.yxbz = '1' and jb.yxbz = '1' and zy.fqr_dm = '1' ")
			.append(" group by zy.fqr_dm ")
			.append(" union all ")
			.append(" select '其中：出资人', count(distinct zy.id) zrs, ")
			.append(" sum(case when jb.xb_dm = '1' then 1 else 0 end) n_zrs, ")
			.append(" sum(case when jb.xb_dm = '2' then 1 else 0 end) v_zrs, ")
			.append(" sum(case when jb.xl_dm = '5' then 1 else 0 end) bs_zrs, ")
			.append(" sum(case when jb.xl_dm = '4' then 1 else 0 end) yjs_zrs, ")
			.append(" sum(case when jb.xl_dm = '1' then 1 else 0 end) bk_zrs, ")
			.append(" sum(case when jb.xl_dm = '2' then 1 else 0 end) dz_zrs, ")
			.append(" sum(case when jb.xl_dm = '3' then 1 else 0 end) gz_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) <= 35 then 1 else 0 end) 35_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 36 and (year(now()) - year(sri) - 1) <= 50 then 1 else 0 end) 50_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 51 and (year(now()) - year(sri) - 1) <= 60 then 1 else 0 end) 60_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) > 60 then 1 else 0 end) 61_zrs ")
			.append(" from zs_zysws zy left join zs_ryjbxx jb on zy.ry_id = jb.id ")
			.append(" where zy.yxbz = '1' and jb.yxbz = '1' and zy.czr_dm = '1' ")
			.append(" group by zy.czr_dm ")
			.append(" union all ")
			.append(" select '2:具有其它中介执业资格的从业人员', count(distinct zy.id) zrs, ")
			.append(" ifnull(sum(case when jb.xb_dm = '1' then 1 else 0 end),0) n_zrs, ")
			.append(" ifnull( sum(case when jb.xb_dm = '2' then 1 else 0 end),0) v_zrs, ")
			.append(" ifnull( sum(case when jb.xl_dm = '5' then 1 else 0 end),0) bs_zrs, ")
			.append(" ifnull(sum(case when jb.xl_dm = '4' then 1 else 0 end),0) yjs_zrs, ")
			.append(" ifnull(sum(case when jb.xl_dm = '1' then 1 else 0 end),0) bk_zrs, ")
			.append(" ifnull( sum(case when jb.xl_dm = '2' then 1 else 0 end),0) dz_zrs, ")
			.append(" ifnull(sum(case when jb.xl_dm = '3' then 1 else 0 end),0) gz_zrs, ")
			.append(" ifnull(sum(case when (year(now()) - year(sri) - 1) <= 35 then 1 else 0 end) ,0)35_zrs, ")
			.append(" ifnull( sum(case when (year(now()) - year(sri) - 1) >= 36 and (year(now()) - year(sri) - 1) <= 50 then 1 else 0 end),0) 50_zrs, ")
			.append(" ifnull(sum(case when (year(now()) - year(sri) - 1) >= 51 and (year(now()) - year(sri) - 1) <= 60 then 1 else 0 end),0) 60_zrs, ")
			.append(" ifnull(sum(case when (year(now()) - year(sri) - 1) > 60 then 1 else 0 end),0) 61_zrs ")
			.append(" from zs_qtry zy left join zs_ryjbxx jb on zy.ry_id = jb.id  ")
			.append(" where zy.qtryzt_dm = '1' and jb.yxbz = '1' ")
			.append(" union all ")
			.append(" select '3:从业人员',count(distinct zy.id) zrs, ")
			.append(" sum(case when jb.xb_dm = '1' then 1 else 0 end) n_zrs, ")
			.append(" sum(case when jb.xb_dm = '2' then 1 else 0 end) v_zrs, ")
			.append(" sum(case when jb.xl_dm = '5' then 1 else 0 end) bs_zrs, ")
			.append(" sum(case when jb.xl_dm = '4' then 1 else 0 end) yjs_zrs, ")
			.append(" sum(case when jb.xl_dm = '1' then 1 else 0 end) bk_zrs, ")
			.append(" sum(case when jb.xl_dm = '2' then 1 else 0 end) dz_zrs, ")
			.append(" sum(case when jb.xl_dm = '3' then 1 else 0 end) gz_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) <= 35 then 1 else 0 end) 35_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 36 and (year(now()) - year(sri) - 1) <= 50 then 1 else 0 end) 50_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) >= 51 and (year(now()) - year(sri) - 1) <= 60 then 1 else 0 end) 60_zrs, ")
			.append(" sum(case when (year(now()) - year(sri) - 1) > 60 then 1 else 0 end) 61_zrs ")
			.append(" from zs_cyry zy left join zs_ryjbxx jb on zy.ry_id = jb.id ")
			.append(" where zy.yxbz = '1' and jb.yxbz = '1' ")
			;
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sql.toString());
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("data", ls);
		return obj;
	}
	
}
