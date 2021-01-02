package com.it.sero.controller;

import java.io.UnsupportedEncodingException;
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

import com.it.sero.MyCollectionsUtil.EmailUtil;
import com.it.sero.model.WhUserType;
import com.it.sero.service.IWhUserTypeService;

@Controller
@RequestMapping("/whUser")
public class WhUserTypeController {
	
	@Autowired
	private IWhUserTypeService service;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@RequestMapping("/reg")
	public String showReg(Model  model)
	{
		model.addAttribute("whUserType",new WhUserType());
		return "WhUserTypeRegister"; 
	}
	
	@PostMapping("/save")
	public String saveWhUserType(@ModelAttribute WhUserType whUserType,Model model) throws UnsupportedEncodingException
	{
		Integer id=service.saveWhUserType(whUserType);
		String message="WhUser Saved with id :" +id ;
		String cc[]=new String []{"sehwagrohith@gmail.com","manjarib05@gmail.com"};
		if(id!=null && id>0)
		{
			//emailUtil.sendEmail(whUserType.getUserEmail(), "USER ACCESS GRANTED","Hello User"+whUserType.getUserCode());
			emailUtil.sendEmail(whUserType.getUserEmail(), "USER ACCESS GRANTED","Hello User"+whUserType.getUserCode(), cc, null, null);
		}
		model.addAttribute("message", message);
		model.addAttribute("whUserType", new WhUserType());
		return "WhUserTypeRegister";
	}
	
	@GetMapping("/all")
	public String getAllWhUsers(Model  model)
	{
		List<WhUserType>list=service.getAllWhUserTypes();
		model.addAttribute("list",list);
		return "WhUserTypeData";
	}
	
	@GetMapping("/delete")
	public String deleteWhUserType(@RequestParam Integer id,Model model)
	{
		service.deleteWhUserType(id);
		model.addAttribute("message", "WhUserType '"+id+"' deleted");
		model.addAttribute("list", service.getAllWhUserTypes());
		return "WhUserTypeData";
	}
	
	@GetMapping("/edit")
	public String showEdit(@RequestParam Integer id,Model model)
	{
		WhUserType whUserType = service.getOneWhUserType(id);
		model.addAttribute("whUserType", whUserType);
		return "WhUserTypeEdit";
	}

	@PostMapping("/update")
	public String updateWhUserType(
			@ModelAttribute WhUserType whUserType,
			Model model ) 
	{
		service.updateWhUserType(whUserType);
		model.addAttribute("message", "WhUserType '"+whUserType.getId()+"' Updated");
		model.addAttribute("list", service.getAllWhUserTypes());
		return "WhUserTypeData";
	}

	//7. AJAX CALL MAIL VALIDATION
	@GetMapping("/checkMail")
	public @ResponseBody String validateEmailExist(@RequestParam String mail) 
	{
		String msg="";
		if(service.isWhUserMailIdExist(mail)) {
			msg=mail + ", <b>already exist</b>";
		}
		return msg;
	}
}
