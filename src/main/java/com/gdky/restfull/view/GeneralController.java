package com.gdky.restfull.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
  
@Controller  
public class GeneralController {  
  
    @RequestMapping(value="index.do")  
    public void index_jsp(Model model){  
        model.addAttribute("liming", "黎明你好");  
        System.out.println("index.jsp");  
    }  
    
    @RequestMapping("/api/jsonfeed")  
    public String getJSON(Model model) {  
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
    		    .getAuthentication()
    		    .getPrincipal();
        List<TournamentContent> tournamentList = new ArrayList<TournamentContent>();  
        tournamentList.add(TournamentContent.generateContent(userDetails.getUsername(), new Date(), "中国World Cup", "www.fifa.com/worldcup/"));  
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-20 World Cup", "www.fifa.com/u20worldcup/"));  
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-17 World Cup", "www.fifa.com/u17worldcup/"));  
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "Confederations Cup", "www.fifa.com/confederationscup/"));  
        model.addAttribute("items", tournamentList);  
        model.addAttribute("status", 0);  
        return "jsontournamenttemplate";  
    }  
    @RequestMapping(value="/add",method=RequestMethod.POST)  
    @ResponseBody  
    public Object addUser(@RequestBody User user)  
    {  
        System.out.println(user.getName() + " " + user.getAge());  
        return new HashMap<String, String>().put("success", "true");  
    } 
}  
