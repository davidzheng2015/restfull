package gov.gdgs.zs.controllers;

import gov.gdgs.zs.dao.SWSDao;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SWSCXController {
	@Resource
	private SWSDao swsDao;

	 @RequestMapping("/api/jsontes/**")  
	    public String getJSON(Model model) {  
//	    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//	    		    .getAuthentication()
//	    		    .getPrincipal();
//	        List<TournamentContent> tournamentList = new ArrayList<TournamentContent>();  
//	        tournamentList.add(TournamentContent.generateContent(userDetails.getUsername(), new Date(), "中国World Cup", "www.fifa.com/worldcup/"));  
//	        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-20 World Cup", "www.fifa.com/u20worldcup/"));  
//	        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-17 World Cup", "www.fifa.com/u17worldcup/"));  
//	        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "Confederations Cup", "www.fifa.com/confederationscup/"));  
//	        model.addAttribute("items", tournamentList);  
	        model.addAttribute("status", 0);  
			model.addAttribute("Data", swsDao.testJDBC());
	        
	        return "jsontournamenttemplate";  
	    }  
	 
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
		
	 @RequestMapping("/api/swscx/*")  
	 public Map<String,Object> swscx(HttpServletRequest request) {  
		 Map<String,Object> sb = new HashMap<>();
		 Map<String,Object> meta = new HashMap<>();
		 int pn = Integer.parseInt(request.getRequestURL().substring(41,43));
		 int ps = 20;
		 meta.put("pageNum",pn);
		 meta.put("pageSize",ps);
		 sb.put("Page",meta);
		 sb.put("Data", swsDao.swscx(pn,ps));
		 return sb;
		 
	 }
}
