package com.it.sero.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.sero.constant.OrderStatus;
import com.it.sero.model.Grn;
import com.it.sero.model.GrnDtl;
import com.it.sero.model.PurchaseOrderDetail;
import com.it.sero.service.IGrnService;
import com.it.sero.service.IPurchaseOrderService;

@Controller
@RequestMapping("/grn")
public class GrnController {

	@Autowired
	private IGrnService service;

	@Autowired
	private IPurchaseOrderService poService;
	
	public void dynamicUiDropdownForGrn(Model model)
	{
		Map<Integer,String>mp=poService.purchaseOrderCodeByStatus(OrderStatus.INVOICED.name());
		model.addAttribute("grnOrderCode", mp);
	}
	
	@GetMapping
	@RequestMapping("/reg")
	public String showGrnReg(Model model)
	{
		model.addAttribute("grn", new Grn());
		dynamicUiDropdownForGrn(model);
		return "GrnRegister";		
	}
	
	@PostMapping
	@RequestMapping("/save")
	public String saveGrn(@ModelAttribute Grn grn,Model model)
	{
		Integer id=service.saveGrn(grn);
		model.addAttribute("grnMsg", "Grn created successfully with id: "+id);
		Integer	orderId=service.getOneGrn(id).getPurchaseOrder().getId();
		poService.updateOrderStatusbyOrderId(OrderStatus.RECEIVED.name(), orderId);
		model.addAttribute("grn", new Grn());
		dynamicUiDropdownForGrn(model);
		
		createGrnDtl(grn);
		return "GrnRegister";
	}
	
	public void createGrnDtl(Grn grn)
	{
		Integer purchaseOrderId=grn.getPurchaseOrder().getId();
		List<PurchaseOrderDetail>poDtl=poService.getPurchaseDtlsByOrderId(purchaseOrderId);
		
		for(PurchaseOrderDetail u:poDtl)
		{
			GrnDtl grnDtl=new GrnDtl();
			grnDtl.setItemCode(u.getOrder().getOrderCode());
			grnDtl.setBaseCost(u.getParts().getPartitemCost());
			grnDtl.setQnty(u.getQnty());
			grnDtl.setValue((u.getQnty())*(u.getParts().getPartitemCost()));
			
			grnDtl.setGrn(grn);
			
			service.saveGrnDtl(grnDtl);
		}
	}
	
	@GetMapping
	@RequestMapping("/all")
	public String showAllGrn(Model model)
	{
		List<Grn>al=service.showAllGrn();
		model.addAttribute("list", al);
		return "GrnData";
	}
	
	@GetMapping
	@RequestMapping("/parts")
	public String showGrnDtl(@RequestParam Integer grnId,Model model)
	{
		Grn grn=service.getOneGrn(grnId);
		model.addAttribute("grn", grn);
		
		List<GrnDtl>grnDtlList=service.getGrnDtlbyGrnId(grnId);
		
		for(GrnDtl o:grnDtlList)
		{
			System.out.println("-!!>>!! " + o);
		}
		model.addAttribute("list", grnDtlList);
		return "GrnPartDtl";
	}
	
	@GetMapping
	@RequestMapping("/updateStatus")
	public String updateGrnDtlStatus(
			@RequestParam Integer grnId,
			@RequestParam Integer grnDtlId,
			@RequestParam String status)
	{
		service.updateGrnDtlStatus(grnDtlId,status);
		return "redirect:parts?grnId="+grnId;
		
	}
}
