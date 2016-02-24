package gov.gdgs.zs.controllers;

import gov.gdgs.zs.dao.SWSDao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SWSCXController {
	@Resource
	private SWSDao swsDao;

		@RequestMapping(value="/modelautobind", method = {RequestMethod.POST})
		public String modelAutoBind(Model model){
			model.addAttribute("accountmodel", swsDao.testJDBC());
			return "jsontournamenttemplate";
		}
		
	/* @RequestMapping("/api/swscx")  
	    public String swscx(Model model) {  
		 model.addAttribute("Data", swsDao.swscx());
		 return "jsontournamenttemplate"; 
	 }*/
		
	 @RequestMapping("/api/swscx/{pageNum:^[0-9]*$}")  
	 public Map<String,Object> swscx(@PathVariable(value="pageNum") int pn ,HttpServletRequest request ) {  
		 Map<String,Object> sb = new HashMap<>();
		 Map<String,Object> meta = new HashMap<>();
		 int ps = 20;
		 meta.put("pageNum",pn);
		 meta.put("pageSize",ps);
		 meta.put("pageAll",swsDao.swscx(pn,ps).get("pagesize"));
		 sb.put("Page",meta);
		 sb.put("Data", swsDao.swscx(pn,ps).get("data"));
		 return sb;
		 
	 }
}
