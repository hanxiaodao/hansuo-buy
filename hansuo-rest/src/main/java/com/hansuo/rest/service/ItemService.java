package com.hansuo.rest.service;

import com.hansuo.pojo.TbItem;
import com.hansuo.pojo.TbItemDesc;
import com.hansuo.pojo.TbItemParamItem;

public interface ItemService {

	public TbItem getItemById(Long itemId);
	
	public TbItemDesc  getItemDescById(Long itemId);
	
	public TbItemParamItem getItemParamById(Long itemId);

}
