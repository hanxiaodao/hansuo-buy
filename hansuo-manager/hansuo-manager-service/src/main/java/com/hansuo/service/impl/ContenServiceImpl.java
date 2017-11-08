package com.hansuo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.mapper.TbContentMapper;
import com.hansuo.pojo.TbContent;
import com.hansuo.pojo.TbContentExample;
import com.hansuo.service.ContenService;
@Service
public class ContenServiceImpl implements ContenService {
	
	@Autowired
	TbContentMapper contentMapper;
	
	@Override
	public List<TbContent> selectByExample(TbContentExample example) {
		return contentMapper.selectByExample(example);
	}
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//插入数据
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}


}
