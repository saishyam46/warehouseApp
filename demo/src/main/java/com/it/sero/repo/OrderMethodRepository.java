package com.it.sero.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.it.sero.model.OrderMethod;

public interface OrderMethodRepository extends JpaRepository<OrderMethod,Integer>{

	@Query("SELECT id,orderCode from OrderMethod  where orderMode=:mode")
	List<Object[]> getOrderMethodIdAndCodeByMode(String mode);
	
}
