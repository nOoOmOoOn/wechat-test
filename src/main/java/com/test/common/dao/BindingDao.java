package com.test.common.dao;

import com.test.common.entity.Binding;

public interface BindingDao {

	long add(Binding binding);
	
	Binding get(Long id);
	
	Binding getByOpenId(String openId);
	
}
