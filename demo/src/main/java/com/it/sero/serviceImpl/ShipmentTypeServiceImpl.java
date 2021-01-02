package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sero.MyCollectionsUtil.MyCollectionUtil;
import com.it.sero.exception.ShipmentTypeNotFoundException;
import com.it.sero.model.ShipmentType;
import com.it.sero.repo.ShipmentTypeRepository;
import com.it.sero.service.IShipmentTypeService;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

	@Autowired
	private ShipmentTypeRepository repo;
	
	@Override
	public Integer saveShipmentType(ShipmentType st) {
		
		return repo.save(st).getId();
	}
	
	@Override
	public void updateShipmentType(ShipmentType st) {
		repo.save(st);

	}

	@Override
	public void deleteShipmentType(Integer id) {
		ShipmentType st=getOneshipmentType(id);
		repo.delete(st);

	}

	@Override
	public ShipmentType getOneshipmentType(Integer id) {
		ShipmentType st = repo.findById(id)
				.orElseThrow(
						()-> new ShipmentTypeNotFoundException(
								"ShipmentType '"+id+"' Not exist"));
		return st;
	}

	@Override
	public List<ShipmentType> getAllShipments() {
		
		return repo.findAll();
	}

	@Override
	public boolean isShipmentTypeexist(String code) {
		return repo.getShipmentTypeCodeCount(code)>0?true:false;
	}

	@Override
	public Map<Integer,String> getShipmentIdAndCodebyEnableShipment(String enableShipmentValue) {
		List<Object[]>al=repo.getShipmentIdAndCodebyEnableShipment(enableShipmentValue);
		return MyCollectionUtil.convertListToMap(al);
	}

	

}
