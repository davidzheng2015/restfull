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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

	@RequestMapping(value = "/api/jgs", method = { RequestMethod.GET })
	public Map<String, Object> swscx(HttpServletRequest request) {
		Map<String, Object> sb = new HashMap<>();
		StringBuffer url = request.getRequestURL();
		String z = url.substring(0, url.indexOf("api") + 3);
		try {
			if (request.getParameterValues("pagenum")[0] != null
					&& !request.getParameterValues("pagenum")[0].equals("0")) {
				if (request.getParameterValues("pagesize")[0] != null
						&& !request.getParameterValues("pagesize")[0]
								.equals("0")) {
					Map<String, Object> qury = new HashMap<>();
					qury.put("pn", request.getParameterValues("pagenum")[0]);
					qury.put("ps", request.getParameterValues("pagesize")[0]);
					// Object y =request.getParameterValues("qury");
					// String q
					// =java.net.URLDecoder.decode(request.getParameterValues("qury")[0].toString(),"UTF-8");
					// qury.put("qury", q);
					int pn = Integer.parseInt(qury.get("pn").toString());
					int ps = Integer.parseInt(qury.get("ps").toString());
					Map<String, Object> meta = new HashMap<>();
					meta.put("pageNum", pn);
					meta.put("pageSize", ps);
					meta.put("pageTotal",
							swsDao.swscx(z, pn, ps).get("totalsize"));
					meta.put("pageAll", swsDao.swscx(z, pn, ps).get("pagesize"));
					sb.put("Page", meta);
					sb.put("Data", swsDao.swscx(z, pn, ps).get("data"));
				}
			}
		} catch (Exception e) {
			sb.put("Data", swsDao.swscx(z).get("data"));
		}
		return sb;

	}

	@RequestMapping("/api/{swsxqTab:^[A-Za-z]+$}/{swjgId:^[0-9]*$}")
	public Map<String, Object> swsxx(
			@PathVariable(value = "swsxqTab") String xqTab,
			@PathVariable(value = "swjgId") int jgid) {
		Map<String, Object> sb = new HashMap<>();
		switch (xqTab) {
		case "swsxx":
			sb.put("Data", swsDao.swsxx(jgid));
			break;
		case "zyryxx":
			sb.put("Data", swsDao.zyryxx(jgid));
			break;
		case "cyryxx":
			sb.put("Data", swsDao.cyryxx(jgid));
			break;
		case "czrylb":
			sb.put("Data", swsDao.czrylb(jgid));
			break;
		case "swsbgxx":
			sb.put("Data", swsDao.swsbgxx(jgid));
			break;
		case "njjl":
			sb.put("Data", swsDao.njjl(jgid));
			break;
		default:
			return sb;
		}
		return sb;
	}

	public class User {
		private String name;
		private String age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}
	}
}