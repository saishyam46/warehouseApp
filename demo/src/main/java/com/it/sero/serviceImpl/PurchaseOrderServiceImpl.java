package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sero.MyCollectionsUtil.MyCollectionUtil;
import com.it.sero.exception.PurchaseOrderNotFoundException;
import com.it.sero.model.Grn;
import com.it.sero.model.GrnDtl;
import com.it.sero.model.PurchaseOrder;
import com.it.sero.model.PurchaseOrderDetail;
import com.it.sero.repo.PurchaseOrderDTLRepository;
import com.it.sero.repo.PurchaseOrderRepository;
import com.it.sero.service.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repo;
	
	@Autowired
	private PurchaseOrderDTLRepository dtlrepo;
	
	
	
	@Override
	public Integer savePurchaseOrder(PurchaseOrder po) {
		return  repo.save(po).getId();
		
	}

	@Override
	public List<PurchaseOrder> getAllOrders() {
		
		return repo.findAll();
	}

	@Override
	public PurchaseOrder getOnePurchaseOrder(Integer id) {
		PurchaseOrder po=repo.findById(id).orElseThrow(()->new PurchaseOrderNotFoundException ("Purchase Oder : "+id+ " not found"));
		return po;
	}

	@Override
	public Integer savePurchaseOrderDetail(PurchaseOrderDetail podtl) {
		
		return dtlrepo.save(podtl).getId();
	}

	@Override
	public List<PurchaseOrderDetail> getPurchaseDtlsByOrderId(Integer id) {
		
		System.out.println("CONSOLE---------->"+dtlrepo.getPurchaseDtlsByOrderId(id).toString());
		return dtlrepo.getPurchaseDtlsByOrderId(id);
	}

	@Override
	public void deletePartDetailforOrder(Integer id) {
		dtlrepo.deleteById(id);
		
	}

	
	@Override
	public Integer getPurchaseOrderDtlCountByOrderId(Integer id) {
		return dtlrepo.getPurchaseDtlsCountByOrderId(id);
	}

	@Override
	@Transactional
	public void updateOrderStatusbyOrderId(String status, Integer orderId) {
		repo.updateOrderStatusByOrderId(status, orderId);		
	}

	@Override
	public Map<Integer, String> purchaseOrderCodeByStatus(String poStatus) {

		return MyCollectionUtil.convertListToMap(repo.purchaseOrderCodeByStatus(poStatus));
	}

	

}
