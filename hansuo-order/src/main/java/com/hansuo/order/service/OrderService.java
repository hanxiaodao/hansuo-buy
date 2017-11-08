package com.hansuo.order.service;

import com.hansuo.common.pojo.TaotaoResult;
import com.hansuo.order.pojo.OrderInfo;

public interface OrderService {

	public TaotaoResult createOrder(OrderInfo orderInfo);
}
