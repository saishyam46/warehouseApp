package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.it.sero.MyCollectionsUtil.MyCollectionUtil;
import com.it.sero.exception.UomNotFoundException;
import com.it.sero.model.Uom;
import com.it.sero.repo.UomRepository;
import com.it.sero.service.IUomService;

@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private UomRepository repo;

	@Override
	public Integer saveUom(Uom uom) {
		return repo.save(uom).getId();
	}

	@Override
	public List<Uom> getAllUoms() {
		return repo.findAll();
	}

	@Override
	public void deleteUom(Integer id) {
		Uom uom  = getOneUom(id);
		repo.delete(uom);
	}

	@Override
	public Uom getOneUom(Integer id) {
		Uom uom=null;
		try {
			uom = repo.findById(id)
					.orElseThrow(()-> new UomNotFoundException("Uom '"+id+"' Not exist"));
		} catch (UomNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uom;
	}

	@Override
	public void updateUom(Uom uom) {
		repo.save(uom);
	}


	@Override
	public boolean isUomModelExist(String uomModel) {
		return repo.getUomModelCount(uomModel) > 0 ? true : false;
	}

	@Override
	public List<Object[]> getUomTypeAndCount() {
		return repo.getUomTypeAndCount();
	}

	@Override
	public Map<Integer, String> getUomIdAndModel() {
		List<Object[]> list = repo.getUomIdAndModel();
		Map<Integer,String> map = MyCollectionUtil.convertListToMap(list);
		return map;
	}
	
	@Override
	public boolean isUomModelExistForEdit(String uomModel, Integer id) {
		//return repo.getUomModelCountForNotId(uomModel, id) > 0 ? true : false;
		return repo.getUomModelCountForNotId(uomModel, id) > 0;
	}
	
	@Override
	public Page<Uom> getAllUoms(Pageable p) {
		return repo.findAll(p);
	}

	@Override
	public Page<Uom> findByUomModelContaining(String uomModel, Pageable pageable) {
		return repo.findByUomModelContaining(uomModel, pageable);
	}

}
