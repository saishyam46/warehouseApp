package com.it.sero.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.it.sero.model.Uom;

public class UomExcelView extends AbstractXlsxView  {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
				//set file name
				response.addHeader("Content-Disposition", "attachment;filename=uoms.xlsx");
							
				//read data from Model
				@SuppressWarnings("unchecked")
				List<Uom> list = (List<Uom>) model.get("list");
				
				Sheet sheet = workbook.createSheet("UOMS");
				setHeader(sheet);
				setBody(sheet,list);
		
	}	

	private void setHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("TYPE");
		row.createCell(2).setCellValue("MODEL");
		row.createCell(3).setCellValue("DESCRIPTION");
	}
	
	private void setBody(Sheet sheet,List<Uom> list)
	{
		Integer rownum=1;
		for(Uom u:list)
		{
			Row row=sheet.createRow(rownum+1);
			row.createCell(0).setCellValue(u.getId());
			row.createCell(1).setCellValue(u.getUomType());
			row.createCell(2).setCellValue(u.getUomModel());
			row.createCell(3).setCellValue(u.getDescription());
		}
	}
}
