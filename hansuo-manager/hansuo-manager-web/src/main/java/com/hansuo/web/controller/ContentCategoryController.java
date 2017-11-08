package com.hansuo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansuo.common.pojo.EasyUITree;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category/")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITree> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EasyUITree> list = contentCategoryService.getContentCatList(parentId);
		return list;
		
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createNode(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertCatgory(parentId, name);
		return result;
	}

	
}
