package com.it.sero.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.it.sero.model.ShipmentType;


public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Integer> {
	
	@Query("SELECT COUNT(shipmentCode) FROM ShipmentType WHERE shipmentCode=:shipmentCode")
	Integer getShipmentTypeCodeCount(String shipmentCode);
	
	@Query("SELECT id,shipmentCode FROM ShipmentType WHERE enableShipment=:enableShipmentValue")
	List<Object[]>getShipmentIdAndCodebyEnableShipment(String enableShipmentValue);
	
}
