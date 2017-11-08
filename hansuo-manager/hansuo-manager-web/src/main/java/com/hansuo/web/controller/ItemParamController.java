package com.hansuo.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbItemParam;
import com.hansuo.pojo.TbItemParamExample;
import com.hansuo.service.ItemParamItemService;
import com.hansuo.service.ItemParamService;

@Controller
public class ItemParamController {
	@Resource
	ItemParamService itemParamService;
	@Resource
	ItemParamItemService itemParamItemService;
	@RequestMapping("/item/param/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult veItemParam(@PathVariable Long cid){
		TaotaoResult result = itemParamService.getItemParamByCid(cid);
		return result;
	}
	@RequestMapping("/item/param/list")
	@ResponseBody
	public JSONObject itemParamList(Integer page, Integer rows){
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = itemParamService.selectByExample( new TbItemParamExample());
		JSONObject jo = new JSONObject();
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		jo.put("total", pageInfo.getTotal());
		JSONArray ja = new JSONArray();
		for (TbItemParam tbItemParam : list) {
			JSONObject job = new JSONObject();
			job.put("id", tbItemParam.getId());
			job.put("itemCatId", tbItemParam.getItemCatId());
			job.put("itemCatName", tbItemParam.getItemCatId());
			job.put("paramData", tbItemParam.getParamData());
			job.put("created", tbItemParam.getCreated());
			job.put("updated", tbItemParam.getUpdated());
			//job.put("ck", false);
			ja.add(job);
		}
		jo.put("rows", ja);
		return jo;
	}
	
	@RequestMapping("/item/param/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
		TaotaoResult result = itemParamService.insertItemParam(cid, paramData);
		return result;
	}
	
	@RequestMapping("/item/param/page/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String html = itemParamItemService.getItemParamHtml(itemId);
		model.addAttribute("html", html);
		return"itemparam";
	}

}
