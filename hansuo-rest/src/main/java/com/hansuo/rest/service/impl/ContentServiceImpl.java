package com.hansuo.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hansuo.common.pojo.JsonUtils;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.mapper.TbContentMapper;
import com.hansuo.pojo.TbContent;
import com.hansuo.pojo.TbContentExample;
import com.hansuo.pojo.TbContentExample.Criteria;
import com.hansuo.rest.component.JedisClient;
import com.hansuo.rest.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	@Override
	public List<TbContent> getContentList(Long cid) {
		try{
			//从redis中取缓存数据
			String json = jedisClient.hget(REDIS_CONTENT_KEY, cid+"");
			if(!StringUtils.isBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json,TbContent.class);
				return list;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		// 根据cid查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		//向redis写入数据
		try{
			//为了规范key,可以使用hash
			//定一个保存内容的key,hash中每个项都是cid
			//value是list，需要把list转换成json数据。
			jedisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;

	}
	@Override
	public TaotaoResult sysContent(Long cid) {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaotaoResult.ok();
	}

}
