package com.it.sero.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.sero.model.User;
import com.it.sero.repo.UserRepo;
import com.it.sero.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService{
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Override
	public Integer saveUser(User user) {
		
		/*
		 * String pwd=pwdEncoder.encode(user.getPwd()); user.setPwd(pwd);
		 */
		return repo.save(user).getId();
	}

	@Override
	public List<User> getAllUsers() {
		
		return repo.findAll();
	}

	@Transactional
	public void modifyStatus(Integer id, boolean active) {
		repo.updateStatus(id, active);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User>userObj=repo.findByEmail(username);
		if(userObj.isEmpty() || !userObj.get().isActive())
		{
			throw new UsernameNotFoundException("UserName does not exist!!");
		}
		else
		{
			User user=userObj.get();
			return new org.springframework.security.core.userdetails.User(
					username,
					user.getPwd(),
					user.getRoles().stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
		}
		
	}

	@Transactional
	public void updatePwd(Integer id, String pwd) {
		repo.updatePwd(id, pwdEncoder.encode(pwd));		
	}

	@Override
	public Optional<User> findByEmail(String name) {
		
		return repo.findByEmail(name);
	}

	@Override
	public User getOneUserById(Integer uid) {		
		return repo.getOne(uid);
	}

	@Transactional
	public void updateNewOtpById(String genOtp, Integer id) {
		repo.updateNewOtpById(genOtp, id);
	}

}
