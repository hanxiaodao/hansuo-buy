package com.hansuo.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hansuo.common.pojo.EasyUITree;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbItem;
import com.hansuo.pojo.TbItemCat;
import com.hansuo.pojo.TbItemCatExample;
import com.hansuo.pojo.TbItemCatExample.Criteria;
import com.hansuo.pojo.TbItemExample;
import com.hansuo.pojo.TbItemParam;
import com.hansuo.pojo.TbItemParamExample;
import com.hansuo.service.ItemCatService;
import com.hansuo.service.ItemParamService;
import com.hansuo.service.ItemService;

@Controller
public class ItemController {

	@Resource
	ItemService itemService;
	@Resource
	ItemCatService itemCatService;
	@Resource
	ItemParamService itemParamService;
	private static Log log = LogFactory.getLog(ItemController.class);
	
	@RequestMapping("selById")
	@ResponseBody
	public TbItem selById(long id) {
		System.out.println("进入：ItemController");
		TbItem tb = itemService.selectByPrimaryKey(id);
		return tb;
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public JSONObject selItemList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbItemExample tbItemExample = new TbItemExample();
		List<TbItem> list = itemService.selectByExample(tbItemExample);
		JSONObject jo = new JSONObject();
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		jo.put("total", pageInfo.getTotal());
		JSONArray ja = new JSONArray();
		for (TbItem tbItem : list) {
			JSONObject job = new JSONObject();
			job.put("id", tbItem.getId());
			job.put("title", tbItem.getTitle());
			job.put("cid", tbItem.getCid());
			job.put("sellPoint", tbItem.getSellPoint());
			job.put("price", tbItem.getPrice());
			job.put("num", tbItem.getNum());
			job.put("barcode", tbItem.getBarcode());
			job.put("status", tbItem.getStatus());
			job.put("created", tbItem.getCreated());
			job.put("updated", tbItem.getUpdated());
			job.put("ck", false);
			ja.add(job);
		}
		jo.put("rows", ja);
		return jo;
	}

	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITree> getitemCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		// 根据parentId查询分类列表
		TbItemCatExample example = new TbItemCatExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatService.selectByExample(example);
		// 转换成EasyUITreeNode列表
		List<EasyUITree> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			// 创建一个节点对象
			EasyUITree node = new EasyUITree();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent() ? "closed" : "open");
			// 添加到列表中
			resultList.add(node);
		}
		return resultList;
	}

	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult itemSave(TbItem item,String desc,String itemParams) {
		TaotaoResult result  =itemService.createItem(item, desc,itemParams);
		log.info("添加商品成功");
		return result;
	}
	
	
}
