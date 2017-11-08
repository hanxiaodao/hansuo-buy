package com.hansuo.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hansuo.common.pojo.PictureResult;
import com.hansuo.service.PicService;

@Controller
public class PicController {
	@Resource
	private PicService picService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String  FileUpoad(MultipartFile uploadFile){
		PictureResult result = picService.uploadPic(uploadFile);
		String json = JSONObject.toJSONString(result);
		return json;
	}
}
