package com.it.sero.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.it.sero.model.Part;

public interface PartRepository extends JpaRepository<Part, Integer>{

	@Query("SELECT p.id,p.partItemCode FROM Part p")
	public List<Object[]> getPartIdAndCode();
}
