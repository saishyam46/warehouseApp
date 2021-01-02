package com.it.sero.setup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.it.sero.model.User;
import com.it.sero.service.IUserService;

@Component
public class SetupAdmin implements CommandLineRunner {

	@Autowired
	private IUserService service;

	@Override
	public void run(String... args) throws Exception {
		User user=new User();
		user.setName("sero");
		user.setEmail("sero@gmail.com");
		user.setPwd("sero");
		user.setActive(true);
		user.setRoles(List.of("ADMIN","APPUSER"));
		if(service.findByEmail(user.getEmail()).isEmpty())
		{
			service.saveUser(user);
		}
		
	}

}
