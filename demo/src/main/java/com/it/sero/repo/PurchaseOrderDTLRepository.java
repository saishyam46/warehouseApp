package com.it.sero.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.it.sero.model.PurchaseOrderDetail;

public interface PurchaseOrderDTLRepository extends JpaRepository<PurchaseOrderDetail, Integer>{

	 @Query("SELECT dtl FROM PurchaseOrderDetail dtl JOIN dtl.order as order WHERE order.id =:orderId")
	 public List<PurchaseOrderDetail> getPurchaseDtlsByOrderId(Integer orderId);
	 
	 @Query("SELECT count(dtl.id) FROM PurchaseOrderDetail dtl JOIN dtl.order as order WHERE order.id =:orderId")
	 public Integer getPurchaseDtlsCountByOrderId(Integer orderId);
}
