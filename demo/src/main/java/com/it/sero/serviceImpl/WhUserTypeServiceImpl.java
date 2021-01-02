package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sero.MyCollectionsUtil.MyCollectionUtil;
import com.it.sero.exception.WhUserTypeNotFoundException;
import com.it.sero.model.WhUserType;
import com.it.sero.repo.WhUserTypeRepository;
import com.it.sero.service.IWhUserTypeService;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {

	@Autowired
	private WhUserTypeRepository repo;
	
	@Override
	public Integer saveWhUserType(WhUserType whUserType) {
		
		return repo.save(whUserType).getId();
	}

	@Override
	public List<WhUserType> getAllWhUserTypes() {
		
		return repo.findAll();
	}

	@Override
	public void updateWhUserType(WhUserType whUserType) {
		
		repo.save(whUserType);
	}

	@Override
	public void deleteWhUserType(Integer id) {
		WhUserType obj=getOneWhUserType(id);
		repo.delete(obj);

	}

	@Override
	public WhUserType getOneWhUserType(Integer id) {
		WhUserType obj=repo.findById(id).orElseThrow(()->new WhUserTypeNotFoundException("WareHouse  User "+ id + " not exist"));
		return obj;
	}

	@Override
	public boolean isWhUserMailIdExist(String email) {
		return repo.getUserEmailCount(email)>0;
		
	}

	@Override
	public Map<Integer, String> getWhUserTypeByUserType(String userType) {
		List<Object[]>al=repo.getWhUserTypeByUserType(userType);
		
		return MyCollectionUtil.convertListToMap(al);
	}

}
