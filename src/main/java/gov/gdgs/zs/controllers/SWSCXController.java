package gov.gdgs.zs.controllers;

import gov.gdgs.zs.dao.SWSDao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SWSCXController {
	@Resource
	private SWSDao swsDao;

		@RequestMapping(value="/modelautobind", method = {RequestMethod.POST})
		public String modelAutoBind(Model model,HttpServletRequest request ){
			 Object s = request.getParameterValues("page");
			 @SuppressWarnings("unused")
			Object l = request.getParameterValues("lll");
			 s.toString().hashCode();
			model.addAttribute("accountmodel", swsDao.testJDBC());
			return "jsontournamenttemplate";
		}
		
	/* @RequestMapping("/api/swscx")  
	    public String swscx(Model model) {  
		 model.addAttribute("Data", swsDao.swscx());
		 return "jsontournamenttemplate"; 
	 }*/
		
	 @RequestMapping(value="/api/jgs", method = {RequestMethod.GET})  
	 public Map<String,Object> swscx(HttpServletRequest request ) { 
		 int pn =0;
		 try {
			    int b = Integer.valueOf(request.getParameterValues("pagenum").toString()).intValue();
			    pn=b;
			} catch (NumberFormatException e) {
			    e.printStackTrace();
			}
		 Map<String,Object> sb = new HashMap<>();
		 Map<String,Object> meta = new HashMap<>();
		 int ps = 20;
		 meta.put("pageNum",pn);
		 meta.put("pageSize",ps);
		 meta.put("pageTotal",swsDao.swscx(pn,ps).get("totalsize"));
		 meta.put("pageAll",swsDao.swscx(pn,ps).get("pagesize"));
		 sb.put("Page",meta);
		 sb.put("Data", swsDao.swscx(pn,ps).get("data"));
		 return sb;
		 
	 }
	 @RequestMapping("/api/{swsxqTab:^[A-Za-z]+$}/{swjgId:^[0-9]*$}")
	 public Map<String,Object> swsxx (@PathVariable(value="swsxqTab") String xqTab, @PathVariable(value="swjgId") int jgid){
		 Map<String,Object> sb = new HashMap<>();
		 switch(xqTab){
		 case "swsxx" :
			 sb.put("Data", swsDao.swsxx(jgid));
			 break;
		 case "zyryxx" :
			 sb.put("Data", "开发中...");
			 break;
		 case "cyryxx" :
			 sb.put("Data", "维护中...");
			 break;
		 default:
			 return sb;
		 } 
		 return sb;
	 }
}
