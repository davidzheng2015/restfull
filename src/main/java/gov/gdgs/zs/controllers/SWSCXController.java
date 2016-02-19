package gov.gdgs.zs.controllers;

import gov.gdgs.zs.dao.SWSDao;
import gov.gdgs.zs.entity.AccountModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.view.TournamentContent;

@Controller
public class SWSCXController {
	@Resource
	private SWSDao swsDao;

	 @RequestMapping("/api/jsontest")  
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
	        model.addAttribute("SWSXX", swsDao.getAllSwsxx()); 
	        model.addAttribute("UUID", this.getUUID()); 
	        
	        return "jsontournamenttemplate";  
	    }  
	 
	 private String getUUID() {
			String s = UUID.randomUUID().toString();
			//去掉“_”符号
			return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
					+ s.substring(19, 23) + s.substring(24,31);
			
		}
	 
}
