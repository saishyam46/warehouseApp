package com.it.sero.service;

import java.util.List;

import com.it.sero.model.Grn;
import com.it.sero.model.GrnDtl;

public interface IGrnService {
	
	//GRN
	public Integer saveGrn(Grn grn);
	
	public Grn getOneGrn(Integer id);
	
	public List<Grn> showAllGrn();

	
	//GRN DTL
	public void saveGrnDtl(GrnDtl grnDtl);

	public List<GrnDtl> showAllGrnDtl();

	public List<GrnDtl> getGrnDtlbyGrnId(Integer grnId);

	public Integer updateGrnDtlStatus(Integer grnDtlId,String grnDtlStatus);
}
