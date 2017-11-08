package com.hansuo.service;

import java.util.List;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.pojo.TbContent;
import com.hansuo.pojo.TbContentExample;

public interface ContenService {

	 List<TbContent> selectByExample(TbContentExample example);
	 
		public TaotaoResult insertContent(TbContent content);
}
