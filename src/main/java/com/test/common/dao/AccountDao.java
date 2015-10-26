package com.test.common.dao;

import com.test.common.entity.Account;

public interface AccountDao {

	long add(Account account);
	
	Account get(Long id);
	
	Account getByName(String name);
	
	Account getByToken(String token);
}
