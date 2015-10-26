package com.test.common.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.test.common.entity.base.BaseBinding;

@Entity
public class Binding extends BaseBinding{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4826116668882382533L;

	public Binding(){}

	public Binding(long qrcodeId, String openId, Date createtime) {
		super(qrcodeId, openId, createtime);
	}
	
	public void init(){
		
	}

	
}
