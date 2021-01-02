package com.it.sero.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.it.sero.model.Document;

public interface DocumentServiceRepository extends JpaRepository<Document, Integer> {
	
	@Query("SELECT docId,docName from Document")
	List<Object[]>getDocumentIdandNames();

}
