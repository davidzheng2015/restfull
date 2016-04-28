package gov.gdgs.zs.api;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.dao.CWBBDao;
import gov.gdgs.zs.service.CWBBService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@RestController 
@RequestMapping(value = Config.URL_PROJECT)

public class CWBBController {
	
	@Resource 
	 	private CWBBService cwbbService; 
	@Resource 
        private CWBBDao cwbbDao;
	@RequestMapping(value = "/zcmx", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> zcmx( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where){ 
	 		Map<String,Object> obj = cwbbService.zcmx(page,pageSize,where); 
	 		return new ResponseEntity<>(obj,HttpStatus.OK); 
	 	} 
	@RequestMapping(value="/zcmx/{Id}",method = RequestMethod.GET)
	 public Map<String,Object> zcmx ( @PathVariable("Id") String id){
		 Map<String,Object> sb = new HashMap<>();
			 sb.put("data", cwbbDao.getZcmxById(id));
		 return sb;
	 }

	 @RequestMapping(value="/api/lrfp", method = {RequestMethod.GET})  
	 public Map<String,Object> lrfp(HttpServletRequest request ) { 
		 Map<String,Object> sb = new HashMap<>();
		 try {
			 if(request.getParameterValues("pagenum")[0]!=null&&!request.getParameterValues("pagenum")[0].equals("0")){
				 if(request.getParameterValues("pagesize")[0]!=null&&!request.getParameterValues("pagesize")[0].equals("0")){
					Map<String,Object> qury = new HashMap<>();
					qury.put("pn", request.getParameterValues("pagenum")[0]);
					qury.put("ps", request.getParameterValues("pagesize")[0]);
					int pn =Integer.parseInt(qury.get("pn").toString());
					int ps =Integer.parseInt(qury.get("ps").toString());
					Map<String,Object> meta = new HashMap<>();
					meta.put("pageNum",pn);
					meta.put("pageSize",ps);
					meta.put("pageTotal",cwbbDao.lrfp1(pn,ps).get("pagetotal"));
					meta.put("total_number1",cwbbDao.lrfp1(pn,ps).get("total_number1"));
					sb.put("page",meta);
					sb.put("data", cwbbDao.lrfp1(pn,ps).get("data"));
				 }
			 }
		} catch (Exception e) {
			sb.put("data", cwbbDao.lrfp().get("data"));
		}
		 return sb;
		 
	 }
	 @RequestMapping("/api/lrfp/{lrfpxqTab:^[A-Za-z]+$}/{Id}")
	 public Map<String,Object> lrfp (@PathVariable(value="lrfpxqTab") String xqTab, @PathVariable(value="Id") String id){
		 Map<String,Object> sb = new HashMap<>();
		 switch(xqTab){
		 case "xx" :
			 sb.put("xx", cwbbDao.xx(id));
			 break;
		     default:
		 
			 return sb;
		 }
		 return sb;
	 }


}
