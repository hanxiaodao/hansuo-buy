package com.hansuo.rest.service;

import java.util.List;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbContent;

public interface ContentService {
	
	public List<TbContent> getContentList(Long cid) ;
	
	public TaotaoResult sysContent (Long cid);
}
