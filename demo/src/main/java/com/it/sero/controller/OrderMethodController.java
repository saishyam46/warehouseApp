package com.it.sero.controller;


import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.sero.model.OrderMethod;
import com.it.sero.service.IOrderMethodService;

@Controller
@RequestMapping("/order")
public class OrderMethodController {
	
	private Logger log=LoggerFactory.getLogger(OrderMethodController.class);
	
	@Autowired
	private IOrderMethodService service;
	
	@GetMapping("/reg")
	public String regOrder(Model model)
	{
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}
	
	@PostMapping("/save")
	public String saveOrder(@ModelAttribute OrderMethod orderMethod,Model model)
	{
		Integer id=service.saveOrderMethod(orderMethod);
		model.addAttribute("message","order saved with id : "+id);
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}
	
	@GetMapping("/all")
	public String showAllOrders(Model model)
	{
		List<OrderMethod>list=service.getAllOrderMethods();
		model.addAttribute("list", list);
		return "OrderMethodData";	
	}
	
	@GetMapping("/edit")
	public String editOrder(@RequestParam Integer id,Model model)
	{
		model.addAttribute("orderMethod",service.getOneOrderMethod(id));
		return "OrderMethodEdit";
		
	}
	
	@GetMapping("/delete/{id}")
	public String deleteOneOrder(@PathVariable Integer id,Model model)
	{	
		service.deleteOrderMethod(id);
		model.addAttribute("message","Order '"+id+"' deleted");
		model.addAttribute("list", service.getAllOrderMethods());	
		return "OrderMethodData";
		
	}
	
	
	
	@PostMapping("/update")
	public String doUpdate(@ModelAttribute OrderMethod orderMethod,Model model) 
	{
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message", "Order '"+orderMethod.getId()+"' Updated");
		model.addAttribute("list", service.getAllOrderMethods());
		return "OrderMethodData";	
	}
	
	
	@GetMapping("/view/{id}")
	public String showView(@PathVariable Integer id,Model model) 
	{
		try {
			log.info("Making Service call");
			OrderMethod od=service.getOneOrderMethod(id);
			log.info("Reading Data from Optional Object");
			model.addAttribute("ob", od);
		} catch (NoSuchElementException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "OrderMethodView";
	}

}
