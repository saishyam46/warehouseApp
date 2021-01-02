package com.it.sero.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.it.sero.model.PurchaseOrder;
import com.it.sero.model.PurchaseOrderDetail;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

public class PurchaseOrderInvoicePdf extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.addHeader("Content-Disposition", "attachment;filename=PurchaseOrderInvoice.pdf");
		
		PurchaseOrder al=(PurchaseOrder) model.get("pOrder");
		
		@SuppressWarnings("unchecked")
		List<PurchaseOrderDetail>al2=(List<PurchaseOrderDetail>) model.get("dtls");
			
		double finalcost=0;
		 
		  for(PurchaseOrderDetail lst:al2) 
		  {
		  finalcost+=Math.ceil((lst.getParts().getPartitemCost())+
					((lst.getQnty())*((lst.getParts().getPartitemCost()*(lst.getParts().getGstSlob()))/100)));
		  }
		
		PdfPTable header = new PdfPTable(4);
		header.addCell("VENDOR CODE");
		header.addCell(al.getWhUserType().getUserCode());
			
		header.addCell("ORDER CODE");
		header.addCell(al.getOrderCode());
		
		header.addCell("FINAL COST");
		header.addCell(String.valueOf(finalcost));
		
		header.addCell("SHIPMENT CODE");
		header.addCell(al.getShipmentType().getShipmentCode());		
		
		document.add(header);
		
		PdfPTable parts= new PdfPTable(5);
		parts.addCell("CODE");
		parts.addCell("COST");
		parts.addCell("QNTY");
		parts.addCell("GST SLOB");
		parts.addCell("VALUE");
		
		for(PurchaseOrderDetail o:al2)
		{
			parts.addCell(o.getOrder().getOrderCode());
			parts.addCell(String.valueOf(o.getParts().getPartitemCost()));
			parts.addCell(String.valueOf(o.getQnty()));
			parts.addCell(String.valueOf(o.getParts().getGstSlob()));
			parts.addCell(String.valueOf(
					(o.getParts().getPartitemCost())+
					(o.getQnty())*((o.getParts().getPartitemCost()*(o.getParts().getGstSlob()))/100))
					);
		}
			
		document.add(parts);
	}

}
