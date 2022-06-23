package com.yutadd;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
	@RequestMapping({"/","/index.html","/index.php","index.htm"})
	public String top() {
		
		return "index";
	}
	@RequestMapping("streamsdata")
	@ResponseBody
	public String createjson() {
		return "[{\"icon\":\"/icon.png\",\"title\":\"eyyyyyyyyyyyyy\"},{\"icon\":\"/icon.png\",\"title\":\"eyyyyyyyyyyyyy\"}]";
	}
}
