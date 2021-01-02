package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sero.MyCollectionsUtil.MyCollectionUtil;
import com.it.sero.exception.OrderNotFoundExcpetion;
import com.it.sero.model.OrderMethod;
import com.it.sero.repo.OrderMethodRepository;
import com.it.sero.service.IOrderMethodService;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {
	
	@Autowired
	private OrderMethodRepository repo;
	
	@Override
	public Integer saveOrderMethod(OrderMethod om)
	{
		return repo.save(om).getId();
	}
	
	@Override
	public void updateOrderMethod(OrderMethod om)
	{
		repo.save(om);
	}
	
	@Override
	public void deleteOrderMethod(Integer id)
	{
		OrderMethod od=repo.findById(id).orElseThrow(()-> new OrderNotFoundExcpetion("Order id: "+id +" doesnot exist"));
		if(od!=null)
		{
			repo.delete(od);
		}
		
	}
	
	@Override
	public OrderMethod getOneOrderMethod(Integer id)
	{
		
		return repo.findById(id).orElseThrow(()-> new OrderNotFoundExcpetion("Order id: "+id +" doesnot exist"));
	}
	
	@Override
	public List<OrderMethod> getAllOrderMethods()
	{
		return  repo.findAll();
	}
	
	@Override
	public Map<Integer,String> getOrderMethodIdAndCodeByMode(String mode)
	{
		
		return MyCollectionUtil.convertListToMap(repo.getOrderMethodIdAndCodeByMode(mode));
	}

}
