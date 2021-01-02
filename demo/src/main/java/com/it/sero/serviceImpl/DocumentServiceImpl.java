package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sero.model.Document;
import com.it.sero.repo.DocumentServiceRepository;
import com.it.sero.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {
	
	@Autowired
	private DocumentServiceRepository repo;

	@Override
	public Integer saveDocument(Document document) {
		return repo.save(document).getDocId();
	}

	@Override
	public List<Document> getAllDocs() {
		
		return repo.findAll();
	}

	@Override
	public List<Object[]> getDocumentIdandNames() {
			
		return repo.getDocumentIdandNames();
	}

	@Override
	public Optional<Document> getOneDocumnet(Integer id) {
		
		return repo.findById(id);
	}

}
