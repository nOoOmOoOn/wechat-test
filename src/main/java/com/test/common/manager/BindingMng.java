package com.test.common.manager;

import java.util.Date;

import com.test.common.entity.Binding;

public interface BindingMng {

	void add(long qrcodeId, String openId, Date createtime);
	
	Binding get(Long id);
		
	Binding getByOpenId(String openId);
	
}
