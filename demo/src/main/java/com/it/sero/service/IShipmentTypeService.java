package com.it.sero.service;

import java.util.List;
import java.util.Map;

import com.it.sero.model.ShipmentType;

public interface IShipmentTypeService {
	
	Integer saveShipmentType(ShipmentType st);
	
	void updateShipmentType(ShipmentType st );
	
	void deleteShipmentType(Integer id) ;
	
	ShipmentType getOneshipmentType(Integer id);
	
	List<ShipmentType> getAllShipments();
	
	boolean isShipmentTypeexist(String code);
	
	Map<Integer,String>getShipmentIdAndCodebyEnableShipment(String enableShipmentValue);

}
