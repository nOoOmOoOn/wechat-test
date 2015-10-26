package com.test.common.entity;

import javax.persistence.Entity;

import com.test.common.entity.base.BaseAccount;

@Entity
public class Account extends BaseAccount{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3314577703748570684L;

	public Account(){}

	public Account(String name, String appId, String secretKey, String token) {
		super(name, appId, secretKey, token);
	}
	
	public void init(){
		
	}

	
}
