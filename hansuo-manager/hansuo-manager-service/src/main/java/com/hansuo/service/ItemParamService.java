package com.hansuo.service;

import java.util.List;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbItemParam;
import com.hansuo.pojo.TbItemParamExample;

public interface ItemParamService {

	 List<TbItemParam> selectByExample(TbItemParamExample example);
	
	  TaotaoResult getItemParamByCid(Long cid);
	  
	  TaotaoResult insertItemParam(Long cid, String paramData);
}
