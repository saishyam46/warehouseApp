package com.it.sero.service;

import java.util.List;
import java.util.Map;

import com.it.sero.model.PurchaseOrder;
import com.it.sero.model.PurchaseOrderDetail;

public interface IPurchaseOrderService {
	
	Integer savePurchaseOrder(PurchaseOrder po);	
	List<PurchaseOrder> getAllOrders();	
	PurchaseOrder getOnePurchaseOrder(Integer id);
	
	
	Integer savePurchaseOrderDetail(PurchaseOrderDetail podtl);	
	List<PurchaseOrderDetail>getPurchaseDtlsByOrderId(Integer id);	
	void deletePartDetailforOrder(Integer id);
	Integer getPurchaseOrderDtlCountByOrderId(Integer id);	
	void updateOrderStatusbyOrderId(String status,Integer orderId);
	
	Map<Integer,String>purchaseOrderCodeByStatus(String poStatus);
}
