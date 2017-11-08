package com.hansuo.service;

import java.util.List;

import com.hansuo.common.pojo.EasyUITree;
import com.hansuo.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	public List<EasyUITree> getContentCatList(Long parentId) ;
	
	public TaotaoResult insertCatgory(Long parentId, String name);
}
