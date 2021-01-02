package com.it.sero.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="users_tab")
public class User {

	@Id
	@GeneratedValue(generator="user_id_seq")
	@SequenceGenerator(name="user_id_seq",sequenceName="user_seq")
	@Column(name="user_id")
	private  Integer id;
	
	@Column(name="user_name")
	private String name;
	
	@Column(name="user_email")
	private String email;
	
	@Column(name="user_pwd")
	private String pwd;
	
	@Column(name="user_otp")
	private String otp;

	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="roles_tab",
			joinColumns = @JoinColumn(name="user_id"))
	@Column(name="user_role")
	private List<String> roles;
	
	@Column(name="user_active")
	private boolean active;
}
