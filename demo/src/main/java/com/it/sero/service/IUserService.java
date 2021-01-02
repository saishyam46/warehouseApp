package com.it.sero.service;

import java.util.List;
import java.util.Optional;

import com.it.sero.model.User;

public interface IUserService {
	
	public Integer saveUser(User user);
	public List<User>getAllUsers();
	public void modifyStatus(Integer id,boolean active);
	
	public void updatePwd(Integer id,String pwd);
	public Optional<User> findByEmail(String name);
	public User getOneUserById(Integer uid);
	public void updateNewOtpById(String genOtp, Integer id);

}
