package com.hansuo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hansuo.common.pojo.HttpClientUtil;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbContent;
import com.hansuo.pojo.TbContentExample;
import com.hansuo.pojo.TbContentExample.Criteria;
import com.hansuo.service.ContenService;

@Controller
public class ContentController {
	
	@Autowired
	ContenService contentService;
	
	@Value("${REST_BASE_URL}")
	String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYNC_URL}")
	String REST_CONTENT_SYNC_URL;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public JSONObject  getContentQueryList(Integer page, Integer rows,Long categoryId){
		PageHelper.startPage(page,rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentService.selectByExample(example);
		JSONObject jo = new JSONObject();
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		jo.put("total", pageInfo.getTotal());
		JSONArray ja = new JSONArray();
		for (TbContent tbContent : list) {
			JSONObject job = new JSONObject();
			job.put("id",tbContent.getId() );
			job.put("title",tbContent.getTitle() );
			job.put("subTitle",tbContent.getSubTitle() );
			job.put("titleDesc",tbContent.getTitleDesc() );
			job.put("url",tbContent.getUrl() );
			job.put("pic",tbContent.getPic() );
			job.put("pic2",tbContent.getPic2() );
			job.put("created", tbContent.getCreated());
			job.put("updated",tbContent.getUpdated() );
			ja.add(job);
		}
		jo.put("rows",ja);
		return jo;
	}

	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		//调用rest服务更新redis
		HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		return result;
	}





}
