package com.test.common.entity;

import javax.persistence.Entity;

import com.test.common.entity.base.BaseQrcode;

@Entity
public class Qrcode extends BaseQrcode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4511298448323785696L;

	public Qrcode(){}

	public Qrcode(long accountId, String ticket, String sign) {
		super(accountId, ticket, sign);
	}
	
	public void init(){
		
	}

	
}
