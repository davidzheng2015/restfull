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

	public List<Map<String, Object>> getYwba() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ID,BBHM,ND,BGWH,YZM,SFJE,SWSSWSXLH,SWSMC,SWSSWDJZH,WTDW,WTDWSWDJZH,XYH,YJFH,RJFH,SJFH,QZSWS, ");
		sb.append(" TXDZ,SWSDZYJ,SWSWZ,YWLX,JTXM,VALUE1,VALUE2,ZSXYID,NSRXZ,HYLX,ZSFS,ISWS,SB,CITY,QX,WTDWXZ,ZTBJ, ");
		sb.append(" DATE_FORMAT(BBRQ,'%Y-%m-%d') AS  BBRQ, ");
		sb.append(" DATE_FORMAT(BGRQ,'%Y-%m-%d') AS  BGRQ, ");
		sb.append(" DATE_FORMAT(ZBRQ,'%Y-%m-%d') AS  ZBRQ, ");
		sb.append(" DATE_FORMAT(SSTARTTIME,'%Y-%m-%d') AS  SSTARTTIME, ");
		sb.append(" DATE_FORMAT(SENDTIME,'%Y-%m-%d') AS  SENDTIME ");
		sb.append(" from gzapi_data_ywba ");
		sb.append(" limit 10 ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		return ls;
	}

	public List<Map<String, Object>> getSwsjg() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select SWSSWSXLH,SWDJZHM,DWMC,SZCS,FRDB,DZ,SWJG_DM,ZJPZWH,YZBM,DH,CZ,JGXZ,ZSBH,ZCZJ,JYFW,DZYJ,KHH,KHHZH,ZXYY,SJZT,PARENTJGID, ");
		sb.append(" DATE_FORMAT(ZJPZSJ,'%Y-%m-%d') AS ZJPZSJ, ");
		sb.append(" DATE_FORMAT(ZXSJ,'%Y-%m-%d') AS ZXSJ ");
		sb.append(" from gzapi_data_swsjg t ");
		sb.append(" limit 10 ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		return ls;
	}

	public List<Map<String, Object>> getZsxy() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ID,XYH,YWLXNAME,WTFMC,DJHM_GS,DJHM_DS,JFLXR,JFTELEPHONE,JFADDRESS,JG_ID,SWSMC, ");
		sb.append(" FPHM,XYJE,SSJE,XYZT,MEMO, ");
		sb.append(" DATE_FORMAT(XYKSSJ,'%Y-%m-%d') AS  XYKSSJ, ");
		sb.append(" DATE_FORMAT(XYJSSJ,'%Y-%m-%d') AS  XYJSSJ, ");
		sb.append(" DATE_FORMAT(EDITDATE,'%Y-%m-%d') AS  EDITDATE ");
		sb.append(" FROM gzapi_data_zsxy ");
		sb.append(" limit 10 ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		return ls;
	}

	
}
