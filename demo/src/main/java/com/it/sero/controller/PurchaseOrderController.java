package com.it.sero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.it.sero.constant.OrderStatus;
import com.it.sero.model.PurchaseOrder;
import com.it.sero.model.PurchaseOrderDetail;
import com.it.sero.service.IPartService;
import com.it.sero.service.IPurchaseOrderService;
import com.it.sero.service.IShipmentTypeService;
import com.it.sero.service.IWhUserTypeService;
import com.it.sero.view.PurchaseOrderInvoicePdf;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {
	
	@Autowired
	private IPurchaseOrderService service;
	
	@Autowired
	private IShipmentTypeService shipmentTypeService;
	
	@Autowired
	private IWhUserTypeService whUserService;
	
	@Autowired
	private IPartService partService;
	
	
	public void dynamicUIComponents(Model model)
	{ 
		
		model.addAttribute("ShipmentCodebyEnableShipment", shipmentTypeService.getShipmentIdAndCodebyEnableShipment("YES"));	
		model.addAttribute("whUserServiceByCode",whUserService.getWhUserTypeByUserType("Vendor"));
	}
	
	@GetMapping("/reg")
	public String showPurchaseOrderReg(Model model)
	
	{
		PurchaseOrder po=new PurchaseOrder();
		po.setStatus("OPEN");
		model.addAttribute("PurchaseOrder", po);
		dynamicUIComponents(model);
		return "PurchaseOrder";
	}
	
	@PostMapping
	@RequestMapping("/save")
	public String savePurchaseOrder(@ModelAttribute PurchaseOrder po,Model model)
	{
		Integer id=service.savePurchaseOrder(po);
		model.addAttribute("message","PurchaseOrder "+ id + " saved");
		PurchaseOrder po1=new PurchaseOrder();
		po.setStatus("OPEN");
		model.addAttribute("PurchaseOrder", po1);	
		dynamicUIComponents(model);
		return "PurchaseOrder";
	}
	
	@GetMapping
	@RequestMapping("/all")
	public String getAllPos(Model model)
	{
		List<PurchaseOrder>list=service.getAllOrders();
		model.addAttribute("list",list);
		return "PurchaseOrderData";
	}
	
	private void dynamicUiDropdownForParts(Model model)
	{
		model.addAttribute("partsDropdown", partService.getPartIdAndCode());
	}
	
	@GetMapping
	@RequestMapping("/parts")
	public String showPartsPage(@RequestParam Integer id,Model model)
	{
		PurchaseOrder po=service.getOnePurchaseOrder(id);
		//Integer orderId=po.getId();		
		model.addAttribute("OnePO", po);
		dynamicUiDropdownForParts(model);
		model.addAttribute("purchaseOrderDtl", new PurchaseOrderDetail());
		
		List<PurchaseOrderDetail> podtlList=service.getPurchaseDtlsByOrderId(id);
		System.out.println("CONSOLE---------->"+podtlList);
		model.addAttribute("dtls",podtlList);
		return "PurchaseOrderParts";
	}
	
	@GetMapping
	@RequestMapping("/add")
	public String showAddedPartDtls(@ModelAttribute PurchaseOrderDetail podtl,Model model)
	{
		service.savePurchaseOrderDetail(podtl);
		Integer id=podtl.getOrder().getId();
		service.updateOrderStatusbyOrderId(OrderStatus.PICKING.name(), id);
		model.addAttribute("poDTL", new PurchaseOrderDetail());
		
		return "redirect:parts?id="+id;
	}
	
	@GetMapping
	@RequestMapping("/remove")
	public String deletePartDtlbasedonId(@RequestParam Integer ordrId,@RequestParam Integer ordrDtlid,Model model  )
	{
		
		service.deletePartDetailforOrder(ordrDtlid);
		if(service.getPurchaseOrderDtlCountByOrderId(ordrId)==0)
		{
			service.updateOrderStatusbyOrderId(OrderStatus.OPEN.name(), ordrId);
		}
		return "redirect:parts?id="+ordrId;
	}
	
	@GetMapping
	@RequestMapping("/showAll")
	public String showAllPos(Model model)
	{
		List<PurchaseOrder>list=service.getAllOrders();
		model.addAttribute("list",list);
		return "PurchaseOrderData";
	}
	
	@GetMapping
	@RequestMapping("/placeOrder")
	public String placeOrderbasedonId(@RequestParam Integer ordrId)
	{
		service.updateOrderStatusbyOrderId(OrderStatus.ORDERED.name(), ordrId);
		return "redirect:parts?id="+ordrId;
	}
	
	@GetMapping
	@RequestMapping("/genInvoice")
	public String genInvoice(@RequestParam Integer id)
	{
		service.updateOrderStatusbyOrderId(OrderStatus.INVOICED.name(), id);
		return "redirect:showAll";
	}
	
	@GetMapping
	@RequestMapping("/downloadInvoice")
	public ModelAndView downloadInvoice(@RequestParam Integer id)
	{
		ModelAndView mv=new ModelAndView();
		mv.setView(new PurchaseOrderInvoicePdf());
		List<PurchaseOrderDetail>al2=service.getPurchaseDtlsByOrderId(id);
		System.out.println("CONSOLE---------->"+al2);
		mv.addObject("dtls",service.getPurchaseDtlsByOrderId(id));
		mv.addObject("pOrder", service.getOnePurchaseOrder(id));
		
		return mv;
	}
}
