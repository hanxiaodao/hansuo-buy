package com.hansuo.portal.service.impl;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hansuo.common.pojo.JsonUtils;
import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.common.utils.CookieUtils;
import com.hansuo.pojo.TbItem;
import com.hansuo.portal.pojo.CartItem;
import com.hansuo.portal.service.CartService;
import com.hansuo.portal.service.ItemService;
@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ItemService itemService;
	@Value("${COOKIE_EXPIRE}")
	private Integer COOKIE_EXPIRE;
	
	
	/**
	 * 
	* @Title: addCart 
	* @Description: TODO  计算购物车列表
	* @param @param itemId
	* @param @param num
	* @param @param request
	* @param @param response
	* @param @return    设定文件  
	* @throws
	 */
	@Override
	public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {

//		1、接收商品id
//		2、从cookie中购物车商品列表
		List<CartItem> cartItemList =getCartItemList(request); 
//		3、从商品列表中查询列表是否存在此商品
		boolean haveFlg = false;
		for (CartItem cartItem : cartItemList) {
			//如果商品存在数量相加
			// 4、如果存在商品的数量加上参数中的商品数量
			if (cartItem.getId().longValue() == itemId) {
				cartItem.setNum(cartItem.getNum() + num);
				haveFlg = true;
				break;
			}
		}
//		5、如果不存在，调用rest服务，根据商品id获得商品数据。
		if(!haveFlg){
			TbItem item  = itemService.getItemById(itemId);
			//转成cartItem
			CartItem cartItem = new CartItem();
			cartItem.setId(itemId);
			cartItem.setNum(num);
			cartItem.setPrice(item.getPrice());
			cartItem.setTitle(item.getTitle());
			if (StringUtils.isNotBlank(item.getImage())) {
				String image = item.getImage();
				String[] strings = image.split(",");
				cartItem.setImage(strings[0]);
			}
			//添加到购物车商品列表
			// 6、把商品数据添加到列表中
			cartItemList.add(cartItem);

		}
//		7、把购物车商品列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), COOKIE_EXPIRE, true);
//		8、返回TaotaoResult
		return TaotaoResult.ok();

	}
	
	
	/**
	 * 
	* @Title: getCartItemList 
	* @Description: TODO  从ccokie中获取购物车列表
	* @param @param request
	* @param @return    设定文件 
	* @return List<CartItem>    返回类型 
	* @throws
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request){
		try {
			//从cookie中取商品列表
			String json = CookieUtils.getCookieValue(request,"TT_CART",true);
			//吧json转成java对象
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			return list==null?new ArrayList<CartItem>():list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<CartItem>();
		}
	}


	@Override
	public List<CartItem> getCartItems(HttpServletRequest request) {
		List<CartItem> list = getCartItemList(request);
		return list;
	}


	@Override
	public TaotaoResult updateCartItem(long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		//根据商品id查询商品
		for (CartItem cartItem : itemList) {
			if (cartItem.getId().longValue() == itemId) {
				//更新数量
				cartItem.setNum(num);
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);
		return TaotaoResult.ok();

	}


	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 1、接收商品id
		// 2、从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 3、找到对应id的商品
		for (CartItem cartItem : itemList) {
			if (cartItem.getId() == itemId) {
				// 4、删除商品。
				itemList.remove(cartItem);
				break;
			}
		}
		// 5、再重新把商品列表写入cookie。
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);
		// 6、返回成功
		return TaotaoResult.ok();

	}
	
	
	
}
