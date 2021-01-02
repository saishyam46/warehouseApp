package com.it.sero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.it.sero.model.Part;
import com.it.sero.service.IOrderMethodService;
import com.it.sero.service.IPartService;
import com.it.sero.service.IUomService;

@Controller
@RequestMapping("/part")
public class PartController {

	@Autowired
	private IPartService service;
	@Autowired
	private IUomService uomService;
	@Autowired
	private IOrderMethodService orderMethodService;
	
	
	private void addDynamicUiComponents(Model model)
	{
		model.addAttribute("uoms",uomService.getUomIdAndModel());
		model.addAttribute("orderMethods",orderMethodService.getOrderMethodIdAndCodeByMode("Sale"));
	}
	
	@RequestMapping("/reg")
	public String showPartReg(Model model)
	{
		 model.addAttribute("Part", new Part());
		 addDynamicUiComponents(model);
		 return "PartRegister";
	}
	
	@PostMapping("/save")
	public String savePart(@ModelAttribute Part part,Model model)
	{
		Integer id=service.savePart(part);
		model.addAttribute("message","Part saved with id : " + id);
		model.addAttribute("Part", new Part());
		addDynamicUiComponents(model);
		return "PartRegister";
	}
	
	@GetMapping("/all")
	public String getAllParts(Model model)
	{
		List<Part>al=service.getAllParts();
		model.addAttribute("al",al);
		return "PartData";
	}
}
