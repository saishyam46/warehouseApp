package com.it.sero.service;

import java.util.List;
import java.util.Optional;

import com.it.sero.model.Document;
import com.it.sero.model.Uom;

public interface IDocumentService {

	public Integer saveDocument(Document document);
	public List<Document> getAllDocs();
	public List<Object[]> getDocumentIdandNames();
	public Optional<Document> getOneDocumnet(Integer id);
}
