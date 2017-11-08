package com.hansuo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hansuo.mapper.TbItemCatMapper;
import com.hansuo.pojo.TbItemCat;
import com.hansuo.pojo.TbItemCatExample;
import com.hansuo.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Resource
	TbItemCatMapper tbItemCatMapper;
	
	
	@Override
	public List<TbItemCat> selectByExample(TbItemCatExample example) {
		
		return tbItemCatMapper.selectByExample(example);
	}
	
	

}
