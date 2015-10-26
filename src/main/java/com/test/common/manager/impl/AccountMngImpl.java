package com.test.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.test.common.dao.AccountDao;
import com.test.common.entity.Account;
import com.test.common.manager.AccountMng;

@Transactional(isolation = Isolation.REPEATABLE_READ)
@Service
public class AccountMngImpl implements AccountMng {

	@Override
	public void add(String name, String appId, String secretKey, String token) {
		Account account = new Account(name, appId, secretKey, token);	
		dao.add(account);
	}
	
	@Override
	public Account get(Long id) {
		return dao.get(id);
	}

	@Override
	public Account getByName(String name) {
		return dao.getByName(name);
	}
	
	@Override
	public Account getByToken(String token) {
		return dao.getByToken(token);
	}
	
	
	
	@Autowired
	private AccountDao dao;

}
