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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.sero.model.ShipmentType;
import com.it.sero.service.IShipmentTypeService;
import com.it.sero.view.ShipmentTypeExcelView;
import com.it.sero.view.ShipmentTypePdfView;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {
	
	@Autowired
	private IShipmentTypeService service;
	
	@GetMapping("/reg")
	public String showReg(Model model)
	{
		model.addAttribute("shipmentType", new ShipmentType());
		return "ShipmentTypeRegister";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute ShipmentType shipmentType, Model model )
	{
		Integer id=service.saveShipmentType(shipmentType);
		model.addAttribute("msg","ShipmentType '"+id+"' Saved");
		model.addAttribute("shipmentType", new ShipmentType());	
		return "ShipmentTypeRegister";
	}
	
	@GetMapping("/all")
	public String showAll(Model model)
	{
		model.addAttribute("list", service.getAllShipments());
		return "ShipmentTypeData";
	}
	
	@GetMapping("/delete")
	public String deleteOneShipment(@RequestParam Integer id, Model model)
	{	
		service.deleteShipmentType(id);
		model.addAttribute("msg","ShipmentType '"+id+"' deleted");
		model.addAttribute("list", service.getAllShipments());	
		return "ShipmentTypeData";
		
	}
	
	@GetMapping("/edit")
	public String showEdit(@RequestParam Integer id,Model model)
	{
		model.addAttribute("shipmentType", service.getOneshipmentType(id));
		return "ShipmentTypeEdit";
	}
	
	@PostMapping("/update")
	public String doUpdate(@ModelAttribute ShipmentType shipmentType,Model model) 
	{
		service.updateShipmentType(shipmentType);
		model.addAttribute("message", "ShipmentType '"+shipmentType.getId()+"' Updated");
		model.addAttribute("list", service.getAllShipments());
		return "ShipmentTypeData";	
	}
	
	
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		
		ModelAndView m = new ModelAndView();
		List<ShipmentType> list = service.getAllShipments();
		m.addObject("list", list);
		if(list==null || list.isEmpty())
		{
			m.addObject("message", "NO DATA FOR EXCEL EXPORT");
			m.setViewName("ShipmentTypeData");
		} 
		else
		{ 
			m.setView(new ShipmentTypeExcelView());
		}
		
		return m;
	}
	
	@GetMapping("/pdf")
	public ModelAndView exportToPdf()
	{
		ModelAndView m = new ModelAndView();
		List<ShipmentType> list = service.getAllShipments();
		m.addObject("list", list);
		if(list==null || list.isEmpty())
		{
			m.addObject("message", "NO DATA FOR EXCEL EXPORT");
			m.setViewName("ShipmentTypeData");
		} 
		else
		{ 
			m.setView(new ShipmentTypePdfView());
		}
		
		return m;
	}
	
	@GetMapping("/validate")
	public @ResponseBody String validateCode(@RequestParam String code) 
	{
		String message = "";
		if(service.isShipmentTypeexist(code)) {
			message = "Shipment Code '"+code+"' already exist";
		}
		return message;
	}
}
