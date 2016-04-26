package gov.gdgs.zs.api;

import gov.gdgs.zs.configuration.ProjectConstants;
import gov.gdgs.zs.dao.RyglDao;
import gov.gdgs.zs.dao.SWSDao;
import gov.gdgs.zs.untils.DbToDb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hashids.Hashids;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + ProjectConstants.URI_API_ZS)
public class SWSCXController {
	@Resource
	private SWSDao swsDao;
	@Resource
	private RyglDao ryglDao;

	@RequestMapping(value = "/modelautobind", method = { RequestMethod.GET })
	@ResponseBody
	public String modelAutoBind(Model model, HttpServletRequest request,
			@PathVariable(value = "name") String jgid,
			@PathVariable(value = "age") String sss) {
		// Object s = request.getParameterValues("page");
		// Object l = request.getParameterValues("lll");
		// s.toString().hashCode();
		model.addAttribute("accountmodel", swsDao.testJDBC());
		model.addAttribute("111", jgid);
		model.addAttribute("22", sss);
		return "jsontournamenttemplate";
	}

	/*
	 * @RequestMapping("/api/swscx") public String swscx(Model model) {
	 * model.addAttribute("Data", swsDao.swscx()); return
	 * "jsontournamenttemplate"; }
	 */

	@RequestMapping(value = "/jgs", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swscx(HttpServletRequest request) throws Exception {
		Map<String, Object> sb = new HashMap<>();
		StringBuffer url = request.getRequestURL();
		String z = url.substring(url.indexOf("api")-1, url.indexOf("jgs"));
		try {
			if (request.getParameterValues("pagenum")[0] != null
					&& !request.getParameterValues("pagenum")[0].equals("0")&&request.getParameterValues("pagesize")[0] != null 
					&& !request.getParameterValues("pagesize")[0].equals("0")) {
					Map<String, Object> qury = new HashMap<>();
					qury.put("pn", request.getParameterValues("pagenum")[0]);
					qury.put("ps", request.getParameterValues("pagesize")[0]);
					
					if(request.getParameterValues("sfield")[0] != null
						&&request.getParameterValues("sorder")[0] != null){
						qury.put("sfield", request.getParameterValues("sfield")[0]);
						qury.put("sorder", request.getParameterValues("sorder")[0]);
					}
					if(!request.getParameterValues("dwmc")[0].equals("null")
							&&!request.getParameterValues("dwmc")[0].equals("undefined")){
					qury.put("dwmc", new String(request.getParameterValues("dwmc")[0].getBytes("ISO-8859-1"),"UTF-8"));}
					
					if(!request.getParameterValues("zsbh")[0].equals("null")
							&&!request.getParameterValues("zsbh")[0].equals("undefined")){
					qury.put("zsbh", request.getParameterValues("zsbh")[0]);}
					
					if(!request.getParameterValues("zczj")[0].equals("null")
							&&!request.getParameterValues("zczj")[0].equals("undefined")){
					qury.put("zczj", request.getParameterValues("zczj")[0]);}
					
					if(!request.getParameterValues("cs")[0].equals("null")
							&&!request.getParameterValues("cs")[0].equals("undefined")){
						qury.put("cs", request.getParameterValues("cs")[0]);}
					
					if(!request.getParameterValues("swsxz")[0].equals("null")
							&&!request.getParameterValues("swsxz")[0].equals("undefined")){
						qury.put("swsxz", request.getParameterValues("swsxz")[0]);}
					
					if(!request.getParameterValues("zczj2")[0].equals("null")
							&&!request.getParameterValues("zczj2")[0].equals("undefined")){
						qury.put("zczj2", request.getParameterValues("zczj2")[0]);}
					
					if(!request.getParameterValues("zrs")[0].equals("null")
							&&!request.getParameterValues("zrs")[0].equals("undefined")){
						qury.put("zrs", request.getParameterValues("zrs")[0]);}
					
					if(!request.getParameterValues("zrs2")[0].equals("null")
							&&!request.getParameterValues("zrs2")[0].equals("undefined")){
						qury.put("zrs2", request.getParameterValues("zrs2")[0]);}
					
					if(!request.getParameterValues("clsj")[0].equals("null")
							&&!request.getParameterValues("clsj")[0].equals("undefined")){
						qury.put("clsj", request.getParameterValues("clsj")[0]);}
					
					if(!request.getParameterValues("clsj2")[0].equals("null")
							&&!request.getParameterValues("clsj2")[0].equals("undefined")){
						qury.put("clsj2", request.getParameterValues("clsj2")[0]);}
					
					
					Map<String, Object> meta = swsDao.swscx(z,qury);
					sb.put("data", meta.get("data"));
					sb.put("page", meta.get("page"));
				}
			
			
		} catch (Exception e) {
//			swsDao.swscx(z);
//			ryglDao.ryqy();
//			DbToDb bb = new DbToDb();
//			bb.dealwithRYDB();
		}
		return new ResponseEntity<>(sb,HttpStatus.OK);

	}

	@RequestMapping(value="/{swsxqTab:^[A-Za-z]+$}/{swjgId}", method = { RequestMethod.GET} )
	public ResponseEntity<Map<String, Object>> swsxx(
			@PathVariable(value = "swsxqTab") String xqTab,
			@PathVariable(value = "swjgId") String gid) {
		
		Map<String, Object> sb = new HashMap<>();
		Hashids hashids = new Hashids("project-zs",6);
		int jgid = (int)hashids.decode(gid)[0];
		switch (xqTab) {
		case "swsxx":
			sb.put("data", swsDao.swsxx(jgid));
			break;
		case "zyryxx":
			sb.put("data", swsDao.zyryxx(jgid));
			break;
		case "cyryxx":
			sb.put("data", swsDao.cyryxx(jgid));
			break;
		case "czrylb":
			sb.put("data", swsDao.czrylb(jgid));
			break;
		case "swsbgxx":
			sb.put("data", swsDao.swsbgxx(jgid));
			break;
		case "njjl":
			sb.put("data", swsDao.njjl(jgid));
			break;
		default:
			return new ResponseEntity<>(sb,HttpStatus.OK);
		}
		return new ResponseEntity<>(sb,HttpStatus.OK);
	}
}