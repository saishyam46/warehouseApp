package com.it.sero.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.sero.MyCollectionsUtil.EmailUtil;
import com.it.sero.MyCollectionsUtil.UserUtil;
import com.it.sero.model.User;
import com.it.sero.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private UserUtil userUtil;		
	
	@GetMapping("/showLogin")
	public String showLogin()
	{
		return "UserLogin";
	}
	
	@GetMapping("/reg")
	public String showReg()
	{
		return "UserRegister";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute User user,Model model) throws MessagingException, UnsupportedEncodingException
	{
		String password=user.getPwd();
		String otp=userUtil.genOtp();
		user.setOtp(otp);
		Integer id=service.saveUser(user);
		
		
		if(id!=null)
		{
			String text="<p><b>Welcome "+user.getName()+" ,</b></p>";
			text+="<p><b>You have been succesfully registered to SERO APP !!</p>";
			text+="\n";
			text+="<p><b>User Name :</b> "+user.getEmail().toString()+" </p>";
			text+="<p><b>Password  :</b> "+password+" </p>";
			text+="<p><b>Roles  :</b> "+user.getRoles()+" </p>";	
			text+="<p><b>Activation OTP  :</b> "+otp+" </p>";	
			text+= "<br><img src='cid:logoimage'/>";
			//emailUtil.sendEmail(user,password);
			emailUtil.sendEmail(user.getEmail(),"USER REGISTERED SUCCESFULLY !!", text);
				
		}	
		model.addAttribute("msg","user " + user.getName() + " saved with id "+ id);
		service.updatePwd(id, user.getPwd());
		return "UserRegister";		
	}
	
	@GetMapping("/all")
	public String showAllUsers(Model model)
	{
		List<User>users=service.getAllUsers();
		model.addAttribute("list",users);
		return "UserData";
	}	
	
	@GetMapping("/activate")
	public String activateUser(@RequestParam Integer uid,Model model,Principal p) throws UnsupportedEncodingException
	{
		service.modifyStatus(uid,true);
		User user=service.getOneUserById(uid);
		String currentUserEmail=user.getName();
		String emailtext="<p><b>Hello "+user.getName()+" ,</b></p>";
		emailtext+="<p><b>You have been activated by Admin "+p.getName() +" !!! </b></p>";
		emailtext+="<p><b>Enjoy your services at Sero APP!!</b></p>";
		emailtext+= "<br><img src='cid:logoimage'/>";
		emailUtil.sendEmail(user.getEmail(), "USER ACTIVATION SUCCESSFULL", emailtext);
		model.addAttribute("message", uid + "activated");
		return "redirect:all";
	}


	
	@GetMapping("/inactivate")
	public String inactivateUser(@RequestParam Integer uid,Model model)
	{
		service.modifyStatus(uid,false);
		model.addAttribute("message", uid + "In activated");
		return "redirect:all";
	}
	
	@GetMapping("/setup")
	public String loginSetup(Principal p,HttpSession session)
	{
		Optional<User>userObj=service.findByEmail(p.getName());
		session.setAttribute("userobj",userObj.get());
		return "redirect:../uom/all";
	}
	
	@GetMapping("/profile")
	public String showProfile(HttpSession ses,Model model) {
		User user = (User) ses.getAttribute("userobj");
		model.addAttribute("user", user);
		return "UserProfile";
	}
	
	@GetMapping("/modifyPwd")
	public String showModifyPwd()
	{
		return "UserPwd";
		
	}
	
	@PostMapping("/pwdUpdate")
	public String UpdatePwd(
			@RequestParam String pwd1,
			@RequestParam String pwd2,
			HttpSession sess
			)
	{
		if(pwd1.equals(pwd2))
		{
			User user=(User)sess.getAttribute("userobj");
			service.updatePwd(user.getId(), pwd1);
		}
		return "redirect:profile";
		
	}
	
	@GetMapping("/showForgotPwd")
	public String ForgotPwd()
	{
		return "UserForgotPwd";
	}
	
	@PostMapping("/newPwdGen")
	public String NewPwdGen(@RequestParam String uemail,Model model) throws UnsupportedEncodingException
	{
		Optional<User>opt=service.findByEmail(uemail);
		User user=opt.get();
		String pwd=userUtil.genNewPwd();		
		service.updatePwd(user.getId(), pwd);
		String emailtext="<p><b>Hello "+user.getName()+" ,</b></p>";
		emailtext+="<p><b>Your new password is : "+pwd +" !!! </b></p>";
		emailtext+="<p><b>Enjoy your services at Sero APP!!</b></p>";
		emailtext+= "<br><img src='cid:logoimage'/>";
		emailUtil.sendEmail(user.getEmail(), "NEW PASSWORD GENERATED", emailtext);
		model.addAttribute("message","New Password genarted and sent via mail");
		return "UserLogin";
	}
	
	@GetMapping("/showactivateUserotp")
	public String activateUserOtp()
	{
		
		return "UserActivationByOtp";
	}
	
	@PostMapping("/activatebyOtp")
	public String activatebyOtp(@RequestParam String uemail,@RequestParam String otp,@RequestParam String opr,Model model) throws UnsupportedEncodingException
	{
		String message=null;
		Optional<User> opt = service.findByEmail(uemail);
		if(opt.isEmpty()) {
			message = "Invail Email Id Entered!!";
		}
		else
		{
		User user=opt.get();
		if(opr.equalsIgnoreCase("ACTIVATE"))
		{
			if(user.getOtp().equals(opt))
			{
				service.modifyStatus(user.getId(), true);
				message="User " + user.getName() +" Activated!! Please Login now!!";
			}
			else
			{
				message="Invalid OTP Entered!!";
			}
		}
		if(opr.equalsIgnoreCase("RESEND"))
		{
			String newOtp=userUtil.genOtp();
			service.updateNewOtpById(newOtp,user.getId());
			String emailtext="<p><b>Hello "+user.getName()+" ,</b></p>";
			emailtext+="<p><b>Your new OTP is : "+newOtp +" !!! </b></p>";
			emailtext+="<p><b>Enjoy your services at Sero APP!!</b></p>";
			emailtext+= "<br><img src='cid:logoimage'/>";		
			emailUtil.sendEmail(user.getEmail(), "NEW OTP FOR ACTIVATION ", emailtext);			 
			message = "NEW OTP SENT TO EMAIL PLEASE CHECK IT";

		}
		}
		model.addAttribute("message",message);
		return "UserActivationByOtp";
	}
}
