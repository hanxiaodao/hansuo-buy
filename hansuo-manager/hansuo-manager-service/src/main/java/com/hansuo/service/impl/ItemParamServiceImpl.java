package com.hansuo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.mapper.TbItemParamMapper;
import com.hansuo.pojo.TbItemParam;
import com.hansuo.pojo.TbItemParamExample;
import com.hansuo.pojo.TbItemParamExample.Criteria;
import com.hansuo.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Resource
	TbItemParamMapper tbItemParamMapper;

	@Override
	public List<TbItemParam> selectByExample(TbItemParamExample example) {
		return tbItemParamMapper.selectByExample(example);
	}

	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		// 根据cid查询
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		// 执行查询
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		// 判断是否查询到结果
		if (list != null && list.size() > 0) {
			TbItemParam itemParam = list.get(0);
			return TaotaoResult.ok(itemParam);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParam(Long cid, String paramData) {
		//创建一个pojo
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入记录
		tbItemParamMapper.insert(itemParam);
		return TaotaoResult.ok();

	}

}
