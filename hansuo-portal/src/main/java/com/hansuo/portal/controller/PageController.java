package com.hansuo.portal.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hansuo.portal.service.ContentService;

@Controller
public class PageController {
	private Log log = LogFactory.getLog(PageController.class);
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String toindex(Model model){
		//取大广告位
		String json =contentService.getAd1List();
		model.addAttribute("ad1", json);
		log.info("正在进入首页。。。。。。。");
		return "index";
	}

	
	
}
