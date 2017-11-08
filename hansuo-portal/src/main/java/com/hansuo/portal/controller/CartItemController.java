package com.hansuo.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.portal.pojo.CartItem;
import com.hansuo.portal.service.CartService;

@Controller
public class CartItemController {
	
	@Autowired
	private CartService cartService;
	
	/**
	 * 
	* @Title: addCart 
	* @Description: TODO 添加到购物车
	* @param @param itemId
	* @param @param num
	* @param @param response
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = cartService.addCart(itemId, num, request, response);
		return "cartSuccess";
	}

	/**
	 * 
	* @Title: showCartList 
	* @Description: TODO 购物车列表
	* @param @param request
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, Model model) {
		List<CartItem> list = cartService.getCartItems(request);
		//把商品列表传递给jsp
		model.addAttribute("cartList", list);
		return "cart";
	}
	/**
	 * 
	* @Title: updateCartItemNum 
	* @Description: TODO 修改购物车数量
	* @param @param num
	* @param @param response
	* @param @param request
	* @param @return    设定文件 
	* @return TaotaoResult    返回类型 
	* @throws
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = cartService.updateCartItem(itemId, num, request, response);
		return result;
	}

	/**
	 * 
	* @Title: deleteCartItem 
	* @Description: TODO 删除
	* @param @param itemId
	* @param @param response
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}

	
}
