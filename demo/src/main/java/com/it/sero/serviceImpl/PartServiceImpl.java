package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sero.MyCollectionsUtil.MyCollectionUtil;
import com.it.sero.exception.PartNotFoundExcpeption;
import com.it.sero.model.Part;
import com.it.sero.repo.PartRepository;
import com.it.sero.service.IPartService;


@Service
public class PartServiceImpl implements IPartService {

	@Autowired
	private PartRepository repo;
	
	@Override
	public Integer savePart(Part part) {
		
		return repo.save(part).getId();
	}

	@Override
	public List<Part> getAllParts() {
		
		return repo.findAll();
	}

	@Override
	public void updatePart(Part part) {
		repo.save(part);

	}

	@Override
	public void deletePart(Integer id) {
		repo.deleteById(id);

	}

	@Override
	public Part getOnePart(Integer id) {
		Part p=repo.findById(id).orElseThrow(()->new PartNotFoundExcpeption("Part id: "+id+" doesnot exist"));
		return p;
	}

	@Override
	public Map<Integer, String> getPartIdAndCode() {
		
		return MyCollectionUtil.convertListToMap(repo.getPartIdAndCode());
	}

}
