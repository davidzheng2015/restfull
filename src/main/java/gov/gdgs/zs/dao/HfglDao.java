package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Common;
import gov.gdgs.zs.untils.Condition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class HfglDao extends BaseDao{
	/**
	 * 会员会费缴纳情况
	 * @param pn
	 * @param ps
	 * @param qury
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> hyhfjnqk(int pn,int ps,Map<String, Object> qury) throws Exception{
		 Calendar ca = Calendar.getInstance();
	     Object lyear =new Object();
	     DateFormat df = new SimpleDateFormat("MM-dd");
	     if(qury.containsKey("nd")){
	    	 lyear=qury.get("nd");
	     }else if(df.parse(ca.get(Calendar.MONTH)+1+"-"+ca.get(Calendar.DATE)).after(df.parse("07-15"))){
	    	 lyear=ca.get(Calendar.YEAR);
	     }else{
	    	 lyear=ca.get(Calendar.YEAR)-1;
	     }
	     Condition condition = new Condition();
		 condition.add("b.dwmc", Condition.FUZZY, qury.get("dwmc"));
		 ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
		StringBuffer sb = new StringBuffer();
		sb.append("		select  SQL_CALC_FOUND_ROWS g.* from (select   @rownum:=@rownum+1 as 'key',b.dwmc,'"+lyear+"' as nd,");
		sb.append("		(select a.ZGYWSR from zs_cwbb_lrgd a where a.jg_id=b.id and a.nd='"+lyear+"' and a.ztbj=1 order by a.TIMEVALUE desc limit 1) as jyzsr,");
		sb.append("		f_yjtt(b.id, '"+lyear+"')+");
		sb.append("		(select count(c.RY_ID)*800 as yjgr from zs_zysws c where c.JG_ID=b.id  and c.YXBZ=1  and c.ry_id not in (select d.RY_ID from zs_hyhfjfryls d where d.nd='"+lyear+"') ) as yjz,");
		sb.append("		f_yjtt(b.id, '"+lyear+"') as yjtt,");
		sb.append("		(select sum(e.YJTTHF) from zs_hyhfjnqk e where e.JG_ID=b.id and e.ND='"+lyear+"' and e.yxbz=1) as yftt,");
		sb.append("		f_qjtt(f_yjtt(b.id, '"+lyear+"'),b.id,'"+lyear+"') as qjtt,");
		sb.append("		(select count(c.RY_ID)*800 as yjgr from zs_zysws c where c.JG_ID=b.id  and c.YXBZ=1  and c.ry_id not in (select d.RY_ID from zs_hyhfjfryls d where d.nd='"+lyear+"') )as yjgr,");
		sb.append("		(select sum(e.YJGRHF) from zs_hyhfjnqk e where e.JG_ID=b.id and e.ND='"+lyear+"' and e.yxbz=1) as yfgr,");
		sb.append("		f_qjgr((select count(c.RY_ID)*800 as yjgr from zs_zysws c where c.JG_ID=b.id  and c.YXBZ=1  and c.ry_id not in (select d.RY_ID from zs_hyhfjfryls d where d.nd='"+lyear+"') ),b.id,'"+lyear+"') as qjgr");
		sb.append("		from zs_jg b,(select @rownum:=?) zs_ry ");
		sb.append("	"+condition.getSql()+"	and b.yxbz=1 ) g ");
		if(qury.containsKey("sorder")){
			Boolean asc = qury.get("sorder").toString().equals("ascend");
			switch (qury.get("sfield").toString()) {
			case "dwmc":
				if(asc){
					sb.append("		    order by convert( g.dwmc USING gbk) COLLATE gbk_chinese_ci ");
				}else{
					sb.append("		    order by convert( g.dwmc USING gbk) COLLATE gbk_chinese_ci desc");
				}
				break;
			case "jyzsr":
				if(asc){
					sb.append("		    order by g.jyzsr ");
				}else{
					sb.append("		    order by g.jyzsr desc");
				}
				break;
			case "qjtt":
				if(asc){
					sb.append("		    order by g.qjtt ");
				}else{
					sb.append("		    order by g.qjtt desc");
				}
				break;
			case "qjgr":
				if(asc){
					sb.append("		    order by g.qjgr ");
				}else{
					sb.append("		    order by g.qjgr desc");
				}
				break;
			}
		}
		sb.append("		    LIMIT ?, ? ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
	     int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
			Map<String,Object> ob = new HashMap<>();
			ob.put("data", ls);
			Map<String, Object> meta = new HashMap<>();
			meta.put("pageNum", pn);
			meta.put("pageSize", ps);
			meta.put("pageTotal",total);
			meta.put("pageAll",(total + ps - 1) / ps);
			ob.put("page", meta);
			return ob;
	}
	/**
	 * 发票打印查询
	 * @param pn
	 * @param ps
	 * @param qury
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> fpdy(int pn,int ps,Map<String, Object> qury) throws Exception{
		Condition condition = new Condition();
		condition.add("a.dwmc", Condition.FUZZY, qury.get("dwmc"));
		condition.add("a.nd", Condition.EQUAL, qury.get("nd"));
		ArrayList<Object> params = condition.getParams();
		params.add(0,(pn-1)*ps);
		params.add((pn-1)*ps);
		params.add(ps);
		StringBuffer sb = new StringBuffer();
		sb.append("		select SQL_CALC_FOUND_ROWS @rownum:=@rownum+1 as 'key',a.ID as jlid,a.JG_ID as jgid,a.SCJLID as scid,a.ND,a.DWMC,a.JFZE,a.YJTTHF,a.YJGRHF,a.JFRQ,a.DYCS"); 
		sb.append("		from zs_hyhfjnqk a,(select @rownum:=?) zs_ry");
		sb.append("		"+condition.getSql()+" and a.yxbz=1 order by a.JFRQ desc LIMIT ?, ?");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(),params.toArray());
		int total = this.jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", int.class);
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", ls);
		Map<String, Object> meta = new HashMap<>();
		meta.put("pageNum", pn);
		meta.put("pageSize", ps);
		meta.put("pageTotal",total);
		meta.put("pageAll",(total + ps - 1) / ps);
		ob.put("page", meta);
		return ob;
	}
	/**
	 * 发票打印修改
	 * @param jlid
	 * @param fptj
	 * @param name
	 * @return
	 */
	public boolean ttgefp(String jlid,Map<String, Object> fptj,String name){
		BigDecimal yjt = new BigDecimal(fptj.get("YJTTHF").toString());
		BigDecimal yjg = new BigDecimal(fptj.get("YJGRHF").toString());
		BigDecimal jfje = new BigDecimal(fptj.get("jfje").toString());
		BigDecimal zj = yjt.add(yjg);
		int rl = zj.compareTo(jfje);
		if((new BigDecimal(fptj.get("YJTTHF").toString()).add(new BigDecimal(fptj.get("YJGRHF").toString())).compareTo(new BigDecimal(fptj.get("jfje").toString()))==0)){
			String sql="update zs_hyhfjnqk set YJTTHF=?,YJGRHF=?,GGR=? where id =?";
			this.jdbcTemplate.update(sql,new Object[]{fptj.get("YJTTHF"),fptj.get("YJGRHF"),name,jlid});
			String yfgr =this.jdbcTemplate.queryForObject("select sum(e.YJGRHF) from zs_hyhfjnqk e where e.JG_ID=? and e.ND=? and e.yxbz=1",
						  new Object[]{fptj.get("jgid"),fptj.get("nd")}, String.class);
			int zys = (int)new BigDecimal(yfgr).divide(new BigDecimal("800"),1,BigDecimal.ROUND_HALF_EVEN).doubleValue();
			int yzys = this.jdbcTemplate.queryForObject("select count(b.ry_id) from zs_hyhfjfryls b where b.nd=? and b.JG_ID=? and b.yxbz=1",
						new Object[]{fptj.get("nd"),fptj.get("jgid")},int.class);
			if(yzys>0){
				if(zys>yzys){
					for(Map<String, Object> k:this.jdbcTemplate.queryForList("select ry_id from zs_zysws a where a.jg_id=? and a.yxbz=1 and a.ry_id not in(select b.ry_id from zs_hyhfjfryls b where b.nd=? and b.JG_ID=? and b.yxbz=1) limit ?",
							new Object[]{fptj.get("jgid"),fptj.get("nd"),fptj.get("jgid"),zys-yzys})){
						this.jdbcTemplate.update("insert into zs_hyhfjfryls (ID,ND,RY_ID,JG_ID,JF_ID,SCJLID,YXBZ) values(?,?,?,?,?,?,1)",new Object[]{new Common().newUUID(),fptj.get("nd"),k.get("ry_id"),fptj.get("jgid"),jlid,fptj.get("scid")});
					}
				}else if(zys<yzys){
					for(Map<String, Object> k:this.jdbcTemplate.queryForList("select b.id from zs_hyhfjfryls b where b.nd=? and b.JG_ID=? and b.yxbz=1 limit ?",
							new Object[]{fptj.get("nd"),fptj.get("jgid"),yzys-zys})){
						this.jdbcTemplate.update("update zs_hyhfjfryls set YXBZ=0 where id=?",new Object[]{k.get("id")});
					}
				}
			}else if(zys>0){
				for(Map<String, Object> k:this.jdbcTemplate.queryForList("select ry_id from zs_zysws a where a.jg_id=? and a.yxbz=1 and a.ry_id not in(select b.ry_id from zs_hyhfjfryls b where b.nd=? and b.JG_ID=? and b.yxbz=1) limit ?",
						new Object[]{fptj.get("jgid"),fptj.get("nd"),fptj.get("jgid"),zys})){
					this.jdbcTemplate.update("insert into zs_hyhfjfryls (ID,ND,RY_ID,JG_ID,JF_ID,SCJLID,YXBZ) values(?,?,?,?,?,?,1)",new Object[]{new Common().newUUID(),fptj.get("nd"),k.get("ry_id"),fptj.get("jgid"),jlid,fptj.get("scid")});
				}
			}
			return true;
		}
		return false;
	}
	//费用统计
	public Map<String,Object> fytj(Map<String, Object> qury) throws Exception{
		 Calendar ca = Calendar.getInstance();
	     Object lyear =new Object();
	     DateFormat df = new SimpleDateFormat("MM-dd");
	     if(qury.containsKey("nd")){
	    	 lyear=qury.get("nd");
	     }else if(df.parse(ca.get(Calendar.MONTH)+1+"-"+ca.get(Calendar.DATE)).after(df.parse("07-15"))){
	    	 lyear=ca.get(Calendar.YEAR);
	     }else{
	    	 lyear=ca.get(Calendar.YEAR)-1;
	     }
	     StringBuffer sb = new StringBuffer();
	     sb.append("			select '"+lyear+"' as nd, sum(if(g.jyzsr>0,g.jyzsr,0)) as yyz,sum(if(g.yftt>0,g.yftt,0))+sum(if(g.yfgr>0,g.yfgr,0)) as yfz,");
	     sb.append("		sum(if(g.yjtt>0,g.yjtt,0))+sum(if(g.yjgr>0,g.yjgr,0))-(sum(if(g.yftt>0,g.yftt,0))+sum(if(g.yfgr>0,g.yfgr,0))) as qjz from(	select  ");
	     sb.append("		(select a.ZGYWSR from zs_cwbb_lrgd a where a.jg_id=b.id and a.nd='"+lyear+"' and a.ztbj=1 order by a.TIMEVALUE desc limit 1) as jyzsr,");
	     sb.append("		f_yjtt(b.id, '"+lyear+"') as yjtt,");
	     sb.append("		(select sum(e.YJTTHF) from zs_hyhfjnqk e where e.JG_ID=b.id and e.ND='"+lyear+"' and e.yxbz=1) as yftt,");
	     sb.append("		(select count(c.RY_ID)*800 as yjgr from zs_zysws c where c.JG_ID=b.id  and c.YXBZ=1  and c.ry_id not in (select d.RY_ID from zs_hyhfjfryls d where d.nd='"+lyear+"') )as yjgr,");
	     sb.append("		(select sum(e.YJGRHF) from zs_hyhfjnqk e where e.JG_ID=b.id and e.ND='"+lyear+"' and e.yxbz=1) as yfgr");
	     sb.append("		from zs_jg b where b.yxbz=1 ) g ");
	     return this.jdbcTemplate.queryForMap(sb.toString());
	  }
	/**
	 * 缴费单据上传
	 * @param file
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> upLoadJFSC(MultipartFile file,int uid) throws Exception{  
		InputStream stream = file.getInputStream();
		String fileType =file.getOriginalFilename().split("\\u002E")[1];
        Workbook wb = null;  
        if (fileType.equals("xls")) {  
            wb = new HSSFWorkbook(stream);  
        }  
        else if (fileType.equals("xlsx")) {  
            wb = new XSSFWorkbook(stream);  
        }  
        else {  
        	throw new Exception("您输入的excel格式不正确");
        }  
        Sheet sheet1 = wb.getSheetAt(0);
        sheet1.removeRow(sheet1.getRow(0));
        Map<String,Object> rs=new HashMap<>();
        List<Object> fls=new ArrayList<Object>();
        String uuid2 = new Common().newUUID();
        int i=0,j=0;
        for (Row row : sheet1) {  
        	ArrayList<Object> params =new ArrayList<Object>();  
            for (Cell cell : row) {  
            	params.add(cell);  
            }
            String nd = (params.get(1)+"").substring(0,4);
            List<Map<String, Object>> jg = this.jdbcTemplate.queryForList("select a.id,f_yjtt(a.id,'"+nd+"') as yjtt,(select count(b.id) from zs_zysws b where b.JG_ID=a.ID and yxbz=1) as yjrs from zs_jg a where a.dwmc=? and a.yxbz=1",
            		params.get(3).toString());
            if(jg.size()==0){
            	j+=1;
            	fls.add(params.get(3).toString()+"（请检查事务所状态及名称）");
            	continue;
            }else{
            	Map<String, Object> jgs = jg.get(0);
            	if((jgs.get("yjtt")+"").equals("null")){
            		j+=1;
                	fls.add(params.get(3).toString()+"（未上报报表）");
                	continue;
            	}
            	String uuid = new Common().newUUID();
            	String yftt = params.get(5).toString();
            	String yfgr = params.get(6).toString();
            	if(params.get(5).toString().equals("")&&params.get(6).toString().equals("")){
            		double syz = new BigDecimal(params.get(4).toString()).subtract(new BigDecimal(jgs.get("yjtt").toString())).doubleValue();
            		if(syz>0){
            			int zys = (int)new BigDecimal(syz).divide(new BigDecimal("800"),1,BigDecimal.ROUND_HALF_EVEN).doubleValue();
            			for(Map<String, Object> k:this.jdbcTemplate.queryForList("select ry_id from zs_zysws a where a.jg_id=? and a.yxbz=1 and a.ry_id not in(select b.ry_id from zs_hyhfjfryls b where b.nd=? and b.JG_ID=? and b.yxbz=1) limit ?",
            					new Object[]{jgs.get("id"),nd,jgs.get("id"),zys})){
            				this.jdbcTemplate.update("insert into zs_hyhfjfryls (ID,ND,RY_ID,JG_ID,JF_ID,SCJLID,YXBZ) values(?,?,?,?,?,?,1)",new Object[]{new Common().newUUID(),nd,k.get("ry_id"),jgs.get("id"),uuid,uuid2});
            			}
            			yftt=(String) jgs.get("yjtt");
            			yfgr=syz+"";
            		}else{
            			yftt=params.get(4).toString();
            			yfgr="0";
            		}
            	}else{
            		if(yftt.equals("")){
            			yftt="0";
            			int zys = (int)new BigDecimal(yfgr).divide(new BigDecimal("800"),1,BigDecimal.ROUND_HALF_EVEN).doubleValue();
            			for(Map<String, Object> k:this.jdbcTemplate.queryForList("select ry_id from zs_zysws a where a.jg_id=? and a.yxbz=1 and a.ry_id not in(select b.ry_id from zs_hyhfjfryls b where b.nd=? and b.JG_ID=? and b.yxbz=1) limit ?",
            					new Object[]{jgs.get("id"),nd,jgs.get("id"),zys})){
            				this.jdbcTemplate.update("insert into zs_hyhfjfryls (ID,ND,RY_ID,JG_ID,JF_ID,SCJLID,YXBZ) values(?,?,?,?,?,?,1)",new Object[]{new Common().newUUID(),nd,k.get("ry_id"),jgs.get("id"),uuid,uuid2});
            			}
            		}
            		if(yfgr.equals("")){
            			yfgr="0";
            		}
            	}
            	this.jdbcTemplate.update("insert into zs_hyhfjnqk (ID,JG_ID,DWMC,ND,JFZE,YJTTHF,YJGRHF,JFRQ,YJRS,SCJLID,YXBZ,BZ) values(?,?,?,?,?,?,?,?,?,?,'1',?)",
            			new Object[]{uuid,jgs.get("id"),params.get(3).toString(),nd,params.get(4).toString(),yftt,yfgr,params.get(0).toString(),jgs.get("yjrs"),uuid2,params.get(2).toString()});
            	i+=1;
            }
        }
        this.jdbcTemplate.update("insert into zs_hyhfscjlb (ID,SUCESS,FAIL,SCRQ,SCR) values(?,?,?,sysdate(),?)",new Object[]{uuid2,i,j,uid});
        rs.put("success", i);
        rs.put("fail", j);
        rs.put("fls", fls);
        stream.close();
        return rs;
    } 
}
