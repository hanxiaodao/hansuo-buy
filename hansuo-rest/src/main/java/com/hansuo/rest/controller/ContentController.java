package com.hansuo.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansuo.common.pojo.ExceptionUtil;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbContent;
import com.hansuo.rest.service.ContentService;

@Controller
public class ContentController {
	

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/{cid}")
	@ResponseBody
	public TaotaoResult getContentList(@PathVariable Long cid) {
		try {
			List<TbContent> list = contentService.getContentList(cid);
			return TaotaoResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/sync/content/{cid}")
	@ResponseBody
	public TaotaoResult sysncContent(@PathVariable Long cid){
		try{
			TaotaoResult result = contentService.sysContent(cid);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
}
