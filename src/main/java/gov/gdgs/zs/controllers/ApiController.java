package gov.gdgs.zs.controllers;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.framework.dao.MenuDao;
import com.gdky.restfull.framework.entity.Menu;
import com.gdky.restfull.view.Greeting;
@RestController
public class ApiController {
	private static final String template="Hello,%s";
	 
    private final AtomicLong counter = new AtomicLong();
    @RequestMapping("/api/test")
    public Greeting greeting(@RequestParam(value="name",defaultValue="World") String name) throws Exception{
    		name =new String(name.getBytes("ISO8859-1"),"UTF-8");
        return new Greeting(counter.getAndIncrement(),String.format(template, name));
 
    }
    public ApiController(){
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
