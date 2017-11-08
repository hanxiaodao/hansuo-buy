package com.hansuo.portal.service;

import com.hansuo.pojo.TbItem;

public interface ItemService {

	public TbItem getItemById(Long itemId) ;
	
	public String getItemDescById(Long itemId);
	
	public String getItemParamById(Long itemId);
}
