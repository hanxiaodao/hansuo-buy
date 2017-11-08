package com.hansuo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hansuo.common.pojo.IDUtils;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.mapper.TbItemDescMapper;
import com.hansuo.mapper.TbItemMapper;
import com.hansuo.mapper.TbItemParamItemMapper;
import com.hansuo.mapper.TbItemParamMapper;
import com.hansuo.pojo.TbItem;
import com.hansuo.pojo.TbItemDesc;
import com.hansuo.pojo.TbItemExample;
import com.hansuo.pojo.TbItemParamItem;
import com.hansuo.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Resource
	TbItemMapper tbItemMapper;
	@Resource
	TbItemDescMapper tbItemDescMapper;
	@Resource
	TbItemParamItemMapper tbItemParamItemMapper;

	private static Log log = LogFactory.getLog(ItemServiceImpl.class);

	@Override
	public TbItem selectByPrimaryKey(Long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TbItem> selectByExample(TbItemExample example) {
		return tbItemMapper.selectByExample(example);
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) {
		// 商品id
		item.setId(IDUtils.genItemId());
		// '商品状态，1-正常，2-下架，3-删除'
		item.setStatus((byte) 1);
		// 创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		tbItemMapper.insertSelective(item);
		log.info("商品添加成功" + item.toString());
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述数据
		tbItemDescMapper.insertSelective(itemDesc);
		log.info("商品描述添加成功" + itemDesc.toString());
		// 添加商品规格参数
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		// 插入数据
		tbItemParamItemMapper.insertSelective(itemParamItem);
		log.info("商品规格添加成功"+itemParamItem.toString());
		return TaotaoResult.ok();
	}

}
