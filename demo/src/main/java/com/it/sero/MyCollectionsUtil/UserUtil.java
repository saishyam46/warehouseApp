package com.it.sero.MyCollectionsUtil;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {
	
	public String genNewPwd()
	{
		return UUID.randomUUID().toString().replaceAll("-","").substring(0, 5);
	}
	
	public String genOtp()
	{
		return String.format("%04d",new Random().nextInt(10000));
	}
	

}
