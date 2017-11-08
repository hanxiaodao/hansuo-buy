package com.hansuo.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hansuo.common.pojo.JsonUtils;
import com.hansuo.mapper.TbItemDescMapper;
import com.hansuo.mapper.TbItemMapper;
import com.hansuo.mapper.TbItemParamItemMapper;
import com.hansuo.pojo.TbItem;
import com.hansuo.pojo.TbItemDesc;
import com.hansuo.pojo.TbItemParamItem;
import com.hansuo.pojo.TbItemParamItemExample;
import com.hansuo.pojo.TbItemParamItemExample.Criteria;
import com.hansuo.rest.component.JedisClient;
import com.hansuo.rest.service.ItemService;

/** 
* @ClassName: ItemServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年10月13日 下午3:21:03 
*  
*/

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${ITEM_BASE_INFO_KEY}")
	private String ITEM_BASE_INFO_KEY;
	@Value("${ITEM_DESC_KEY}")
	private String ITEM_DESC_KEY;
	@Value("${ITEM_PARAM_KEY}")
	private String ITEM_PARAM_KEY;
	@Value("${ITEM_EXPIRE_SECOD}")
	private Integer ITEM_EXPIRE_SECOD;

	@Override
	public TbItem getItemById(Long itemId) {
		//从redis中读取数据
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId +":" + ITEM_BASE_INFO_KEY );
			if(!StringUtils.isBlank(json)){
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 根据商品id查询商品的基本信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		try {
			// 向redis中添加缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId +":" + ITEM_BASE_INFO_KEY, JsonUtils.objectToJson(item));
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId +":" + ITEM_BASE_INFO_KEY,ITEM_EXPIRE_SECOD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return item;
	}
	
	/**
	 * 
	* @Title: getItemDescById 
	* @Description: 查询商品详情
	* @param @param itemId
	* @param @return    设定文件  
	* @throws
	 */
	
	@Override
	public TbItemDesc  getItemDescById(Long itemId) {
		
		try {
			//从redis中查询
			String json= jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY);
			if(!StringUtils.isBlank(json)){
				TbItemDesc itemDesc= JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//根据itemId查询itemDesc
		TbItemDesc itemDesc =itemDescMapper.selectByPrimaryKey(itemId);
		
		try {
			//添加缓存
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY, JsonUtils.objectToJson(itemDesc));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY, ITEM_EXPIRE_SECOD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}
	/** 
	* @Title: getItemParamById 
	* @Description: 商品规格参数查询
	* @param itemId
	* @return TbItemParamItem  
	* @throws 
	*/
	@Override
	public TbItemParamItem getItemParamById(Long itemId) {
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_KEY);
			if(!StringUtils.isBlank(json)){
				TbItemParamItem itemParam = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return itemParam;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//取规格参数
				if (list != null && list.size() > 0) {
					TbItemParamItem itemParamItem = list.get(0);
					//添加缓存
					try {
						//向redis中添加缓存
						jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY
								, JsonUtils.objectToJson(itemParamItem));
						//设置key的过期时间
						jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY, ITEM_EXPIRE_SECOD);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return itemParamItem;
				}
		
		return null;
	}

	
	
	
	
}
