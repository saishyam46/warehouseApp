package com.it.sero.service;

import java.util.List;
import java.util.Map;

import com.it.sero.model.OrderMethod;

public interface IOrderMethodService {
	
	Integer saveOrderMethod(OrderMethod om);
	void updateOrderMethod(OrderMethod om);
	
	void deleteOrderMethod(Integer id);
	OrderMethod getOneOrderMethod(Integer id);
	
	List<OrderMethod> getAllOrderMethods();
	
	Map<Integer,String> getOrderMethodIdAndCodeByMode(String mode);

}
