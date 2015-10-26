package com.test.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.test.common.dao.AccountDao;
import com.test.common.entity.Account;

@Repository
public class AccountDaoImpl extends JdbcTemplateBaseDao implements AccountDao{

	@Override
	protected Class<?> getEntityClass() {
		return Account.class;
	}
	
	@Override
	public long add(Account account) {
		return super.add(account);
	}

	@Override
	public Account get(Long id) {
		return super.queryForObject(id);
	}

	@Override
	public Account getByName(String name) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Account where 1=1");
		if(sqlBuilder.ifNotNull(name)){
			sqlBuilder.andEqualTo("name", name);
		}
		return queryForObject(sqlBuilder);
	}
	
	@Override
	public Account getByToken(String token) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Account where 1=1");
		if(sqlBuilder.ifNotNull(token)){
			sqlBuilder.andEqualTo("token", token);
		}
		return queryForObject(sqlBuilder);
	}

}
