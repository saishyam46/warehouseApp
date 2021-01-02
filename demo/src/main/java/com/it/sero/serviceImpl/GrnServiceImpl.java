package com.it.sero.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.sero.model.Grn;
import com.it.sero.model.GrnDtl;
import com.it.sero.repo.GrnDtlRepository;
import com.it.sero.repo.GrnRepository;
import com.it.sero.service.IGrnService;

@Service
public class GrnServiceImpl implements IGrnService {
	
	@Autowired
	private GrnRepository repo;
	
	@Autowired
	private GrnDtlRepository repoDtl;
	
	@Override
	public Integer saveGrn(Grn grn)
	{
		return repo.save(grn).getId();
	}

	@Override
	public Grn getOneGrn(Integer id) {
		
		return repo.findById(id).get();
	}

	@Override
	public List<Grn> showAllGrn() {
		return repo.findAll();
	}

	@Override
	public void saveGrnDtl(GrnDtl grnDtl) {
		repoDtl.save(grnDtl);
		
	}

	@Override
	public List<GrnDtl> showAllGrnDtl() {
		
		return repoDtl.findAll();
	}

	@Override
	public List<GrnDtl> getGrnDtlbyGrnId(Integer grnId) {

		return repoDtl.getAllGrnDtlsByGrnId(grnId);
	}

	@Transactional
	@Override
	public Integer updateGrnDtlStatus(Integer grnDtlId, String grnDtlStatus) {
		
		return repoDtl.updateGrnDtlStatus(grnDtlId, grnDtlStatus);
	}

	

}
