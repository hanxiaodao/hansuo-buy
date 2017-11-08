package com.hansuo.service;

import java.util.List;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbItem;
import com.hansuo.pojo.TbItemExample;

public interface ItemService {

	TbItem selectByPrimaryKey(Long id);
	
	  List<TbItem> selectByExample(TbItemExample example);
	  
	  public TaotaoResult createItem(TbItem item, String desc,String itemParam);
}
