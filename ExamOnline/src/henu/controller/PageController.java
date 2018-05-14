package henu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

	@RequestMapping("/{uri}")
	public String page(@PathVariable String uri) {
		return StringUtils.isEmpty(uri) ? "login" : uri;
	}
	
}
