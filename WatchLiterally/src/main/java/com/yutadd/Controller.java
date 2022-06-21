package com.yutadd;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {
	@RequestMapping({"/","/index.html","/index.php","index.htm"})
	public String top() {
		
		return "index";
	}
}
