package com.test.common.manager;

import com.test.common.entity.Account;

public interface AccountMng {

	void add(String name, String appId, String secretKey, String token);
	
	Account get(Long id);
		
	Account getByName(String name);
	
	Account getByToken(String token);
	
}
