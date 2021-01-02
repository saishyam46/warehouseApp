package com.it.sero.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.it.sero.model.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	
	@Modifying
	@Query("UPDATE com.it.sero.model.User SET active=:active where id=:id")	
	public void updateStatus(Integer id, boolean active);
	
	@Modifying
	@Query("UPDATE com.it.sero.model.User SET pwd=:encodedPwd where id=:id")	
	public void updatePwd(Integer id, String encodedPwd);
	
	@Modifying
	@Query("UPDATE com.it.sero.model.User SET otp=:genOtp where id=:id")
	public void updateNewOtpById(String genOtp, Integer id);
	
	Optional<User> findByEmail(String email);
	
	/*
	 * @Query(value = "{call GET_ACTIVE_USERS(:param1)}", nativeQuery = true) public
	 * List<Map<String, Object>> getActiveUsers(@Param("v_id")int param1);
	 */

}
