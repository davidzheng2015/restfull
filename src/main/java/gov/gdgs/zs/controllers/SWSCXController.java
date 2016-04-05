package gov.gdgs.zs.controllers;

import gov.gdgs.zs.ZsConstants;
import gov.gdgs.zs.dao.SWSDao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + ZsConstants.URI_API_ZS)
public class SWSCXController {
	@Resource
	private SWSDao swsDao;

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
	public Map<String, Object> swscx(HttpServletRequest request) {
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
					
					Map<String, Object> meta = swsDao.swscx(z,qury);
					sb.put("data", meta.get("data"));
					sb.put("page", meta.get("page"));
				}
			
			
		} catch (Exception e) {
			sb.put("data", swsDao.swscx(z).get("data"));
			sb.put("pageTotal",swsDao.swscx(z).get("totalsize"));
		}
		return sb;

	}

	@RequestMapping(value="/{swsxqTab:^[A-Za-z]+$}/{swjgId:^[0-9]*$}", method = { RequestMethod.GET} )
	public Map<String, Object> swsxx(
			@PathVariable(value = "swsxqTab") String xqTab,
			@PathVariable(value = "swjgId") int jgid) {
		Map<String, Object> sb = new HashMap<>();
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
			return sb;
		}
		return sb;
	}
}