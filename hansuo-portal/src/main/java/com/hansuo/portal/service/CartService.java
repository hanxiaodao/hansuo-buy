package com.hansuo.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.portal.pojo.CartItem;

public interface CartService {

	public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

	public List<CartItem> getCartItems(HttpServletRequest request);
	
	public TaotaoResult updateCartItem(long itemId, Integer num, HttpServletRequest request,HttpServletResponse response);
	
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);

}
