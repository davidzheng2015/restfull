package gov.gdgs.zs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import gov.gdgs.zs.configuration.Config;







import gov.gdgs.zs.untils.Condition;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import com.gdky.restfull.dao.BaseJdbcDao;



@Repository
public class LrbDao extends BaseJdbcDao implements ILrbDao{
	 
	@Override
	public String addLrb( Map <String,Object> obj){
		String uuid = UUID.randomUUID().toString().replace("-", ""); 
		final StringBuffer sb = new StringBuffer("insert into "
				+ Config.PROJECT_SCHEMA + "zs_cwbb_lrgd ");
		sb.append("(id,nd,timevalue,zgywsr,zgywsr1,zgywcb,zgywcb1,zgywsj,zgywsj1,zgwylr,zgwylr1,qtywlr,qtywlr1,yyfy,yyfy1,glfy,glfy1,");
		sb.append(" cwfy,cwfy1,yylr,yylr1,tzsy,tzsy1,btsr,btsr1,yywsr,yywsr1,yywzc,yywzc1,lrze,lrze1,sds,sds1,jlr,jlr1,");
		sb.append("  csczsy,csczsy1,zhss,zhss1,zcbglr,zcbglr1,gjbglr,gjbglr1,zwczss,zwczss1,qt,qt1,dlswdjhs,dlswdjsr,");
		sb.append("  dlswdjsr1,dlnssbhs,dlnssbsr,dlnssbsr1,dlnsschs,dlnsscsr,dlnsscsr1,dljzjzhs,dljzjzsr,dljzjzsr1,spgwzxhs,");
		sb.append("  spgwzxsr,spgwzxsr1,dlsqswfyhs,dlsqswfysr,dlsqswfysr1,pxhs,pxsr,pxsr1,qtzyywsrhs,qtzyywsr,qtzyywsr1,sz,zgkj,zbr,ztbj)");
		sb.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
		sb.append("  ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		final Object[] arg = new Object[] {uuid, obj.get("nd"),obj.get("timevalue"),obj.get("zgywsr"),
				 obj.get("zgywsr1"),obj.get("zgywcb"),obj.get("zgywcb1"),obj.get("zgywsj"),obj.get("zgywsj1"),obj.get("zgwylr"),obj.get("zgwylr1"),
				 obj.get("qtywlr"),obj.get("qtywlr1"),obj.get("yyfy"),obj.get("yyfy1"),obj.get("glgy"),obj.get("glfy1"),
				 obj.get("cwfy"),obj.get("cwfy1"),obj.get("yylr"),obj.get("yylr1"),obj.get("tzsy"),obj.get("tzsy1"),obj.get("btsr"),obj.get("btsr1"),
				 obj.get("yywsr"),obj.get("yywsr1"),obj.get("yywzc"),obj.get("yywzc1"),obj.get("lrze"),obj.get("lrze1"),obj.get("sds"),obj.get("sds1"),
				 obj.get("jlr"),obj.get("jlr1"),obj.get("csczsy"),obj.get("csczsy1"),obj.get("zhss"),obj.get("zhss1"),
				 obj.get("zcbglr"),obj.get("zcbglr1"),obj.get("gjbglr"),obj.get("gjbglr1"),obj.get("zwczss"),obj.get("zwczss1"),
				 obj.get("qt"),obj.get("qt1"),obj.get("dlswdjhs"),obj.get("dlswdjsr"),obj.get("dlswdjsr1"),obj.get("dlnssbhs"),
				 obj.get("dlnssbsr"),obj.get("dlnssbsr1"),obj.get("dlnsschs"),obj.get("dlnsscsr"),obj.get("dlnsscsr1"),
				 obj.get("dljzjzhs"),obj.get("dljzjzsr"),obj.get("dljzjzsr1"),obj.get("spgwzxhs"),obj.get("spgwzxsr"),obj.get("spgwzxsr1"),
				 obj.get("dlsqswfyhs"),obj.get("dlsqswfysr"),obj.get("dlsqswfysr1"),obj.get("pxhs"),obj.get("pxsr"),obj.get("pxsr1"),
				 obj.get("qtzyywsrhs"),obj.get("qtzyywsr"),obj.get("qtzyywsr1"),obj.get("sz"),obj.get("zgkj"),obj.get("zbr"),obj.get("ztbj")};
		int count=this.jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = connection.prepareStatement(
						sb.toString());

				for (int i = 0; i < arg.length; i++) {
					ps.setObject(i + 1, arg[i]);
				}
				return ps;
			}
		});
		if(count==0){
			return null;
		}else {
			return uuid;
		}
//		Number rs = this.insertAndGetKeyByJdbc(sb.toString(), arg,
//				new String[] {"id","zgywsr"});


	}

	public Map<String, Object> getlrb(int page, int pageSize,
			Map<String, Object> where) {

		Condition condition = new Condition();
		condition.add("a.nd", "FUZZY", where.get("nd"));
		condition.add("a.ZTBJ", "FUZZY", where.get("ZTBJ"));
		condition.add("a.TIMEVALUE", "FUZZY", where.get("TIMEVALUE"));

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 AS 'key',t.*");
		sb.append(" FROM    ( SELECT a.id,b.DWMC,a.nd,");
		sb.append(" CASE a.ZTBJ WHEN 0 THEN '保存' WHEN 1 THEN '提交' ELSE NULL END AS ZTBJ,");
		sb.append(" CASE a.TIMEVALUE WHEN 0 THEN '半年' WHEN 1 THEN '全年' ELSE NULL END AS TIMEVALUE ");
		sb.append(" FROM " + Config.PROJECT_SCHEMA
				+ "zs_cwbb_lrgd a,zs_jg b,(SELECT @rownum:=?) temp");
		sb.append(condition.getSql());// 相当元 where b.DWMC like '%%'
		sb.append(" AND a.JG_ID=b.ID  and a.JG_ID=68 ORDER BY a.nd DESC ) AS t");
		sb.append("    LIMIT ?, ? ");
		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(0, pageSize * (page - 1));
		params.add(startIndex);
		params.add(pageSize);

		// 获取符合条件的记录
		List<Map<String, Object>> ls = jdbcTemplate.queryForList(sb.toString(),
				params.toArray());

		// 获取符合条件的记录数

		int total = jdbcTemplate.queryForObject("SELECT FOUND_ROWS()",
				Integer.class);

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);

		return obj;
	}
	public Map<String, Object> getLrbById(String id) {
		String sql = "select b.DWMC,a.* from "+Config.PROJECT_SCHEMA+"zs_cwbb_lrgd a, zs_jg b where a.jg_id = b.id and a.id = ?";
		Map<String,Object> rs = jdbcTemplate.queryForMap(sql, id);
		return rs;
	}
	@Override
	public void updateLrb(Map <String,Object> obj) {
		String sql = "update "+ Config.PROJECT_SCHEMA + "zs_cwbb_lrgd  set nd=?,timevalue=?,sz=? where id=?";
		this.jdbcTemplate.update(
				sql,
				new Object[] {obj.get("nd"), obj.get("timevalue"),obj.get("sz"),obj.get("id") });
	}


}