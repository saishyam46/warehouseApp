package com.it.sero.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.it.sero.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer>
{
	

	@Modifying
	@Query("UPDATE PurchaseOrder set status=:status where id=:orderId")
	public void updateOrderStatusByOrderId(String status,Integer orderId);

	@Query("SELECT id,orderCode from PurchaseOrder where status=:poStatus")
	public List<Object[]> purchaseOrderCodeByStatus(String poStatus);
	
}
