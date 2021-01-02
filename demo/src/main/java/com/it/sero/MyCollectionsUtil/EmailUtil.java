package com.it.sero.MyCollectionsUtil;


import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.it.sero.model.User;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public boolean sendEmail(String to,String subject,String text,
			String cc[],String bcc[],MultipartFile file) throws UnsupportedEncodingException
	{
		boolean flag=false;
		
		MimeMessage message=sender.createMimeMessage();
		try {
			MimeMessageHelper helper=new MimeMessageHelper(message,true);// file!=null);
			helper.setFrom("testsero22@gmail.com", "SERO");
			helper.setTo(to);
			if(cc!=null)
				helper.setCc(cc);
			if(bcc!=null)
				helper.setCc(bcc);
			helper.setText(text,true);
			
			helper.setSubject(subject);
			/*
			 * if(file!=null) { helper.addAttachment(file.getOriginalFilename(), file); }
			 */
			
			ClassPathResource resource=new ClassPathResource("/static/images/logo3.png");
			helper.addInline("logoimage", resource);
			sender.send(message);
			flag=true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		
		return flag;
	}
	public boolean sendEmail(
			String to,String subject,String text) throws UnsupportedEncodingException 
	{
		return sendEmail(to, subject, text,null,null,null);
	}
	
	public void sendEmail(User user,String password) throws MessagingException, UnsupportedEncodingException
	{
		Context context=new Context();
		context.setVariable("user", user);
		String process=templateEngine.process("WelcomeMail", context);
		MimeMessage message=sender.createMimeMessage();
		try {
			MimeMessageHelper helper=new MimeMessageHelper(message);
			helper.setFrom("testsero22@gmail.com", "SERO");
			helper.setTo(user.getEmail());
			helper.setText(process, true);
			helper.setSubject("USER REGISTERED SUCCESFULLY !!! ");
			sender.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	
}
